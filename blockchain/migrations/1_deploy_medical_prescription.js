var MedicalPrescription = artifacts.require("MedicalPrescription");

module.exports = function(deployer) {
    deployer.deploy(MedicalPrescription);
};