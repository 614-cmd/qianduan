import request from "@/utils/httpRequest";

export default {
	models: function (data) {
		return request({
			// url: "/rest/models",
			url: "/flowable/model/rest/models",
			method: "post",
			data: data,
		});
	},
};
