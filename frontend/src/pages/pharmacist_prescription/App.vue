<template>
  <div>
    <PharmacistPrescription />
  </div>
</template>
  
<script>
import PharmacistPrescription from '@/components/PharmacistPrescription.vue';

export default {
  name: 'App',
  components: {
    PharmacistPrescription,
  },
};
</script>
<style>
#prescPharma {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #1817BA;
}

.swal2-popup {
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
</style>
  