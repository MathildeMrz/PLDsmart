pragma solidity ^0.8.0;

import "@openzeppelin/contracts/access/AccessControl.sol";
import "@openzeppelin/contracts/utils/math/SafeMath.sol";

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
    mapping(address => bytes32[]) private doctorPrescriptions;

    event PrescriptionAdded(address indexed doctor, bytes32 indexed prescriptionHash, uint256 expirationDate);
    event PrescriptionRevoked(address indexed doctor, bytes32 indexed prescriptionHash, string reason);
    event PrescriptionDelivered(address indexed pharmacist, bytes32 indexed prescriptionHash, uint256 deliveredAt);
    event PrescriptionExpired(bytes32 indexed prescriptionHash);

    constructor() {
        _setupRole(DEFAULT_ADMIN_ROLE, msg.sender);
    }

    // Modifier to check prescription expiration
    modifier checkPrescription(bytes32 prescriptionHash) {
        Prescription storage p = prescriptions[prescriptionHash];
        require(p.doctor != address(0), "Prescription does not exist");
        if (p.status == PrescriptionStatus.Valid && block.timestamp > p.expirationDate) {
            p.status = PrescriptionStatus.Expired;
            emit PrescriptionExpired(prescriptionHash);
        }
        require(p.status != PrescriptionStatus.Expired, "Prescription has expired");
        require(p.status != PrescriptionStatus.Delivered, "Prescription has already been delivered");
        require(p.status != PrescriptionStatus.Revoked, "Prescription has been revoked");
        _;
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
        require(prescriptions[prescriptionHash].doctor == address(0), "Prescription with this hash already exists");
        prescriptions[prescriptionHash] = Prescription({
            doctor: msg.sender,
            status: PrescriptionStatus.Valid,
            expirationDate: block.timestamp.add(daysValid.mul(1 days))
        });
        doctorPrescriptions[msg.sender].push(prescriptionHash);
        emit PrescriptionAdded(msg.sender, prescriptionHash, prescriptions[prescriptionHash].expirationDate);
    }

    function verifyPrescription(bytes32 prescriptionHash) public view onlyRole(PHARMACIST_ROLE) returns (bool) {
        Prescription memory p = prescriptions[prescriptionHash];
        if (p.status == PrescriptionStatus.Valid && block.timestamp > p.expirationDate) {
            return false;
        }
        return p.status == PrescriptionStatus.Valid && block.timestamp <= p.expirationDate;
    }

    function deliverPrescription(bytes32 prescriptionHash) public onlyRole(PHARMACIST_ROLE) checkPrescription(prescriptionHash) {
        Prescription storage p = prescriptions[prescriptionHash];
        p.status = PrescriptionStatus.Delivered;
        emit PrescriptionDelivered(msg.sender, prescriptionHash, block.timestamp);
    }

    function revokePrescription(bytes32 prescriptionHash, string memory reason) public onlyRole(DOCTOR_ROLE) checkPrescription(prescriptionHash) {
        Prescription storage p = prescriptions[prescriptionHash];
        require(p.doctor == msg.sender, "Only the doctor who created the prescription can revoke it");
        p.status = PrescriptionStatus.Revoked;
        emit PrescriptionRevoked(msg.sender, prescriptionHash, reason);
    }

    function getDoctorPrescriptions(address doctor) public view onlyRole(DOCTOR_ROLE) returns (bytes32[] memory) {
        require(doctor == msg.sender, "Only a doctor can access their own prescription list");
        return doctorPrescriptions[doctor];
    }

    function getPrescriptionDetails(bytes32 prescriptionHash) public view returns (address, PrescriptionStatus, uint256) {
        require(hasRole(DOCTOR_ROLE, msg.sender) || hasRole(PHARMACIST_ROLE, msg.sender), "Caller must have Doctor or Pharmacist role");
        Prescription storage prescription = prescriptions[prescriptionHash];
        require(prescription.doctor != address(0), "Prescription does not exist");
        return (prescription.doctor, prescription.status, prescription.expirationDate);
    }
}