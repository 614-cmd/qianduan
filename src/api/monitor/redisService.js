import request from "@/utils/httpRequest";

export default {
	treeData: function (params) {
		return request({
			url: "/redis/treeData",
			method: "get",
			params: params,
		});
	},

	flushdb: function () {
		return request({
			url: "/redis/flushdb",
			method: "delete",
		});
	},

	delPattern: function (pattern) {
		return request({
			url: "/redis/delPattern",
			method: "delete",
			params: { pattern: pattern },
		});
	},

	delKey: function (key) {
		return request({
			url: "/redis/delKey",
			method: "delete",
			params: { key: key },
		});
	},

	setValue: function (key, value) {
		return request({
			url: "/redis/setValue",
			method: "put",
			params: { key: key, value: value },
		});
	},

	queryByKey: function (key) {
		return request({
			url: "/redis/queryByKey",
			method: "get",
			params: { key: key },
		});
	},
};
