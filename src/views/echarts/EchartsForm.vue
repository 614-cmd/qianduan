<template>
	<el-dialog
		:title="title"
		center
		width="80%"
		:close-on-click-modal="false"
		class="design-chart"
		v-model="visible"
		draggable
	>
		<el-form
			:model="inputForm"
			ref="inputForm"
			@keyup.enter="doSubmit()"
			:class="method === 'view' ? 'readonly' : ''"
			:disabled="method === 'view'"
			v-loading="loading"
			@submit.prevent
		>
			<el-container>
				<el-container>
					<el-aside width="250px" style="padding-left: 15px">
						<p>请选择一个数据源：</p>
						<el-select
							style="width: 215px; padding-top: 5px"
							@change="resetMetas"
							v-model="inputForm.dataSet.id"
							filterable
							placeholder="请选择一个数据源"
						>
							<el-option
								v-for="(item, index) in dataSets"
								:key="index"
								:label="item.name"
								:value="item.id"
							>
							</el-option>
						</el-select>
						<draggable
							class="jp-filed-panel"
							v-model="metas"
							item-key="name"
							group="charts"
						>
							<template #item="{ element }">
								<div class="list-group-item">
									<el-icon><rank></rank></el-icon>
									{{ element.label }}
								</div>
							</template>
						</draggable>
					</el-aside>
					<el-main class="eMain">
						<el-header class="eHeader">
							<el-menu
								class="el-menu-dm"
								:ellipsis="false"
								default-active="1"
								mode="horizontal"
								@select="handleSelect"
							>
								<el-sub-menu index="1">
									<template #title>折线图</template>
									<el-menu-item index="line"
										>折线图</el-menu-item
									>
									<el-menu-item index="smooth"
										>平滑折线图</el-menu-item
									>
									<el-menu-item index="area"
										>面积图</el-menu-item
									>
									<el-menu-item index="smootharea"
										>平滑面积图</el-menu-item
									>
								</el-sub-menu>
								<el-sub-menu index="2">
									<template #title>柱状图</template>
									<el-menu-item index="histogram"
										>柱状图</el-menu-item
									>
									<el-menu-item index="waterfall"
										>堆叠柱状图</el-menu-item
									>
									<el-menu-item index="bar"
										>条形图</el-menu-item
									>
									<el-menu-item index="stack"
										>堆叠条形图</el-menu-item
									>
								</el-sub-menu>
								<el-sub-menu index="3">
									<template #title>饼图</template>
									<el-menu-item index="pie"
										>饼图</el-menu-item
									>
									<el-menu-item index="ring"
										>环形图</el-menu-item
									>
									<el-menu-item index="ringBorder"
										>圆角环形图</el-menu-item
									>
									<el-menu-item index="rose"
										>南丁格尔玫瑰图</el-menu-item
									>
								</el-sub-menu>
								<el-sub-menu index="4">
									<template #title>其他</template>
									<el-menu-item index="gauge"
										>仪表盘</el-menu-item
									>
									<el-menu-item index="funnel"
										>漏斗图</el-menu-item
									>
									<el-menu-item index="table"
										>表格</el-menu-item
									>
								</el-sub-menu>
							</el-menu>
						</el-header>

						<el-row class="chartMain" :gutter="12">
							<el-col :span="12">
								<el-card class="jp-box-card">
									<template #header>
										<div class="card-header">
											<span>
												<el-icon
													><fenlei1></fenlei1
												></el-icon>
												X轴
											</span>
										</div>
									</template>
									<draggable
										class="fieldpanel"
										@add="onMove"
										v-model="xMetas"
										group="charts"
									>
										<template #item="{ element }">
											<li class="list-group-item">
												<el-icon><rank></rank></el-icon
												>{{ element.label }}
											</li>
										</template>
									</draggable>
								</el-card>
							</el-col>
							<el-col :span="12">
								<el-card class="jp-box-card">
									<template #header>
										<div class="card-header">
											<span>
												<el-icon
													><fenlei1></fenlei1
												></el-icon>
												Y轴</span
											>
										</div>
									</template>
									<draggable
										class="fieldpanel"
										v-model="yMetas"
										group="charts"
									>
										<template #item="{ element }">
											<li class="list-group-item">
												<el-icon><rank></rank></el-icon
												>{{ element.label }}
											</li>
										</template>
									</draggable>
								</el-card>
							</el-col>
							<el-col :span="24">
								<el-card class="chartMain">
									<template v-if="inputForm.type === 'table'">
										<el-table :data="chartData.rows">
											<el-table-column
												v-for="(
													column, index
												) in chartData.columns"
												:key="index"
												:prop="column"
												:label="column"
											>
											</el-table-column>
										</el-table>
									</template>
									<template v-else>
										<v-chart
											style="
												width: 700px;
												height: 365px;
												margin-left: 50px;
											"
											:loading="barLoading"
											ref="bar"
											:option="objOption"
										></v-chart>
									</template>
								</el-card>
							</el-col>
						</el-row>
					</el-main>
					<el-aside
						width="280px"
						style="
							padding-right: 15px;
							padding-left: 15px;
							border-left: 1px solid rgb(238, 238, 238);
						"
					>
						<el-form
							label-width="100px"
							v-if="activeOption"
							label-position="left"
							size="small"
						>
							<!-- 组件配置 -->
							<p>
								<el-checkbox v-model="custom"
									>开启自定义配置</el-checkbox
								>
							</p>
							<common-option v-show="custom"></common-option>
							<el-alert
								v-show="!custom"
								title="不使用自定义配置，将按照默认风格展示echarts图表，生成的代码会比较简洁，不包含复杂的配置项。"
								type="info"
								:closable="false"
								show-icon
							>
							</el-alert>
						</el-form>
					</el-aside>
				</el-container>
			</el-container>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<!-- <el-button  @click="handleGenerateCode">生成代码</el-button> -->
				<el-button
					v-if="method != 'view'"
					@click="doSubmit()"
					type="primary"
					icon="circle-check"
					v-noMoreClick
					>确定</el-button
				>
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
			</span>
		</template>

		<v-dialog
			v-model="codeVisible"
			width="800px"
			center
			append-to-body
			title="生成代码"
		>
			<el-tabs
				type="border-card"
				style="box-shadow: none"
				v-model="codeActiveName"
			>
				<el-tab-pane label="动态数据" name="dy">
					<VAceEditor
						v-model:value="dyTemplate"
						lang="ftl"
						theme="twilight"
						style="height: 450px"
						:options="{ useWorker: true }"
					/>
				</el-tab-pane>
				<el-tab-pane label="静态数据" name="static">
					<VAceEditor
						v-model:value="staticTemplate"
						lang="ftl"
						theme="twilight"
						style="height: 450px"
						:options="{ useWorker: true }"
					/>
				</el-tab-pane>
			</el-tabs>
			<template #footer>
				<span class="dialog-footer">
					<el-button text v-copy="codeCopyValue">
						拷贝代码
					</el-button>
				</span>
			</template>
		</v-dialog>
	</el-dialog>
</template>

<script>
import draggable from "vuedraggable/src/vuedraggable";
import commonOption from "./option/common";
import { dicOption } from "./option/config";
import generateCode from "./generateCode.js";
import echartsService from "@/api/echarts/echartsService";
import dataMetaService from "@/api/database/dataMetaService";
import dataSetService from "@/api/database/dataSetService";
import { VAceEditor } from "vue3-ace-editor";
import "ace-builds/src-noconflict/mode-ftl";
import "ace-builds/src-noconflict/theme-chrome";
import "ace-builds/src-noconflict/theme-twilight";
import ace from "ace-builds";
import workerJsonUrl from "ace-builds/src-noconflict/worker-json?url";
ace.config.setModuleUrl("ace/mode/json_worker", workerJsonUrl);
let requireComponent = import.meta.globEager("./data/**/*.js");
const modules = {};

for (const key in requireComponent) {
	if (Object.prototype.hasOwnProperty.call(requireComponent, key)) {
		modules[key.replace(/(\.\/|\.ts)/g, "")] =
			requireComponent[key].default;
	}
}

export default {
	components: {
		draggable,
		commonOption,
		VAceEditor,
	},
	provide() {
		return {
			main: this,
			contain: this,
		};
	},
	data() {
		return {
			visible: false,
			loading: false,
			custom: true,
			codeVisible: false,
			codeActiveName: "dy",
			codeCopyValue: "",
			staticTemplate: "",
			dyTemplate: "",
			title: "",
			method: "",
			dataSets: [],
			metas: [],
			xMetas: [],
			yMetas: [],
			chartData: {},
			objOption: {},
			activeOption: {
				title: {
					show: true,
					text: "",
					left: "auto",
					textStyle: {
						color: "#333",
						fontSize: 18,
					},
					subtext: "",
					subtextStyle: {
						color: "#aaa",
						fontSize: 12,
					},
				},
				tooltip: {
					textStyle: {
						color: "#333",
						fontSize: 14,
					},
				},
				grid: {
					left: 50,
					top: 60,
					right: 10,
					bottom: 60,
				},
				legend: {
					show: true,
					orient: "horizontal",
					textStyle: {
						fontSize: 12,
						color: "#333",
					},
				},
				color: [],
			},
			dicOption: dicOption,
			inputForm: {
				id: "",
				name: "",
				option: "",
				xnames: "",
				ynames: "",
				dataSet: {
					id: "",
				},
				type: "line",
			},
		};
	},
	watch: {
		"inputForm.dataSet.id"(val) {
			if (val) {
				dataMetaService.queryNeedByDataSetId(val).then((data) => {
					this.metas = data.filter((item) => {
						let excludeNames =
							"," +
							this.inputForm.xnames +
							"," +
							this.inputForm.ynames +
							",";
						if (excludeNames.indexOf("," + item.name + ",") >= 0) {
							return false;
						} else {
							return true;
						}
					});
				});
			} else {
				this.metas = [];
			}
		},
		custom(val) {
			if (!val) {
				this.refreshChart();
			} else {
				this.objOption = { ...this.objOption, ...this.activeOption };
			}
		},
		xMetas: {
			handler(val) {
				this.inputForm.xnames = val
					.map((item) => {
						return item.name;
					})
					.join(",");
				this.refreshChart();
			},
			immediate: false,
		},
		yMetas: {
			handler(val) {
				this.inputForm.ynames = val
					.map((item) => {
						return item.name;
					})
					.join(",");
				this.refreshChart();
			},
			immediate: false,
		},
		activeOption() {
			this.objOption = { ...this.objOption, ...this.activeOption };
		},
		codeActiveName(val) {
			this.codeCopyValue =
				val === "dy" ? this.dyTemplate : this.staticTemplate;
		},
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建图表组件`;
			} else if (method === "edit") {
				this.title = "修改图表组件";
			} else if (method === "view") {
				this.title = "查看图表组件";
			}
			this.visible = true;
			this.xMetas = [];
			this.yMetas = [];
			this.dataSets = [];

			this.inputForm.dataSet.id = "";
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				dataSetService.list({ current: 1, size: -1 }).then((data) => {
					this.dataSets = data.records;
				});
				if (method === "edit" || method === "view") {
					// 修改或者查看
					echartsService
						.queryDesignById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(
								this.inputForm,
								data.echarts
							);
							this.xMetas = data.xColumnList;
							this.yMetas = data.yColumnList;
							if (data.echarts.option === "") {
								this.custom = false;
							} else {
								this.objOption = JSON.parse(
									data.echarts.option
								);
							}
						});
				}
			});
		},
		handleGenerateCode() {
			this.codeVisible = true;
			let url = `/echarts/getChartData/${this.inputForm.id}`;
			this.dyTemplate = generateCode(
				this.activeOption,
				url,
				"dy",
				this.inputForm.type,
				this.custom
			);
			this.staticTemplate = generateCode(
				this.activeOption,
				this.chartData,
				"static",
				this.inputForm.type,
				this.custom
			);
			this.codeCopyValue =
				this.codeActiveName === "dy"
					? this.dyTemplate
					: this.staticTemplate;
		},
		refreshChart() {
			if (
				this.inputForm.dataSet.id &&
				this.inputForm.xnames &&
				this.inputForm.ynames
			) {
				echartsService
					.mergeChartData({
						dataSetId: this.inputForm.dataSet.id,
						xnames: this.inputForm.xnames,
						ynames: this.inputForm.ynames,
					})
					.then((data) => {
						this.chartData = data;
						this.barLoading = true;
						this.$refs.bar && this.$refs.bar.clear();
						let defaultOption =
							modules[`data/${this.inputForm.type}.js`];
						if (defaultOption) {
							if (this.custom) {
								this.objOption = {
									...defaultOption(data),
									...this.activeOption,
								};
							} else {
								this.objOption = defaultOption(data);
							}
						}
						this.barLoading = false;
					});
			} else {
				this.$refs.bar && this.$refs.bar.clear();
				this.objOption = null;
				this.chartData = [];
			}
		},
		vaildProp(name) {
			return this.dicOption[name].includes(this.inputForm.type);
		},
		resize() {
			this.$refs.chart.echarts.resize();
		},
		onMove() {
			if (this.xMetas.length > 1) {
				this.$message.error(
					`X轴只能设置一条数据，现在有${this.xMetas.length}条数据，请移除多余数据!`
				);
			}
		},
		resetMetas() {
			this.xMetas = [];
			this.yMetas = [];
		},
		log(evt) {
			window.console.log(evt);
		},
		handleSelect(key) {
			this.inputForm.type = key;
			this.refreshChart();
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.$prompt("请输入图表的名字", "提示", {
						confirmButtonText: "确定",
						cancelButtonText: "取消",
						inputValue: this.inputForm.name,
					}).then(({ value }) => {
						this.loading = true;
						this.inputForm.name = value;
						this.inputForm.option = this.custom
							? JSON.stringify(this.objOption)
							: "";
						echartsService.save(this.inputForm).then((data) => {
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
							this.loading = false;
						});
					});
				}
			});
		},
	},
};
</script>
<style lang="scss">
.eHeader {
	background-color: #f8f8f8;
	text-align: left;
	line-height: 50px !important;
	height: 50px !important;
	width: 500px;
	padding: 0px !important;
	.el-menu--horizontal > .el-sub-menu .el-sub-menu__title {
		height: 44px;
		line-height: 44px;
		border-bottom: 2px solid transparent;
		color: #909399;
	}
	.el-menu-dm {
		height: 45px;
	}
	.el-btn-dm {
		float: right;
		margin-right: 10px;
	}
}
.eMain {
	padding: 0px !important;
}
.chartMain {
	margin-top: 10px;
	margin-left: 0px !important;
	margin-right: 0px !important;
}
.list-group-item {
	padding: 5px;
	margin-bottom: 5px;
	width: 100%;
	background: #e3f2f8;
	display: inline-block;
}
.jp-box-card {
	height: 200px;
	border: 2px solid #a0d4e6;
	overflow: auto;
	.el-card__header {
		padding: 5px 20px;
		background: #e3f2f8;
		padding-left: 6px;
		font-weight: 600;
		border-bottom: 2px solid #a0d4e6;
		color: #3f8ce8;
	}
	.el-card__body {
		padding: 5px;
		.fieldpanel {
			height: 150px;
			overflow: auto;
		}
	}
}
.jp-filed-panel {
	padding-top: 10px;
	padding-right: 20px;
	height: 500px;
	overflow: auto;
}
.design-chart .el-dialog__body {
	padding: 0px !important;
	color: #606266;
	font-size: 14px;
	word-break: break-all;
}
</style>
