import request from "@/utils/httpRequest";

export default {
	save: function (inputForm) {
		return request({
			url: "/sys/desk/save",
			method: "post",
			data: inputForm,
		});
	},
	getGridInfo: function () {
		return request({
			url: "/sys/desk/getGridInfo",
			method: "get",
		});
	},
};
