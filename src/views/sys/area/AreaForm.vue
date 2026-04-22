<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧区域后操作" :image-size="250">
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
					<span>{{ inputForm.name || "新增区域" }}</span>
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
				<el-form-item label="区域名称" prop="name">
					<el-input
						maxlength="50"
						v-model="inputForm.name"
						placeholder="区域名称"
					></el-input>
				</el-form-item>

				<el-form-item label="区域编码" prop="code">
					<el-input
						maxlength="50"
						v-model="inputForm.code"
						placeholder="区域编码"
					></el-input>
				</el-form-item>

				<el-form-item label="区域类型" prop="type">
					<el-select
						v-model="inputForm.type"
						placeholder="请选择"
						style="width: 100%"
					>
						<el-option
							v-for="item in this.$dictUtils.getDictList(
								'sys_area_type'
							)"
							:key="item.value"
							:label="item.label"
							:value="item.value"
						>
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="备注" prop="remarks">
					<el-input
						maxlength="200"
						v-model="inputForm.remarks"
						type="textarea"
						:rows="2"
						placeholder="备注"
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
import areaService from "@/api/sys/areaService";
export default {
	props: ["dataList"],
	data() {
		return {
			loading: false,
			inputForm: {},
			basicForm: {
				id: "",
				name: "",
				code: "", // 区域编码
				type: "", // 区域类型
				remarks: "",
			},
			dataRule: {
				name: [
					{
						required: true,
						message: "名称不能为空",
						trigger: "blur",
					},
				],
				type: [
					{
						required: true,
						message: "区域类型不能为空",
						trigger: "blur",
					},
				],
				code: [
					{
						required: true,
						message: "区域编码不能为空",
						trigger: "blur",
					},
				],
			},
		};
	},
	methods: {
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					areaService
						.save(this.inputForm)
						.then(() => {
							this.loading = false;
							this.$message({
								message: "保存成功!",
								type: "success",
								duration: 1500,
							});
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
