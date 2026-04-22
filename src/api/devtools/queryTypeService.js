import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/devtools/queryType/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/devtools/queryType/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/devtools/queryType/queryById",
			method: "get",
			params: { id: id },
		});
	},

	list: function (params) {
		return request({
			url: "/devtools/queryType/list",
			method: "get",
			params: params,
		});
	},
};
