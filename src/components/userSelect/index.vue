<template>
	<template v-if="printRead">
		<label>{{ name }}</label>
	</template>
	<template v-else>
		<el-input
			placeholder="请选择"
			readonly
			:clearable="clearable"
			:disabled="disabled"
			style="line-hight: 40px"
			v-model="name"
		>
			<template #append>
				<el-button
					:disabled="disabled"
					:readonly="readonly"
					@click="showUserSelect"
					icon="search"
				></el-button>
			</template>
		</el-input>
		<user-select
			ref="userSelect"
			@doSubmit="selectUsersToInput"
			:limit="limit"
		></user-select>
	</template>
</template>
<script>
import userSelect from "./UserSelectDialog";
import userService from "@/api/sys/userService";
export default {
	data() {
		return {
			name: "",
			selectData: [],
		};
	},
	props: {
		limit: Number,
		modelValue: { type: String, default: "" },
		printRead: {
			type: Boolean,
			default: () => {
				return false;
			},
		},
		clearable: {
			type: Boolean,
			default: () => {
				return true;
			},
		},
		readonly: {
			type: Boolean,
			default: () => {
				return false;
			},
		},
		disabled: {
			type: Boolean,
			default: () => {
				return false;
			},
		},
	},
	components: {
		userSelect,
	},
	watch: {
		modelValue: {
			handler(newVal) {
				this.selectData = [];
				if (newVal) {
					newVal.split(",").forEach((id) => {
						userService.queryById(id).then((data) => {
							if (data && data.id !== "") {
								this.selectData.push(data);
								this.name = this.selectData
									.map((user) => {
										return user.name;
									})
									.join(",");
							}
						});
					});
				} else {
					this.name = "";
				}
			},
			immediate: true,
			deep: false,
		},
	},
	methods: {
		selectUsersToInput(users) {
			let selectIds = users
				.map((user) => {
					return user.id;
				})
				.join(",");
			this.$emit("update:modelValue", selectIds);
		},
		showUserSelect() {
			this.$refs.userSelect.open(this.selectData);
		},
	},
};
</script>
<style>
.el-form-item__content .el-input-group {
	vertical-align: middle;
}
.el-tag + .el-tag {
	margin-left: 5px;
	margin-bottom: 5px;
}
</style>
