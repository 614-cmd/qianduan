<!--
 * @Descripttion: 区域选择器
-->

<template>
	<template v-if="printRead">
		{{ defaultValue.join("/") }}
	</template>
	<template v-else>
		<el-cascader
			style="width: 100%"
			:readonly="readonly"
			:clearable="clearable"
			:disabled="disabled"
			v-model="defaultValue"
			:props="{
				checkStrictly: true,
				value: 'name',
				label: 'name', // 显示名称
				children: 'children', // 子级字段名
			}"
			:options="areaTreeData"
		/>
	</template>
</template>
<script>
import areaService from "@/api/sys/areaService";
export default {
	props: {
		modelValue: { type: String, default: "" },
		clearable: {
			type: Boolean,
			default: () => {
				return true;
			},
		},
		printRead: {
			type: Boolean,
			default: () => {
				return false;
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
	data() {
		return {
			defaultValue: [],
			dialogVisible: false,
			areaTreeData: [],
		};
	},
	watch: {
		modelValue: {
			handler(val) {
				if (val) {
					this.defaultValue = val.split("/");
				} else {
					this.defaultValue = "";
				}
			},
			immediate: true,
			deep: false,
		},
		defaultValue(val) {
			if (val) {
				this.$emit("update:modelValue", val.join("/"));
			} else {
				this.$emit("update:modelValue", "");
			}
		},
	},
	mounted() {
		areaService.treeData().then((data) => {
			this.areaTreeData = data;
		});
	},
};
</script>
