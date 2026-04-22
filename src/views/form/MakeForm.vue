<template>
	<div class="e-fm">
		<el-dialog
			:title="title"
			center
			fullscreen
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
					:bindDataTable="inputForm.autoCreate !== '1'"
					ref="formDesign"
					:data="options"
					v-if="visible"
					:uploadPath="`${this.$http.BASE_URL}/file/upload?uploadPath=/formbuilder`"
					preview
					:ds="inputForm.dataSource"
					:tableName="inputForm.tableName"
					tab-list
					generate-json
					clearable
				>
				</fm-making-form>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
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
		</el-dialog>
	</div>
</template>

<script>
import makeFormService from "@/api/form/makeFormService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			options: {},
			inputForm: {
				id: "",
				code: "",
				autoCreate: "1",
				dataSource: {
					id: "master",
					name: "本地数据库",
					enName: "master",
					type: "",
				},
				name: "",
				tableName: "",
				source: "",
				version: "",
				remarks: "",
			},
		};
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建数据表单`;
			} else if (method === "edit") {
				this.title = "修改数据表单";
			} else if (method === "view") {
				this.title = "查看数据表单";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				if (method === "add") {
					// this.$refs.formDesign.setJSON( {"list":[],"config":{"labelWidth":100,"labelPosition":"right","size":"default","customClass":"","ui":"element","layout":"horizontal","width":"100%","hideLabel":false,"hideErrorMessage":false,"eventScript":[{"key":"mounted","name":"mounted","func":""},{"key":"refresh","name":"refresh","func":""}],"dataSource":[]}})
				}
				// this.options = {}
				this.inputForm.name = "";
				this.inputForm.tableName = "";
				this.inputForm.source = "";
				this.inputForm.version = "";
				this.inputForm.remarks = "";
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					makeFormService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							if (this.inputForm.source) {
								this.options = JSON.parse(
									this.inputForm.source
								);
								this.$refs.formDesign.setJSON(this.options);
							} else {
								// this.$refs.formDesign.setJSON( {"list":[],"config":{"labelWidth":100,"labelPosition":"right","size":"default","customClass":"","ui":"element","layout":"horizontal","width":"100%","hideLabel":false,"hideErrorMessage":false,"eventScript":[{"key":"mounted","name":"mounted","func":""},{"key":"refresh","name":"refresh","func":""}],"dataSource":[]}})
							}
							this.loading = false;
						});
				}
			});
		},
		handleSubmit() {},
		// 表单提交
		doSubmit() {
			this.inputForm.source = JSON.stringify(
				this.$refs.formDesign.getJSON()
			);
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					makeFormService
						.saveFormSource(this.inputForm)
						.then((data) => {
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
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
