import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/test/shop/testGoods/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/test/shop/testGoods/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/test/shop/testGoods/queryById",
			method: "get",
			params: { id: id },
		});
	},

	list: function (params) {
		return request({
			url: "/test/shop/testGoods/list",
			method: "get",
			params: params,
		});
	},

	exportTemplate: function () {
		return request({
			url: "/test/shop/testGoods/import/template",
			method: "get",
			responseType: "blob",
		});
	},

	exportExcel: function (params) {
		return request({
			url: "/test/shop/testGoods/export",
			method: "get",
			params: params,
			responseType: "blob",
		});
	},

	importExcel: function (data) {
		return request({
			url: "/test/shop/testGoods/import",
			method: "post",
			data: data,
		});
	},
};
