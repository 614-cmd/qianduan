<template>
	<v-dialog :close-on-click-modal="false" v-model="visible">
		<el-form
			:model="inputForm"
			v-loading="loading"
			status-icon
			ref="inputForm"
			@keyup.enter="inputFormSubmit()"
			label-width="120px"
			@submit.prevent
		>
			<el-row :gutter="15">
				<el-col :span="24">
					<el-form-item
						label="流程分类"
						prop="category"
						:rules="[
							{
								required: true,
								message: '流程分类不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-select
							v-model="inputForm.category"
							placeholder="请选择"
							style="width: 100%"
						>
							<el-option
								v-for="item in this.$dictUtils.getDictList(
									'act_category'
								)"
								:key="item.value"
								:label="item.label"
								:value="item.value"
							>
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="24">
					<el-form-item
						label="流程文件"
						prop="file"
						:rules="[
							{
								required: true,
								message: '流程文件不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-upload
							class="upload-demo"
							ref="upload"
							:limit="1"
							:on-change="handleChange"
							:action="`${this.$http.BASE_URL}/rest/import-process-model`"
							:on-success="handleSuccess"
							:on-error="handleError"
							:before-upload="beforeUpload"
							:auto-upload="false"
							:show-file-list="true"
						>
							<el-button type="primary">选取文件</el-button>
							<template #tip>
								<div class="el-upload__tip">
									只允许导入“zip、bar、bpmn、bpmn20.xml”格式文件！
								</div>
							</template>
						</el-upload>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
				<el-button
					type="primary"
					@click="inputFormSubmit()"
					icon="circle-check"
					v-noMoreClick
					>确定</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
export default {
	data() {
		return {
			visible: false,
			loading: false,
			inputForm: {
				file: "",
				category: "",
			},
		};
	},
	methods: {
		init() {
			this.visible = true;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				this.$refs.upload.clearFiles();
			});
		},
		beforeUpload(file) {
			const isExcel =
				file.name.indexOf(".zip") >= 0 ||
				file.name.indexOf(".bar") >= 0 ||
				file.name.indexOf(".bpmn") >= 0 ||
				file.name.indexOf(".bpmn20.xml") >= 0;
			const isLt2M = file.size / 1024 / 1024 < 10;
			if (!isExcel) {
				this.$message.error("只能上传zip、bar、bpmn、bpmn20.xml文件!");
				return false;
			}
			if (!isLt2M) {
				this.$message.error("上传文件大小不能超过 10MB!");
				return false;
			}
			this.loading = true;
			return true;
		},
		handleSuccess(res) {
			this.$emit("refreshList");
			window.open(
				`${
					import.meta.env.VITE_APP_BASE
				}/static/modler/index.html#/editor/${res.id}`
			);
			this.visible = false;
			this.loading = false;
		},
		handleError({ message }) {
			this.$message.error(message);
			this.loading = false;
		},
		handleChange(file) {
			this.inputForm.file = file.name;
		},
		// 表单提交
		inputFormSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.$refs.upload.submit();
				}
			});
		},
	},
};
</script>
