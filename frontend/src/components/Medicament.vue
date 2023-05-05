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
        <button class="buttonTable" type="button">
            <img src="../assets/delete.png" alt="button delete prescription" />
        </button>
        </td>
    </tr>
</template>

<script>
    export default {
        name: 'MedicamentComponent',
        props: {}
    }
</script>

<style scoped>
    input
    {
        border: none;
        border-bottom: 2px solid #1817BA;
        width:20vh;
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
</style>