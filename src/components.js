import config from "./config";
// import api from './api'
import tool from "./utils/tool";

import ImageSelect from "./components/upload/ImageSelect.vue";
import ImageUpload from "./components/upload/ImageUpload.vue";
import FileUpload from "./components/upload/FileUpload.vue";
import vueDialog from "./components/vueDialog/index.vue";
import time from "./directives/time";
import copy from "./directives/copy";
import noMoreClick from "./directives/noMoreClick";
// import errorHandler from './utils/errorHandler'

// import * as elIcons from '@element-plus/icons-vue'
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import * as jpIcons from "./assets/icons";
import * as icons from "@element-plus/icons-vue";
import VCharts from "./components/vueEcharts/index.vue";

export default {
	install(app) {
		//挂载全局对象
		app.config.globalProperties.$CONFIG = config;
		app.config.globalProperties.$TOOL = tool;

		//注册全局组件
		app.component("QuillEditor", QuillEditor);
		app.component("ImageSelect", ImageSelect);
		app.component("ImageUpload", ImageUpload);
		app.component("FileUpload", FileUpload);
		app.component("v-dialog", vueDialog);
		app.component("v-chart", VCharts);

		//注册全局指令
		app.directive("time", time);
		app.directive("copy", copy);
		app.directive("noMoreClick", noMoreClick);

		//统一注册el-icon图标
		for (let icon in icons) {
			app.component(`ElIcon${icon}`, icons[icon]);
		}
		Object.keys(icons).forEach((key) => {
			app.component(`${key}`, icons[key]);
		});

		//统一注册sc-icon图标
		for (let icon in jpIcons) {
			app.component(`${icon}`, jpIcons[icon]);
		}

		//全局代码错误捕捉
		// app.config.errorHandler = errorHandler
	},
};
