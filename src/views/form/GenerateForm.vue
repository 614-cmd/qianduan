<template>
	<div>
		<v-dialog
			:title="title"
			:close-on-click-modal="false"
			v-model="visible"
		>
			<fm-generate-form
				:edit="method !== 'view'"
				:data="options"
				v-if="visible"
				v-loading="loading"
				:class="method === 'view' ? 'readonly' : ''"
				ref="generateForm"
			>
			</fm-generate-form>
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
		</v-dialog>
	</div>
</template>

<script>
import makeFormService from "@/api/form/makeFormService";
import generateFormService from "@/api/form/generateFormService";
export default {
	data() {
		return {
			title: "",
			method: "",
			beanId: "",
			visible: false,
			loading: false,
			dataBindMap: new Map(),
			options: { list: [] },
		};
	},
	activated() {
		this.createForm();
	},
	components: {},
	methods: {
		init(method, formId, beanId) {
			this.method = method;
			this.beanId = beanId;
			if (method === "add") {
				this.title = `新建`;
			} else if (method === "edit") {
				this.title = "修改";
			} else if (method === "view") {
				this.title = "查看";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.generateForm.reset();
				if (beanId) {
					this.loading = true;
					generateFormService
						.queryById({ formId: formId, id: beanId })
						.then((data) => {
							this.loading = false;
							for (let key in data) {
								let dataField = this.dataBindMap.get(key);
								if (
									dataField &&
									(dataField["type"] === "checkbox" ||
										dataField["type"] === "imgupload" ||
										dataField["type"] === "cascader" ||
										dataField["type"] === "table" ||
										dataField["type"] === "group" ||
										dataField["type"] === "subform" ||
										(dataField["type"] === "select" &&
											dataField.options.multiple) ||
										(dataField["type"] === "treeselect" &&
											dataField.options.multiple) ||
										dataField["type"] === "fileupload")
								) {
									if (
										data[key] &&
										typeof data[key] === "string"
									) {
										data[key] = JSON.parse(data[key]);
									} else if (!data[key]) {
										data[key] = [];
									}
								}
								if (
									dataField &&
									(dataField["type"] === "number" ||
										dataField["options"].dataType ===
											"number")
								) {
									if (
										data[key] !== undefined &&
										data[key] !== "" &&
										typeof data[key] === "string"
									) {
										data[key] = JSON.parse(data[key]);
									}
								}
							}
							this.$refs.generateForm.setData(data);
						});
				}
			});
		},
		generateModel(genList) {
			for (let i = 0; i < genList.length; i++) {
				if (genList[i].type === "grid") {
					genList[i].columns.forEach((item) => {
						this.generateModel(item.list);
					});
				} else if (genList[i].type === "tabs") {
					genList[i].tabs.forEach((item) => {
						this.generateModel(item.list);
					});
				} else {
					// 处理老版本没有dataBind值的情况，默认绑定数据
					if (genList[i].options.dataBind) {
						this.dataBindMap.set(genList[i].model, genList[i]);
					}
				}
			}
		},
		createForm() {
			makeFormService.queryById(this.$route.query.id).then((data) => {
				if (data.source) {
					this.options = JSON.parse(data.source);
				} else {
					this.options = {
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
					};
				}
				this.dataBindMap.clear();
				this.generateModel(this.options.list);
				console.log(this.options.list);
			});
		},

		// 表单提交
		doSubmit() {
			// 自定义js ，保存前执行
			if (this.options.config.eventType === "1") {
				// eslint-disable-next-line no-new-func
				new Function(`return ${this.options.config.customJs}`)();
			}
			this.loading = true;
			this.$refs.generateForm.getData().then((data) => {
				if (this.beanId) {
					data.id = this.beanId;
				}
				generateFormService
					.save({
						formId: this.$route.query.id,
						data: JSON.stringify(data),
					})
					.then((data) => {
						this.visible = false;
						this.loading = false;
						this.$message.success(data);
						this.$emit("refreshDataList");

						// 自定义js 保存后执行
						if (this.options.config.eventType === "2") {
							// eslint-disable-next-line no-new-func
							new Function(
								`return ${this.options.config.customJs}`
							)();
						}
					});
			});
		},
	},
};
</script>
