import { createApp } from "vue";
import router from "./router/router";
import "./style.css";
import "element-plus/dist/index.css";
import App from "./App.vue";
import ElementPlus from "element-plus";

const app = createApp(App);

app.use(router);
app.use(ElementPlus);
app.mount("#app");
