<template>
	<div :class="['widgets-home', customizing ? 'customizing' : '']" ref="main">
		<div class="widgets-content">
			<div v-if="mode != 'view'" class="widgets-top">
				<div class="widgets-top-title">
					{{ name }}
				</div>
				<div class="widgets-top-actions">
					<el-button
						v-if="customizing"
						type="primary"
						icon="el-icon-check"
						round
						@click="save"
						>完成</el-button
					>
					<el-button
						v-if="customizing && !adding"
						type="primary"
						icon="el-icon-plus"
						round
						@click="custom"
					></el-button>
					<el-button
						v-if="!customizing"
						type="primary"
						icon="el-icon-edit"
						round
						@click="layout"
					></el-button>
				</div>
			</div>
			<div class="widgets" ref="widgets">
				<div class="widgets-wrapper">
					<div v-if="nowCompsList.length <= 0" class="no-widgets">
						<el-empty
							image="img/no-widgets.svg"
							description="没有部件啦"
							:image-size="280"
						></el-empty>
					</div>
					<div>
						<grid-layout
							:layout="grid"
							:col-num="colNum"
							:row-height="30"
							:is-draggable="customizing"
							:is-resizable="customizing"
							:responsive="false"
						>
							<grid-item
								v-for="(item, index) in grid"
								:key="index"
								:static="item.static"
								:x="item.x"
								:y="item.y"
								:w="item.w"
								:h="item.h"
								:i="item.i"
							>
								<div class="widgets-item">
									<ChartPanel
										:id="item.id"
										:w="item.w"
										:h="item.h"
										:title="item.title"
									></ChartPanel>

									<!-- <component :is="allComps[item.name]"></component> -->
									<div
										v-if="customizing"
										:class="[
											'customize-overlay',
											customizing ? 'layout-overlay' : '',
										]"
									>
										<el-button
											class="close"
											type="danger"
											v-if="customizing"
											icon="el-icon-close"
											size="small"
											@click="removeItem(item.i)"
										></el-button>
										<!-- <label v-if="customizing">{{item.w}}{{item.h}}<el-icon><component :is="allComps[item.name].icon" /></el-icon>{{ allComps[item.name].title }}</label> -->
									</div>
								</div>
							</grid-item>
						</grid-layout>
					</div>
				</div>
			</div>
		</div>
		<div v-if="adding" class="widgets-aside">
			<el-container>
				<el-header>
					<div class="widgets-aside-title">
						<el-icon><el-icon-circle-plus-filled /></el-icon
						>添加部件
					</div>
					<div class="widgets-aside-close" @click="close()">
						<el-icon><el-icon-close /></el-icon>
					</div>
				</el-header>
				<el-main class="nopadding">
					<div class="widgets-list">
						<div
							v-if="myCompsList.length <= 0"
							class="widgets-list-nodata"
						>
							<el-empty
								description="没有部件啦"
								:image-size="60"
							></el-empty>
						</div>
						<div
							v-for="item in myCompsList"
							:key="item.title"
							class="widgets-list-item"
						>
							<div class="item-logo">
								<el-icon><component :is="item.icon" /></el-icon>
							</div>
							<div class="item-info">
								<h2>{{ item.title }}</h2>
								<p>{{ item.description }}</p>
							</div>
							<div class="item-actions">
								<el-button
									type="primary"
									icon="el-icon-plus"
									size="small"
									@click="push(item)"
								></el-button>
							</div>
						</div>
					</div>
				</el-main>
				<el-footer style="height: 51px">
					<el-button type="default" size="small" @click="backDefaul()"
						>恢复默认</el-button
					>
				</el-footer>
			</el-container>
		</div>
	</div>
</template>

<script>
import dashboardService from "@/api/echarts/dashboardService";
import echartsService from "@/api/echarts/echartsService";
import draggable from "vuedraggable/src/vuedraggable";
import ChartPanel from "./components/ChartPanel";
import { GridLayout, GridItem } from "vue-grid-layout-jp";
export default {
	props: ["mode"],
	components: {
		draggable,
		GridLayout,
		ChartPanel,
		GridItem,
	},
	data() {
		return {
			customizing: false,
			adding: false,
			id: "",
			name: "",
			allComps: [],
			selectedComps: [],
			selectLayout: [],
			defaultGrid: [],
			grid: [],
			draggable: true,
			resizable: true,
			responsive: true,
			colNum: 12,
			index: 0,
		};
	},
	created() {
		this.id = this.$route.query.id;
		dashboardService.queryById(this.id).then((data) => {
			this.name = data.name;
			if (data.grid) {
				this.grid = JSON.parse(data.grid);
				this.defaultGrid = JSON.parse(data.grid);
			}
		});

		echartsService
			.list({
				current: 0,
				size: -1,
			})
			.then((data) => {
				this.allComps = [];
				data.records.forEach((item) => {
					this.allComps.push({
						id: item.id,
						title: item.name,
						icon: "tubiao",
						description: item.remarks,
						layout: {
							w: 6,
							h: 6,
						},
					});
				});
			});
	},
	mounted() {
		this.$emit("on-mounted");
		this.index = this.grid.length;
	},
	computed: {
		allCompsList() {
			var allCompsList = [];
			for (var key in this.allComps) {
				allCompsList.push({
					key: key,
					id: this.allComps[key].id,
					title: this.allComps[key].title,
					icon: this.allComps[key].icon,
					description: this.allComps[key].description,
					layout: this.allComps[key].layout,
				});
			}
			for (let comp of allCompsList) {
				const _item = this.grid.find((item) => {
					return item.name === comp.key;
				});
				if (_item) {
					comp.disabled = true;
				}
			}
			return allCompsList;
		},
		myCompsList() {
			return this.allCompsList.filter((item) => !item.disabled);
		},
		nowCompsList() {
			return this.grid;
		},
	},
	methods: {
		addItem: function (item) {
			// Add a new item. It must have a unique key!
			this.grid.push({
				x: (this.grid.length * 2) % (this.colNum || 12),
				y: this.grid.length + (this.colNum || 12), // puts it at the bottom
				w: item.layout.w,
				h: item.layout.h,
				i: this.index,
				name: item.key,
				id: item.id,
				title: item.title,
			});
			// Increment the counter to ensure key is always unique.
			this.index++;
		},
		removeItem: function (val) {
			const index = this.grid.map((item) => item.i).indexOf(val);
			this.grid.splice(index, 1);
		},
		//开启自定义
		custom() {
			this.adding = true;
			// const oldWidth = this.$refs.widgets.offsetWidth
			// this.$nextTick(() => {
			// 	const scale =  this.$refs.widgets.offsetWidth / oldWidth
			// 	this.$refs.widgets.style.setProperty('transform', `scale(${scale})`)
			// })
		},
		layout() {
			this.customizing = true;
		},
		//追加
		push(item) {
			this.addItem(item);
		},
		//隐藏组件
		remove(item) {
			var newCopmsList = this.grid.copmsList;
			newCopmsList.forEach((obj, index) => {
				var newObj = obj.filter((o) => o != item);
				newCopmsList[index] = newObj;
			});
		},
		//保存
		save() {
			this.customizing = false;
			this.adding = false;
			// this.$refs.widgets.style.removeProperty('transform')
			// this.$TOOL.data.set("grid", this.grid)
			dashboardService.save({
				id: this.id,
				grid: JSON.stringify(this.grid),
			});
		},
		//恢复默认
		backDefaul() {
			this.customizing = true;
			// this.$refs.widgets.style.removeProperty('transform')
			this.grid = JSON.parse(JSON.stringify(this.defaultGrid));
			dashboardService.save({
				id: this.id,
				grid: JSON.stringify(this.defaultGrid),
			});
			// this.$TOOL.data.remove("grid")
		},
		//关闭
		close() {
			this.adding = false;
			// this.$refs.widgets.style.removeProperty('transform')
		},
	},
};
</script>

<style scoped lang="scss">
.el-header {
	background: #fff;
	border-bottom: 1px solid var(--el-border-color-light);
	padding: 13px 15px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.el-footer {
	background: #fff;
	border-top: 1px solid var(--el-border-color-light);
	padding: 13px 15px;
	height: 51px;
}

.el-main {
	flex-basis: 100%;
}

.widgets-home {
	display: flex;
	flex-direction: row;
	flex: 1;
	height: 100%;
}

.widgets-content {
	flex: 1;
	overflow: auto;
	overflow-x: hidden;
	padding: 5px;
}

.widgets-aside {
	margin: -10px -10px -10px 0;
	width: 360px;
	background: #fff;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	position: relative;
	overflow: auto;
}

.widgets-aside-title {
	font-size: 14px;
	display: flex;
	align-items: center;
	justify-content: center;

	i {
		margin-right: 10px;
		font-size: 18px;
	}
}

.widgets-aside-close {
	font-size: 18px;
	width: 30px;
	height: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 3px;
	cursor: pointer;

	&:hover {
		background: rgba(180, 180, 180, 0.1);
	}
}

.widgets-top {
	margin-bottom: 15px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.widgets-top-title {
	font-size: 18px;
	font-weight: bold;
}

.draggable-box {
	height: 100%;
}

.widgets-item {
	position: relative;
	height: 100%;

	// margin-bottom: 15px;
}

.customizing .widgets-wrapper {
	margin-right: -360px;

	.el-col {
		padding-bottom: 15px;
	}

	.draggable-box {
		border: 1px dashed var(--el-color-primary);
		padding: 15px;
	}

	.no-widgets {
		display: none;
	}
}

// .customizing .widgets-item {position: relative;margin-bottom: 15px;}

.customize-overlay {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	z-index: 1;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	background: rgba(255, 255, 255, 0.9);
	cursor: move;

	label {
		background: var(--el-color-primary);
		color: #fff;
		height: 40px;
		padding: 0 30px;
		border-radius: 40px;
		font-size: 18px;
		display: flex;
		align-items: center;
		justify-content: center;
		cursor: move;

		i {
			margin-right: 15px;
			font-size: 24px;
		}
	}

	.close {
		position: absolute;
		top: 15px;
		right: 15px;
	}
}

// .customize-overlay .close:focus, .customize-overlay .close:hover {background: var(--el-button-hover-color);}

.layout-overlay {
	border: 2px dotted #2d8cf0;
	background: transparent !important;
}

.widgets-list-item {
	display: flex;
	flex-direction: row;
	padding: 15px;
	align-items: center;

	.item-logo {
		width: 40px;
		height: 40px;
		border-radius: 50%;
		background: rgba(180, 180, 180, 0.1);
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 18px;
		margin-right: 15px;
		color: #6a8bad;
	}

	.item-info {
		flex: 1;

		h2 {
			font-size: 16px;
			font-weight: normal;
			cursor: default;
		}

		p {
			font-size: 12px;
			color: #999;
			cursor: default;
		}
	}

	&:hover {
		background: rgba(180, 180, 180, 0.1);
	}
}

.widgets-wrapper .sortable-ghost {
	opacity: 0.5;
}

.selectLayout {
	width: 100%;
	display: flex;
}

.selectLayout-item {
	width: 60px;
	height: 60px;
	border: 2px solid var(--el-border-color-light);
	padding: 5px;
	cursor: pointer;
	margin-right: 15px;

	span {
		display: block;
		background: var(--el-border-color-light);
		height: 46px;
	}

	&.item02 {
		span {
			height: 30px;
		}

		.el-col:nth-child(1) span {
			height: 14px;
			margin-bottom: 2px;
		}
	}

	&.item03 span {
		height: 14px;
		margin-bottom: 2px;
	}

	&:hover {
		border-color: var(--el-color-primary);
	}

	&.active {
		border-color: var(--el-color-primary);

		span {
			background: var(--el-color-primary);
		}
	}
}

.dark {
	.widgets-aside {
		background: #2b2b2b;
	}
	.customize-overlay {
		background: rgba(43, 43, 43, 0.9);
	}
}

@media (max-width: 992px) {
	.customizing .widgets-aside {
		width: 100%;
		position: absolute;
		top: 50%;
		right: 0;
		bottom: 0;
	}
	.customizing .widgets-wrapper {
		margin-right: 0;
	}
}

.layoutJSON {
	background: #ddd;
	border: 1px solid black;
	margin-top: 10px;
	padding: 10px;
}

.columns {
	-moz-columns: 120px;
	-webkit-columns: 120px;
	columns: 120px;
}

/*************************************/

.remove {
	position: absolute;
	right: 2px;
	top: 0;
	cursor: pointer;
}

.vue-resizable-handle {
	z-index: 300;
}

.vue-grid-item {
	.resizing {
		opacity: 0.9;
	}

	.static {
		background: #cce;
	}

	.text {
		font-size: 24px;
		text-align: center;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		margin: auto;
		height: 100%;
		width: 100%;
	}

	.no-drag {
		height: 100%;
		width: 100%;
	}

	.minMax {
		font-size: 12px;
	}

	.add {
		cursor: pointer;
	}
}

.vue-draggable-handle {
	position: absolute;
	width: 20px;
	height: 20px;
	top: 0;
	left: 0;
	background: url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='10' height='10'><circle cx='5' cy='5' r='5' fill='#999999'/></svg>")
		no-repeat;
	background-position: bottom right;
	padding: 0 8px 8px 0;
	background-repeat: no-repeat;
	background-origin: content-box;
	box-sizing: border-box;
	cursor: pointer;
}
</style>
