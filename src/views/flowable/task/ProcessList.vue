<template>
	<splitpanes class="default-theme">
		<pane size="20">
			<el-container class="jp-container">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="请输入关键字过滤"
						clearable
						v-model="filterText"
					>
					</el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
						class="filter-tree"
						:data="categoryTreeData"
						:props="{
							value: 'id', // ID字段名
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						default-expand-all
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						node-key="id"
						highlight-current
						@node-click="handleNodeClick"
						ref="categoryTree"
					>
					</el-tree>
				</el-main>
			</el-container>
		</pane>

		<pane size="80">
			<el-container class="jp-container">
				<el-header>
					<div class="left-panel">
						<el-form
							:inline="true"
							class="query-form m-b-10"
							ref="searchForm"
							:model="searchForm"
							@keyup.enter="refreshList()"
							@submit.prevent
						>
							<el-form-item label="流程名称：" prop="name">
								<el-input
									v-model="searchForm.name"
									clearable
									placeholder="请输入流程名称"
								></el-input>
							</el-form-item>
						</el-form>
					</div>
					<div class="right-panel">
						<el-button
							type="primary"
							class="round"
							@click="refreshList()"
							icon="search"
							>查询</el-button
						>
						<el-button
							type="default"
							@click="resetSearch()"
							icon="refresh-right"
							>重置</el-button
						>
					</div>
				</el-header>
				<el-main class="nopadding page2">
					<div class="jp-table" style="overflow: auto">
						<div class="jp-table-body">
							<el-row :gutter="12">
								<el-col
									:span="8"
									v-for="data in dataList2"
									:key="data.id"
								>
									<el-card
										class="box-card"
										style="margin-bottom: 10px"
									>
										<el-space wrap :size="15">
											<img
												src="@/assets/img/Scheme.png"
											/>
											<el-button
												style="
													color: #409eff;
													margin-left: 10px;
													width: 150px;
													text-overflow: ellipsis;
													overflow: hidden;
													white-space: nowrap;
												"
												text
												@click="start(data)"
												>{{
													data.name +
													" " +
													data.version
												}}</el-button
											>
										</el-space>
									</el-card>
								</el-col>
							</el-row>
						</div>
					</div>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import actCategoryService from "@/api/flowable/actCategoryService";
import processService from "@/api/flowable/processService";
export default {
	data() {
		return {
			searchForm: {
				category: "",
				name: "",
			},
			filterText: "",
			dataList: [],
			categoryTreeData: [],
			total: 0,
			isSearchCollapse: false,
			loading: false,
			visible: false,
			processPhotoUrl: "",
		};
	},
	components: { Splitpanes, Pane },
	activated() {
		this.refreshTree();
		this.refreshList();
	},
	watch: {
		filterText(val) {
			this.$refs.categoryTree.filter(val);
		},
	},
	computed: {
		dataList2() {
			return this.dataList.filter((data) => {
				return data.name.indexOf(this.searchForm.name) >= 0;
			});
		},
		userName() {
			return JSON.parse(localStorage.getItem("USER_INFO")).name;
		},
	},
	methods: {
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
		// 获取树数据
		refreshTree() {
			actCategoryService.treeData().then((data) => {
				this.categoryTreeData = data;
			});
		},
		// 获取数据列表
		refreshList() {
			this.loading = true;
			processService
				.list({
					...this.searchForm,
				})
				.then((data) => {
					this.dataList = data.records;
					this.total = data.total;
					this.loading = false;
				});
		},
		start(row) {
			// 读取流程表单
			let tabTitle = `发起流程【${row.name}】`;
			this.$router.push({
				path: "/flowable/task/TaskForm",
				query: {
					procDefId: row.id,
					status: "start",
					title: tabTitle,
				},
			});
		},
		handleNodeClick(data) {
			this.searchForm.category = data.name;
			this.refreshList();
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.searchForm.category = "";
			this.filterText = "";
			this.$refs.categoryTree.setCurrentKey(null);
			this.refreshList();
		},
	},
};
</script>
