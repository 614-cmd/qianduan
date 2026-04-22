<template>
	<v-dialog :title="title" :close-on-click-modal="false" v-model="visible">
		       <el-form
			:model="inputForm"
			ref="inputForm"
			@keyup.enter="doSubmit()"
			label-width="120px"
			:class="method === 'view' ? 'readonly' : ''"
			:disabled="method === 'view'"
			v-loading="loading"
			@submit.prevent
		>
			<el-row :gutter="15">
				<el-col :span="12">
					<el-form-item
						label="连接名称"
						prop="name"
						:rules="[
							{
								required: true,
								message: '连接名称不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.name"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="连接英文名"
						prop="enName"
						:rules="[
							{
								required: true,
								message: '连接英文名不能为空',
								trigger: 'blur',
							},
							{ validator: checkEnName, trigger: 'blur' },
						]"
					>
						<el-input
							v-model="inputForm.enName"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="数据库类型"
						prop="type"
						:rules="[
							{
								required: true,
								message: '数据库类型不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-select
							v-model="inputForm.type"
							placeholder="请选择来源"
							style="width: 100%"
						>
							<el-option
								v-for="item in this.$dictUtils.getDictList(
									'db_type'
								)"
								:key="item.value"
								:label="item.label"
								:value="item.value"
							>
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="主机地址"
						prop="host"
						:rules="[
							{
								required: true,
								message: '主机地址不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.host"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="端口"
						prop="port"
						:rules="[
							{
								required: true,
								message: '端口不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							type="number"
							v-model.number="inputForm.port"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="数据库名"
						prop="databaseName"
						:rules="[
							{
								required: true,
								message: '数据库名不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.databaseName"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="数据库用户名"
						prop="username"
						:rules="[
							{
								required: true,
								message: '数据库用户名不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.username"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="数据库密码"
						prop="password"
						:rules="[
							{
								required: true,
								message: '数据库密码名不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.password"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="是否启用" prop="enable">
						<el-switch v-model="inputForm.enable"> </el-switch>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button
					v-if="method != 'view'"
					type="primary"
					@click="testDbLink()"
					>测试连接</el-button
				>
				<el-button
					v-if="method != 'view'"
					@click="doSubmit()"
					icon="circle-check"
					v-noMoreClick
					>确定</el-button
				>
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import dataSourceService from "@/api/database/dataSourceService";
export default {
	data() {
		return {
			visible: false,
			loading: false,
			method: "",
			title: "",
			checkEnName: (rule, value, callback) => {
				if (value === "master") {
					return callback(
						new Error("master是系统默认主数据源名，请换一个名字!")
					);
				}
				dataSourceService
					.checkEnName(this.inputForm.oldEnName, value)
					.then(() => {
						return callback();
					})
					.catch((e) => {
						// eslint-disable-next-line no-debugger
						return callback(new Error(e.response.data));
					});
			},
			inputForm: {
				id: "",
				name: "",
				enName: "",
				oldEnName: "",
				type: "",
				host: "",
				port: "",
				databaseName: "",
				username: "",
				password: "",
				enable: true,
			},
		};
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建数据库连接`;
			} else if (method === "edit") {
				this.title = "修改数据库连接";
			} else if (method === "view") {
				this.title = "查看数据库连接";
			}
			this.visible = true;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					dataSourceService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							this.inputForm.oldEnName = this.inputForm.enName;
						});
				}
			});
		},

		testDbLink() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					dataSourceService
						.test(this.inputForm)
						.then((data) => {
							this.$message.info(data);
							this.loading = false;
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					dataSourceService
						.save(this.inputForm)
						.then((data) => {
							this.loading = false;
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
