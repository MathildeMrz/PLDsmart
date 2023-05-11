<template>
    <NavigationBar />

    <div id="doctorPrescription">
        <div id="import-prescription">
            <img id="import-prescription-img" src="../assets/importPrescription.png" alt="">
            <div id="import-prescription-text" @click="handleFileImport">
                <h3>Importer une ordonnance</h3>
            </div>
            <div><img id="image-preview"></div>
        </div>
        <div id="doctor">
            <div>
                <h3>Le médecin</h3>
            </div>
            <div class="information">
                <div class="column">
                    <input id="doctorName" type="text" min="0" max="150" name="" />
                    <p class="indications">NOM Prénom</p>
                </div>

                <div class="column">
                    <input id="doctorJob" type="text" min="0" max="150" name="" />
                    <p class="indications">Qualification</p>
                </div>

                <div class="column">
                    <input id="RPPSNum" type="number" min="0" max="150" name="" />
                    <p class="indications">Numéro RPPS</p>
                </div>
            </div>
        </div>

        <div id="patient">
            <h3>Le patient</h3>
            <div class="prescription-box information">
                <div class="column">
                    <input id="patientName" type="text" name="" required>
                    <p class="indications" style="margin-bottom:2vh;">NOM *</p>
                    <input id="patientFirstName" type="text" name="" required>
                    <p class="indications">Prénom *</p>
                </div>

                <div class="column">
                    <input id="patientAge" type="number" min="0" max="150" name=""
                        onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
                        oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                        maxlength="3">
                    <p class="indications" style="margin-bottom:2vh;">Âge (ans)</p>
                    <select id="patientSexe" name="sexe">
                        <option value="" disabled selected hidden></option>
                        <option value="Homme">Homme</option>
                        <option value="Femme">Femme</option>
                        <option value="Autre">Autre</option>
                    </select>
                    <p class="indications">Sexe</p>
                </div>

                <div class="column">
                    <input id="patientWeight" type="number" min="0" max="1000" name=""
                        onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
                        oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                        maxlength="3">
                    <p class="indications" style="margin-bottom:2vh;">Poids (kg)</p>
                    <input id="patientHeight" type="number" min="0" max="300" name=""
                        onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
                        oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                        maxlength="3">
                    <p class="indications">Taille (cm)</p>
                </div>
            </div>
        </div>
        <div id="consultation">
            <h3>La consultation</h3>
            <div class="information">
                <div class="column">
                    <input id="prescriptionDate" min="0" max="150" name="" />
                    <p class="indications">Date de l'ordonnance (yyyy-MM-dd hh:mm)</p>
                </div>

                <div class="column">
                    <input id="addressPrescription" type="text" min="0" max="150" name="" />
                    <p class="indications">Adresse du cabinet</p>
                </div>

                <div class="column">
                    <input id="consultationPhoneNumber" type="text" name="" />
                    <p class="indications">Tel. cabinet</p>
                </div>
            </div>
        </div>
        <div id="prescription">
            <h3>La prescription</h3>

            <div class="column">
                <input id="validityPrescriptionDays" type="text" name="" placeholder="90">
                <p class="indications">La validité de l'ordonnance (jours)</p>
            </div>

            <table id="tablePrescription" class="my-table">
                <thead>
                    <tr>
                        <th>Médicament / acte médical *</th>
                        <th>Posologie *</th>
                        <th>Durée du traitement *</th>
                        <th>Renouvellement *</th>
                        <th>NR</th>
                        <th>Indications</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <tbody id="drugList">
                    <Medicament v-for="(index) in medicines" :key="index" :index="index" @delete="deleteMedicine" />
                </tbody>
            </table>
            <button class="buttonTable" type="submit" @click="addMedicine">
                <img src="../assets/plus.png" alt="button add prescription" />
            </button>
        </div>
        <button id="verifyPrescButton" class="ordonnance" @click="verifyValidity">Vérifier l'ordonnance</button>
    </div>
</template>


<script>
import Medicament from './Medicament.vue';
import NavigationBar from './NavigationBar.vue';
import { deliverPrescription } from '@/utils/web3Utils'
import Swal from 'sweetalert2'
import Web3 from "web3";
const web3 = new Web3();

export default {
    name: 'PharmacistPrescription',
    components:
    {
        NavigationBar,
        Medicament
    },
    data() {
        return {
            medicines: []
        };
    },
    methods:
    {

        fillFormAfterOCR(jsonData) {
            const doctorName = jsonData["NomDocteur"];
            const doctorJob = jsonData["Qualification"];
            const RPPSNum = jsonData["RPPS"];
            const patientName = jsonData["NomPatient"];
            const patientFirstName = jsonData["PrenomPatient"];
            const patientAge = jsonData["Age"];
            const patientSexe = jsonData["Sexe"];
            const patientWeight = jsonData["Poids"];
            const patientHeight = jsonData["Taille"];
            const prescriptionDate = jsonData["Date"];
            const addressPrescription = jsonData["Adresse"];
            const consultationPhoneNumber = jsonData["Tel"];
            const validityPrescriptionDays = jsonData["Validite"];

            document.getElementById("doctorName").value = doctorName;
            document.getElementById("doctorJob").value = doctorJob;
            document.getElementById("RPPSNum").value = RPPSNum;
            document.getElementById("patientName").value = patientName;
            document.getElementById("patientFirstName").value = patientFirstName;
            document.getElementById("patientAge").value = patientAge;
            document.getElementById("patientSexe").value = patientSexe;
            document.getElementById("patientWeight").value = patientWeight;
            document.getElementById("patientHeight").value = patientHeight;
            document.getElementById("prescriptionDate").value = prescriptionDate;
            document.getElementById("addressPrescription").value = addressPrescription;
            document.getElementById("consultationPhoneNumber").value = consultationPhoneNumber;
            document.getElementById("validityPrescriptionDays").value = validityPrescriptionDays;

            //Médicaments
            const self = this;
            var index = 0;

            const medicaments = jsonData["Medicaments"];

            medicaments.forEach(function (medicament) {
                console.log("medicament : " + medicament + " index = " + index);
                const NomMedicament = medicament["NomMedicament"];
                const Posologie = medicament["Posologie"];
                const Periode = medicament["Periode"];
                const PeriodeTexte = medicament["PeriodeTexte"];
                const Renouvelable = medicament["Renouvelable"];
                const Remboursable = medicament["Remboursable"];
                const Indication = medicament["Indication"];

                self.addMedicine();

                setTimeout(function () {
                    document.querySelectorAll(".row")[index].querySelector("#medicineAct").value = NomMedicament;
                    document.querySelectorAll(".row")[index].querySelector("#posology").value = Posologie;
                    document.querySelectorAll(".row")[index].querySelector("#treatmentPeriod").value = Periode;
                    document.querySelectorAll(".row")[index].querySelector("#treatmentPeriodTexte").value = PeriodeTexte;
                    document.querySelectorAll(".row")[index].querySelector("#renewal").value = Renouvelable;

                    if (Remboursable == "Non") {
                        document.querySelectorAll(".row")[index].querySelector("#refundable").checked = true;
                    }
                    document.querySelectorAll(".row")[index].querySelector("#indication").value = Indication;
                    index = index + 1;
                }, 1000);

            });
        },
        handleOCR(input) {
            console.log("Ok dans handleOCR");
            var file = input.files[0];
            const type = file.type;
            console.log("typeeeeeee = " + type);

            if (type != "image/png" && type != "image/jpeg" && type != "image/pdf" &&
                type != "application/png" && type != "application/jpeg" && type != "application/pdf") {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops',
                    text: 'Les formats pris en compte pour l\'OCR sont : jpeg, pdf et png'
                })
            }
            else {
                /* Contrôler le type */
                if (type == "application/pdf") {
                    console.log("PDF détecté");
                    const reader = new FileReader();
                    reader.onload = function () {
                        import("pdfjs-dist/build/pdf.min.js").then((pdfjsLib) => {
                            /* Initialiser PDF.js */
                            pdfjsLib.GlobalWorkerOptions.workerSrc = "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.6.172/pdf.worker.min.js";

                            /* Charger le fichier PDF */
                            pdfjsLib.getDocument(reader.result).promise.then(function (pdf) {
                                console.log("PDF chargé avec succès !");
                                console.log("pdf : " + pdf);

                                console.log("Nombre de pages : " + pdf.numPages);

                                for (var pageNumber = 1; pageNumber <= pdf.numPages; pageNumber++) {
                                    pdf.getPage(pageNumber).then(function (page) {
                                        var canvas = document.createElement("canvas");
                                        var context = canvas.getContext("2d");
                                        var viewport = page.getViewport({ scale: 1.5 });
                                        canvas.width = viewport.width;
                                        canvas.height = viewport.height;

                                        var renderTask = page.render({
                                            canvasContext: context,
                                            viewport: viewport
                                        });

                                        renderTask.promise.then(function () {
                                            const imageData = canvas.toDataURL('image/png');
                                            // Récupérer l'élément <img> à partir de son ID
                                            const img = document.getElementById("image-preview");
                                            img.src = imageData;
                                            const self = this;

                                            fetch(imageData)
                                                .then(res => res.arrayBuffer())
                                                .then(buffer => {
                                                    /*Appel OCR*/

                                                    const blob = new Blob([buffer], { type: 'image/png' });
                                                    const reader = new FileReader();
                                                    reader.onload = function (event) {
                                                        const byteArray = new Uint8Array(event.target.result);
                                                        const url = 'http://localhost:9000/OCR-api';
                                                        fetch(url, {
                                                            method: 'POST',
                                                            body: byteArray.buffer, // encode le tableau de bytes en ArrayBuffer
                                                        })
                                                            .then(response => response.json())
                                                            .then(data => {
                                                                self.fillFormAfterOCR(data);
                                                            })
                                                            .catch(error => {
                                                                console.error(error);
                                                            });
                                                    };
                                                    reader.readAsArrayBuffer(blob);
                                                });
                                            console.log("Page " + pageNumber + " convertie en PNG : " + imageData);

                                        });
                                    });
                                }
                            }).catch(function (error) {
                                console.log("Erreur lors du chargement du PDF :", error);
                            });
                        }).catch(function (error) {
                            console.log("Erreur lors du chargement de PDF.js :", error);
                        });
                    };
                    reader.readAsArrayBuffer(file);
                }
                else {
                    /*Appel OCR*/
                    const self = this;
                    const reader = new FileReader();
                    reader.onload = function (event) {
                        const byteArray = new Uint8Array(event.target.result);

                        const url = 'http://localhost:9000/OCR-api';
                        fetch(url, {
                            method: 'POST',
                            body: byteArray.buffer, // encode le tableau de bytes en ArrayBuffer
                        })
                            .then(response => response.json())
                            .then(data => {
                                self.fillFormAfterOCR(data);
                            })
                            .catch(error => {
                                console.error(error);
                            });
                    };

                    reader.readAsArrayBuffer(file);
                }
            }

        },

        handleFileImport() {
            console.log("in the method handleFileImport!");
            document.querySelectorAll("input").forEach((input) => { input.value = ""; })
            var indication = document.querySelector("#indication");
            var joursMois = document.querySelector("#treatmentPeriodTexte");


            if (indication != null) {
                indication.value = "";
            }

            if (joursMois != null) {
                joursMois.value = "";
            }

            this.medicines = [];

            let input = document.createElement('input');
            input.type = 'file';
            input.multiple = false;
            input.onchange = () => {
                let files = Array.from(input.files);
                console.log(files);
                this.handleOCR(input);
            };
            input.click();

        },

        deleteMedicine(index) {
            this.medicines.splice(index, 1);
        },
        addMedicine() {
            this.medicines.push({});
        },
        async verifyValidity() {
            const table = document.querySelector("table");

            if (table.rows.length == 1) {
                incorrectPrescription = Boolean(true);
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Au moins un médicament ou acte doit être renseigné'
                })
                this.addMedicine();
            }

            var incorrectPrescription = Boolean(false);
            const inputs = document.querySelectorAll("input");

            for (let i = 0; i < inputs.length; i++) {
                const input = inputs[i];
                input.classList.remove("red-border");
                if (!(input.id == "patientAge" || input.id == "patientHeight" || input.id == "patientWeight" ||
                    input.id == "refundable" || input.id == "indication") && input.value === '') {
                    incorrectPrescription = Boolean(true);
                    input.classList.add("red-border");
                }
            }
            const selects = document.querySelectorAll("select");
            for (let i = 0; i < selects.length; i++) {
                const select = selects[i];
                select.classList.remove("red-border");
                if (!(select.id == "patientSexe") && select.value === '') {
                    incorrectPrescription = Boolean(true);
                    select.classList.add("red-border");
                }
            }

            if (incorrectPrescription) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Veuillez remplir tous les champs obligatoires.'
                })
            }

            if (!incorrectPrescription) {
                const doctorName = document.getElementById("doctorName").value;
                const doctorJob = document.getElementById("doctorJob").value;
                const RPPSNum = document.getElementById("RPPSNum").value;
                const patientName = document.getElementById("patientName").value;
                const patientFirstName = document.getElementById("patientFirstName").value;
                const patientAge = document.getElementById("patientAge").value;
                const patientSexe = document.getElementById("patientSexe").value;
                const patientWeight = document.getElementById("patientWeight").value;
                const patientHeight = document.getElementById("patientHeight").value;
                const date = new Date(document.getElementById("prescriptionDate").value);
                const validityPrescriptionDays = document.getElementById("validityPrescriptionDays").value;

                const y = date.getFullYear();
                const m = date.getMonth() + 1;
                const d = date.getDate();
                const h = date.getHours();
                const min = date.getMinutes();
                const prescriptionDate = y + '-' + ('0' + m).slice(-2) + '-' + ('0' + d).slice(-2) + 'T' + ('0' + h).slice(-2) + ':' + ('0' + min).slice(-2);
                const addressPrescription = document.getElementById("addressPrescription").value;
                const consultationPhoneNumber = document.getElementById("consultationPhoneNumber").value;

                const table = document.querySelector("table");
                const rows = table.querySelectorAll("tbody tr");

                const rowsMedicamentsActs = [];

                rows.forEach(row => {
                    const medicineAct = row.querySelector("#medicineAct").value;
                    const posology = row.querySelector("#posology").value;
                    const treatmentPeriod = row.querySelector("#treatmentPeriod").value;
                    const treatmentPeriodTexte = row.querySelector("#treatmentPeriodTexte").value;
                    const renewal = row.querySelector("#renewal").value;
                    const refundable = row.querySelector("#refundable").checked;
                    const indication = row.querySelector("#indication").value;

                    const rowMedicamentAct = {
                        "medicineAct": medicineAct,
                        "posology": posology,
                        "treatmentPeriod": treatmentPeriod,
                        "treatmentPeriodTexte": treatmentPeriodTexte,
                        "renewal": renewal,
                        "refundable": refundable,
                        "indication": indication
                    };

                    rowsMedicamentsActs.push(rowMedicamentAct);
                });

                let jsonPdf = {
                    "doctorName": doctorName,
                    "doctorJob": doctorJob,
                    "RPPSNum": RPPSNum,
                    "patientName": patientName,
                    "patientFirstName": patientFirstName,
                    "patientAge": patientAge,
                    "patientSexe": patientSexe,
                    "patientWeight": patientWeight,
                    "patientHeight": patientHeight,
                    "prescriptionDate": prescriptionDate,
                    "addressPrescription": addressPrescription,
                    "consultationPhoneNumber": consultationPhoneNumber,
                    "validityPrescriptionDays": validityPrescriptionDays,
                    "prescriptions": rowsMedicamentsActs
                };
                let JSONString = JSON.stringify(jsonPdf);

                const prescriptionHash = web3.utils.sha3(JSONString);
                console.log("Hash : " + prescriptionHash);

                try {
                    const txReceipt = await deliverPrescription(prescriptionHash);
                    if (txReceipt.success) {
                        Swal.fire({
                            icon: 'success',
                            title: 'Success',
                            text: 'L\'ordonnance est valide'
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: txReceipt.message
                        })
                    }
                }
                catch (error) {
                    console.error('Error while calling addPrescription:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'L\'ordonnance est invalide'
                    })
                    return;
                }
            }
        }
    },
    props: {}
};

</script>


<style>
#import-prescription {
    display: flex;
    align-items: center;
}

#import-prescription-text {
    font-size: 1.17em;
    font-weight: bold;
    text-decoration-line: underline;
    text-decoration-color: #1817BA;
    margin-left: 10px;
    cursor: pointer;
}

.red-border {
    border-color: red;
}

blue-border {
    border-color: #1817BA;
}

td:nth-child(5) {
    width: 10%;
}

header img {
    float: left;
    width: 4vw;
}

input {
    border: none;
    border-bottom: 2px solid #1817BA;
    width: 20vh;
}

textarea {
    border: none;
    border-bottom: 2px solid #1817BA;
    width: 20vh;
}

select {
    border: none;
    border-bottom: 2px solid #1817BA;
    width: 20vh;
}

#doctorPrescription {
    margin: 1vw 10vh 3vw 10vh;
}

.indications {
    color: #A3A3A3;
    font-size: 1.5vh;
}

#doctor #patient {
    display: flex;
    flex-direction: column;
}

.information {
    display: flex;
    justify-content: space-around;
    margin-left: 5vh;
}

.column {
    display: flex;
    flex-direction: column;
    width: 33%;
}

.column p {
    margin-top: 0;
    margin-bottom: 0;
}

.my-table {
    border-collapse: collapse;
    font-size: 16px;
    text-align: center;
    width: 95%;
    margin-top: 3vh;

}

.my-table th,
.my-table td {
    border: 1px solid;
    padding: 8px;
}

.my-table td {
    background-color: rgba(24, 23, 186, 0.23);
    color: white;

}

.my-table th {
    background-color: rgba(24, 23, 186, 0.63);
    color: white;

}

.ordonnance {
    display: block;
    margin: auto;
    margin-top: 5vh;
    padding: 15px 25px 15px 25px;
    background: rgba(24, 23, 186, 0.46);
    border: none;
    color: white;
    font-variant: small-caps;
    font-size: 20px;
    cursor: pointer;

}

input {
    border: none;
    border-bottom: 2px solid #1817BA;
    width: 20vh;
}

.buttonTable {
    border: none;
    background-color: transparent;
}

.buttonTable img {
    width: 2vw;
}

#image-preview {
    width: 8vw;
}
</style>