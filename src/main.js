import { createApp } from "vue";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import moment from "moment";

import VXETable from "vxe-table";
import "vxe-table/lib/style.css";
import utils from "@/utils/index";
import dictUtils from "@/utils/dictUtils";
import httpRequest from "@/utils/httpRequest";
import validator from "@/utils/validator";
import components from "./components";
import i18n from "./i18n";
import router from "./router";
import store from "./store";
import App from "./App.vue";
import lodash from "lodash/object";
import VueClipboard from "vue-clipboard2";
import VueCookies from "vue-cookies";
import JeeplusDevtools from "jeeplus-devtools-boot-v3";
import "jeeplus-devtools-boot-v3/dist/index.css";
import JeeplusFlow from "jeeplus-flowable-boot-v3";
import "jeeplus-flowable-boot-v3/dist/index.css";
import FormMaking from "jeeplus-form-boot-v3";
import "jeeplus-form-boot-v3/dist/index.css";

const app = createApp(App);
import "moment/locale/zh-cn";
moment.locale("zh-cn");

app.config.globalProperties.moment = moment;
app.config.globalProperties.hasPermission = utils.hasPermission; // 权限方法
app.config.globalProperties.treeDataTranslate = utils.treeDataTranslate; // 树形数据转换
app.config.globalProperties.$utils = utils;
app.config.globalProperties.$window = window;
app.config.globalProperties.$dictUtils = dictUtils;
app.config.globalProperties.recover = utils.recover;
app.config.globalProperties.recoverNotNull = utils.recoverNotNull;
app.config.globalProperties.pickProperties = utils.pickProperties;
app.config.globalProperties.validator = validator;
app.config.globalProperties.moment = moment;
app.config.globalProperties.lodash = lodash;
app.config.globalProperties.deepClone = utils.deepClone;
app.config.globalProperties.validatenull = utils.validatenull;
app.config.globalProperties.$http = httpRequest; // ajax请求方法
app.config.globalProperties.$cookies = VueCookies;

app.use(store);
app.use(router);
app.use(ElementPlus);
app.use(i18n);
app.use(components);

// app.use(FormMaking)

app.use(FormMaking, {
	locale: "zh-cn",
});
app.use(JeeplusDevtools, { saas: false });
app.use(JeeplusFlow);
app.use(VueClipboard);
app.use(VueCookies);

VXETable.setup({
	// 对组件内置的提示语进行国际化翻译
	i18n: (key, args) => i18n.global.t(key, args),
});

app.use(VXETable);

//挂载app
app.mount("#app");
