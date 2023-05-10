<template>
    <tr>
        <td>
            <input id="professionalName" type="text" required>
        </td>
        <td>
            <input id="professionalFirstName" type="text" required>
        </td>

        <td>
            <input id="professionalQualification" type="text" required>
        </td>

        <td>
            <input id="professionalRPPSNumber" type="text" required>
        </td>

        <td>
            <input id="professionalAddress" type="text" required>
        </td>

        <td>
            <input id="professionalPhoneNumber" type="text" required>        
        </td>                    
        
        <td>
            <button class="buttonTable" type="button" @click="deleteProfessional">
                <img src="../assets/delete.png" alt="button delete prescription" />
            </button>
        </td>
    </tr>
</template>

<script>

    export default {
        name: 'ProfessionalComponent',
        props: ['index'],
        methods: 
        {
            deleteProfessional() 
            {
                this.$emit('delete',this.index);
            },
        }
    };
</script>

<style>
    .buttonTable {
        border: none;
        background-color: transparent;
    }

    .buttonTable img {
        width:2vw;
    }
</style>