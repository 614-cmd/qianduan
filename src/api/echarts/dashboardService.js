import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/echarts/dashboard/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/echarts/dashboard/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/echarts/dashboard/queryById",
			method: "get",
			params: { id: id },
		});
	},

	list: function (params) {
		return request({
			url: "/echarts/dashboard/list",
			method: "get",
			params: params,
		});
	},
};
