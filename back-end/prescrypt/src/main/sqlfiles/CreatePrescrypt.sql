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
    telephone VARCHAR(100),
    address VARCHAR(100),
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
INSERT INTO users VALUES (4, 'Arthur', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (5, 'Fatma', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (6, 'Mathilde', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (7, 'Yi', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (8, 'Chloé', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (9, 'Juliana', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (10, 'Stéphane', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');
INSERT INTO users VALUES (11, 'Lionel', 'f4f263e439cf40925e6a412387a9472a6773c2580212a4fb50d224d3a817de17');

INSERT INTO doctor VALUES (1, 'OLIVIER', 'Malo', 111, 'str', 'str', 'str', '0xstr');
INSERT INTO doctor VALUES (4, 'UNGRIA', 'Arthur', 112, 'Neurochirurgien nucléaire', '5 avenue des Maréchaussées Paris 75001', '0601456118', '0xstr');
INSERT INTO doctor VALUES (5, 'LARIBI', 'Fatma', 113, 'Psychologue astrologue', '11 rue des Parisiens Massy Pal', '0601020304', '0xstr');
INSERT INTO doctor VALUES (6, 'MARZA', 'Mathilde', 114, 'Physicien théoricien', '25 impasse des arbres Lyon', '0703020104', '0xstr');
INSERT INTO doctor VALUES (7, 'ZHANG', 'Yi', 115, 'Médecin cosmonaute', '2 rue des russes Moscou', '0101010103', '0xstr');
INSERT INTO pharmacist VALUES (2, 'AIMÉ', 'Korantin', '0601020304', 'str');
INSERT INTO pharmacist VALUES (8, 'VIGNERON', 'Chloé', '0603030302', '42 rue des physiciens Villeurbanne');
INSERT INTO pharmacist VALUES (9, 'MARTINS', 'Juliana', '0302010605', '1 rue de la police Marseille');
INSERT INTO pharmacist VALUES (10, 'BRES', 'Stéphane', '0609090807', '90 avenue des beaux gars Washington D.C.');
INSERT INTO pharmacist VALUES (11, 'BRUNIE', 'Lionel', '0601456120', '12 Grove Street Los Angeles');
INSERT INTO patient VALUES (10, 'DEPARDIEU', 'Gérard', 60, 100, TRUE, 190);

INSERT INTO medication VALUES (12, 'paracetamol', 10, 'str', 'PT20S');
INSERT INTO medicationPrescription VALUES (20, 12, 11);
INSERT INTO prescription VALUES (11, 1, 10, '1977-04-22T06:00:00Z', 20, 3, FALSE, 'str');