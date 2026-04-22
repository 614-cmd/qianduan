<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧数据后操作" :image-size="250">
			<template #image>
				<img src="@/assets/img/empty.svg" />
			</template>
		</el-empty>
	</template>
	<template v-else>
		<el-card
			shadow="never"
			style="border: 0; padding: 10px"
			:body-style="{ border: 0, padding: '20px' }"
		>
			<template #header>
				<div class="card-header">
					<span>{{ inputForm.name || "新增类型" }}</span>
					<el-button icon="back" @click="toView" text></el-button>
				</div>
			</template>
			<el-form
				label-width="100px"
				:model="inputForm"
				:rules="dataRule"
				ref="inputForm"
				v-loading="loading"
				@submit.prevent
			>
				<el-form-item
					label="名称"
					prop="name"
					:rules="[
						{
							required: true,
							message: '名称不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.name"
						placeholder="请填写名称"
					></el-input>
				</el-form-item>
				<el-form-item label="备注信息" prop="remarks">
					<el-input
						type="textarea"
						v-model="inputForm.remarks"
						placeholder="请填写备注信息"
					></el-input>
				</el-form-item>
				<el-form-item>
					<el-button
						type="primary"
						@click="doSubmit()"
						icon="circle-check"
						v-noMoreClick
						>确定</el-button
					>
				</el-form-item>
			</el-form>
		</el-card>
	</template>
</template>

<script>
import actCategoryService from "@/api/flowable/actCategoryService";
export default {
	props: ["dataList"],
	data() {
		return {
			loading: false,
			inputForm: {},
			basicForm: {
				id: "",
				parent: {
					id: "",
				},
				name: "",
				sort: 0,
				remarks: "",
			},
		};
	},
	methods: {
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					actCategoryService
						.save(this.inputForm)
						.then(() => {
							this.loading = false;
							this.$message({
								message: "保存成功!",
								type: "success",
								duration: 1500,
							});
							this.$emit("refreshDataList");
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
		//表单注入数据
		setData(data) {
			this.inputForm = this.pickProperties(
				data,
				JSON.parse(JSON.stringify(this.basicForm))
			);
		},
		toView() {
			this.$emit("toView");
		},
	},
};
</script>
