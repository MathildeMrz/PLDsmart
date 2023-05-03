<template>
  <div id="index-page">
    <div id="login-box">
      <img id="logo-image-login" src="../assets/logo_Prescrypt.png" alt="Prescrypt logo">
      <p style="font-size: 26px;">J'accède à mon <b>compte Prescrypt</b></p>
      <form>
        <div class="user-box">
          <input class="bottomInput" type="text" placeholder="Mon adresse" name="" required="" checked="checked">
        </div>
        <div class="user-box">
          <input class="bottomInput" type="password" placeholder="Mot de passe"  name="" required="">
        </div>

        <div>
          <div class="radio">
            <input type="radio" id="role-doctor" name="role" value="Médecin" />
            <label for="role-doctor">Médecin</label>
          </div>

          <div class="radio">
            <input type="radio" id="role-pharmacist" name="role" value="Pharmacien" />
            <label for="role-pharmacist">Pharmacien</label>
          </div>

          <div class="radio">
            <input type="radio" id="role-admin" name="role" value="Administrateur" />
            <label for="role-admin">Administrateur</label>
          </div>
        </div>  
        <button id="connexion-button" type="button">ME CONNECTER</button>    
      </form>
    </div>
    <div id="doctor-box">
        <img id="doctor-image" src="../assets/doctor.jpg" alt="Doctor smiling">
        <div class="centered"><p id="slogan">Votre vérificateur d’ordonnances</p><p id="secured">100% sécurisé</p></div>
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
    display: flex;
    flex-wrap: wrap;
    width: 100vw;
    justify-content: right;
  }

  #login-box
  {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 60vw;
    height: 100vh;
  }

  .bottomInput
  {
    border: none;
    border-bottom: 2px solid #1817BA;
    width: 300px;
    padding: 8px;
  }

  #connexion-button
  {
    padding: 15px 25px 15px 25px;
    background: rgba(24, 23, 186, 0.46);
    border: none;
    color:white;
    font-variant: small-caps;
    font-size: 20px;
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
    width: 350px;
    margin-bottom: 50px;
  }

  .radio
  {
    display: inline-block;
    margin: 10px;
  }

  .centered {
    position: absolute;
    width: 100%;
    top: 67%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1;
  }

  #slogan {
    color:white;
    font-size: 50px;
    font-weight: 600;
  }

  #secured {
    color: black;
    font-size: 30px;
    font-weight: 600;
  }

  #doctor-box {
    position: relative;
    margin: 0;
    display: flex;
  }

  #doctor-image {
    height: 100vh;
    width: 40vw;
    z-index: -1;
  }

</style>
