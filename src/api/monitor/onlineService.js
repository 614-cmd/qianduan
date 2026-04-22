import request from "@/utils/httpRequest";

export default {
	kickout: function (token) {
		return request({
			url: "/online/users/kickout",
			method: "delete",
			params: { token: token },
		});
	},
	list: function (params) {
		return request({
			url: "/online/users/list",
			method: "get",
			params: params,
		});
	},
};
