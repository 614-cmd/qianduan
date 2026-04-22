<template>
	<el-container class="jp-container">
		<el-header style="padding: 13px 15px">
			<el-page-header
				content="数据模型配置"
				@back="goBack"
			></el-page-header>
			<div class="do">
				<el-button
					v-if="$route.query.method != 'view'"
					type="primary"
					icon="check"
					@click="doSubmit"
					>保存数据模型</el-button
				>
			</div>
		</el-header>
		<el-main>
			<el-row :gutter="10" v-loading="loading">
				<el-col :span="12">
					<el-card shadow="never">
						<el-form
							:model="inputForm"
							ref="inputForm"
							v-loading="loading"
							label-width="150px"
						>
							<el-form-item
								label="目标数据库"
								prop="dataSource.id"
								:rules="[
									{
										required: true,
										message: '目标数据库不能为空',
										trigger: 'blur',
									},
								]"
							>
								<el-tree-select
									ref="dataSource"
									value-key="id"
									:data="dataSourceTreeData"
									v-model="inputForm.dataSource.id"
									style="width: 100%"
									:clearable="true"
									:accordion="true"
								/>
							</el-form-item>
							<el-form-item
								label="模型名称"
								prop="name"
								:rules="[
									{
										required: true,
										message: '模型名称不能为空',
										trigger: 'blur',
									},
								]"
							>
								<el-input
									v-model="inputForm.name"
									placeholder="请填写模型名称"
								></el-input>
							</el-form-item>
							<div class="el-form-item is-required">
								<label
									for="name"
									class="el-form-item__label"
									style="width: 150px"
									>sql语句</label
								>
								<div
									class="el-form-item__content"
									style="display: block"
								>
									<VAceEditor
										v-model:value="inputForm.sqlCmd"
										lang="sql"
										theme="chrome"
										style="height: 300px"
										:options="{ useWorker: true }"
									/>
								</div>
							</div>
						</el-form>
					</el-card>
				</el-col>
				<el-col :span="12">
					<el-card shadow="never">
						<el-table :data="paramForm.tableData">
							<el-table-column type="index" width="50">
							</el-table-column>
							<el-table-column prop="field" label="参数名">
								<template #default="scope">
									<el-input
										v-model="scope.row.field"
										placeholder="请输入内容"
									></el-input>
								</template>
							</el-table-column>
							<el-table-column prop="defaultValue" label="默认值">
								<template #default="scope">
									<el-input
										v-model="scope.row.defaultValue"
										placeholder="请输入内容"
									></el-input>
								</template>
							</el-table-column>
							<el-table-column fixed="right" label="操作">
								<template #default="scope">
									<el-button
										@click.prevent="
											deleteRow(
												scope.$index,
												paramForm.tableData
											)
										"
										text
									>
										移除
									</el-button>
								</template>
							</el-table-column>
						</el-table>
						<el-button
							type="primary"
							@click="addRow"
							icon="plus"
							style="margin-top: 10px; margin-bottom: 10px"
							>增加参数</el-button
						>
						<el-alert
							title="SQL中添加参数的方式：格式：{#参数名#}，示例：select * from table where id = '{#ID#}'"
							:closable="false"
							type="success"
						>
						</el-alert>
						<el-button
							type="primary"
							@click="doPreviewTable"
							icon="edit-filled"
							style="margin-top: 10px; margin-bottom: 10px"
							>解析</el-button
						>
						<el-button
							type="primary"
							@click="doPreviewData"
							icon="view-filled"
							style="margin-top: 10px; margin-bottom: 10px"
						>
							预览数据</el-button
						>
					</el-card>
				</el-col>
				<el-col :span="24" style="margin-top: 10px">
					<el-card shadow="never">
						<template #header>
							<div class="card-header">
								<span>数据源列配置</span>
							</div>
						</template>
						<el-table
							:data="columnForm.columnList"
							style="width: 100%"
						>
							<el-table-column prop="name" label="字段名">
							</el-table-column>
							<el-table-column prop="type" label="类型">
							</el-table-column>
							<el-table-column prop="label" label="标签">
								<template #default="scope">
									<el-input
										v-model="scope.row.label"
									></el-input>
								</template>
							</el-table-column>
							<el-table-column>
								<template v-slot:header>
									<el-checkbox
										v-model="columnForm.isNeedAll"
										:indeterminate="isIndeterminate"
										@change="handleCheckAllChange"
										>参与分析</el-checkbox
									>
								</template>
								<template #default="scope">
									<el-checkbox
										v-model="scope.row.isNeed"
										@change="handleCheckedNeedChange"
									></el-checkbox>
								</template>
							</el-table-column>
						</el-table>
					</el-card>
				</el-col>
			</el-row>
			<v-dialog
				title="数据预览"
				:close-on-click-modal="false"
				v-model="previewVisible"
			>
				<el-tabs type="border-card">
					<el-tab-pane label="HTML">
						<el-table
							:data="previewData.json"
							style="height: 500px; width: 100%"
						>
							<el-table-column
								v-for="(
									value, key, index
								) in previewData.column"
								:key="index"
								:prop="key"
								:label="key"
								width="180"
							>
							</el-table-column>
						</el-table>
					</el-tab-pane>
					<el-tab-pane label="JSON">
						<VAceEditor
							v-model:value="previewData.json_str"
							lang="json"
							theme="twilight"
							style="height: 300px"
							:options="{ useWorker: true }"
						/>
					</el-tab-pane>
					<el-tab-pane label="XML">
						<VAceEditor
							v-model:value="previewData.xml"
							lang="xml"
							theme="twilight"
							style="height: 300px"
							:options="{ useWorker: true }"
						/>
					</el-tab-pane>
				</el-tabs>
			</v-dialog>
		</el-main>
	</el-container>
</template>

<script>
import useTabs from "@/utils/useTabs";
import omit from "lodash.omit";
import dataSetService from "@/api/database/dataSetService";
import dataSourceService from "@/api/database/dataSourceService";
import { VAceEditor } from "vue3-ace-editor";
import "ace-builds/src-noconflict/mode-json";
import "ace-builds/src-noconflict/mode-xml";
import "ace-builds/src-noconflict/mode-sql";
import "ace-builds/src-noconflict/theme-chrome";
import "ace-builds/src-noconflict/theme-twilight";
import ace from "ace-builds";
import workerJsonUrl from "ace-builds/src-noconflict/worker-json?url";
ace.config.setModuleUrl("ace/mode/json_worker", workerJsonUrl);
export default {
	data() {
		return {
			title: "",
			method: "",
			loading: false,
			previewVisible: false,
			isIndeterminate: false,
			dataSourceTreeData: [],
			inputForm: {
				id: "",
				dataSource: {
					id: "",
				},
				name: "",
				sqlCmd: "",
			},
			paramForm: {
				tableData: [],
			},
			previewData: {
				column: [],
				html: "",
				json: [],
				json_str: "",
				xml: "",
			},
			columnForm: {
				isNeedAll: true,
				columnList: [],
			},
		};
	},
	created() {
		dataSourceService.treeData().then((data) => {
			this.dataSourceTreeData = data;
		});
	},
	mounted() {
		this.$refs["inputForm"].resetFields();
		this.paramForm.tableData = [];
		this.columnForm.columnList = [];
		this.inputForm.id = this.$route.query.id;
		if (this.inputForm.id) {
			dataSetService.queryById(this.inputForm.id).then((data) => {
				this.inputForm = this.recover(this.inputForm, data);
				this.columnForm.columnList = data.columnList;
				this.paramForm.tableData = data.params;
			});
		}
	},
	components: {
		VAceEditor,
	},
	methods: {
		deleteRow(index, rows) {
			rows.splice(index, 1);
		},
		addRow() {
			this.paramForm.tableData.push({ field: "", defaultValue: "" });
		},
		doPreviewData: function () {
			let fields = this.paramForm.tableData.map((row) => {
				return row.field;
			});
			let defaultValues = this.paramForm.tableData.map((row) => {
				return row.defaultValue;
			});
			defaultValues.push("");
			dataSetService
				.exec({
					dataSourceId: this.inputForm.dataSource.id,
					sql: this.inputForm.sqlCmd,
					field: fields,
					defaultValue: defaultValues,
				})
				.then((data) => {
					this.previewData.json = data.json;
					this.previewData.json_str = JSON.stringify(data.json);
					this.previewData.xml = data.xml;
					this.previewData.column = data.json[0] || {};
					this.previewVisible = true;
				});
		},
		doPreviewTable() {
			let fields = this.paramForm.tableData.map((row) => {
				return row.field;
			});
			let defaultValues = this.paramForm.tableData.map((row) => {
				return row.defaultValue;
			});
			defaultValues.push("");
			dataSetService
				.getMeta({
					dataSourceId: this.inputForm.dataSource.id,
					sql: this.inputForm.sqlCmd,
					field: fields,
					defaultValue: defaultValues,
				})
				.then((data) => {
					this.columnForm.isNeedAll = true;
					data.forEach((column) => {
						column.isNeed = true;
					});
					this.columnForm.columnList = JSON.parse(
						JSON.stringify(data)
					).map((column) => {
						return (
							this.columnForm.columnList.filter((oldColumn) => {
								return oldColumn.name === column.name;
							})[0] || column
						);
					});
				})
				.catch(() => {
					this.loading = false;
				});
		},
		handleCheckAllChange(val) {
			this.columnForm.columnList.forEach((column) => {
				column.isNeed = val;
			});
			this.columnForm.columnList = JSON.parse(
				JSON.stringify(this.columnForm.columnList)
			);
			this.isIndeterminate = false;
		},
		handleCheckedNeedChange() {
			let trueCount = 0;
			let falseCount = 0;
			this.columnForm.columnList.forEach((column) => {
				if (column.isNeed) {
					trueCount++;
				} else {
					falseCount++;
				}
			});
			this.isIndeterminate = trueCount > 0 && falseCount > 0;
			this.columnForm.isNeedAll = trueCount > 0;
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					let params = [];
					let columnList = [];
					this.paramForm.tableData.forEach((item, index) => {
						item.sort = index;
						params.push(omit(item, "id"));
					});
					this.columnForm.columnList.forEach((item, index) => {
						item.sort = index;
						columnList.push(omit(item, "id"));
					});
					dataSetService
						.save({
							...this.inputForm,
							params: params,
							columnList: columnList,
						})
						.then(() => {
							this.loading = false;
							this.goBack();
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
		goBack() {
			useTabs.close();
			this.$router.push("/database/datamodel/DataSetList");
		},
	},
};
</script>

<style lang="less" scoped>
.jp-container {
	margin: -10px;
}
</style>
