<template>
  <div id="index-page">
    <div id="login-box">
      <img id="logo-image-login" src="../assets/logo_Prescrypt.png" alt="Prescrypt logo">
      <p style="font-size: 26px;">J'accède à mon <b>compte Prescrypt</b></p>
      <form>
        <div class="user-box">
          <input id="username" class="bottomInput" type="text" placeholder="Mon adresse" name="" required="" checked="checked">
        </div>
        <div class="user-box">
          <input id="password" class="bottomInput" type="password" placeholder="Mot de passe"  name="" required="">
        </div>

        <div>
          <div class="radio">
            <input type="radio" id="role-doctor" name="role" value="Médecin" checked/>
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
        <button id="connexion-button" v-on:click="authenticate(event)">Me Connecter</button>
      </form>
    </div>
    <div id="doctor-box">
        <img id="doctor-image" src="../assets/doctor.jpg" alt="Doctor smiling">
        <div class="centered">
          <p id="slogan">Votre vérificateur d’ordonnances</p>
          <p id="secured">100% sécurisé</p>
          <img id="zigouigoui" src="../assets/zigouigoui.png" alt="Zigouigoui secured">
        </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'AuthentificationComponent',
    props: {},
    methods: {
      async authenticate(e) {
        e = e || window.event;
        e.preventDefault();
        
        var jobSelected = document.querySelectorAll("input[type='radio'][name='role']:checked");
        var job = "";

        switch(jobSelected[0].value) {
          case "Médecin":
            job = "doctor";
            console.log("Authentification en tant que médecin");
            break;
          case "Pharmacien":
            job = "pharmacist";
            console.log("Authentification en tant que pharmacien");
            break;
          case "Administrateur":
            job = "admin";
            console.log("Authentification en tant qu'administrateur");
            break;
        }

        try {
          let handleAuth = await fetch("http://localhost:9000/api/auth/" + job, {
              method: 'POST',
              body: JSON.stringify({
                  username: document.getElementById("username").value,
                  password: document.getElementById("password").value
                }),
              headers: {
                'Content-Type': 'application/json; charset=UTF-8'
              }
            });
          let response = await handleAuth.json();
          console.log(response);

          if(response.length != 0) {
            location.href = job + ".html";
          }
          else {
            console.log("User not registered");
          }
        }
        catch (error) {
          console.log(error);
        }
      }
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
    cursor: pointer;
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
    top: 66%;
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
    padding: 0;
    margin: 0;
    margin-top: 10px;
  }

  #zigouigoui {
    margin: 0;
    padding-left: 80px;
    width: 89px;
    height: 20px;
    transform: translateY(-5px) ;
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
