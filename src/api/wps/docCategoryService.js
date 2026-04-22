import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/wps/docCategory/save",
			method: "post",
			data: inputForm,
		});
	},

	delete: function (ids) {
		return request({
			url: "/wps/docCategory/delete",
			method: "delete",
			params: { ids: ids },
		});
	},
	drag: function (inputForm) {
		return request({
			url: "/wps/docCategory/drag",
			method: "post",
			data: inputForm,
		});
	},
	queryById: function (id) {
		return request({
			url: "/wps/docCategory/queryById",
			method: "get",
			params: { id: id },
		});
	},

	treeData: function (extId) {
		return request({
			url: "/wps/docCategory/treeData",
			method: "get",
			params: { extId: extId },
		});
	},
};
