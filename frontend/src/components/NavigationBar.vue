<template>
    <nav class="navigation-bar">
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
        margin: 0;
        width: 100%;  /* i'm assuming full width */
        height: 80px; /* change it to desired width */
        background: rgba(24, 23, 186, 0.1);
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    #disconnection-button {
        position: relative;
        vertical-align: middle;
        padding: 15px 25px 15px 25px;
        margin-right: 20px;
        background: none;
        border: 2px solid rgba(24, 23, 186, 0.7);
        color: rgba(24, 23, 186, 0.7);
        font-variant: small-caps;
        font-size: 20px;
        cursor: pointer;
        transition: 0.3s;
    }

    #disconnection-button:hover {
        background: rgba(24, 23, 186, 0.63);
        color: white;
    }

    .logo {
        display: inline-block;
        width: 200px; /*4vw*/
        margin-left: 5vh;
    }
    </style>