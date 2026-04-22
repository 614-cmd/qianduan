<template>
	<!-- <div class="page-white" > -->
	<el-main>
		<el-row :gutter="20">
			<el-col
				:xs="24"
				:sm="12"
				:md="8"
				:lg="6"
				:xl="4"
				v-for="item in dataList"
				:key="item.id"
			>
				<el-card
					shadow="hover"
					border
					:body-style="{ padding: '0px' }"
					@click="click(item.path)"
				>
					<div class="code-item">
						<div class="img">
							<el-icon><component :is="item.icon" /></el-icon>
						</div>
						<div class="title">
							<h2>{{ item.name }}</h2>
							<h4>{{ item.remarks }}</h4>
						</div>
					</div>
				</el-card>
			</el-col>
		</el-row>
	</el-main>
	<!-- </div> -->
</template>

<script>
export default {
	data() {
		return {
			dataList: [
				{
					name: "代码生成",
					tip: "打开代码生成器",
					remarks: "通过设置生成增删改查功能。",
					icon: "element-plus",
					disabled: false,
					color: "blue",
					path: "/gen/GenTableList",
				},
				{
					name: "图表开发",
					tip: "可视化配置图表",
					remarks: "快速可视化开发图表",
					icon: "pie-chart",
					disabled: false,
					color: "red",
					path: "/echarts/EchartsList",
				},
				{
					name: "报表开发",
					tip: "打开报表设计器",
					remarks: "通过设置快速开发报表。",
					icon: "data-analysis",
					disabled: false,
					color: "green",
					path: "/reports/index",
				},
				{
					name: "表单开发",
					tip: "打开表单设计器",
					remarks: "通过设置快速开发自定义表单。",
					icon: "document",
					disabled: false,
					color: "yellow",
					path: "/form/MakeFormList",
				},
				{
					name: "仪表盘开发",
					tip: "即将上线，敬请期待",
					remarks: "通过设置快速开发仪表盘。",
					icon: "orange",
					disabled: true,
					color: "blue",
					path: "",
				},
				{
					name: "数据管理",
					tip: "打开数据管理",
					remarks: "通过sql语句快速创建模型。",
					icon: "connection",
					disabled: false,
					color: "blue",
					path: "/database/datamodel/DataSetList",
				},
			],
		};
	},
	methods: {
		// 新增
		click(path) {
			this.$router.push(path);
		},
	},
};
</script>

<style lang="less" scoped>
.el-main {
	padding: 10px;
}

.el-card {
	margin-bottom: 15px;
}

.code-item {
	cursor: pointer;

	.img {
		width: 100%;
		height: 150px;
		display: flex;
		align-items: center;
		justify-content: center;

		&:hover {
			background-image: -webkit-linear-gradient(
				top left,
				#fff,
				var(--el-color-primary) 200px
			);
		}

		i {
			font-size: 100px;
			color: var(--el-color-primary);
			background-image: -webkit-linear-gradient(
				top left,
				#fff,
				#09f 100px
			);
			-webkit-background-clip: text;
			-webkit-text-fill-color: transparent;
		}

		&:hover i {
			font-size: 100px;
			color: #fff;
			background-image: -webkit-linear-gradient(
				top left,
				#fff,
				#09f 100px
			);
			-webkit-background-clip: text;
			-webkit-text-fill-color: transparent;
		}
	}

	.title {
		border-top: 1px solid #e8e8e8;
		background: #f5f8fa;
		padding: 10px;

		h2 {
			font-size: 16px;
		}

		h4 {
			font-size: 12px;
			color: #999;
			font-weight: normal;
			margin-top: 8px;
		}

		p {
			margin-top: 15px;
		}
	}
}

.bottom {
	margin-top: 13px;
	padding-right: 20px;
	line-height: 12px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}
</style>
