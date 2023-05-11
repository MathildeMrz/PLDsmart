import { createApp } from 'vue'
import App from './App.vue'
const user = JSON.parse(localStorage.getItem("user"));
if (user != undefined && user.type == "doctor") {
    createApp(App).mount('#presc')
} else {
    location.href = "/";
}