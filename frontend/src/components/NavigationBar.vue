<template>
    <nav  class="navigation-bar">
        <img class="logo" src="../assets/entete.png">
        <button id="disconnection-button" v-on:click="disconnect()">Se Déconnecter</button>
    </nav>
</template>
<script>
export default {
        name: 'NavigationBar',
        props: {},
        methods: {
            disconnect() {
                location.href = '/';
            }
        }
    }   ;
</script>
<style>
  .navigation-bar {
        width: 100%;  /* i'm assuming full width */
        height: 80px; /* change it to desired width */
        box-shadow: 0px 11px 10px -14px #111;        
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    #disconnection-button {
        position: relative;
        vertical-align: middle;
        padding: 15px 25px 15px 25px;
        margin-bottom: 10px;
        margin-right: 20px;
        background: rgba(24, 23, 186, 0.46);
        border: none;
        color:white;
        font-variant: small-caps;
        font-size: 20px;
        cursor: pointer;
    }
    </style>