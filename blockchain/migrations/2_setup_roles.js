const MedicalPrescription = artifacts.require("MedicalPrescription");

module.exports = async function(deployer, network, accounts) {
    const medicalPrescription = await MedicalPrescription.deployed();

    const admin = accounts[0];
    const doctor = accounts[1];
    const pharmacist = accounts[2];

    // Calculate role hashes
    const DOCTOR_ROLE = web3.utils.keccak256("DOCTOR_ROLE");
    const PHARMACIST_ROLE = web3.utils.keccak256("PHARMACIST_ROLE");

    console.log("Setting up roles...");
    await medicalPrescription.grantRole(DOCTOR_ROLE, doctor, { from: admin });
    await medicalPrescription.grantRole(PHARMACIST_ROLE, pharmacist, { from: admin });
    console.log("Roles set up successfully.");
};