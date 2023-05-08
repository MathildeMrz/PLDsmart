<template>
    <DoctorPrescription/>
  </template>
  
  <script>
  import DoctorPrescription from '@/components/Admin.vue';
  
  export default {
    name: 'App',
    components: {
      DoctorPrescription
  }
  }
  </script>
  
  <style>
  #admin {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }
  
  </style>
  