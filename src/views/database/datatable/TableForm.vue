<template>
	<div class="page">
		<div class="el-scrollbar__wrap">
			<div class="el-scrollbar__view">
				<el-container>
					<el-header>
						<el-page-header @back="goBack">
							<template #content>
								<div class="flex items-center">
									<span class="text-large font-600 mr-3">
										编辑表
									</span>
								</div>
							</template>
							<template #extra>
								<div class="flex items-center">
									<el-button
										type="primary"
										icon="circle-check"
										@click="doSubmit"
										>保存</el-button
									>
								</div>
							</template>
						</el-page-header>
					</el-header>
					<el-main>
						<el-form
							:model="inputForm"
							ref="inputForm"
							v-loading="loading"
							label-width="150px"
						>
							<el-row :gutter="20" v-loading="loading">
								<el-col :span="24">
									<el-col :span="12">
										<el-form-item
											label="表名"
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
												:disabled="method === 'edit'"
												placeholder=""
											></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="12">
										<el-form-item
											label="模式"
											prop="schema"
										>
											<el-input
												v-model="inputForm.schema"
												:disabled="true"
												placeholder=""
											></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="12">
										<el-form-item
											label="数据库"
											prop="catalog"
										>
											<el-input
												v-model="inputForm.catalog"
												:disabled="true"
												placeholder=""
											></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="12">
										<el-form-item
											label="描述"
											prop="description"
										>
											<el-input
												v-model="inputForm.description"
												placeholder="请填写描述"
											></el-input>
										</el-form-item>
									</el-col>
								</el-col>
								<el-col :span="24">
									<vxe-toolbar
										:refresh="{ query: refreshList }"
										export
										print
										custom
									>
										<template #buttons>
											<el-button
												type="default"
												icon="plus"
												@click="addRow"
												>增加一列</el-button
											>
										</template>
										<template #tools> </template>
									</vxe-toolbar>
									<vxe-table
										:data="inputForm.columns"
										border="inner"
										auto-resize
										ref="table"
										resizable
										:loading="loading"
										size="small"
									>
										<vxe-column field="name" title="列名">
											<template #default="scope">
												<el-input
													v-model="scope.row.name"
												></el-input>
											</template>
										</vxe-column>
										<vxe-column field="type" title="类型">
											<template #default="scope">
												<el-select
													v-model="scope.row.type"
													placeholder="请选择"
													style="width: 100%"
												>
													<el-option
														v-for="(
															item, index
														) in columnTypes"
														:key="index"
														:label="item.name"
														:value="item.name"
													>
													</el-option>
												</el-select>
											</template>
										</vxe-column>
										<vxe-column field="size" title="长度">
											<template #default="scope">
												<el-input
													v-model="scope.row.size"
												></el-input>
											</template>
										</vxe-column>
										<vxe-column
											field="defaultValue"
											title="默认值"
										>
											<template #default="scope">
												<el-input
													v-model="
														scope.row.defaultValue
													"
												></el-input>
											</template>
										</vxe-column>
										<vxe-column title="主键">
											<template #default="scope">
												<el-checkbox
													v-model="
														scope.row.primaryKey
													"
													>主键</el-checkbox
												>
											</template>
										</vxe-column>
										<vxe-column title="非空">
											<template #default="scope">
												<el-checkbox
													v-model="scope.row.required"
													>非空</el-checkbox
												>
											</template>
										</vxe-column>
										<vxe-column title="自增">
											<template #default="scope">
												<el-checkbox
													v-model="
														scope.row.autoIncrement
													"
													>自增</el-checkbox
												>
											</template>
										</vxe-column>
										<vxe-column
											field="description"
											title="说明"
										>
											<template #default="scope">
												<el-input
													v-model="
														scope.row.description
													"
												></el-input>
											</template>
										</vxe-column>
										<vxe-column
											title="操作"
											width="200px"
											fixed="right"
											align="center"
										>
											<template #default="scope">
												<el-button
													@click.prevent="
														deleteRow(
															scope.rowIndex,
															inputForm.columns
														)
													"
													type="danger"
													text
												>
													移除
												</el-button>
											</template>
										</vxe-column>
									</vxe-table>
								</el-col>
							</el-row>
						</el-form>
					</el-main>
				</el-container>
			</div>
		</div>
	</div>
</template>

<script>
import useTabs from "@/utils/useTabs";
import dataTableService from "@/api/database/dataTableService";
export default {
	data() {
		return {
			title: "",
			method: "",
			loading: false,
			columnTypes: [],
			inputForm: {
				dataSourceId: "",
				name: "",
				schema: "",
				catalog: "",
				description: "",
				columns: [],
			},
		};
	},
	mounted() {
		this.$refs.inputForm.resetFields();
		this.inputForm.columns = [];
		this.inputForm.dataSourceId = this.$route.query.dataSourceId;
		this.inputForm.name = this.$route.query.name;
		this.method = this.$route.query.method;
		if (this.method === "edit") {
			dataTableService
				.queryAlter(this.inputForm.dataSourceId, this.inputForm.name)
				.then((data) => {
					this.inputForm = this.recoverNotNull(
						this.inputForm,
						data.table
					);
					this.columnTypes = data.columnTypes;
				});
		} else {
			dataTableService
				.queryCreate(this.inputForm.dataSourceId)
				.then((data) => {
					this.inputForm = this.recoverNotNull(
						this.inputForm,
						data.table
					);
					this.columnTypes = data.columnTypes;
				});
		}
	},
	methods: {
		deleteRow(index, rows) {
			rows.splice(index, 1);
			this.$refs.table.reloadData(rows);
		},
		addRow() {
			this.inputForm.columns.push({
				name: "",
				type: "",
				size: 64,
				defaultValue: "",
				primaryKey: false,
				required: false,
				autoIncrement: false,
				description: "",
			});
			this.$refs.table.reloadData(this.inputForm.columns);
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					if (this.method === "add") {
						dataTableService
							.submitCreate(this.inputForm)
							.then((data) => {
								this.$message.success(data);
								this.goBack();
								this.loading = false;
							})
							.catch(() => {
								this.loading = false;
							});
					} else {
						dataTableService
							.submitAlter(this.inputForm)
							.then((data) => {
								this.$message.success(data);
								this.goBack();
								this.loading = false;
							})
							.catch(() => {
								this.loading = false;
							});
					}
				}
			});
		},
		goBack() {
			useTabs.close();
			this.$router.push({ path: `/database/datatable/Table` });
		},
	},
};
</script>
