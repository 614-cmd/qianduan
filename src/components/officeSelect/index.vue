<!--
 * @Descripttion: 部门选择器
-->

<template>
	<template v-if="printRead">
		{{ getTreeText(defaultValue, officeTreeData) }}
	</template>
	<template v-else>
		<el-tree-select
			value-key="id"
			:props="{
				label: 'name', // 显示名称
				children: 'children', // 子级字段名
			}"
			check-strictly
			:data="officeTreeData"
			style="width: 100%"
			:readonly="readonly"
			:clearable="clearable"
			:disabled="disabled"
			v-model="defaultValue"
		>
			<template #default="{ node, data }">
				<span class="custom-tree-node el-tree-node__label">
					<span class="label">
						<span>
							<i
								v-if="data.type === '1'"
								class="iconfont icon-qiye"
							></i>
							<i v-else class="iconfont icon-bumen"></i>
							{{ node.label }}
						</span>
					</span>
				</span>
			</template>
		</el-tree-select>
	</template>
</template>

<script>
import officeService from "@/api/sys/officeService";
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
		// 默认“”公司部门都可以选择，type设置为1 只可以选择公司，type设置为2 只可以选择部门
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
			defaultValue: "",
			dialogVisible: false,
			officeTreeData: [],
		};
	},
	watch: {
		modelValue: {
			handler(val) {
				this.defaultValue = val
			},
			immediate: true,
			deep: false,
		},
		defaultValue(val) {
			this.$emit("update:modelValue", val);
		},
	},
	mounted() {
		officeService.treeData({ type: this.type }).then((data) => {
			this.officeTreeData = data;
		});
	},
	methods: {
		getTreeText(value, options) {
			for (let i = 0; i < options.length; i++) {
				let currentOpt = options[i];

				if (currentOpt.id == value) {
					return currentOpt.name;
				}

				if (currentOpt.children && currentOpt.children.length > 0) {
					let res = this.getTreeText(value, currentOpt.children);

					if (res == "-") {
						continue;
					} else {
						return res;
					}
				}
			}

			return "-";
		},
	},
};
</script>
