import { createApp } from "vue";
import axios from "./axios";
import router from "./router.js";
import ElementPlus from "element-plus";
import createIcon from "./icon";
import "element-plus/dist/index.css";
import DataVVue3 from "@kjgl77/datav-vue3";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import Avue from "@smallwei/avue";
import { website } from "@/datav/config.js";
import "@smallwei/avue/lib/index.css";
import draggable from "@/datav/page/components/draggable.vue";
import { loadScript } from "@/datav/utils/utils";
import error from "./error";
import App from "./App.vue";
import "./styles/common.scss";
import "@/datav/utils/es6";
const app = createApp(App);
window.axios = axios;
window.$loadScript = loadScript;
document.title = website.title;
createIcon(app);
app.component("avue-draggable", draggable);
app.config.globalProperties.$component = app.component;
app.config.globalProperties.$website = website;
app.use(DataVVue3);
app.use(error);
app.use(router);
app.use(ElementPlus, {
	locale: zhCn,
});
app.use(Avue, {
	axios,
});
app.mount("#app");
