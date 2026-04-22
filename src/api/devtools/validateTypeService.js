import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/devtools/validateType/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/devtools/validateType/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/devtools/validateType/queryById",
			method: "get",
			params: { id: id },
		});
	},

	list: function (params) {
		return request({
			url: "/devtools/validateType/list",
			method: "get",
			params: params,
		});
	},
};
