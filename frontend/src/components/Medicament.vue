<template>
    <tr>
        <td>
            <input type="text" id="medicineAct" list="medicineActDatalist"/>
            <datalist id="medicineActDatalist">
                
            </datalist>
        </td>
        <td>
            <input id="posology" type="text" name="" required>
        </td>

        <td>
            <input id="treatmentPeriod" type="number" min="1" name="" required
            onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);" maxlength="3"
            style="width: 7vh; border-right: 2px solid #1817BA;">
            <select id="treatmentPeriodTexte" required style="width: 12vh; height: 2.7vh;">
                <option value="" disabled selected hidden></option>
                <option value="jours">jours</option>
                <option value="mois">mois</option>
            </select>
        </td>

        <td>
            <input id="renewal" type="number" min="0" max="100" name="" required
            onkeydown="return event.key !== ' ' && event.key !== '-' && event.key !== '+' && !['e', 'E'].includes(event.key);"
            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);" maxlength="2">
        </td>

        <td>
            <input id="refundable" type="checkbox" name="">
        </td>

        <td>
            <textarea id="indication" rows="4" style="word-wrap: break-word;" name=""></textarea>
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
        props: ['index'],
        mounted() 
        {
            this.loadData();
        },
        methods: 
        {
            deleteMedicine() 
            {
                this.$emit('delete',this.index);
            },
            loadData() 
            {
                //Ajout médicaments liste déroulante
                const data = require('../assets/medicine.json');
                const medicineSelect = document.getElementById("medicineActDatalist");
                let result = [];
                for(let i=0; i<data.length; i++) 
                {
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