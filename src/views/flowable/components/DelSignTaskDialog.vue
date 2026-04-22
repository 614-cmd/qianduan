<template>
	<v-dialog
		title="减签"
		:close-on-click-modal="false"
		v-model="visible"
		:appendToBody="true"
	>
		<el-row>
			<el-col :span="16">
				<el-table :data="taskList" style="width: 100%">
					<el-table-column prop="name" label="任务名称" />
					<el-table-column prop="assigneeName" label="审批人员" />
					<el-table-column fixed="right" label="减签" min-width="120">
						<template #default="{ row }">
							<el-button
								link
								type="danger"
								size="small"
								icon="delete"
								@click="handleDelete(row)"
							>
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-col>
			<el-col :span="6">
				<div style="padding: 0px 20px">
					<el-alert
						type="warning"
						:closable="false"
						size="mini"
						style="margin-bottom: 5px"
						>已选择减签的人员</el-alert
					>
					<el-tag
						v-for="(tag, index) in delTaskList"
						:key="index"
						closable
						@close="handleAdd(tag)"
					>
						{{ tag.assigneeName }}
					</el-tag>
				</div>
			</el-col>
		</el-row>

		<template #footer>
			<span class="dialog-footer">
				<el-button icon="close" @click="visible = false"
					>取消</el-button
				>
				<el-button
					icon="check"
					type="primary"
					@click="doConfirm('before')"
					>确定</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import taskService from "@/api/flowable/taskService.js";
export default {
	name: "addSign",
	data() {
		return {
			visible: false,
			taskList: [],
			delTaskList: [],
		};
	},
	methods: {
		open(taskId) {
			this.visible = true;
			this.delTaskList = [];
			taskService.getMultiInstanceList(taskId).then((data) => {
				this.taskList = data;
			});
		},
		handleDelete(tag) {
			this.delTaskList.push(tag);
			this.taskList.splice(this.taskList.indexOf(tag), 1);
		},
		handleAdd(tag) {
			this.taskList.push(tag);
			this.delTaskList.splice(this.delTaskList.indexOf(tag), 1);
		},
		doConfirm() {
			if (this.delTaskList.length == 0) {
				this.$message.error("至少要选择一条数据!");
				return;
			}
			this.visible = false;
			this.$emit("delSign", this.delTaskList);
		},
	},
};
</script>
