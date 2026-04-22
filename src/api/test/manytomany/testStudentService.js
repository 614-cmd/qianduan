import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/test/manytomany/testStudent/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/test/manytomany/testStudent/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/test/manytomany/testStudent/queryById",
			method: "get",
			params: { id: id },
		});
	},

	list: function (params) {
		return request({
			url: "/test/manytomany/testStudent/list",
			method: "get",
			params: params,
		});
	},

	exportTemplate: function () {
		return request({
			url: "/test/manytomany/testStudent/import/template",
			method: "get",
			responseType: "blob",
		});
	},

	exportExcel: function (params) {
		return request({
			url: "/test/manytomany/testStudent/export",
			method: "get",
			params: params,
			responseType: "blob",
		});
	},

	importExcel: function (data) {
		return request({
			url: "/test/manytomany/testStudent/import",
			method: "post",
			data: data,
		});
	},
};
