<template>
    <NavigationBar/>

    <div class="searchnav">
        <div class="toggle">
            <input type="radio" id="doctors" class="job" name="jobView" checked/>
            <label for="doctors">Médecins</label>
            <input type="radio" id="pharmacists" class="job" name="jobView"/>
            <label for="pharmacists">Pharmaciens</label>
        </div>

        <div class="searchbar">
            <input type="text" placeholder="Rechercher un professionnel" />
            <button class="buttonTable" type="submit">
                <img src="../assets/search.png" id="searchIcon" alt="button search" />
            </button>
        </div>
    </div>

    <table class="my-table">
        <thead>
            <tr>
                <th>NOM</th>
                <th>Prénom</th>
                <th>Qualification</th>
                <th>Numéro RPPS</th>
                <th>Adresse</th>
                <th>Téléphone</th>
                <th><button class="buttonTable" type="submit" @click="addProfessional">
                        <img src="../assets/plus.png" alt="button add prescription" />
                    </button>
                </th>
            </tr>
        </thead>
        <tbody id="profesionnalsList">
            <Professional v-for="(index) in professionals" :key="index" :index="index" @delete="deleteProfessional"/>
        </tbody>
    </table>
</template>

<script>
    import Professional from './Professional.vue';
    import NavigationBar from './NavigationBar.vue';

    export default {
        name: 'AdminComponent',
        components: {
            NavigationBar,
            Professional
        },
        data() {
        return {
            professionals: []
            };
        },
        methods: {
            deleteProfessional(index) 
            {
                this.professionals.splice(index, 1);
            },
            addProfessional() 
            {
                this.professionals.push({

                });
            },
            disconnect() 
            {
                location.href = '/';
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
        width : 95%;
        margin-top: 3vh;
        margin-left: auto;
        margin-right: auto;
    
    }

    .my-table th, .my-table td {
        border: 1px solid; 
        padding: 8px;
    }

    .my-table td {
        color:rgba(24,23,186,0.23);

    }

    .my-table th {
        background-color: rgba(24,23,186,0.63); 
        color:white;
    }

    input {
        border: none;
        border-bottom: 2px solid #1817BA;
        width:20vh;
    }

    input:focus {
        outline: none;
    }

    .buttonTable {
        border:none;
        background-color:transparent;
    }

    .buttonTable img {
        width:2vw;
    }

    .searchnav {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
    }

    .toggle {
        display: flex;
        justify-content: center;
        margin-left: 39px;
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
        margin-right: 39px;
        border: 2px solid rgba(24,23,186,0.63);
        border-radius: 20px;
    }

    .searchbar > input {
        margin: 0px;
        padding-left: 15px;
        width: 500px;
        height: 30px;
        border: none;
        background: transparent;
        font-size: 16px;
    }

    #searchIcon {
        width: 25px;
        height: 25px;
        margin: 0 7px 0 7px;
    }
    
</style>