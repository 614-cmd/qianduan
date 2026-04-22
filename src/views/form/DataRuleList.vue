<template>
	<div style="padding: 10px">
		<el-row style="margin-bottom: 10px">
			<el-alert
				style="margin-bottom: 10px"
				title="如需对动态表单创建数据权限，请添加数据规则。添加后的规则，可在[菜单管理->数据规则]中查看。"
				type="success"
			>
			</el-alert>
			<el-button type="primary" @click="add()">添加数据规则</el-button>
		</el-row>
		<el-table
			:data="dataRuleDTOList"
			v-loading="loading"
			class="table"
			style="width: 100%"
		>
			<el-table-column
				type="selection"
				header-align="center"
				align="center"
				width="50"
			>
			</el-table-column>
			<el-table-column
				prop="name"
				header-align="center"
				align="center"
				width="120px"
				label="数据规则名称"
			>
			</el-table-column>
			<el-table-column
				prop="className"
				header-align="center"
				align="center"
				label="表名"
			>
			</el-table-column>
			<el-table-column
				show-overflow-tooltip
				prop="field"
				header-align="center"
				align="center"
				label="规则字段"
			>
			</el-table-column>
			<el-table-column
				show-overflow-tooltip
				prop="express"
				header-align="center"
				align="center"
				label="规则条件"
			>
				<template #default="scope">
					{{
						$dictUtils.getDictLabel(
							"t_express",
							scope.row.express,
							""
						)
					}}
				</template>
			</el-table-column>
			<el-table-column
				show-overflow-tooltip
				prop="value"
				header-align="center"
				align="center"
				label="规则值"
			>
			</el-table-column>
			<el-table-column
				show-overflow-tooltip
				prop="sqlSegment"
				header-align="center"
				align="center"
				width="100px"
				label="自定义sql"
			>
			</el-table-column>
			<el-table-column
				show-overflow-tooltip
				prop="remarks"
				header-align="center"
				align="center"
				label="备注信息"
			>
			</el-table-column>
			<el-table-column
				fixed="right"
				header-align="center"
				align="center"
				width="150"
				label="操作"
			>
				<template #default="scope">
					<el-button @click="edit(scope.row)">修改</el-button>
					<el-button type="danger" @click="del(scope.row)">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<data-rule-form
			ref="dataRuleForm"
			@addRow="saveDataRuleForm"
		></data-rule-form>
	</div>
</template>

<script>
import DataRuleForm from "./DataRuleForm";

export default {
	data() {
		return {
			dataRuleDTOList: [],
			loading: false,
			rows: [],
			menuId: "",
			pageNo: 1,
			pageSize: -1,
			total: 0,
			title: "",
		};
	},
	props: ["dataRuleTitle", "form"],
	components: {
		DataRuleForm,
	},
	methods: {
		saveDataRuleForm(oldItem, newInput) {
			if (oldItem === "") {
				this.dataRuleDTOList.push(newInput);
			} else {
				this.dataRuleDTOList.forEach((item, index) => {
					if (item === oldItem) {
						this.dataRuleDTOList.splice(index, 1, newInput);
					}
				});
			}
		},
		// 新增
		add() {
			this.$refs.dataRuleForm.init(
				"add",
				{},
				{
					menuId: this.menuId,
					dsName: this.form.dataSource.enName,
					tableName: this.form.tableName,
				}
			);
		},
		// 修改
		edit(row) {
			this.$refs.dataRuleForm.init("edit", row, {
				menuId: this.menuId,
				dsName: this.form.dataSource.enName,
				tableName: this.form.tableName,
			});
		},
		// 删除
		del() {
			this.$confirm(`确定删除该条记录吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.dataRuleDTOList.forEach((item, index) => {
					this.dataRuleDTOList.splice(index, 1);
				});
			});
		},
	},
};
</script>

<style>
.el-dropdown-link {
	cursor: pointer;
	color: #409eff;
	font-size: 12px;
	font-weight: 500;
}
.el-icon-arrow-down {
	font-size: 12px;
}
</style>
