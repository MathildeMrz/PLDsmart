<template>
    <tr>
        <td>
            <input id="professionalName" type="text" v-on:dblclick="setEditable($event)" v-on:keyup.enter="setUneditable($event)" v-on:blur="setUneditable($event)" @input="validateModification" required :readonly="!editable || selectedInput !== 'professionalName'" :value="lastName" name="lastName" ref="professionalNameInput">
        </td>

        <td>
            <input id="professionalFirstName" type="text" v-on:dblclick="setEditable($event)" v-on:keyup.enter="setUneditable($event)" v-on:blur="setUneditable($event)" @input="validateModification" required :readonly="!editable || selectedInput !== 'professionalFirstName'" :value="firstName" name="firstName" ref="professionalFirstNameInput">
        </td>
        
        <td>
            <button id="delete" class="buttonTable" type="button" @click="deletePharmacist">
                <img src="../assets/delete.png" alt="button delete" />
            </button>
            <button id="validate" class="buttonTable" type="button" @click="addDoctor">
                <img src="../assets/validate.png" alt="button validate" />
            </button>
        </td>
    </tr>
</template>

<script>

    export default {
        name: 'PharmacistComponent',
        props: ['index', 'lastName', 'firstName'],
        data() {
            return {
                editable: false, // initialize editable to false
                selectedInput: null // initialize selectedInput to null
            }
        },
        methods: 
        {
            deletePharmacist() 
            {
                this.$emit('delete', this.index);
            },
            addDoctor() 
            {
                this.$emit('add', this.index);
                this.$el.querySelector("#delete").style.display = "initial";
                this.$el.querySelector("#validate").style.display = "none";
            },
            setEditable(event) {
                // set editable to true and store a reference to the input field that triggered the event
                this.editable = true;
                this.selectedInput = event.target.id;
                this.$refs[event.target.id + 'Input'].focus();
            },
            setUneditable(event){
                // set editable to false and clear the selectedInput
                this.editable = false;
                this.$emit('update', this.index, event.target.value, event.target.name);
                this.selectedInput = null;
            },
            validateModification() {
                this.$el.querySelector("#delete").style.display = "none";
                this.$el.querySelector("#validate").style.display = "initial";
            }
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
        height: auto;
        margin: 2px;
    }

    #validate {
        display: none;
    }

    input {
        font-size: 14px;
        margin: auto;
        width: calc(100% - 30px);
        text-align: center;
        border: none;
    }

    input:read-write {
        border: none;
        border-bottom: 2px solid #1817BA;
    }
</style>