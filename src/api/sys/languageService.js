import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/sys/language/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/sys/language/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/sys/language/queryById",
			method: "get",
			params: { id: id },
		});
	},

	getLanguageMap: function () {
		return request({
			url: "/sys/language/getLanguageMap",
			method: "get",
		});
	},

	list: function (params) {
		return request({
			url: "/sys/language/list",
			method: "get",
			params: params,
		});
	},
};
