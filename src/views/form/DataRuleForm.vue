<template>
	<v-dialog :title="title" :close-on-click-modal="false" v-model="visible">
		<el-form
			:model="inputForm"
			v-loading="loading"
			:class="method === 'view' ? 'readonly' : ''"
			:disabled="method === 'view'"
			ref="inputForm"
			@keyup.enter="doSubmit()"
			label-width="120px"
			@submit.prevent
		>
			<el-form-item label="数据规则名称" prop="name">
				<el-input
					v-model="inputForm.name"
					placeholder="数据规则名称"
				></el-input>
			</el-form-item>
			<el-form-item label="表名" prop="className">
				<el-input
					v-model="inputForm.className"
					placeholder="表名"
				></el-input>
			</el-form-item>
			<el-form-item label="规则字段" prop="field">
				<el-select
					v-model="inputForm.field"
					style="width: 100%"
					filterable
					placeholder="请选择表"
				>
					<el-option
						v-for="item in dataTableColumns"
						:key="item.name"
						:label="item.nameAndComments"
						:value="item.name"
					>
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="规则条件" prop="express">
				<el-select
					v-model="inputForm.express"
					placeholder="规则条件"
					clearable
					style="width: 100%"
				>
					<el-option
						v-for="item in this.$dictUtils.getDictList('t_express')"
						:key="item.value"
						:label="item.label"
						:value="item.value"
					>
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="规则值" prop="value">
				<el-autocomplete
					clearable
					style="width: 100%"
					v-model="inputForm.value"
					:fetch-suggestions="querySearch"
					placeholder="请输入规则值"
				></el-autocomplete>
			</el-form-item>
			<el-form-item label="自定义sql" prop="sqlSegment">
				<el-input
					v-model="inputForm.sqlSegment"
					type="textarea"
					:rows="2"
					placeholder="备注"
				></el-input>
			</el-form-item>
			<el-form-item label="备注" prop="remarks">
				<el-input
					v-model="inputForm.remarks"
					type="textarea"
					:rows="2"
					placeholder="备注"
				></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
				<el-button
					v-if="method != 'view'"
					type="primary"
					@click="doSubmit()"
					icon="circle-check"
					v-noMoreClick
					>确定</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import makeFormService from "@/api/form/makeFormService";
export default {
	data() {
		return {
			visible: false,
			loading: false,
			method: "",
			title: "",
			oldInputForm: "",
			dataTableColumns: [],
			inputForm: {
				id: "",
				menuId: "", // 所属菜单
				name: "", // 数据规则名称
				className: "", // 实体类名
				field: "", // 规则字段
				express: "", // 规则条件
				value: "", // 规则值
				sqlSegment: "", // 自定义sql
				remarks: "", // 备注
			},
		};
	},
	methods: {
		init(method, row, obj) {
			this.method = method;
			this.inputForm.id = obj.id;
			this.inputForm.menuId = obj.menuId;
			this.inputForm.className = obj.tableName;
			if (method === "add") {
				this.title = `新增数据规则`;
			} else if (method === "edit") {
				this.title = "修改数据规则";
			} else if (method === "view") {
				this.title = "查看数据规则";
			}
			this.visible = true;
			this.$nextTick(() => {
				this.$refs["inputForm"].resetFields();
				this.getTableColumnList(obj.tableName, obj.dsName);
				this.$nextTick(() => {
					this.$refs.inputForm.resetFields();
					this.inputForm.id = "";
					this.oldInputForm = "";
					if (method === "edit" || method === "view") {
						// 修改或者查看
						this.oldInputForm = row;
						this.inputForm = JSON.parse(JSON.stringify(row));
					}
				});
			});
		},
		getTableColumnList(name, dsName) {
			makeFormService
				.getTableColumnList({
					name: name,
					"dataSource.enName": dsName || "master",
				})
				.then((data) => {
					this.dataTableColumns = data.rows;
				});
		},
		querySearch(queryString, cb) {
			// var restaurants = this.restaurants;
			var results = [
				{ title: "当前登录用户姓名", value: "@currentUser.name" },
				{ title: "当前登录用户id", value: "@currentUser.id" },
				{
					title: "当前登录用户登录名",
					value: "@currentUser.loginName",
				},
				{ title: "当前登录用户工号", value: "@currentUser.no" },
				{
					title: "当前登录用户所属公司",
					value: "@currentUser.companyDTO.name",
				},
				{
					title: "当前登录用户所属公司id",
					value: "@currentUser.companyDTO.id",
				},
				{
					title: "当前登录用户所属部门",
					value: "@currentUser.officeDTO.name",
				},
				{
					title: "当前登录用户所属部门id",
					value: "@currentUser.officeDTO.id",
				},
			];
			// 调用 callback 返回建议列表的数据
			cb(results);
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.$emit(
						"addRow",
						this.oldInputForm,
						JSON.parse(JSON.stringify(this.inputForm))
					);
					this.visible = false;
				}
			});
		},
	},
};
</script>
