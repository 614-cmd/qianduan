import tool from "@/utils/tool";
import sysConfig from "@/config";
import { clearLoginInfo } from "@/utils";
import { ElNotification, ElMessageBox } from "element-plus";
import axios from "axios";
import router from "@/router";
// import axiosRetry from 'axios-retry'
import qs from "qs";
import { ElLoading } from "element-plus";

// 超时时间
// axios.defaults.timeout = 100000;
// 跨域请求，允许保存cookie
// axios.defaults.withCredentials = true;
// axios.defaults.headers = { "Content-Type": "application/json; charset=utf-8" };
// axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8'
// 非生产环境 && 开启代理, 接口前缀统一使用[/api]前缀做代理拦截!import.meta.env.VITE_APP_BASE
const BASE_URL =
	process.env.NODE_ENV !== "production"
		? import.meta.env.VITE_APP_API
		: import.meta.env.VITE_APP_BASE;
// 对面暴露的基础请求路径

const service = axios.create({
	baseURL: BASE_URL,
	timeout: 100000,
	withCredentials: true,
	headers: {
		"Content-Type": "application/json; charset=utf-8",
	},
});
service.BASE_URL = BASE_URL;
/**
 * 请求拦截
 */
let loading;
service.interceptors.request.use(
	(config) => {
		let showLoading = false;
		if (config.loading === true) {
			showLoading = true;
		}
		if (showLoading) {
			loading = ElLoading.service({
				text: config.loadingText || "Loading...",
				spinner: "el-icon-loading",
				background: "rgba(0, 0, 0, 0.7)",
			});
		}
		// 请求头带上token
		if (tool.data.get(sysConfig.TOKEN)) {
			config.headers.token = tool.data.get(sysConfig.TOKEN);
		}

		// 请求地址处理
		// if (
		// 	!config.url.startsWith("http") &&
		// 	!config.url.startsWith(BASE_URL)
		// ) {
		// 	config.url = BASE_URL + config.url;
		// }

		const type = config.method;
		const arrayFormat = config.headers.arrayFormat || "indices";
		if (
			type === "post" &&
			config.headers["Content-Type"] ===
				"application/x-www-form-urlencoded; charset=utf-8"
		) {
			// post请求参数处理
			config.data = qs.stringify(config.data, {
				allowDots: true,
				arrayFormat: arrayFormat,
			});
		} else if (type === "get") {
			// get请求参数处理
			config.paramsSerializer = (params) => {
				return qs.stringify(params, {
					allowDots: true,
					arrayFormat: arrayFormat,
				});
			};
		}
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);

/**
 * 响应拦截
 */
service.interceptors.response.use(
	(response) => {
		if (loading) {
			loading.close();
		}
		return response.data;
	},
	(error) => {
		if (loading) {
			loading.close();
		}
		// eslint-disable-next-line no-debugger
		if (error.response) {
			// eslint-disable-next-line no-debugger
			if (error.response.status === 408) {
				// 超时自动刷新
				if (!tool.session.getWithExpiry("logout_timeout")) {
					// 10秒内只如果有多个超时提示 ，只弹出一次提示框
					tool.session.setWithExpiry(
						"logout_timeout",
						true,
						10 * 1000
					);
					// 超时自动刷新
					ElMessageBox.confirm(
						"当前用户已被登出或无权限访问当前资源，请尝试重新登录后再操作。",
						"无权限访问",
						{
							type: "error",
							closeOnClickModal: false,
							center: true,
							confirmButtonText: "重新登录",
						}
					).then(() => {
						clearLoginInfo();
						router.replace({ path: "/login" });
					});
				}
			} else if (error.response.status === 401) {
				// 需要重新登录
				ElNotification.error({
					title: "登录失败",
					message: error.response.data,
				});
				clearLoginInfo();
				router.replace({ path: "/login" });
			} else if (error.response.status === 404) {
				// 路径找不到
				ElNotification.error({
					title: "404",
					message: "路径找不到" + ": " + error.config.url,
				});
			} else if (error.response.status === 503) {
				ElNotification.error({
					title: "503",
					message: "服务不可用" + ": " + error.config.url,
				});
			} else if (error.response.status === 504) {
				ElNotification.error({
					title: "504",
					message: "网络连接错误" + ": " + error.data,
				});
			} else if (error.response.status === 400) {
				ElNotification.error({
					title: "请求失败",
					message: error.response.data || error.data || error,
				});
			} else {
				ElNotification.error({
					title: "系统错误",
					message:
						(error.response && error.response.data) ||
						error.data ||
						error,
				});
			}
		}

		return Promise.reject(error);
	}
);

// 配置axios
// axiosRetry(axios, {
//   retries: 3,  // 设置自动发送请求次数
//   retryCondition: () => {
//       // true为打开自动发送请求，false为关闭自动发送请求
//       // 这里的意思是当请求方式为get时打开自动发送请求功能
//     return false
//   }
// })
export default service;
