<template>
	<span class="bt-group">
		<el-button
			style="width: 100px"
			type="danger"
			v-if="inputForm.id && hasPermission('sys:role:del')"
			@click="del()"
			text
			bg
			>删除</el-button
		>
		<el-button
			style="width: 100px"
			type="primary"
			v-if="
				hasPermission('sys:role:edit') || hasPermission('sys:role:add')
			"
			@click="inputFormSubmit()"
			v-noMoreClick
			>保存</el-button
		>
	</span>
	<el-form
		:model="inputForm"
		label-position="right"
		status-icon
		v-loading="loading"
		ref="inputForm"
		label-width="120px"
		@submit.prevent
	>
		<el-row :gutter="25">
			<el-col :span="24">
				<el-form-item
					label="角色名称"
					prop="name"
					:rules="[
						{
							required: true,
							message: '角色名称不能为空',
							trigger: 'blur',
						},
						{ validator: validateName, trigger: 'blur' },
					]"
				>
					<el-input
						v-model="inputForm.name"
						placeholder="角色名称"
						maxlength="50"
					></el-input>
				</el-form-item>
			</el-col>
			<el-col :span="24">
				<el-form-item
					label="英文名称"
					prop="enName"
					:rules="[
						{
							required: true,
							message: '角色英文名称不能为空',
							trigger: 'blur',
						},
						{ validator: validateEnName, trigger: 'blur' },
					]"
				>
					<el-input
						v-model="inputForm.enName"
						maxlength="50"
						placeholder="角色英文名称"
					></el-input>
				</el-form-item>
			</el-col>
			<el-col :span="24" v-if="$TOOL.data.get('IS_PRIMARY_TENANT')">
				<el-form-item
					label="是否系统数据"
					prop="sysData"
					:rules="[
						{
							required: true,
							message: '是否系统数据不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-select
						v-model="inputForm.sysData"
						placeholder="请选择"
						style="width: 100%"
					>
						<el-option
							v-for="item in this.$dictUtils.getDictList(
								'yes_no'
							)"
							:key="item.value"
							:label="item.label"
							:value="item.value"
						>
						</el-option>
					</el-select>
					<div class="help-block">
						“是”代表此数据只有平台管理员能进行修改
					</div>
				</el-form-item>
			</el-col>
			<el-col :span="24">
				<el-form-item
					label="是否可用"
					prop="useable"
					:rules="[
						{
							required: true,
							message: '是否可用不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-select
						v-model="inputForm.useable"
						placeholder="请选择"
						style="width: 100%"
					>
						<el-option
							v-for="item in this.$dictUtils.getDictList(
								'yes_no'
							)"
							:key="item.value"
							:label="item.label"
							:value="item.value"
						>
						</el-option>
					</el-select>
					<div class="help-block">
						“是”代表此数据可用，“否”则表示此数据不可用
					</div>
				</el-form-item>
			</el-col>
			<el-col :span="24">
				<el-form-item label="备注" prop="remarks">
					<el-input
						type="textarea"
						v-model="inputForm.remarks"
						placeholder="备注"
					></el-input>
				</el-form-item>
			</el-col>
		</el-row>
	</el-form>
</template>

<script>
import roleService from "@/api/sys/roleService";
export default {
	data() {
		return {
			loading: false,
			title: "",
			oldName: "",
			oldEnName: "",
			inputForm: {
				id: "",
				name: "",
				enName: "",
				remarks: "",
				sysData: "0",
				useable: "1",
			},
		};
	},
	watch: {
		"inputForm.name": {
			handler(val) {
				this.$emit("getRoleName", val);
			},
			immediate: true,
			deep: false,
		}
	},
	methods: {
		init(id) {
			this.inputForm.id = id;
			this.$nextTick(() => {
				this.inputForm.sysData = "0";
				this.$refs.inputForm.resetFields();
				this.oldName = "";
				this.oldEnName = "";
				if (this.inputForm.id) {
					this.loading = true;
					let p3 = roleService.queryById(this.inputForm.id);
					Promise.all([p3]).then((result) => {
						let data = result[0];
						this.loading = false;
						this.inputForm = this.recover(this.inputForm, data);
						this.oldName = data.name;
						this.oldEnName = data.enName;
					});
				}
			});
		},
		// 删除
		del() {
			this.$emit("delRoleById", this.inputForm.id);
		},
		validateName(rule, value, callback) {
			if (this.oldName === "" || this.oldName !== this.inputForm.name) {
				roleService
					.validateNotExist({
						name: this.inputForm.name,
					})
					.then((data) => {
						if (data === true) {
							callback();
						} else {
							callback(new Error("角色名称已存在!"));
						}
					});
			} else {
				callback();
			}
		},
		validateEnName(rule, value, callback) {
			if (
				this.oldEnName === "" ||
				this.oldEnName !== this.inputForm.enName
			) {
				roleService
					.validateNotExist({
						enName: this.inputForm.enName,
					})
					.then((data) => {
						if (data === true) {
							callback();
						} else {
							callback(new Error("角色英文名已存在!"));
						}
					});
			} else {
				callback();
			}
		},
		// 表单提交
		inputFormSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					roleService
						.save(this.inputForm)
						.then((data) => {
							this.loading = false;
							this.$message.success("保存成功");
							this.inputForm.id = data.id;
							this.oldName = data.name;
							this.oldEnName = data.enName;
							this.$emit("getRoleId", data.id);
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
