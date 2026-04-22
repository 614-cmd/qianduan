import request from "@/utils/httpRequest";

export default {
	stop: function (id) {
		return request({
			url: `/quartz/scheduleJob/stop`,
			method: "put",
			params: { id: id },
		});
	},

	save: function (inputForm) {
		return request({
			url: `/quartz/scheduleJob/save`,
			method: "post",
			data: inputForm,
		});
	},

	startNow: function (id) {
		return request({
			url: `/quartz/scheduleJob/startNow`,
			method: "put",
			params: { id: id },
		});
	},

	resume: function (id) {
		return request({
			url: `/quartz/scheduleJob/resume`,
			method: "put",
			params: { id: id },
		});
	},

	delete: function (ids) {
		return request({
			url: "/quartz/scheduleJob/delete",
			method: "delete",
			params: { ids: ids },
		});
	},

	queryById: function (id) {
		return request({
			url: `/quartz/scheduleJob/queryById`,
			method: "get",
			params: { id: id },
		});
	},

	existsClass: function (className) {
		return request({
			url: `/quartz/scheduleJob/existsClass`,
			method: "get",
			params: { className: className },
		});
	},

	list: function (params) {
		return request({
			url: "/quartz/scheduleJob/list",
			method: "get",
			params: params,
		});
	},
};
