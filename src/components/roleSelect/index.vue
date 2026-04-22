<!--
 * @Descripttion: 角色选择器
-->

<template>
	<template v-if="printRead">
		{{
			defaultValue
				.map((dm) => roleList.find((item) => item.id == dm).name)
				.join("、")
		}}
	</template>
	<template v-else>
		<el-select
			multiple
			style="width: 100%"
			:readonly="readonly"
			:clearable="clearable"
			:disabled="disabled"
			v-model="defaultValue"
		>
			<el-option
				v-for="item in roleList"
				:key="item.id"
				:label="item.name"
				:value="item.id"
			>
				<el-avatar :size="30">
					{{ item.name.substring(0, 1) }}
				</el-avatar>
				{{ item.name }}
			</el-option>
		</el-select>
	</template>
</template>

<script>
import roleService from "@/api/sys/roleService";
export default {
	props: {
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
		type: { type: String, default: "" },
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
	data() {
		return {
			defaultValue: [],
			dialogVisible: false,
			roleList: [],
		};
	},
	watch: {
		modelValue: {
			handler(val) {
				if (val) {
					this.defaultValue = val.split(",");
				} else {
					this.defaultValue = [];
				}
			},
			immediate: true,
			deep: false,
		},
		defaultValue(val) {
			this.$emit("update:modelValue", val.join(","));
		},
	},
	mounted() {
		roleService.list({ size: -1 }).then((data) => {
			this.roleList = data.records;
		});
	},
};
</script>
