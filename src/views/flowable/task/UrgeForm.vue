<template>
	<v-dialog :close-on-click-modal="false" title="流程催办" v-model="visible">
		<el-form
			ref="inputForm"
			:model="inputForm"
			v-loading="loading"
			@keyup.enter="inputFormSubmit()"
			label-width="120px"
			@submit.prevent
		>
			<el-form-item
				label="催办方式"
				prop="checkedUrgeTypes"
				:rules="[
					{
						required: true,
						message: '催办方式不能为空',
						trigger: 'blur',
					},
				]"
			>
				<el-checkbox-group v-model="inputForm.checkedUrgeTypes">
					<el-checkbox
						v-for="urgeType in urgeTypes"
						:value="urgeType"
						:key="urgeType"
						>{{ urgeType }}</el-checkbox
					>
				</el-checkbox-group>
			</el-form-item>
			<el-form-item
				label="催办内容"
				prop="content"
				:rules="[
					{
						required: true,
						message: '催办内容不能为空',
						trigger: 'blur',
					},
				]"
			>
				<el-input
					type="textarea"
					v-model="inputForm.content"
					:rows="4"
					placeholder="请输入催办内容"
				></el-input>
			</el-form-item>
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
import taskService from "@/api/flowable/taskService";
export default {
	data() {
		return {
			visible: false,
			loading: false,
			urgeTypes: ["系统通知", "站内信", "短信", "邮件"],
			inputForm: {
				checkedUrgeTypes: ["系统通知", "站内信"],
				taskId: "",
				content: "",
			},
		};
	},
	methods: {
		init(taskId) {
			this.visible = true;
			this.inputForm.taskId = taskId;
			this.$refs.inputForm.resetFields();
		},
		// 表单提交
		inputFormSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					taskService
						.urge(this.inputForm)
						.then((data) => {
							this.$message.success(data);
							this.refreshList();
							this.visible = false;
							this.loading = false;
						})
						.catch(() => {
							this.loading = false;
							this.visible = false;
						});
				}
			});
		},
	},
};
</script>
