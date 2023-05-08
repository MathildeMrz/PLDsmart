<template>
    <NavigationBar/>
    <button id="doctorsButton">Médecins</button>
    <button id="pharmacistsButton">Pharmaciens</button>

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

    .buttonTable {
        border:none;
        background-color:transparent;
    }

    .buttonTable img {
        width:2vw;
    }

    #doctorsButton
    {
        background-color: rgba(24,23,186,0.63); 
    }

    #doctorsButton, #pharmacistsButton
    {
        border: 1px solid #000000;
        border-radius: 15px;
        height : 5vh;
        width : 10vw;
        margin-top : 2vh;
        margin-left: 5vh;
    }

    
    
</style>