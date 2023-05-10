<template>
    <NavigationBar/>

    <div id="doctorPrescription">
        <div id="import-prescription">
            <img id="import-prescription-img" src="../assets/importPrescription.png" alt="">
            <div id="import-prescription-text" @click="handleFileImport"> <h3>Importer une ordonnance</h3></div>
            <div><img id="image-preview"></div>
        </div>
        <div id="doctor">
            <div>
                <h3>Le médecin</h3>
            </div>
            <div class="information">
                <div class="column">
                    <input id="doctorName" type="text" min="0" max="150" name=""/>
                    <p class="indications">NOM Prénom</p>
                </div>

                <div class="column">
                    <input id="doctorJob" type="text" min="0" max="150" name=""/>
                    <p class="indications">Qualification</p>
                </div>

                <div class="column">
                    <input id="RPPSNum" type="number" min="0" max="150" name=""/>
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
                    <input id="prescriptionDate" type="datetime-local" min="0" max="150" name=""/>
                    <p class="indications">Date de l'ordonnance (JJ/MM/AAAA HH:MM)</p>
                </div>

                <div class="column">
                    <input id="addressPrescription" type="text" min="0" max="150" name=""/>
                    <p class="indications">Adresse du cabinet</p>
                </div>

                <div class="column">
                    <input id="consultationPhoneNumber" type="number" min="0" max="9999999999" name=""/>
                    <p class="indications">Tel. cabinet</p>
                </div>
            </div>
        </div>
        <div id="prescription">
            <h3>La prescription</h3>

            <div class="column" >
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
                <Medicament v-for="(index) in medicines" :key="index" :index="index" @delete="deleteMedicine"/>
            </tbody>
            </table>
            <button class="buttonTable" type="submit" @click="addMedicine">
                <img src="../assets/plus.png" alt="button add prescription" />
            </button>
        </div>
        <button id="verifyPrescButton" class="ordonnance">Générer l'ordonnance</button>
    </div>
</template>


<script>
    import Medicament from './Medicament.vue';
    import NavigationBar from './NavigationBar.vue';

    export default 
    {
        name: 'PharmacistPrescription',
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
            handleOCR(input)
            {
                console.log("Ok dans handleOCR");
                var file = input.files[0];
                const type = file.type;
                console.log("typeeeeeee = "+type);

                if(type != "image/png" && type != "image/jpeg" && type != "image/pdf" && 
                    type != "application/png" && type != "application/jpeg" && type != "application/pdf" )
                {
                    alert("Les formats pris en compte pour l'OCR sont : jpeg, pdf et png");
                }
                else
                {
                    /* Contrôler le type */
                    if(type == "application/pdf")
                    {
                        console.log("PDF détecté");
                        const reader = new FileReader();
                        reader.onload = function () 
                        {
                            import("pdfjs-dist/build/pdf.min.js").then((pdfjsLib) => {
                                /* Initialiser PDF.js */
                                pdfjsLib.GlobalWorkerOptions.workerSrc = "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.6.172/pdf.worker.min.js";
                                
                                /* Charger le fichier PDF */
                                pdfjsLib.getDocument(reader.result).promise.then(function (pdf) {
                                    console.log("PDF chargé avec succès !");
                                    console.log("pdf : "+pdf);

                                    console.log("Nombre de pages : " + pdf.numPages);

                                    for (var pageNumber = 1; pageNumber <= pdf.numPages; pageNumber++) 
                                    {
                                        pdf.getPage(pageNumber).then(function(page) {
                                            var canvas = document.createElement("canvas");
                                            var context = canvas.getContext("2d");
                                            var viewport = page.getViewport({scale: 1.5});
                                            canvas.width = viewport.width;
                                            canvas.height = viewport.height;

                                            var renderTask = page.render({
                                                canvasContext: context,
                                                viewport: viewport
                                            });

                                            renderTask.promise.then(function() {
                                                const imageData = canvas.toDataURL('image/png');
                                                // Récupérer l'élément <img> à partir de son ID
                                                const img = document.getElementById("image-preview");

                                                // Définir la propriété "src" de l'élément <img> sur la valeur de imageData
                                                img.src = imageData;
                                                
                                                fetch(imageData)
                                                    .then(res => res.arrayBuffer())
                                                    .then(buffer => {
                                                        /*Appel OCR*/
                                                        const blob = new Blob([buffer], { type: 'image/png' });
                                                        const reader = new FileReader();
                                                        reader.onload = function(event) 
                                                        {
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
                                                        reader.readAsArrayBuffer(blob);
                                                });
                                                console.log("Page " + pageNumber + " convertie en PNG : " + imageData);

                                            });
                                        });
                                    }
                                }).catch(function(error) {
                                    console.log("Erreur lors du chargement du PDF :", error);
                                });
                            }).catch(function(error) {
                                console.log("Erreur lors du chargement de PDF.js :", error);
                            });
                        };
                        reader.readAsArrayBuffer(file);
                    }
                    else
                    {
                        /*Appel OCR*/
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
                }         
          
            },
            handleFileImport(){
                console.log("in the method handleFileImport!");
                let input = document.createElement('input');
                input.type = 'file';
                input.multiple = false;
                input.onchange = () => {
                    // you can use this method to get file and perform respective operations
                            let files =   Array.from(input.files);
                            console.log(files);
                            this.handleOCR(input);
                        };
                input.click();
                
            },
            deleteMedicine(index) {
                console.log("delete medicine of parent!!!!");
                this.medicines.splice(index, 1);
                },
            addMedicine() {
                this.medicines.push({
                });
                },
        },
        props: {},
    };
    
    document.addEventListener("DOMContentLoaded", function() 
    {
           
        //Listener generate pdf button
        var button = document.getElementById("verifyPrescButton");
        button.addEventListener("click", function() 
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
            // TODO: modify this to match the button
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
        });

    });

</script>


<style>
    #import-prescription{
        display: flex;
        align-items: center;
    }
    
    #import-prescription-text{
        font-size: 1.17em;
        font-weight: bold;
        text-decoration-line: underline;  
        text-decoration-color: #1817BA;  
        margin-left: 10px;
        cursor: pointer;


   }

    td:nth-child(5){
        width : 10%;
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
        margin: 1vw 10vh 3vw 10vh;
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

    input {
        border: none;
        border-bottom: 2px solid #1817BA;
        width:20vh;
    }

    .buttonTable {
        border:none;
        background-color:transparent;
    }

    .buttonTable img {
        width:2vw;
    }

    #image-preview
    {
        width:8vw;
    }

</style>