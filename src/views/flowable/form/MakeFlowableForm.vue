<template>
	<div class="e-fm">
		<el-dialog
			fullscreen
			title="设计流程表单"
			center
			:close-on-click-modal="false"
			v-model="visible"
		>
			<el-form
				:model="inputForm"
				ref="inputForm"
				v-loading="loading"
				:class="method === 'view' ? 'readonly' : ''"
				:disabled="method === 'view'"
				label-width="120px"
			>
				<fm-making-form
					ref="formDesign"
					:data="options"
					v-if="visible"
					:uploadPath="`${this.$http.BASE_URL}/file/upload?uploadPath=/formbuilder`"
					preview
					clearable
					tab-list
					generate-json
					:bindDataTable="dmForm.autoCreate !== '1'"
					:ds="dmForm.dataSource"
					:tableName="dmForm.tableName"
				>
					<template v-slot:action> </template>
				</fm-making-form>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button
						type="primary"
						v-if="method != 'view'"
						@click="doSubmit(0)"
						v-noMoreClick
						>保存草稿</el-button
					>
					<el-button
						type="primary"
						v-if="method != 'view'"
						@click="doSubmit(1)"
						v-noMoreClick
						>保存并发布</el-button
					>
					<el-button @click="visible = false">放弃</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script>
import formDefinitionJsonService from "@/api/flowable/formDefinitionJsonService";
import formDefinitionService from "@/api/flowable/formDefinitionService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			options: {},
			dmForm: {
				autoCreate: "1",
				dataSource: {
					id: "master",
					name: "本地数据库",
					enName: "master",
					type: "",
				},
				tableName: "",
			},
			inputForm: {
				id: "",
				version: "",
				formDefinitionId: "",
				status: "",
				isPrimary: "",
				json: "",
			},
		};
	},
	methods: {
		init(formDefinitionId, jsonId) {
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.inputForm.json = "";
				this.inputForm.version = "";
				this.inputForm.id = jsonId;
				this.inputForm.formDefinitionId = formDefinitionId;
				formDefinitionService
					.queryById(formDefinitionId)
					.then((data) => {
						this.dmForm = this.recover(this.dmForm, data);
					});
				if (jsonId) {
					// 修改或者查看
					this.loading = true;
					formDefinitionJsonService.queryById(jsonId).then((data) => {
						this.inputForm = this.recover(this.inputForm, data);
						if (this.inputForm.json) {
							this.options = JSON.parse(this.inputForm.json);
							this.$refs.formDesign.setJSON(this.options);
						}
						this.loading = false;
					});
				} else {
					this.$refs.formDesign.setJSON({
						list: [],
						config: {
							labelWidth: 100,
							labelPosition: "right",
							size: "default",
							customClass: "",
							ui: "element",
							layout: "horizontal",
							width: "100%",
							hideLabel: false,
							hideErrorMessage: false,
							eventScript: [
								{ key: "mounted", name: "mounted", func: "" },
								{ key: "refresh", name: "refresh", func: "" },
							],
							dataSource: [],
						},
					});
				}
			});
		},
		// 表单提交
		doSubmit(status) {
			this.inputForm.json = JSON.stringify(
				this.$refs.formDesign.getJSON()
			);
			this.inputForm.status = status;
			this.inputForm.isPrimary = "1";
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					formDefinitionJsonService
						.save(this.inputForm)
						.then((data) => {
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
						});
				}
			});
		},
	},
};
</script>
<style lang="less">
.e-fm {
	.el-dialog__body {
		height: calc(100% - 150px);
		.el-form {
			height: 100%;
		}
	}
}
</style>
