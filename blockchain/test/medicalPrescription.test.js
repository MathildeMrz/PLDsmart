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
        const tx = await medicalPrescription.verifyPrescription(prescriptionHash, { from: pharmacist });

        // Check for the PrescriptionVerified event in the logs
        const event = tx.logs.find(e => e.event === "PrescriptionVerified");
        assert.exists(event, "PrescriptionVerified event not emitted");

        // Check the event parameters
        assert.equal(event.args.pharmacist, pharmacist, "Incorrect pharmacist in event");
        assert.equal(event.args.prescriptionHash, prescriptionHash, "Incorrect prescriptionHash in event");
    });

    it("should not verify an invalid prescription", async() => {
        await medicalPrescription.addPharmacist(pharmacist, { from: owner });
        // Assuming an invalid prescription hash
        const invalidPrescriptionHash = web3.utils.sha3("invalid-prescription");

        // Attempt to verify the invalid prescription
        const tx = await medicalPrescription.verifyPrescription(invalidPrescriptionHash, { from: pharmacist });

        // Check for the absence of PrescriptionVerified event in the logs
        const event = tx.logs.find(e => e.event === "PrescriptionVerified");
        assert.notExists(event, "PrescriptionVerified event should not be emitted");

        // Check the return value
        const isValid = await medicalPrescription.verifyPrescription.call(invalidPrescriptionHash, { from: pharmacist });
        assert.isFalse(isValid, "Prescription should not be valid");
    });
});