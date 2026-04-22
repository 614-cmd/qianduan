import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/test/treetable/testCarKind/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/test/treetable/testCarKind/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/test/treetable/testCarKind/queryById",
			method: "get",
			params: { id: id },
		});
	},

	drag: function (inputForm) {
		return request({
			url: "/test/treetable/testCarKind/drag",
			method: "post",
			data: inputForm,
		});
	},

	treeData: function () {
		return request({
			url: "/test/treetable/testCarKind/treeData",
			method: "get",
		});
	},
};
