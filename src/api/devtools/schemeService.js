import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/devtools/scheme/save",
			method: "post",
			data: inputForm,
		});
	},

	createMenu: function (inputForm) {
		return request({
			url: "/devtools/scheme/createMenu",
			method: "post",
			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded; charset=utf-8",
			},
			data: inputForm,
		});
	},

	getFileTree: function (parentNode) {
		return request({
			url: "/devtools/scheme/getFileTree",
			method: "get",
			params: { parentNode: parentNode },
		});
	},
};
