import tool from "@/utils/tool";
export default {
	namespaced: true,
	state: {
		id: tool.data.get("USER_INFO")?.id,
		name: tool.data.get("USER_INFO")?.name,
		loginName: tool.data.get("USER_INFO")?.loginName,
		no: tool.data.get("USER_INFO")?.no,
		office: {
			id: tool.data.get("USER_INFO")?.officeDTO?.id,
			name: tool.data.get("USER_INFO")?.officeDTO?.name,
		},
		company: {
			id: tool.data.get("USER_INFO")?.companyDTO?.id,
			name: tool.data.get("USER_INFO")?.companyDTO?.name,
		},
		photo: tool.data.get("USER_INFO")?.photo,
	},
	mutations: {
		updateId(state, id) {
			state.id = id;
		},
		updateName(state, name) {
			state.name = name;
		},
		updateLoginName(state, loginName) {
			state.loginName = loginName;
		},
		updatePhoto(state, photo) {
			state.photo = photo;
		},
		updateUser(state, user) {
			state.id = user.id;
			state.name = user.name;
			state.loginName = user.loginName;
			state.company = user.companyDTO;
			state.office = user.officeDTO;
			state.no = user.no;
			state.photo = user.photo;
		},
	},
};
