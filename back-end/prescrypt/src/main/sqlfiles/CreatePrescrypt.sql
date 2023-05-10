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
    sex boolean,
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
    medicationPrescriptionId bigint NOT NULL
);

CREATE TABLE prescription
(
    prescriptionId bigint NOT NULL,
    doctorId bigint,
    patientId bigint,
    dateConsultation VARCHAR(100),
    medicationPrescriptionId bigint,
    nbRenewals int,
    NR boolean,
    notes VARCHAR(1000),
    PRIMARY KEY (prescriptionId),
    FOREIGN KEY (doctorId) REFERENCES doctor(doctorId),
    FOREIGN KEY (patientId) REFERENCES patient(patientId)
);

ALTER TABLE medicationPrescription
ADD    medicationId bigint REFERENCES medication(medicationId)
;

ALTER TABLE medicationPrescription
ADD    prescriptionId bigint
;

----------------------------- INSERT INTO TABLES
INSERT INTO users VALUES (1, 'Malo', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (2, 'Koko', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (3, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

INSERT INTO doctor VALUES (1, 'OLIVIER', 'Malo', 111, 'str', 'str', 'str', '0xstr');
INSERT INTO pharmacist VALUES (2, 'AIMÉ', 'Korantin');
INSERT INTO patient VALUES (10, 'DEPARDIEU', 'Gérard', 60, 100, TRUE, 190);

INSERT INTO medication VALUES (12, 'paracetamol', 10, 'str', 'PT20S');
INSERT INTO medicationPrescription VALUES (20, 12, 11);
INSERT INTO prescription VALUES (11, 1, 10, '1977-04-22T06:00:00Z', 20, 3, FALSE, 'str');