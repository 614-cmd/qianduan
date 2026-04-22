import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/sys/tenant/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/sys/tenant/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: `/sys/tenant/queryById?id=${id}`,
			method: "get",
		});
	},

	validateNotExist: function (params) {
		return request({
			url: "/sys/tenant/validateNotExist",
			method: "get",
			params: params,
		});
	},

	list: function (params) {
		return request({
			url: "/sys/tenant/list",
			method: "get",
			params: params,
		});
	},
};
