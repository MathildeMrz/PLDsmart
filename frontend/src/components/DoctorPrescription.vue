<template>
    <NavigationBar/>

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
                    <input id="patientName" type="text" name=""  required>
                    <p class="indications" style="margin-bottom:2vh;">NOM *</p>
                    <input id="patientFirstName" type="text" name="" required>
                    <p class="indications">Prénom *</p>
                </div>

                <div class="column">
                    <input id="patientAge" type="number" min="0" max="150" name="">
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
                    <input id="patientWeight" type="number" min="0" max="1000" name="">
                    <p class="indications" style="margin-bottom:2vh;">Poids (kg)</p>
                    <input id="patientHeight" type="number" min="0" max="300" name="">
                    <p class="indications">Taille (cm)</p>
                </div>
            </div>
        </div>
        <div id="consultation">
            <h3>La consultation</h3>
            <div class="information">
                <div class="column">
                    <p id="prescriptionDate"></p>
                    <p class="indications">Date de l'ordonnance (JJ/MM/AAAA HH:MM)</p>
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

            <div class="column" >
            <input id="validityPrescriptionDays" type="text" name="" value="90">
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
                <Medicament v-for="(index) in medicines" :key="index" :index="index" @delete="deleteMedicine"/>
            </tbody>
            </table>
            <button class="buttonTable" type="submit" @click="addMedicine">
                <img src="../assets/plus.png" alt="button add prescription" />
            </button>
        </div>
        <button id="generatePdfButton" class="ordonnance" @click="verifyValidity">Générer l'ordonnance</button>
            <input type="file" id="myFile" name="filename">
            <input id="submitImageToOCR" type="submit" style="margin-left: 200px;">
    </div>
</template>


<script>
    //import { read } from 'xlsx';
    import Medicament from './Medicament.vue';
    import NavigationBar from './NavigationBar.vue';

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
            deleteMedicine(index) 
            {
                this.medicines.splice(index, 1);
            },
            addMedicine() 
            {
                this.medicines.push({

                });
            },
            disconnect() 
            {
                location.href = '/';
            },
            verifyValidity()
            {
               const table = document.querySelector("table");

                    if(table.rows.length == 1)
                    {
                        incorrectPrescription = Boolean(true);
                        alert("Au moins un médicament ou acte doit être renseigné");
                        this.addMedicine();
                    }
                
                    var incorrectPrescription = Boolean(false);
                    const inputs = document.querySelectorAll("input");
                    
                    for (let i = 0; i < inputs.length; i++) 
                    {
                        const input = inputs[i];
                        input.classList.remove("red-border");
                        if (! (input.id == "patientAge" || input.id == "patientHeight" ||  input.id == "patientWeight" ||
                            input.id == "refundable" || input.id == "indication") && input.value === '') 
                        {
                            incorrectPrescription = Boolean(true);
                            input.classList.add("red-border");
                        }
                    }
                    
                    if(!incorrectPrescription)
                    {
                        const doctorName = document.getElementById("doctorName").textContent;
                        const doctorJob = document.getElementById("doctorJob").textContent;
                        const RPPSNum = document.getElementById("RPPSNum").textContent;
                        const patientName = document.getElementById("patientName").value;
                        const patientFirstName = document.getElementById("patientFirstName").value;
                        const patientAge = document.getElementById("patientAge").value;
                        const patientWeight = document.getElementById("patientWeight").value;
                        const patientHeight = document.getElementById("patientHeight").value;
                        const prescriptionDate = document.getElementById("prescriptionDate").textContent;
                        const addressPrescription = document.getElementById("addressPrescription").textContent;
                        const consultationPhoneNumber = document.getElementById("consultationPhoneNumber").textContent;

                        const table = document.querySelector("table");
                        const rows = table.querySelectorAll("tbody tr");

                        const rowsMedicamentsActs = [];

                        rows.forEach(row => {
                            const medicineAct = row.querySelector("#medicineAct").value;
                            const posology = row.querySelector("#posology").value;
                            const treatmentPeriod = row.querySelector("#treatmentPeriod").value;
                            const renewal = row.querySelector("#renewal").value;
                            const refundable = row.querySelector("#refundable").checked;
                            const indication = row.querySelector("#indication").value;

                            const rowMedicamentAct = {
                                "medicineAct": medicineAct,
                                "posology": posology,
                                "treatmentPeriod": treatmentPeriod,
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
                            "patientWeight": patientWeight,
                            "patientHeight": patientHeight,
                            "prescriptionDate": prescriptionDate,
                            "addressPrescription": addressPrescription,
                            "consultationPhoneNumber": consultationPhoneNumber,
                            "prescriptions": rowsMedicamentsActs 
                        };
                            
                        let queryString = 'jsonPdf=' + encodeURIComponent(JSON.stringify(jsonPdf));

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
                
            }
        },
        props: {},
    }
  
    document.addEventListener("DOMContentLoaded", function() 
    {
        //Listener generate pdf button
        
        const n = new Date();
        const y = n.getFullYear();
        const m = n.getMonth() + 1;
        const d = n.getDate();
        const h = n.getHours();
        const min = n.getMinutes();
        document.getElementById("prescriptionDate").innerHTML = ('0' + d).slice(-2) + "/" + ('0' + m).slice(-2) + "/" + y + " " + ('0' + h).slice(-2) + ":" + ('0' + min).slice(-2);

        const button = document.getElementById("submitImageToOCR");
        button.addEventListener("click", function() 
        {
            const input = document.getElementById("myFile");
            const file = input.files[0];
            const type = file.type;

            /* Contrôler le type */
            if(type == "application/pdf")
            {
                console.log("PDF détecté");
                /* Transformer pdf en png */
                

            }

            else
            {
                 const reader = new FileReader();
                reader.onload = function(event) {
                const byteArray = new Uint8Array(event.target.result);

                const url = 'http://localhost:9000/OCR-api';
                fetch(url, {
                    method: 'POST',
                    body: byteArray.buffer, // encode le tableau de bytes en ArrayBuffer
                })
                .then(response => {
                    console.log(response);
                })
                .catch(error => {
                    console.error(error);
                });
                };
                reader.readAsArrayBuffer(file);

            }


           


            //const byteArray = reader.readAsArrayBuffer(file);
            /*const xhr = new XMLHttpRequest();
            xhr.open('POST', 'http://localhost:9000/OCR-api');
            xhr.setRequestHeader('Content-Type', 'application/octet-stream');
            xhr.setRequestHeader('Content-Disposition', `attachment; filename=${file.name}`);
            xhr.send(byteArray);*/
            
            //const formData = new FormData();
            /*formData.append('file', file);
            let url = 'http://localhost:9000/OCR-api';
            fetch(url, {
                method: 'POST',
                body: bytesArray
            })
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.error(error);
            });*/

           
        
        });
    });

</script>


<style>
    td:nth-child(5){
        width : 10%;
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
        width: 200px; /*4vw*/
        margin-left: 5vh;
        margin-bottom: 10px;
    }

    header img {
    float: left;
    width:4vw;
    }

    input
    {
        border: none;
        border-bottom: 2px solid #1817BA;
        width:20vh;
    }

    textarea
    {
        border: none;
        border-bottom: 2px solid #1817BA;
        width:20vh;
    }

    select
    {
        border: none;
        border-bottom: 2px solid #1817BA;
        width:20vh;
    }

    #doctorPrescription
    {
        margin-left:10vh;
        margin-top: 5vw;
        
    }
    .indications
    {
        color : #A3A3A3;
        font-size: 1.5vh;
    }

    #doctor #patient
    {
        display:flex;
        flex-direction: column;
    }

    .information
    {
        display:flex;
        justify-content: space-around;
        margin-left:5vh;
    }
    .column
    {
        display:flex;
        flex-direction: column;
        width:33%;
    }

    .column p
    {
        margin-top:0;
        margin-bottom: 0;
    }
    .my-table {
    border-collapse: collapse; 
    font-size: 16px; 
    text-align: center; 
    width : 95%;
    margin-top: 3vh;
    
    }

    .my-table th, .my-table td {
    border: 1px solid; 
    padding: 8px;
    }

    .my-table td {
    background-color: rgba(24,23,186,0.23); 
    color:white;

    }

    .my-table th {
    background-color: rgba(24,23,186,0.63); 
    color:white;

    }

    .ordonnance {
        display: block;
        margin: auto;
        margin-top: 5vh;
        padding: 15px 25px 15px 25px;
        background: rgba(24, 23, 186, 0.46);
        border: none;
        color:white;
        font-variant: small-caps;
        font-size: 20px;
        cursor: pointer;

    }

    .buttonTable {
        border:none;
        background-color:transparent;
    }

    .buttonTable img {
        width:2vw;
    }

</style>