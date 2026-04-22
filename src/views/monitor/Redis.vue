<template>
	<splitpanes class="default-theme">
		<pane size="30">
			<el-container class="jp-container">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="输入redis key 过滤"
						clearable
						v-model="filterText"
					>
					</el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
						:data="data"
						:props="{
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						ref="redisTree"
						node-key="id"
						class="redis-tree"
						@node-click="showDetail"
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						default-expand-all
					>
						<template #default="{ node, data }">
							<span class="custom-tree-node">
								<span>
									<el-icon
										v-if="data.type === 'db'"
										class="icon-m-r"
										color="#e91e63"
									>
										<redis2></redis2>
									</el-icon>
									<el-icon
										v-else-if="data.type === 'folder'"
										class="icon-m-r"
										color="#ff9800"
									>
										<folder3 v-if="node.expanded"></folder3>
										<folder2 v-else></folder2>
									</el-icon>
									<el-icon
										v-else
										class="icon-m-r"
										color="#ff9800"
									>
										<key2></key2>
									</el-icon>
									{{ node.label }}
									{{
										data.children
											? "(" + data.children.length + ")"
											: ""
									}}
								</span>
							</span>
							<span class="xx">
								<a
									title="清空redis"
									class="icon-m-l"
									@click="flushdb()"
									v-if="data.type === 'db'"
								>
									<el-icon color="#724bb8"
										><BrushFilled
									/></el-icon>
								</a>
								<a
									title="复制key"
									class="icon-m-l"
									v-copy="data.id"
									v-if="
										data.type === 'folder' ||
										data.type === 'key'
									"
								>
									<el-icon color="#03a9f4"
										><DocumentCopy
									/></el-icon>
								</a>
								<a
									title="删除key"
									class="icon-m-l"
									v-if="
										data.type === 'folder' ||
										data.type === 'key'
									"
								>
									<el-popconfirm
										title="确认删除此key吗？"
										@confirm="remove(node, data)"
									>
										<template #reference>
											<el-icon
												color="#724bb8"
												@click.stop="() => {}"
												><DeleteFilled
											/></el-icon>
										</template>
									</el-popconfirm>
								</a>
								<a
									title="刷新"
									class="icon-m-l"
									@click="reload(data.id)"
									v-if="
										data.type === 'db' ||
										data.type === 'folder'
									"
									><el-icon color="#8bc34a"
										><Refresh /></el-icon
								></a>
							</span>
						</template>
					</el-tree>
				</el-main>
			</el-container>
		</pane>
		<pane size="70">
			<el-container class="jp-container">
				<el-header>
					<div class="left-panel">
						<el-form class="query-form">
							<el-form-item prop="key" label="Redis Key :">
								<label>{{ key }}</label>
							</el-form-item>
						</el-form>
					</div>
					<div class="right-panel" style="width: 200px">
						<el-button
							type="primary"
							class="round"
							@click="showDetail({ type: 'key', id: key })"
							icon="refresh"
							>刷新值</el-button
						>
						<el-button @click="saveValue()" icon="select"
							>保存值</el-button
						>
					</div>
				</el-header>
				<el-main class="nopadding page2">
					<VAceEditor
						v-loading="loading"
						v-model:value="redisContent"
						lang="json"
						style="height: calc(100% - 10px)"
						theme="chrome"
						:options="{ useWorker: true }"
					/>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
import redisService from "@/api/monitor/redisService";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import { VAceEditor } from "vue3-ace-editor";
import "ace-builds/src-noconflict/mode-json";
import "ace-builds/src-noconflict/theme-chrome";
import ace from "ace-builds";
import workerJsonUrl from "ace-builds/src-noconflict/worker-json?url";
ace.config.setModuleUrl("ace/mode/json_worker", workerJsonUrl);
export default {
	data() {
		return {
			loading: false,
			filterText: "",
			redisContent: "",
			key: "",
			data: [{ id: "0", name: "db", type: "db" }],
		};
	},
	components: { Splitpanes, Pane, VAceEditor },
	watch: {
		filterText(val) {
			this.$refs.redisTree.filter(val);
		},
	},
	mounted() {
		this.reload();
	},
	methods: {
		flushdb() {
			this.$confirm(`确认要清空redis缓存吗?`, "警告", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				redisService.flushdb().then((data) => {
					this.$message.success(data);
					this.data = [{ id: "0", name: "db", type: "db" }];
				});
			});
		},
		showDetail(data) {
			this.redisContent = "";
			this.key = "";
			if (data.type === "key") {
				this.key = data.id;
				this.loading = true;
				redisService.queryByKey(data.id).then((result) => {
					this.redisContent = JSON.stringify(result);
					this.loading = false;
				});
			}
		},
		remove(node, data) {
			if (data.type === "folder") {
				redisService.delPattern(data.id + "*").then((result) => {
					this.$message.success(result);
					this.removeNode(node, data);
				});
			} else {
				redisService.delKey(data.id).then((result) => {
					this.$message.success(result);
					this.removeNode(node, data);
				});
			}
		},
		removeNode(node, data) {
			const parent = node.parent;
			const children = parent.data.children || parent.data;
			const index = children.findIndex((d) => d.id === data.id);
			children.splice(index, 1);
			this.data = [...this.data];
		},
		saveValue() {
			redisService.setValue(this.key, this.redisContent).then((data) => {
				this.$message(data);
			});
		},
		reload(id) {
			if (!id || id === "0") {
				// const loading = this.$loading();
				this.data = [{ id: "0", name: "db", type: "db" }];
				redisService
					.treeData({ pattern: "*" })
					.then((data) => {
						this.data = data;
						// loading.close()
						this.$message.success("刷新成功!");
					})
					.catch(() => {
						// loading.close()
					});
			} else {
				// const loading = this.$loading();
				redisService
					.treeData({ pattern: id + "*" })
					.then((data) => {
						this.$refs.redisTree.updateKeyChildren(id, data);
						// loading.close()
						this.$message.success("刷新成功!");
					})
					.catch(() => {
						// loading.close()
					});
			}
		},
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
	},
};
</script>
<style lang="less">
.redis-tree {
	.el-tree-node__label {
		display: flex;
	}
	.el-tree-node__content:hover {
		.xx {
			display: inline;
			position: relative;
		}
	}
	.el-tree-node__content {
		.xx {
			display: none;
		}
	}
}

.icon-m-l {
	margin-left: 5px;
}
.icon-m-r {
	margin-right: 5px;
}
.custom-tree-node {
	width: 100%;
}
</style>
