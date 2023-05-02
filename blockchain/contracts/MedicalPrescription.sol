pragma solidity ^0.8.0;

contract MedicalPrescription {

    address private owner;
    mapping(address => bool) private doctors;
    mapping(address => bool) private pharmacists;
    mapping(bytes32 => bool) private prescriptions;

    event PrescriptionAdded(address indexed doctor, bytes32 indexed prescriptionHash);
    event PrescriptionVerified(address indexed pharmacist, bytes32 indexed prescriptionHash);

    modifier onlyOwner() {
        require(msg.sender == owner, "Caller is not the owner");
        _;
    }

    modifier onlyDoctor() {
        require(doctors[msg.sender], "Caller is not a doctor");
        _;
    }

    modifier onlyPharmacist() {
        require(pharmacists[msg.sender], "Caller is not a pharmacist");
        _;
    }

    constructor() {
        owner = msg.sender;
    }

    function addDoctor(address doctor) public onlyOwner {
        doctors[doctor] = true;
    }

    function removeDoctor(address doctor) public onlyOwner {
        doctors[doctor] = false;
    }

    function addPharmacist(address pharmacist) public onlyOwner {
        pharmacists[pharmacist] = true;
    }

    function removePharmacist(address pharmacist) public onlyOwner {
        pharmacists[pharmacist] = false;
    }

    function addPrescription(bytes32 prescriptionHash) public onlyDoctor {
        prescriptions[prescriptionHash] = true;
        emit PrescriptionAdded(msg.sender, prescriptionHash);
    }

    function verifyPrescription(bytes32 prescriptionHash) public view onlyPharmacist returns (bool) {
        return prescriptions[prescriptionHash];
    }

    function isDoctor(address doctor) public view returns (bool) {
        return doctors[doctor];
    }

    function isPharmacist(address pharmacist) public view returns (bool) {
        return pharmacists[pharmacist];
    }
}
