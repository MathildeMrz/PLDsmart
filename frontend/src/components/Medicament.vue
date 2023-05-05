<template>
    <tr>
        <td>
            <input type="text" id="medicineAct" list="medicineActDatalist"/>
            <datalist id="medicineActDatalist">
                
            </datalist>
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
        <button class="buttonTable" type="button" @click="deleteMedicine">
            <img src="../assets/delete.png" alt="button delete prescription" />
        </button>
        </td>
    </tr>
</template>

<script>

    export default {
        name: 'MedicamentComponent',
        props: ['medicine'],
        mounted() {
        this.loadData();
          },
        methods: {
            deleteMedicine() {
                console.log("delete medicine of child!!!!");

                this.$emit('delete',this.index);
            },
            loadData() {
            console.log('Component loaded!');
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
            }
        }
    }   ;
</script>

<style>

</style>