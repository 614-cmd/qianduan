import request from "@/utils/httpRequest";

export default {
	info: function (params) {
		return request({
			url: "/monitor/server/info",
			method: "get",
			params: params,
		});
	},
};
