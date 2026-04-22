<template>
	<div>
		<v-dialog
			:title="title"
			:close-on-click-modal="false"
			v-model="visible"
		>
			<el-form
				:model="inputForm"
				ref="inputForm"
				v-loading="loading"
				:class="method === 'view' ? 'readonly' : ''"
				:disabled="method === 'view'"
				@keyup.enter="doSubmit()"
				label-width="120px"
				@submit.prevent
			>
				<el-row :gutter="15">
					<el-col :span="24">
						<el-form-item
							label="日程内容"
							prop="title"
							:rules="[
								{
									required: true,
									message: '日程内容不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								type="textarea"
								v-model="inputForm.title"
								placeholder=""
							></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button
						type="danger"
						v-if="method === 'edit'"
						icon="del-filled"
						@click="del"
						>删除</el-button
					>
					<el-button @click="visible = false" icon="circle-close"
						>关闭</el-button
					>
					<el-button
						type="primary"
						v-if="method != 'view'"
						@click="doSubmit()"
						icon="circle-check"
						v-noMoreClick
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import myCalendarService from "@/api/calendar/myCalendarService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			inputForm: {
				id: "",
				title: "",
				startDate: "",
				endDate: "",
			},
		};
	},
	methods: {
		init(method, id, startDate, endDate) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建日程`;
			} else if (method === "edit") {
				this.title = "修改日程";
			} else if (method === "view") {
				this.title = "查看日程";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					myCalendarService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							this.loading = false;
						});
				} else {
					this.inputForm.startDate = this.moment(startDate).format(
						"YYYY-MM-DD HH:mm:ss"
					);
					this.inputForm.endDate = this.moment(endDate).format(
						"YYYY-MM-DD HH:mm:ss"
					);
				}
			});
		},
		del() {
			this.loading = true;
			myCalendarService.delete(this.inputForm.id).then((data) => {
				this.visible = false;
				this.$message.success(data);
				this.$emit("refreshDataList");
				this.loading = false;
			});
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					myCalendarService
						.save(this.inputForm)
						.then((data) => {
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
							this.loading = false;
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
	},
};
</script>
