pragma solidity ^0.8.0;

import "../node_modules/@openzeppelin/contracts/access/AccessControl.sol";
import "../node_modules/@openzeppelin/contracts/utils/math/SafeMath.sol";

contract MedicalPrescription is AccessControl {
    using SafeMath for uint256;

    // Define roles using constants
    bytes32 public constant DOCTOR_ROLE = keccak256("DOCTOR_ROLE");
    bytes32 public constant PHARMACIST_ROLE = keccak256("PHARMACIST_ROLE");

    enum PrescriptionStatus { Valid, Delivered, Revoked, Expired }

    struct Prescription {
        address doctor;
        PrescriptionStatus status;
        uint256 expirationDate;
    }

    mapping(bytes32 => Prescription) private prescriptions;

    event PrescriptionAdded(address indexed doctor, bytes32 indexed prescriptionHash);
    event PrescriptionRevoked(address indexed doctor, bytes32 indexed prescriptionHash);
    event PrescriptionDelivered(address indexed pharmacist, bytes32 indexed prescriptionHash);

    constructor() {
        _setupRole(DEFAULT_ADMIN_ROLE, msg.sender);
    }

    // Role management functions
    function grantDoctorRole(address doctor) public onlyRole(DEFAULT_ADMIN_ROLE) {
        grantRole(DOCTOR_ROLE, doctor);
    }

    function revokeDoctorRole(address doctor) public onlyRole(DEFAULT_ADMIN_ROLE) {
        revokeRole(DOCTOR_ROLE, doctor);
    }

    function grantPharmacistRole(address pharmacist) public onlyRole(DEFAULT_ADMIN_ROLE) {
        grantRole(PHARMACIST_ROLE, pharmacist);
    }

    function revokePharmacistRole(address pharmacist) public onlyRole(DEFAULT_ADMIN_ROLE) {
        revokeRole(PHARMACIST_ROLE, pharmacist);
    }

    // Prescription management functions
    function addPrescription(bytes32 prescriptionHash, uint256 daysValid) public onlyRole(DOCTOR_ROLE) {
        require(prescriptions[prescriptionHash].doctor == address(0), "Prescription with this hash already exist");
        prescriptions[prescriptionHash] = Prescription({
            doctor: msg.sender,
            status: PrescriptionStatus.Valid,
            expirationDate: block.timestamp.add(daysValid.mul(1 days))
        });
        emit PrescriptionAdded(msg.sender, prescriptionHash);
    }

    function verifyPrescription(bytes32 prescriptionHash) public view onlyRole(PHARMACIST_ROLE) returns (bool) {
        Prescription memory p = prescriptions[prescriptionHash];
        return p.status == PrescriptionStatus.Valid && (block.timestamp <= p.expirationDate);
    }

    function deliverPrescription(bytes32 prescriptionHash) public onlyRole(PHARMACIST_ROLE) {
        Prescription storage p = prescriptions[prescriptionHash];
        require(p.doctor != address(0), "Prescription does not exist");
        require(p.status == PrescriptionStatus.Valid, "Prescription is not valid");
        require(block.timestamp <= p.expirationDate, "Prescription has expired");
        p.status = PrescriptionStatus.Delivered;
        emit PrescriptionDelivered(msg.sender, prescriptionHash);
    }

    function revokePrescription(bytes32 prescriptionHash) public onlyRole(DOCTOR_ROLE) {
        Prescription storage p = prescriptions[prescriptionHash];
        require(p.doctor != address(0), "Prescription does not exist");
        require(p.doctor == msg.sender, "Only the doctor who created the prescription can revoke it");
        require(p.status == PrescriptionStatus.Valid, "Prescription is not valid or has already been delivered");
        p.status = PrescriptionStatus.Revoked;
        emit PrescriptionRevoked(msg.sender, prescriptionHash);
    }
}