<template>
    <body>
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
                        <p>DUBOIS Charline</p>
                        <p class="indications">NOM Prénom</p>
                    </div>

                    <div class="column">
                        <p>Médecin généraliste</p>
                        <p class="indications">Qualification</p>
                    </div>

                    <div class="column">
                        <p>10100169461</p>
                        <p class="indications">Numéro RPPS</p>
                    </div>
                </div>
            </div>

            <div id="patient">
                <h3>Le patient</h3>
                <div class="prescription-box information">
                    <div class="column">
                        <input type="text" name=""  required>
                        <p class="indications" style="margin-bottom:2vh;">NOM *</p>
                        <input type="text" name="" required>
                        <p class="indications">Prénom *</p>
                    </div>

                    <div class="column">
                        <input type="number" min="0" max="150" name="">
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
                        <input type="number" min="0" max="1000" name="">
                        <p class="indications" style="margin-bottom:2vh;">Poids (kg)</p>
                        <input type="number" min="0" max="300" name="">
                        <p class="indications">Taille (cm)</p>
                    </div>
                </div>
            </div>
            <div id="consultation">
                <h3>La consultation</h3>
                <div class="information">
                    <div class="column">
                        <p>02/05/2023</p>
                        <p class="indications">Date de l'ordonnance (JJ/MM/AAAA)</p>
                    </div>

                    <div class="column">
                        <p>1, rue de la République</p>
                        <p class="indications">Adresse du cabinet</p>
                    </div>

                    <div class="column">
                        <p>0454234454</p>
                        <p class="indications">Tel. cabinet</p>
                    </div>
                </div>
            </div>
            <div id="prescription">
                <h3>La prescription</h3>
                <table class="my-table">
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
                    <tr>
                        <td>
                            <select id="medicineAct" name="medicamentActe">
                                <option value="" disabled selected hidden></option>
                                <option value="MedicamentActe1">MedicamentActe1</option>
                                <option value="MedicamentActe2">MedicamentActe2</option>
                                <option value="MedicamentActe3">MedicamentActe3</option>
                            </select>
                        </td>
                        <td>
                            <input id="posology" type="text" name="" value="30 mg" required>
                        </td>

                        <td>
                            <input id="treatmentPeriod" type="text" name="" value="1 mois" required>
                        </td>

                        <td>
                            <input id="renewal" type="number" name="" value="3" required>
                        </td>

                        <td>
                            <input id="refundable" type="checkbox" name="">
                        </td>

                        <td>
                            <textarea id="indication" rows="4" style="word-wrap: break-word;" name="">Prendre le traitement pendant le déjeuner</textarea>
                        </td>                    
                        
                        <td>
                        <button class="buttonTable" type="delete">
                            <img src="../assets/delete.png" alt="button delete prescription" />
                        </button>
                        </td>
                    </tr>
                </tbody>
                </table>
                <button class="buttonTable" type="submit">
                    <img src="../assets/plus.png" alt="button add prescription" />
                </button>
               
            </div>
                <button class="ordonnance"
                type="button">Générer l'ordonnance</button>
        </div>
    </body>
</template>

<script>
    export default {
    name: 'DoctorPrescription',
    props: {
    }
    }
</script>

<style scoped>

.navigation-bar {
    width: 100%;  /* i'm assuming full width */
    height: 80px; /* change it to desired width */
}
.logo {
    display: inline-block;
    vertical-align: top;
    width: 200px; /*4vw*/
    margin-left: 5vh;
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

.buttonTable
{
    border:none;
    background-color:transparent;
}

.buttonTable img
{
    width:2vw;
}
.ordonnance {
  display: block;
  margin: auto;
}

</style>