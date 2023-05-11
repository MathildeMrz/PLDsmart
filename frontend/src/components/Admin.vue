<template>
    <NavigationBar/>

    <div class="searchnav">
        <div class="toggle">
            <input type="radio" id="doctors" class="job" name="jobView" value="doctors" v-model="selectedOption"/>
            <label for="doctors">Médecins</label>
            <input type="radio" id="pharmacists" class="job" name="jobView" value="pharmacists" v-model="selectedOption"/>
            <label for="pharmacists">Pharmaciens</label>
        </div>

        <div class="searchbar">
            <input id="searchInput" type="text" placeholder="Rechercher un professionnel" v-model="searchedProfessional" @input="searchProfessionalByName" />
            <img src="../assets/search.png" id="searchIcon" alt="button search" class="buttonTable" />
        </div>
    </div>

    <table class="my-table" id="doctors-table">
        <thead>
            <tr>
                <th style="width: 9vw;">NOM</th>
                <th style="width: 9vw;">Prénom</th>
                <th style="width: 10vw;">Qualification</th>
                <th style="width: 8vw;">Numéro RPPS</th>
                <th style="width: 20vw;">Adresse</th>
                <th style="width: 8vw;">Téléphone</th>
                <th style="width: 14vw;">Adresse Ethereum</th>
                <th style="width: 3vw;">
                    <button class="buttonTable" type="submit" @click="addProfessional">
                        <img src="../assets/plus.png" alt="button add prescription" />
                    </button>
                </th>
            </tr>
        </thead>
        <tbody id="profesionnalsList">
            <Doctor v-for="(professional, index) in professionals" :key="index" :index="index" :lastName="professional.lastName" :firstName="professional.firstName" :qualification="professional.qualification" :idPSdoctor="professional.idPSdoctor" :officeAddress="professional.officeAddress" :telephone="professional.telephone" :ethAddress="professional.ethAddress" @add="registerProfessional" @delete="deleteProfessional" @update="modifyProfessional"/>
        </tbody>
    </table>

    <table class="my-table" id="pharmacists-table" hidden>
        <thead>
            <tr>
                <th style="width: 9vw;">NOM</th>
                <th style="width: 9vw;">Prénom</th>
                <th style="width: 3vw;">
                    <button class="buttonTable" type="submit" @click="addProfessional">
                        <img src="../assets/plus.png" alt="button add prescription" />
                    </button>
                </th>
            </tr>
        </thead>
        <tbody id="profesionnalsList">
            <Pharmacist v-for="(professional, index) in professionals" :key="index" :index="index" :lastName="professional.lastName" :firstName="professional.firstName" @add="registerProfessional" @delete="deleteProfessional" @update="modifyProfessional"/>
        </tbody>
    </table>

    <div id="loader-div">
        <img id="loader" src="../assets/loading_spinner.gif" alt="Loading spinner"/>
        <span id="error-span">Erreur lors du chargement des professionnels</span>
    </div>
</template>

<script>
    import Doctor from './Doctor.vue';
    import Pharmacist from './Pharmacist.vue';
    import NavigationBar from './NavigationBar.vue';

    export default {
        name: 'AdminComponent',
        components: {
            NavigationBar,
            Doctor,
            Pharmacist
        },
        data() {
            return {
                selectedOption: 'doctors',
                searchedProfessional: '',
                allProfessionals: [],
                professionals: []
            };
        },
        mounted() {
            this.loadProfessionals();
        },
        methods: {
            deleteProfessional(index)
            {
                this.professionals.splice(index, 1);
                console.log("deleting the doctor from index " + index + " in DB");
            },
            addProfessional() 
            {
                this.professionals.push({
                    lastName: '',
                    firstName: '',
                    qualification: '',
                    idPSdoctor: '',
                    officeAddress: '',
                    telephone: '',
                    ethAddress: ''
                });
            },
            registerProfessional(index) {
                if(index === this.professionals.length - 1) {
                    console.log("adding the doctor from index " + index + " to DB");
                }
                else {
                    console.log("updating the doctor from index " + index + " in DB");
                    /*if (this.selectedOption === 'doctors') {
                        this.registerDoctor(index);
                    } else if (this.selectedOption === 'pharmacists') {
                        this.registerPharmacist(index);
                    }*/
                }
            },
            modifyProfessional(index, newValue, where) {
                if (where === 'lastName') {
                    this.professionals[index].lastName = newValue;
                } else if (where === 'firstName') {
                    this.professionals[index].firstName = newValue;
                } else if (where === 'qualification') {
                    this.professionals[index].qualification = newValue;
                } else if (where === 'idPSdoctor') {
                    this.professionals[index].idPSdoctor = newValue;
                } else if (where === 'officeAddress') {
                    this.professionals[index].officeAddress = newValue;
                } else if (where === 'telephone') {
                    this.professionals[index].telephone = newValue;
                } else if (where === 'ethAddress') {
                    this.professionals[index].ethAddress = newValue;
                }
            },
            searchProfessionalByName() {
                this.professionals = [];

                this.$nextTick(() => {
                    for(var index in this.allProfessionals) {
                        if(this.allProfessionals[index].lastName.includes(this.searchedProfessional.toUpperCase())) {
                            this.professionals.push(this.allProfessionals[index]);
                        }
                    }
                });
            },
            disconnect() 
            {
                location.href = '/';
            },
            loadProfessionals() {
                if (this.selectedOption === 'doctors') {
                    document.getElementById("doctors-table").style.display = "table";
                    document.getElementById("pharmacists-table").style.display = "none";
                    this.loadDoctors();
                } else if (this.selectedOption === 'pharmacists') {
                    document.getElementById("doctors-table").style.display = "none";
                    document.getElementById("pharmacists-table").style.display = "table";
                    this.loadPharmacists();
                }

                this.allProfessionals = this.professionals;
            },
            async loadDoctors() {
                try {
                    document.getElementById("loader-div").style.visibility = "visible";

                    this.professionals = [];

                    let handleProf = await fetch("http://localhost:9000/api/admin/getDoctors", {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json; charset=UTF-8'
                        }
                    });
                    let response = await handleProf.json();
                    console.log(handleProf);
                    console.log(response);

                    if(handleProf.status == 200) {
                        console.log("Doctors' list loaded successfully !");
                        document.getElementById("loader-div").style.visibility = "hidden";
                        for (var res in response) {
                            this.professionals.push(response[res]);
                        }
                        return handleProf;
                    }
                    else {
                        console.log("Error while loading doctors' list...");
                        document.getElementById("loader").style.visibility = "hidden";
                        document.getElementById("error-span").style.visibility = "visible";
                        return null;
                    }
                }
                catch (error) {
                    console.log(error);
                }
            },
            async loadPharmacists() {
                try {
                    document.getElementById("loader-div").style.visibility = "visible";

                    this.professionals = [];

                    let handleProf = await fetch("http://localhost:9000/api/admin/getPharmacists", {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json; charset=UTF-8'
                        }
                    });
                    let response = await handleProf.json();
                    console.log(handleProf);
                    console.log(response);

                    if(handleProf.status == 200) {
                        console.log("Pharmacists' list loaded successfully !");
                        document.getElementById("loader-div").style.visibility = "hidden";
                        for (var res in response) {
                            this.professionals.push(response[res]);
                        }
                        return handleProf;
                    }
                    else {
                        console.log("Error while loading pharmacists' list...");
                        document.getElementById("loader").style.visibility = "hidden";
                        document.getElementById("error-span").style.visibility = "visible";
                        return null;
                    }
                }
                catch (error) {
                    console.log(error);
                }
            }
        },
        watch: {
            selectedOption: function() {
                this.loadProfessionals();
            }
        },
        props: {},
    }
</script>

<style>
    .my-table {
        border-collapse: collapse; 
        font-size: 16px; 
        text-align: center; 
        width : auto;
        margin-top: 5vh;
        margin-left: auto;
        margin-right: auto;
    }

    .my-table th, .my-table td {
        border: 1px solid; 
        padding: 0;
    }

    .my-table td {
        color:rgba(24,23,186,0.23);
        height: 45px;
    }

    .my-table th {
        background-color: rgba(24,23,186,0.63); 
        color:white;
        font-size: 18px;
    }

    input:focus {
        outline: none;
    }

    .searchnav {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
    }

    .toggle {
        display: flex;
        justify-content: center;
        margin-left: 40px;
    }

    input[type="radio"].job {
        display: none;
		cursor: pointer;
		outline: none;
        transition: all .3s ease;
    }

    input[type="radio"].job + label {
        position: relative;
        text-align: center;
        font-size: 20px;
        font-variant: small-caps;
        background: rgba(24,23,186,0.05);
        border: none;
        width: 150px;
        height: 40px;
        line-height: 40px;
        cursor: pointer;
        transition: all .3s ease;
    }

    #doctors + label {
        border-left: 2px solid rgba(24,23,186,0.63);
        border-top: 2px solid rgba(24,23,186,0.63);
        border-bottom: 2px solid rgba(24,23,186,0.63);
    }

    #pharmacists + label {
        border-right: 2px solid rgba(24,23,186,0.63);
        border-top: 2px solid rgba(24,23,186,0.63);
        border-bottom: 2px solid rgba(24,23,186,0.63);
    }

    input[type="radio"].job:checked + label {
        background: rgba(24,23,186,0.63);
        background-clip: padding-box;
        color: white;
    }

    .searchbar {
        display: flex;
        align-items: center;
        margin-right: 40px;
        border: 2px solid rgba(24,23,186,0.63);
        border-radius: 20px;
    }

    .searchbar > input {
        margin: 0px;
        padding-left: 15px;
        width: 400px;
        height: 30px;
        border: none;
        background: transparent;
        font-size: 16px;
        text-align: left;
    }

    #searchIcon {
        width: 25px;
        height: 25px;
        margin: 0 7px 0 7px;
    }

    #loader-div {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        width: 100vw;
        height: auto;
        visibility: visible;
    }

    #loader {
        margin-top: 10px;
        width: 50px;
        height: 50px;
    }

    #error-span {
        font-size: 20px;
        color: red;
        font-style: italic;
        margin-bottom: 5px;
        height: 20px;
        visibility: hidden;
    }
    
</style>