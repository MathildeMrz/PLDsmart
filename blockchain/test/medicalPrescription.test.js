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

    it("Test not deliver expired prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const prescriptionHash = web3.utils.sha3("prescription2");
        const daysValid = 1; // Set expiration to the past
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        // Increase time by more than a day to make the prescription expire
        await time.increase(time.duration.days(1).add(time.duration.seconds(1)));

        await expectRevert(
            medicalPrescription.deliverPrescription(prescriptionHash, { from: pharmacist }),
            "Prescription has expired"
        );
    });

    it("Test doctor revokes a prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });
        await medicalPrescription.grantPharmacistRole(pharmacist, { from: admin });
        const daysValid = 7;
        await medicalPrescription.addPrescription(prescriptionHash, daysValid, { from: doctor });

        await medicalPrescription.revokePrescription(prescriptionHash, { from: doctor });

        const isValid = await medicalPrescription.verifyPrescription.call(prescriptionHash, {
            from: pharmacist,
        });

        assert.isFalse(isValid, "The revoked prescription should not be valid");
    });

    it("Test revoking a non-existent prescription", async() => {
        await medicalPrescription.grantDoctorRole(doctor, { from: admin });

        const nonExistentPrescriptionHash = web3.utils.sha3("non-existent-prescription");

        await expectRevert(
            medicalPrescription.revokePrescription(nonExistentPrescriptionHash, { from: doctor }),
            "Prescription does not exist"
        );
    });
});