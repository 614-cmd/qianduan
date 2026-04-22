import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/test/shop/testCategory/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/test/shop/testCategory/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/test/shop/testCategory/queryById",
			method: "get",
			params: { id: id },
		});
	},

	drag: function (inputForm) {
		return request({
			url: "/test/shop/testCategory/drag",
			method: "post",
			data: inputForm,
		});
	},

	treeData: function () {
		return request({
			url: "/test/shop/testCategory/treeData",
			method: "get",
		});
	},
};
