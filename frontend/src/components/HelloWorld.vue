<template>
  <div id="index-page">
    <div id="login-box">
      <img id="logo-image-login" src="../assets/logo_Prescrypt.png" alt="Prescrypt logo">
      <p>J'accède à mon <b>compte Prescrypt</b></p>
      <form>
        <div class="user-box">
          <input class="bottomInput" type="text" placeholder="Mon adresse" name="" required="">
        </div>
        <div class="user-box">
          <input class="bottomInput" type="password" placeholder="Mot de passe"  name="" required="">
        </div>

        <div>
          <input type="radio" id="role-doctor" name="role" value="Médecin" />
          <label for="role-doctor">Médecin</label>

          <input type="radio" id="role-pharmacist" name="role" value="Pharmacien" />
          <label for="role-pharmacist">Pharmacien</label>

          <input type="radio" id="role-admin" name="role" value="Administrateur" />
          <label for="role-admin">Administrateur</label>
        </div>  
        <button id="connexion-button" type="button">Me connecter</button>    
      </form>
    </div>
    <div id="doctor-box">
        <img id="doctor-image" src="../assets/doctor.png" alt="Doctor smiling">
      <div class="centered">Votre vérificateur d’ordonnances <br> 100% sécurisé</div>
    </div>
  </div>  
  
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  #index-page
  {
    font-size: 20px;
    display:flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  #login-box 
  {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    /*width:100%;*/
  }
  
  #doctor-box{
    margin: 0;
  }

  .bottomInput
  {
    border: none;
    border-bottom: 2px solid #1817BA;
  }

  #connexion-button
  {
    background: rgba(24, 23, 186, 0.46);
    color:white;
    font-size: 24px;
  }

  input::placeholder
  {
    font-size: 18px;
  }

  input
  {
    margin-bottom: 30px;
  }

  #logo-image-login
  {
    max-width: 40%;
    margin-bottom: 50px;
  }
  .centered {
    color:white;
    font-size: 40px;
    font-weight: 600;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1;
  }

  #doctor-box {
  position: relative;
  }

  #doctor-image {
    max-width: 100%;
    height: auto;
  }

</style>
