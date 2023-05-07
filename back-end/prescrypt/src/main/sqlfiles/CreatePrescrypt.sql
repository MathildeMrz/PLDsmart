CREATE TABLE users
(
    userId bigint NOT NULL,
    username VARCHAR(100),
    password VARCHAR(100),
    PRIMARY KEY (userId)
);

CREATE TABLE patient
(
    patientId bigint NOT NULL,
    lastName VARCHAR(100),
    firstName VARCHAR(100),
    age int,
    weight int,
    sex bit,
    height int,
    PRIMARY KEY (patientId)
);

CREATE TABLE doctor
(
    doctorId bigint NOT NULL,
    lastName VARCHAR(100),
    firstName VARCHAR(100),
    idPSdoctor bigint,
    qualification VARCHAR(100),
    address VARCHAR(100),
    telephone VARCHAR(100),
    ethAddress VARCHAR(100),
    PRIMARY KEY (doctorId),
    FOREIGN KEY (doctorId) REFERENCES users(userId)
);

CREATE TABLE pharmacist
(
    pharmacistId bigint NOT NULL,
    lastName VARCHAR(100),
    firstName VARCHAR(100),
    PRIMARY KEY (pharmacistId),
    FOREIGN KEY (pharmacistId) REFERENCES users(userId)
);

CREATE TABLE medication
(
  medicationId bigint NOT NULL,
  name VARCHAR(100),
  dosage int,
  instructions VARCHAR(1000),
  duration VARCHAR(100),
  PRIMARY KEY (medicationId)
);

CREATE TABLE medicationPrescription
(
    medicationPrescriptionId bigint NOT NULL,
    PRIMARY KEY (medicationPrescriptionId)
);

CREATE TABLE prescription
(
    prescriptionId bigint NOT NULL,
    doctorId bigint,
    patientId bigint,
    dateConsultation VARCHAR(100),
    medicationPrescriptionId bigint,
    nbRenewals int,
    NR bit,
    notes VARCHAR(1000),
    PRIMARY KEY (prescriptionId),
    FOREIGN KEY (doctorId) REFERENCES doctor(doctorId),
    FOREIGN KEY (patientId) REFERENCES patient(patientId),
    FOREIGN KEY (medicationPrescriptionId) REFERENCES medicationPrescription(medicationPrescriptionId)
);

ALTER TABLE medicationPrescription
ADD    medicationId bigint REFERENCES medication(medicationId)
;

ALTER TABLE medicationPrescription
ADD    prescriptionId bigint
;