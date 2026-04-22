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
				label-width="120px"
			>
				<el-row :gutter="15">
					<el-col :span="24">
						<el-form-item
							label="表单名称"
							prop="name"
							:rules="[
								{
									required: true,
									message: '表单名称不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.name"
								placeholder="请填写表单名称"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="表单key"
							prop="code"
							:rules="[
								{
									required: true,
									message: '表单key不能为空',
									trigger: 'blur',
								},
								{
									validator: validateKeyNoExist,
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.code"
								placeholder="请填写表单编码"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="所属数据库"
							prop="dataSource.id"
							:rules="[
								{
									required: true,
									message: '所属数据库不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-tree-select
								ref="dataSourceTree"
								value-key="id"
								:data="dataSourceTree"
								v-model="inputForm.dataSource.id"
								style="width: 100%"
								:clearable="true"
								:accordion="true"
							/>
						</el-form-item>
					</el-col>

					<el-col :span="24">
						<el-form-item
							label="是否自动建表"
							prop="autoCreate"
							:rules="[
								{
									required: true,
									message: '是否自动建表不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-radio-group
								:disabled="method !== 'add'"
								@change="changeAutoCreate"
								v-model="inputForm.autoCreate"
							>
								<el-radio
									v-for="item in $dictUtils.getDictList(
										'yes_no'
									)"
									:value="item.value"
									:key="item.id"
									>{{ item.label }}</el-radio
								>
							</el-radio-group>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							v-if="inputForm.autoCreate === '0'"
							label="表名"
							prop="tableName"
							:rules="[
								{
									required: true,
									message: '表名不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-select
								:disabled="method !== 'add'"
								v-model="inputForm.tableName"
								style="width: 100%"
								filterable
								placeholder="请选择表"
							>
								<el-option
									v-for="item in dataTable"
									:key="item.name"
									:label="item.nameAndComments"
									:value="item.name"
								>
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item
							v-else
							label="表名"
							prop="tableName"
							:rules="[
								{ validator: validateValid, trigger: 'blur' },
								{
									required: true,
									message: '表名不能为空',
									trigger: 'blur',
								},
								{
									validator: validateTableNoExist,
									trigger: 'blur',
								},
							]"
						>
							<el-input
								:disabled="method !== 'add'"
								v-model="inputForm.tableName"
								placeholder="请输入表名"
							>
								<template #append>
									<el-button
										:disabled="method !== 'add'"
										@click="createTableName"
										>生成随机表名</el-button
									>
								</template>
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="备注信息"
							prop="remarks"
							:rules="[]"
						>
							<el-input
								type="textarea"
								v-model="inputForm.remarks"
								placeholder="请填写备注信息"
							></el-input>
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
import dataSourceService from "@/api/database/dataSourceService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			dataSourceTree: [],
			dataTable: [],
			oldCode: "",
			oldTableName: "",
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
				remarks: "",
			},
		};
	},
	watch: {
		"inputForm.dataSource.id": {
			handler(val) {
				if (val) {
					this.inputForm.dataSource.enName =
						this.$refs.dataSourceTree?.getNode(val)?.data?.enName;
					this.inputForm.dataSource.type =
						this.$refs.dataSourceTree?.getNode(val)?.data?.dbType;
				}
				if (val === "master") {
					this.inputForm.dataSource.enName = "master";
				}
				makeFormService
					.getTableList({
						"dataSource.enName": this.inputForm.dataSource.enName,
					})
					.then((data) => {
						this.dataTable = data.rows;
					});
			},
			immediate: true,
			deep: false,
		},
		dataTable(val) {
			if (this.inputForm.autoCreate === "0") {
				var list = val.filter((table) => {
					return table.name === this.inputForm.tableName;
				});
				if (list.length === 0) {
					this.inputForm.tableName = "";
				}
			}
		},
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建表单`;
			} else if (method === "edit") {
				this.title = "修改表单";
			} else if (method === "view") {
				this.title = "查看表单";
			}
			dataSourceService.treeData().then((data) => {
				this.dataSourceTree = data;
			});
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					makeFormService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							this.oldCode = this.inputForm.code;
							this.oldTableName = this.inputForm.tableName;
							this.loading = false;
						});
				}
			});
		},
		changeAutoCreate() {
			this.inputForm.tableName = "";
		},
		createTableName() {
			this.inputForm.tableName = "jp_form_" + new Date().getTime();
		},
		validateKeyNoExist(rule, value, callback) {
			if (
				this.method === "edit" &&
				this.inputForm.code === this.oldCode
			) {
				callback();
			}
			makeFormService.validateKeyNoExist({ key: value }).then((data) => {
				if (data) {
					callback();
				} else {
					callback(new Error("表单key已经存在"));
				}
			});
		},
		validateTableNoExist(rule, value, callback) {
			if (
				this.method === "edit" &&
				this.inputForm.tableName === this.oldTableName
			) {
				callback();
			}
			makeFormService
				.validateTableNoExist({
					name: value,
					"dataSource.enName": this.inputForm.dataSource.enName,
				})
				.then((data) => {
					if (data) {
						callback();
					} else {
						callback(new Error("表在数据库中已经存在"));
					}
				});
		},
		validateValid(rule, value, callback) {
			if (value && !/^[A-Za-z]+[A-Za-z0-9_-]*$/.test(value)) {
				callback(
					new Error(
						"请输入合法的表名，只能包含A-Za-z0-9_-这些字符，且必须以英文字符开头!"
					)
				);
			} else {
				callback();
			}
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					makeFormService
						.saveBasicInfo(this.inputForm)
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
