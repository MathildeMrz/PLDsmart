<template>
    <nav  class="navigation-bar">
        <img class="logo" src="../assets/entete.png">
    </nav>

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
                    <p id="prescriptionDate">02/05/2023</p>
                    <p class="indications">Date de l'ordonnance (JJ/MM/AAAA)</p>
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
            <tbody>
                <Medicament/>
            </tbody>
            </table>
            <button class="buttonTable" type="submit">
                <img src="../assets/plus.png" alt="button add prescription" />
            </button>
        </div>
        <button id="generatePdfButton" class="ordonnance">Générer l'ordonnance</button>
    </div>
</template>


<script>
    import Medicament from './Medicament.vue';

    export default {
        name: 'DoctorPrescriptionComponent',
        components: {
            Medicament
        },
        props: {}
    }

    document.addEventListener("DOMContentLoaded", function() 
    {
        //Ajout médicaments liste déroulante
        const data = require('../assets/medicine.json');
        const medicineSelect = document.getElementById("medicineActDatalist");
        let result = [];
        for(let i=0; i<data.length; i++) {
            let concat = data[i].CODE_UCD.toString() + " " + data[i].NOM_COURT;
            result.push(concat);
            //Ajout liste déroulante
            let option = document.createElement("option");
            option.value = data[i].NOM_COURT;
            option.text = concat;
            medicineSelect.appendChild(option);
        }

        //Listener generate pdf button
        var button = document.getElementById("generatePdfButton");
        button.addEventListener("click", function() {

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
        });
    });

</script>


<style scoped>
.navigation-bar {
    width: 100%;  /* i'm assuming full width */
    height: 80px; /* change it to desired width */
    border-bottom: 1px solid black;
    
}

td:nth-child(5){
    width : 10%;
}

.logo {
    display: inline-block;
    vertical-align: top;
    width: 200px; /*4vw*/
    margin-left: 5vh;
    margin-top: 10px;
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

</style>