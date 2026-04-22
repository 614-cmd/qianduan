<!--
 * @Descripttion: 角色选择器
-->

<template>
	<template v-if="printRead">
		{{
			defaultValue
				.map((dm) => postList.find((item) => item.id == dm).name)
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
				v-for="item in postList"
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
import postService from "@/api/sys/postService";
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
			postList: [],
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
		postService.list({ size: -1 }).then((data) => {
			this.postList = data.records;
		});
	},
};
</script>
