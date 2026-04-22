// import { Loading } from 'element-ui';
import { checkUrl } from "@/datav/utils/utils";
import axios from "axios";
window.$glob = {
	url: "",
	params: {},
	query: {},
	header: {},
};
function getGlobParams() {
	var query = window.location.search.substring(1);
	query = query.split("&");
	query.forEach((ele) => {
		var pair = ele.split("=");
		window.$glob.params[pair[0]] = pair[1];
	});
}
getGlobParams();
axios.defaults.timeout = 10000;
//返回其他状态吗
axios.defaults.validateStatus = function (status) {
	return status >= 200 && status <= 500; // 默认的
};
//跨域请求，允许保存cookie
// let loadingInstance = '';
// axios.defaults.withCredentials = true;
axios.interceptors.request.use(
	(config) => {
		// loadingInstance = Loading.service({
		//   text: '拼命加载中',
		//   background: 'rgba(0,0,0,0)',
		//   spinner: 'el-icon-loading'
		// });
		if (!checkUrl(config.url)) config.url = window.$glob.url + config.url;
		let header = window.$glob.header || {};
		config.headers = Object.assign(config.headers, header);
		let data = window.$glob.query || {};
		let key;
		if (["get", "delete"].includes(config.method)) {
			key = "params";
		} else if (["post", "put"].includes(config.method)) {
			key = "data";
		}
		if (typeof config[key] === "object") {
			config[key] = Object.assign(config[key] || {}, data);
		}
		if (config.headers.proxy) {
			let headers = {};
			for (let ele in config.headers) {
				if (typeof config.headers[ele] !== "object")
					headers[ele] = config.headers[ele];
			}
			let form = {
				url: config.url,
				method: config.method,
				headers: headers,
			};
			form[key] = config[key];
			config.url = window.$website.url + "/visual/proxy";
			config.method = "post";
			config.data = form;
		}
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);
//HTTPrequest拦截
axios.interceptors.response.use(
	(config) => {
		// loadingInstance.close();
		return config;
	},
	(error) => {
		// loadingInstance.close();
		return Promise.reject(new Error(error));
	}
);

export default axios;
