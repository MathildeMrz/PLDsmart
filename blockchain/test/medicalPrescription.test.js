const MedicalPrescription = artifacts.require("MedicalPrescription");
const { expectEvent, expectRevert, time } = require('@openzeppelin/test-helpers');

contract("MedicalPrescription", (accounts) => {
    const [admin, doctor, doctor2, pharmacist, other] = accounts;
    const prescriptionHash = web3.utils.sha3("prescription data");

    let medicalPrescription;

    beforeEach(async() => {
        medicalPrescription = await MedicalPrescription.new();
    });

    it("Test grant/revoke doctor role", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });

        const DOCTOR_ROLE = await medicalPrescription.DOCTOR_ROLE();
        const isAddedDoctor = await medicalPrescription.hasRole(DOCTOR_ROLE, doctor);
        assert.equal(isAddedDoctor, true, "Doctor role should be granted");

        await medicalPrescription.revokeDoctorRole(doctor, { from: admin });

        const isRemovedDoctor = await medicalPrescription.hasRole(DOCTOR_ROLE, doctor);
        assert.equal(isRemovedDoctor, false, "Doctor role should be revoked");
    });

    it("Test doctor add a prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        const isValid = await medicalPrescription.verifyPrescription.call(prescriptionHash, {
            from: pharmacist,
        });
        assert.isTrue(isValid, "The prescription should be valid");
    });

    it("Test doctor add a prescription twice", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        const isValid = await medicalPrescription.verifyPrescription.call(prescriptionHash, {
            from: pharmacist,
        });
        assert.isTrue(isValid, "The prescription should be valid");

        await expectRevert(
            medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor }),
            "Prescription with this hash already exist"
        );
    });

    it("Test verify invalid prescription", async() => {
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        // Assuming an invalid prescription hash
        const invalidPrescriptionHash = web3.utils.sha3("invalid-prescription");

        // Attempt to verify the invalid prescription
        const isValid = await medicalPrescription.verifyPrescription(invalidPrescriptionHash, { from: pharmacist });
        assert.isFalse(isValid, "Prescription should not be valid");
    });

    it("Test pharmacist deliver a prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        await medicalPrescription.deliverPrescription(prescriptionHash, { from: pharmacist });

        const isValid = await medicalPrescription.verifyPrescription.call(prescriptionHash, {
            from: pharmacist,
        });

        assert.isFalse(isValid, "The prescription should not be valid after being delivered");
    });

    it("Test automatic expiration when delivering or revoking an expired prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const prescriptionHash = web3.utils.sha3("prescription3");
        const daysValid = 1; // Set expiration to the past
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        // Increase time by more than a day to make the prescription expire
        await time.increase(time.duration.days(1).add(time.duration.seconds(1)));

        await expectRevert(
            medicalPrescription.deliverPrescription(prescriptionHash, { from: pharmacist }),
            "Prescription has expired"
        );

        await expectRevert(
            medicalPrescription.revokePrescription(prescriptionHash, "Wrong prescription", { from: doctor }),
            "Prescription has expired"
        );
    });


    it("Test doctor revokes a prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        await medicalPrescription.revokePrescription(prescriptionHash, "Wrong prescription", { from: doctor });

        const isValid = await medicalPrescription.verifyPrescription.call(prescriptionHash, {
            from: pharmacist,
        });

        assert.isFalse(isValid, "The revoked prescription should not be valid");
    });

    it("Test revoking a non-existent prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });

        const nonExistentPrescriptionHash = web3.utils.sha3("non-existent-prescription");

        await expectRevert(
            medicalPrescription.revokePrescription(nonExistentPrescriptionHash, "Wrong prescription", { from: doctor }),
            "Prescription does not exist"
        );
    });

    it("Test get doctor's prescriptions", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        const doctorPrescriptions = await medicalPrescription.getDoctorPrescriptions(doctor, { from: doctor });
        assert.equal(doctorPrescriptions.length, 1, "Doctor should have 1 prescription");
        assert.equal(doctorPrescriptions[0], prescriptionHash, "Prescription hash should match");
    });

    it("Test get prescription details", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        const prescriptionDetails = await medicalPrescription.getPrescriptionDetails(prescriptionHash, { from: pharmacist });
        assert.equal(prescriptionDetails[0], doctor, "Doctor address should match");
        assert.equal(prescriptionDetails[1].toString(), "0", "Prescription status should be valid (0)");
        console.log(prescriptionDetails[2]);
        assert.isAtLeast(parseInt(prescriptionDetails[2]), parseInt(time.duration.days(7)), "Prescription expiration date should be valid");
    });
});