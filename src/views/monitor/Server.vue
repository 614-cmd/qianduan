<template>
	<el-container class="container">
		<el-main class="p-0 page-white">
			<el-tabs class="tabs-pages" stretch>
				<el-tab-pane label="服务器监控" lazy>
					<el-main>
						<el-card shadow="never" header="服务器">
							<el-row>
								<el-col :span="8">
									<div class="server-top-item">
										<el-progress
											type="dashboard"
											:percentage="ServerOS.cpu.used"
											:stroke-width="8"
											:width="150"
											:color="colors"
										>
											<template #default="{ percentage }">
												<span class="percentage-value"
													>{{ percentage
													}}<em>%</em></span
												>
												<span class="percentage-label"
													>CPU</span
												>
											</template>
										</el-progress>
									</div>
								</el-col>
								<el-col :span="8">
									<div class="server-top-item">
										<el-progress
											type="dashboard"
											:percentage="ServerOS.mem.used"
											:stroke-width="8"
											:width="150"
											:color="colors"
										>
											<template #default="{ percentage }">
												<span class="percentage-value"
													>{{ percentage
													}}<em>%</em></span
												>
												<span class="percentage-label"
													>内存</span
												>
											</template>
										</el-progress>
									</div>
								</el-col>
								<el-col :span="8">
									<div class="server-top-item">
										<el-progress
											type="dashboard"
											:percentage="ServerOS.jvm.used"
											:stroke-width="8"
											:width="150"
											:color="colors"
										>
											<template #default="{ percentage }">
												<span class="percentage-value"
													>{{ percentage
													}}<em>%</em></span
												>
												<span class="percentage-label"
													>JVM</span
												>
											</template>
										</el-progress>
									</div>
								</el-col>
							</el-row>
							<el-tabs
								style="margin-top: 20px"
								@tab-click="tabClick"
							>
								<el-tab-pane label="CPU" lazy>
									<div class="server-item-info">
										<div class="title">
											<label>CPU</label>
											<span>{{
												ServerOS.cpu.cpucard
													.processorIdentifier.name
											}}</span>
										</div>
										<div
											class="chart"
											style="
												border-color: #409eff;
												background: rgba(
													64,
													158,
													255,
													0.05
												);
											"
										>
											<v-chart
												ref="cpu"
												height="100%"
												:option="option.cpu"
											></v-chart>
										</div>
										<div class="data">
											<el-descriptions
												:column="2"
												border
												title="CPU使用率"
											>
												<el-descriptions-item
													label="利用率"
													>{{
														ServerOS.cpu.used
													}}%</el-descriptions-item
												>
												<el-descriptions-item
													label="速度"
													>{{
														ServerOS.cpu.cpucard.processorIdentifier.name.split(
															"@"
														)[1]
													}}</el-descriptions-item
												>
												<el-descriptions-item
													label="内核"
													>{{
														ServerOS.cpu.cpucard
															.logicalProcessorCount
													}}</el-descriptions-item
												>
											</el-descriptions>
										</div>
									</div>
								</el-tab-pane>
								<el-tab-pane label="内存" lazy>
									<div class="server-item-info">
										<div class="title">
											<label>内存</label>
											<span
												>{{ ServerOS.mem.total }}G</span
											>
										</div>
										<div
											class="chart"
											style="
												border-color: #8b12ae;
												background: rgba(
													139,
													18,
													174,
													0.05
												);
											"
										>
											<v-chart
												ref="memory"
												height="100%"
												:option="option.memory"
											></v-chart>
										</div>
										<div class="data">
											<el-descriptions
												:column="2"
												border
												title="内存使用率"
											>
												<el-descriptions-item
													label="使用率"
													>{{
														ServerOS.mem.used
													}}%</el-descriptions-item
												>
												<el-descriptions-item
													label="总内存"
													>{{
														ServerOS.mem.total
													}}G</el-descriptions-item
												>
												<el-descriptions-item
													label="已用内存"
													>{{
														ServerOS.mem.usedMem
													}}G</el-descriptions-item
												>
											</el-descriptions>
										</div>
									</div>
								</el-tab-pane>
								<el-tab-pane label="JVM" lazy>
									<div class="server-item-info">
										<div class="title">
											<label>JVM大小</label>
											<span
												>{{ ServerOS.jvm.total }}M</span
											>
										</div>
										<div
											class="chart"
											style="
												border-color: #a74f01;
												background: rgba(
													167,
													79,
													1,
													0.05
												);
											"
										>
											<v-chart
												ref="jvm"
												height="100%"
												:option="option.jvm"
											></v-chart>
										</div>
										<div class="data">
											<el-descriptions
												:column="2"
												border
												title="JVM使用率"
											>
												<el-descriptions-item
													label="使用率"
													>{{
														ServerOS.jvm.used
													}}%</el-descriptions-item
												>
												<el-descriptions-item
													label="JVM大小"
													>{{
														ServerOS.jvm.total
													}}G</el-descriptions-item
												>
												<el-descriptions-item
													label="已用JVM"
													>{{
														ServerOS.jvm.usedMem
													}}G</el-descriptions-item
												>
											</el-descriptions>
										</div>
									</div>
								</el-tab-pane>
								<el-tab-pane label="硬盘" lazy>
									<div class="server-item-info">
										<div class="title">
											<label>硬盘</label>
										</div>
										<div class="disk-list">
											<el-row :gutter="20">
												<el-col
													:span="6"
													v-for="(
														disk, index
													) in ServerOS.file"
													:key="index"
												>
													<div class="disk-list-item">
														<h2>{{ disk.name }}</h2>
														<el-progress
															:stroke-width="10"
															:percentage="
																disk.rate
															"
															:color="colors"
														/>
														<p>
															{{ disk.free }} GB
															可用，共
															{{ disk.total }} GB
														</p>
													</div>
												</el-col>
											</el-row>
										</div>
									</div>
								</el-tab-pane>
							</el-tabs>
						</el-card>
					</el-main>
				</el-tab-pane>
				<el-tab-pane label="基本信息" lazy>
					<el-main>
						<el-card shadow="never">
							<div class="data" v-if="ServerOS">
								<el-descriptions
									:column="2"
									border
									title="服务器信息"
								>
									<el-descriptions-item label="服务器名称">{{
										ServerOS.hostname
									}}</el-descriptions-item>
									<el-descriptions-item label="操作系统"
										>{{ ServerOS.sys["os.name"]
										}}{{
											ServerOS.sys["os.version"]
										}}</el-descriptions-item
									>
									<el-descriptions-item label="服务器IP">{{
										ServerOS.ip
									}}</el-descriptions-item>
									<el-descriptions-item label="系统架构">{{
										ServerOS.sys["os.arch"]
									}}</el-descriptions-item>
									<el-descriptions-item label="CPU">{{
										ServerOS.cpu.cpucard.processorIdentifier
											.name
									}}</el-descriptions-item>
								</el-descriptions>
							</div>
						</el-card>
						<el-card shadow="never" style="margin-top: 15px">
							<div class="data" v-if="ServerOS">
								<el-descriptions
									:column="2"
									border
									title="JAVA信息"
								>
									<el-descriptions-item label="JAVA名称">{{
										ServerOS.sys["java.vm.name"]
									}}</el-descriptions-item>
									<el-descriptions-item label="JAVA版本">{{
										ServerOS.sys["java.version"]
									}}</el-descriptions-item>
									<el-descriptions-item
										label="JAVA的安装路径"
										>{{
											ServerOS.sys["java.home"]
										}}</el-descriptions-item
									>
									<el-descriptions-item label="JAVA供应商">{{
										ServerOS.sys["java.vendor"]
									}}</el-descriptions-item>
									<el-descriptions-item
										label="JVM最大可用内存"
										>{{
											ServerOS.jvm.maxTotal
										}}M</el-descriptions-item
									>
								</el-descriptions>
							</div>
						</el-card>
					</el-main>
				</el-tab-pane>
			</el-tabs>
		</el-main>
	</el-container>
</template>

<script>
import monitorService from "@/api/monitor/monitorService";
export default {
	name: "server",
	data() {
		return {
			ServerOS: {
				cpu: {
					cpucard: {
						processorIdentifier: {
							name: "",
						},
					},
				},
				mem: {},
				jvm: {},
				file: [],
			},
			interval: "",
			group: [
				{
					label: "SQL Server",
					icon: "el-icon-coin",
					code: "1",
				},
				{
					label: "Web Server",
					icon: "el-icon-monitor",
					code: "2",
				},
				{
					label: "Api Server",
					icon: "el-icon-monitor",
					code: "3",
				},
				{
					label: "Nginx Server",
					icon: "el-icon-guide",
					code: "4",
				},
			],
			event: [
				{
					title: "Disk 大于 90%",
					type: "告警事件",
					push: "短信",
					time: "2021-10-09 21:25:19",
				},
				{
					title: "Disk 大于 90%",
					type: "致命事件",
					push: "短信",
					time: "2021-10-09 21:25:19",
				},
			],
			rule: [
				{
					name: "test_cpu",
					title: "测试CPU报警",
					type: "CPU",
					push: "短信",
				},
				{
					name: "test_cpu",
					title: "测试CPU报警",
					type: "CPU",
					push: "短信",
				},
			],
			option: {
				cpu: {
					tooltip: {
						trigger: "axis",
					},
					grid: {
						top: "0",
						bottom: "0",
						left: "0",
						right: "0",
					},
					animation: false,
					xAxis: {
						type: "category",
						boundaryGap: false,
						data: (function () {
							var now = new Date();
							var res = [];
							var len = 60;
							while (len--) {
								res.unshift(
									now.toLocaleTimeString().replace(/^\D*/, "")
								);
								now = new Date(now - 2000);
							}
							return res;
						})(),
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							show: false,
						},
					},
					yAxis: {
						type: "value",
						min: 0,
						max: 100,
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							show: false,
						},
					},
					series: [
						{
							data: (function () {
								var res = [];
								var len = 60;
								while (len--) {
									res.push(Math.round(Math.random() * 0));
								}
								return res;
							})(),
							type: "line",
							symbol: "none",
							lineStyle: {
								width: 1,
								color: "#409EFF",
							},
							areaStyle: {
								opacity: 0.1,
								color: "#409EFF",
							},
						},
					],
				},
				memory: {
					tooltip: {
						trigger: "axis",
					},
					grid: {
						top: "0",
						bottom: "0",
						left: "0",
						right: "0",
					},
					animation: false,
					xAxis: {
						type: "category",
						boundaryGap: false,
						data: (function () {
							var now = new Date();
							var res = [];
							var len = 60;
							while (len--) {
								res.unshift(
									now.toLocaleTimeString().replace(/^\D*/, "")
								);
								now = new Date(now - 2000);
							}
							return res;
						})(),
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							show: false,
						},
					},
					yAxis: {
						type: "value",
						min: 0,
						max: 100,
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							show: false,
						},
					},
					series: [
						{
							data: (function () {
								var res = [];
								var len = 60;
								while (len--) {
									res.push(Math.round(Math.random() * 0));
								}
								return res;
							})(),
							type: "line",
							symbol: "none",
							lineStyle: {
								width: 1,
								color: "#8b12ae",
							},
							areaStyle: {
								opacity: 0.1,
								color: "#8b12ae",
							},
						},
					],
				},
				jvm: {
					tooltip: {
						trigger: "axis",
					},
					grid: {
						top: "0",
						bottom: "0",
						left: "0",
						right: "0",
					},
					animation: false,
					xAxis: {
						type: "category",
						boundaryGap: false,
						data: (function () {
							var now = new Date();
							var res = [];
							var len = 60;
							while (len--) {
								res.unshift(
									now.toLocaleTimeString().replace(/^\D*/, "")
								);
								now = new Date(now - 2000);
							}
							return res;
						})(),
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							show: false,
						},
					},
					yAxis: {
						type: "value",
						min: 0,
						max: 100,
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							show: false,
						},
					},
					series: [
						{
							data: (function () {
								var res = [];
								var len = 60;
								while (len--) {
									res.push(Math.round(Math.random() * 0));
								}
								return res;
							})(),
							type: "line",
							symbol: "none",
							lineStyle: {
								width: 1,
								color: "#a74f01",
							},
							areaStyle: {
								opacity: 0.1,
								color: "#a74f01",
							},
						},
					],
				},
			},
		};
	},
	mounted() {
		this.refreshList();
	},
	unmounted() {
		clearInterval(this.interval);
	},
	methods: {
		refreshList() {
			this.loading = true;
			this.getInfo();
			var timesRun = 0;
			if (this.interval) {
				clearInterval(this.interval);
			}
			this.interval = setInterval(() => {
				timesRun += 1;
				if (timesRun === 50) {
					clearInterval(this.interval);
				}
				this.getInfo();
			}, 1000);
		},
		getInfo() {
			monitorService.info().then((data) => {
				this.ServerOS = data;
				this.option.cpu.series[0].data.shift();
				this.option.cpu.series[0].data.push(
					Math.round(this.ServerOS.cpu.used)
				);
				this.option.cpu.xAxis.data.shift();
				this.option.cpu.xAxis.data.push(
					new Date().toLocaleTimeString().replace(/^\D*/, "")
				);

				this.option.memory.series[0].data.shift();
				this.option.memory.series[0].data.push(
					Math.round(this.ServerOS.mem.used)
				);
				this.option.memory.xAxis.data.shift();
				this.option.memory.xAxis.data.push(
					new Date().toLocaleTimeString().replace(/^\D*/, "")
				);

				this.option.jvm.series[0].data.shift();
				this.option.jvm.series[0].data.push(
					Math.round(this.ServerOS.jvm.used)
				);
				this.option.jvm.xAxis.data.shift();
				this.option.jvm.xAxis.data.push(
					new Date().toLocaleTimeString().replace(/^\D*/, "")
				);
			});
		},
		colors(percentage) {
			if (percentage < 50) {
				return "#409EFF";
			}
			if (percentage < 75) {
				return "#E6A23C";
			}
			return "#F56C6C";
		},
		tabClick(tab) {
			this.$nextTick(() => {
				if (tab.props.label == "CPU" && this.$refs.cpu.myChart) {
					this.$refs.cpu.myChart.resize();
				}
				if (tab.props.label == "内存" && this.$refs.memory.myChart) {
					this.$refs.memory.myChart.resize();
				}
				if (tab.props.label == "jvm" && this.$refs.jvm.myChart) {
					this.$refs.jvm.myChart.resize();
				}
			});
		},
	},
};
</script>

<style scoped>
.container {
	padding: 12px 12px 8px 12px;
	display: flex;
}

.tabs-pages {
	display: flex;
	flex-flow: column;
	flex-shrink: 0;
	height: 100%;
}
.tabs-pages > .el-tabs__header {
	background: #fff;
	margin: 0;
}
.tabs-pages > .el-tabs__header .el-tabs__nav-wrap {
	display: flex;
	justify-content: center;
}
.tabs-pages > .el-tabs__header .el-tabs__item {
	height: 60px;
	line-height: 60px;
	font-size: 14px;
}
.tabs-pages > .el-tabs__content {
	overflow-x: hidden;
	overflow: auto;
}
.server-top-item {
	text-align: center;
}

.server-top-item .percentage-value {
	display: block;
	font-size: 24px;
	font-weight: bold;
}

.server-top-item .percentage-value em {
	font-size: 14px;
	font-style: normal;
	margin-left: 5px;
	font-weight: normal;
}

.server-top-item .percentage-label {
	display: block;
	font-size: 12px;
	margin-top: 10px;
}

.server-item-info .title {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 15px;
}

.server-item-info .title label {
	font-size: 26px;
}

.server-item-info .title span {
	font-size: 14px;
}
.server-item-info .cell {
	font-size: 14px;
	margin: 5px;
}

.server-item-info .chart {
	height: 250px;
	border: 1px solid #eee;
}

.server-item-info .data {
	margin-top: 20px;
}

.disk-list-item {
	margin: 10px 0;
}

.disk-list-item h2 {
	font-weight: normal;
	font-size: 12px;
}

.disk-list-item .el-progress {
	margin: 5px 0;
}

.disk-list-item p {
	color: #999;
	font-size: 12px;
}
</style>
