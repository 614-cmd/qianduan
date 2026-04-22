import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/devtools/customObj/save",
			method: "post",
			data: inputForm,
		});
	},
	add: function (tableId, tableType) {
		return request({
			url: "/devtools/customObj/add",
			method: "post",
			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded; charset=utf-8",
			},
			data: { tableId: tableId, tableType: tableType },
		});
	},
	delete: function (ids) {
		return request({
			url: "/devtools/customObj/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: "/devtools/customObj/queryById",
			method: "get",
			params: { id: id },
		});
	},

	getByValue: function (value) {
		return request({
			url: "/devtools/customObj/getByValue",
			method: "get",
			params: { value: value },
		});
	},

	list: function (params) {
		return request({
			url: "/devtools/customObj/list",
			method: "get",
			params: params,
		});
	},
};
