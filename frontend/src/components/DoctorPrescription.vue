<template>
    <NavigationBar />

    <div id="doctorPrescription">
        <div id="doctor">
            <div>
                <h3>Le médecin</h3>
            </div>
            <div class="information">
                <div class="column">
                    <p id="doctorName">Docteur DUBOIS Charline</p>
                    <p class="indications">NOM Prénom</p>
                </div>

                <div class="column">
                    <p id="doctorJob">Médecin généraliste</p>
                    <p class="indications">Qualification</p>
                </div>

                <div class="column">
                    <p id="RPPSNum">10100169461</p>
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
                        maxlength="3" onchange="if (this.value > 150 || this.value < 0 ) {this.value = ''; alert('La valeur saisie n\'est pas valable');}">
                    <p class="indications" style="margin-bottom:2vh;">Âge (ans)</p>
                    <select id="sexe" name="sexe">
                        <option value="" disabled selected hidden></option>
                        <option value="Homme">Homme</option>
                        <option value="Femme">Femme</option>
                        <option value="Autre">Autre</option>
                    </select>
                    <p class="indications">Sexe</p>
                </div>

                <div class="column">
                    <input id="patientWeight" type="number" min="1" max="1000" name=""
                        onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
                        oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                        maxlength="3" onchange="if (this.value > 1000 || this.value < 1) {this.value = ''; alert('La valeur saisie n\'est pas valable');}">
                    <p class="indications" style="margin-bottom:2vh;">Poids (kg)</p>
                    <input id="patientHeight" type="number" min="1" max="300" name=""
                        onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
                        oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                        maxlength="3"  onchange="if (this.value > 300 || this.value < 1) {this.value = ''; alert('La valeur saisie n\'est pas valable');}">
                    <p class="indications">Taille (cm)</p>
                </div>
            </div>
        </div>
        <div id="consultation">
            <h3>La consultation</h3>
            <div class="information">
                <div class="column">
                    <p id="prescriptionDate"></p>
                    <p class="indications">Date de l'ordonnance (yyyy-MM-ddThh:mm)</p>
                </div>

                <div class="column">
                    <p id="addressPrescription">1, rue de la République</p>
                    <p class="indications">Adresse du cabinet</p>
                </div>

                <div class="column">
                    <p id="consultationPhoneNumber">0454234454</p>
                    <p class="indications">Tel. cabinet</p>
                </div>
            </div>
        </div>
        <div id="prescription">
            <h3>La prescription</h3>

            <div class="column">
                <input id="validityPrescriptionDays" type="number" name="" value="90">
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
        <button class="ordonnance" @click="verifyValidity">Générer l'ordonnance</button>
    </div>
</template>


<script>
import Medicament from './Medicament.vue';
import NavigationBar from './NavigationBar.vue';
import { addPrescription } from '@/utils/web3Utils';
import Swal from 'sweetalert2'
import Web3 from "web3";
const web3 = new Web3();
export default {
    name: 'DoctorPrescriptionComponent',
    components: {
        NavigationBar,
        Medicament
    },
    data() {
        return {
            medicines: []
        };
    },
    methods: {
        mounted() {
            import("pdfjs-dist/build/pdf.min").then((pdfjsLib) => {
                pdfjsLib.GlobalWorkerOptions.workerSrc =
                    "https://cdn.jsdelivr.net/npm/pdfjs-dist@2.7.570/build/pdf.worker.min.js";
                pdfjsLib.getDocument("/orddd.pdf").promise.then(function (pdf) {
                    pdf.getPage(1).then((page) => {
                        console.log(page);
                    });
                });
            });

        },
        deleteMedicine(index) {
            this.medicines.splice(index, 1);
        },
        addMedicine() {
            this.medicines.push({

            });
        },
        disconnect() {
            location.href = '/';
        },
        async verifyValidity() 
        {
            const table = document.querySelector("table");

            if (table.rows.length == 1) {
                incorrectPrescription = Boolean(true);
                alert("Au moins un médicament ou acte doit être renseigné");
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

            if (!incorrectPrescription) 
            {
                console.log("!incorrectPrescription");

                const doctorName = document.getElementById("doctorName").textContent;
                const doctorJob = document.getElementById("doctorJob").textContent;
                const RPPSNum = document.getElementById("RPPSNum").textContent;
                const patientName = document.getElementById("patientName").value;
                const patientFirstName = document.getElementById("patientFirstName").value;
                const patientAge = document.getElementById("patientAge").value;
                const patientSexe = document.getElementById("sexe").value;
                const patientWeight = document.getElementById("patientWeight").value;
                const patientHeight = document.getElementById("patientHeight").value;
                const prescriptionDate = document.getElementById("prescriptionDate").textContent;
                const addressPrescription = document.getElementById("addressPrescription").textContent;
                const consultationPhoneNumber = document.getElementById("consultationPhoneNumber").textContent;
                const validityPrescriptionDays = document.getElementById("validityPrescriptionDays").value;

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
                    "validityPrescriptionDays":validityPrescriptionDays,
                    "prescriptions": rowsMedicamentsActs                    
                };

                let JSONString = JSON.stringify(jsonPdf);

                const prescriptionHash = web3.utils.sha3(JSONString);
                var daysValid = document.getElementById("validityPrescriptionDays").value

                try {
                    const txReceipt = await addPrescription(prescriptionHash, daysValid);

                    if (txReceipt.success) {
                        Swal.fire({
                            icon: 'success',
                            title: 'Success',
                            text: 'The prescription is registered succesfully in the block chain'
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: txReceipt.message
                        })
                    }
                } catch (error) {
                    console.error('Error while calling addPrescription:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Something when wrong when trying to call the blockchain. Try again later'
                    })
                    return;
                }

                let queryString = 'jsonPdf=' + encodeURIComponent(JSONString);

                let url = 'http://localhost:9000/generate-pdf?' + queryString;

                fetch(url, {
                    method: 'GET'
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Erreur lors de la récupération du PDF');
                        }
                        return response.blob();
                    })
                    .then(blob => {
                        const url = URL.createObjectURL(blob);
                        window.open(url);
                    })
                    .catch(error => console.error(error));
            }
        },
        props: {},
    }
}

    document.addEventListener("DOMContentLoaded", function() 
    {       
        const n = new Date();
        const y = n.getFullYear();
        const m = n.getMonth() + 1;
        const d = n.getDate();
        const h = n.getHours();
        const min = n.getMinutes();
        //document.getElementById("prescriptionDate").innerHTML = ('0' + d).slice(-2) + "/" + ('0' + m).slice(-2) + "/" + y + " " + ('0' + h).slice(-2) + ":" + ('0' + min).slice(-2);
        document.getElementById("prescriptionDate").innerHTML  = y+'-'+('0' + m).slice(-2)+'-'+('0' + d).slice(-2)+'T'+('0' + h).slice(-2)+':'+('0' + min).slice(-2);

       /* // Récupérer l'URL courante
        var url = window.location.href;

        // Diviser l'URL en deux parties : avant et après le "?"
        var parts = url.split("?");

        // Vérifier s'il y a un paramètre dans l'URL
        if (parts.length > 1) {
            var doctorId = parts[1];
        }*/

       // Récupération du cookie
        const cookies = document.cookie.split(';');

        // Recherche du cookie "monCookie"
        let idValue = null;
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.startsWith('id=')) {
                idValue = cookie.substring('id='.length, cookie.length);
                break;
            }
        }
        console.log(idValue);

        //Recuperer les donnes du docteur
        fetch('http://localhost:9000/Doctor-api/' + idValue) 
            .then(response => response.json())
            .then(data => {
            // récupérer les informations de l'objet Doctor à partir de la réponse JSON
            const firstName = data.firstName;
            const lastName = data.lastName;
            const telephone = data.telephone;
            const qualification = data.qualification;
            const officeAddress = data.officeAddress;
            const idPSdoctor = data.idPSdoctor;

            // remplir les champs de formulaire avec les informations récupérées
            document.getElementById('doctorName').innerHTML = "Docteur " + firstName + " " + lastName;
            document.getElementById('consultationPhoneNumber').innerHTML = telephone;
            document.getElementById('doctorJob').innerHTML = qualification;
            document.getElementById('addressPrescription').innerHTML = officeAddress;
            document.getElementById('RPPSNum').innerHTML = idPSdoctor;
            })
            .catch(error => {
            console.error('Erreur lors de la récupération des informations de l\'objet Doctor:', error);
        });
    });
</script>


<style>
td:nth-child(5) {
    width: 10%;
}

.red-border {
    border-color: red;
}

blue-border {
    border-color: #1817BA;
}

.logo {
    display: inline-block;
    vertical-align: top;
    width: 200px;
    /*4vw*/
    margin-left: 5vh;
    margin-bottom: 10px;
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
    margin-left: 10vh;
    margin-top: 5vw;

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

.buttonTable {
    border: none;
    background-color: transparent;
}

.buttonTable img {
    width: 2vw;
}
</style>