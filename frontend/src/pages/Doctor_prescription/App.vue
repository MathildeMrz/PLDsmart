<template>
  <DoctorPrescription />
</template>

<script>
import DoctorPrescription from '@/components/DoctorPrescription.vue';

export default {
  name: 'App',
  components: {
    DoctorPrescription
  }
}
</script>

<style>
#presc {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #1817BA;
}

.swal2-popup {
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
</style>
