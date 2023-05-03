pragma solidity ^0.8.0;
import "../node_modules/@openzeppelin/contracts/access/AccessControl.sol";

contract MedicalPrescription is AccessControl {
    // Define roles using constants
    bytes32 public constant DOCTOR_ROLE = keccak256("DOCTOR_ROLE");
    bytes32 public constant PHARMACIST_ROLE = keccak256("PHARMACIST_ROLE");

    struct Prescription {
        address doctor;
        bool toDeliver;
        uint256 expirationDate;
    }

    mapping(bytes32 => Prescription) private prescriptions;

    event PrescriptionAdded(address indexed doctor, bytes32 indexed prescriptionHash);

    constructor() {
        _setupRole(DEFAULT_ADMIN_ROLE, msg.sender);
    }

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

    function addPrescription(bytes32 prescriptionHash, uint256 daysValid) public onlyRole(DOCTOR_ROLE) {
        prescriptions[prescriptionHash] = Prescription({
            doctor: msg.sender,
            toDeliver: true,
            expirationDate: block.timestamp + daysValid * 1 days
        });
        emit PrescriptionAdded(msg.sender, prescriptionHash);
    }

    function verifyPrescription(bytes32 prescriptionHash) public view onlyRole(PHARMACIST_ROLE) returns (bool) {
        Prescription memory p = prescriptions[prescriptionHash];
        return p.toDeliver && (block.timestamp <= p.expirationDate);
    }

    function deliverPrescription(bytes32 prescriptionHash) public onlyRole(PHARMACIST_ROLE) {
        Prescription storage p = prescriptions[prescriptionHash];
        require(p.doctor != address(0), "Prescription does not exist");
        require(p.toDeliver, "Prescription has already been delivered");
        require(block.timestamp <= p.expirationDate, "Prescription has expired");
        p.toDeliver = false;
    }
}
