const MedicalPrescription = artifacts.require("MedicalPrescription");

contract("MedicalPrescription", (accounts) => {
    const owner = accounts[0];
    const doctor = accounts[1];
    const pharmacist = accounts[2];
    const prescriptionHash = web3.utils.sha3("prescription data");

    let medicalPrescription;

    beforeEach(async() => {
        medicalPrescription = await MedicalPrescription.new();
    });

    it("should add and remove a doctor", async() => {
        await medicalPrescription.addDoctor(doctor, { from: owner });

        const isAddedDoctor = await medicalPrescription.isDoctor(doctor);
        assert.equal(isAddedDoctor, true, "Doctor should be added");

        await medicalPrescription.removeDoctor(doctor, { from: owner });

        const isRemovedDoctor = await medicalPrescription.isDoctor(doctor);
        assert.equal(isRemovedDoctor, false, "Doctor should be removed");
    });

    it("should add and verify a prescription", async() => {
        await medicalPrescription.addDoctor(doctor, { from: owner });
        await medicalPrescription.addPrescription(prescriptionHash, { from: doctor });

        await medicalPrescription.addPharmacist(pharmacist, { from: owner });

        // Verify the prescription
        const isValid = await medicalPrescription.verifyPrescription.call(prescriptionHash, { from: pharmacist });
        assert.isTrue(isValid, "Prescription should be valid");
    });

    it("should not verify an invalid prescription", async() => {
        await medicalPrescription.addPharmacist(pharmacist, { from: owner });
        // Assuming an invalid prescription hash
        const invalidPrescriptionHash = web3.utils.sha3("invalid-prescription");

        // Attempt to verify the invalid prescription
        const isValid = await medicalPrescription.verifyPrescription(invalidPrescriptionHash, { from: pharmacist });
        assert.isFalse(isValid, "Prescription should not be valid");
    });
});