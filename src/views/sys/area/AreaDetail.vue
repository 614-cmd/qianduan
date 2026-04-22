<template>
	<div v-if="!inputForm.id">
		<el-empty description="请选择左侧区域后操作" :image-size="250">
			<template #image>
				<img src="@/assets/img/empty.svg" />
			</template>
		</el-empty>
	</div>
	<div v-else>
		<el-descriptions
			class="detail"
			:title="inputForm.name"
			:column="1"
			border
		>
			<template #extra>
				<el-button
					icon="edit-filled"
					v-if="hasPermission('sys:area:edit')"
					@click="edit"
					text
				></el-button>
			</template>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">区域名称</div>
				</template>
				{{ inputForm.name }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">区域编码</div>
				</template>
				{{ inputForm.code }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">区域类型</div>
				</template>
				{{ $dictUtils.getDictLabel("sys_area_type", inputForm.type) }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">备注</div>
				</template>
				{{ inputForm.remarks }}
			</el-descriptions-item>
		</el-descriptions>
	</div>
</template>

<script>
export default {
	data() {
		return {
			inputForm: {
				id: "",
				name: "",
				code: "", // 区域编码
				type: "", // 区域类型（1：公司；2：部门）
				remarks: "",
			},
		};
	},
	methods: {
		//表单注入数据
		setData(data) {
			this.inputForm = this.recover(this.inputForm, data);
		},
		edit() {
			this.$emit("toEdit");
		},
	},
};
</script>
