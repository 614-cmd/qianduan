<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧机构后操作" :image-size="250">
			<template #image>
				<img src="@/assets/img/empty.svg" />
			</template>
		</el-empty>
	</template>
	<template v-else>
		<el-descriptions
			class="detail"
			:title="inputForm.name || '新增机构'"
			:column="1"
			border
		>
			<template #extra>
				<el-button
					icon="edit-filled"
					v-if="hasPermission('sys:office:edit')"
					@click="edit"
					text
				></el-button>
			</template>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">机构名称</div>
				</template>
				{{ inputForm.name }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">机构编码</div>
				</template>
				{{ inputForm.code }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">机构类型</div>
				</template>
				{{ $dictUtils.getDictLabel("sys_office_type", inputForm.type) }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">所在地区</div>
				</template>
				{{ inputForm.area }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">详细地址</div>
				</template>
				{{ inputForm.address }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">邮政编码</div>
				</template>
				{{ inputForm.zipCode }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">
						{{
							inputForm.type === "1" ? "公司负责人" : "部门负责人"
						}}
					</div>
				</template>
				{{ inputForm.master }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">联系电话</div>
				</template>
				{{ inputForm.phone }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">是否可用</div>
				</template>
				{{ $dictUtils.getDictLabel("yes_no", inputForm.useable) }}
			</el-descriptions-item>
			<el-descriptions-item>
				<template #label>
					<div class="cell-item">备注</div>
				</template>
				{{ inputForm.remarks }}
			</el-descriptions-item>
		</el-descriptions>
	</template>
</template>

<script>
export default {
	data() {
		return {
			inputForm: {
				id: "",
				name: "",
				sort: "30",
				area: "", // 归属区域
				code: "", // 机构编码
				type: "", // 机构类型（1：公司；2：部门）
				address: "", // 联系地址
				zipCode: "", // 邮政编码
				master: "", // 负责人
				phone: "", // 电话
				fax: "", // 传真
				email: "", // 邮箱
				useable: "", // 是否可用
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
<style lang="less">
.detail {
	padding: 20px;
	.el-descriptions__table {
		td.el-descriptions__label {
			width: 250px;
		}
	}
}
</style>
