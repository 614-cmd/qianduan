<template>
	<el-card shadow="never" :body-style="{ padding: '17px 20px 21px 20px' }">
		<template #header>
			<div class="card-header">
				<span>线上热门内容</span>
				<el-button type="primary" text>更多</el-button>
			</div>
		</template>

		<el-radio-group
			v-model="type"
			type="button"
			class="hot"
			@change="typeChange"
		>
			<el-radio-button label="text"> 文本 </el-radio-button>
			<el-radio-button label="image"> 图片 </el-radio-button>
			<el-radio-button label="video"> 视频 </el-radio-button>
		</el-radio-group>
		<el-table class="content" :data="renderList">
			<el-table-column
				:show-overflow-tooltip="true"
				label="排名"
				prop="key"
			></el-table-column>
			<el-table-column
				:show-overflow-tooltip="true"
				label="内容标题"
				prop="title"
			></el-table-column>
			<el-table-column
				:show-overflow-tooltip="true"
				label="点击量"
				prop="clickNumber"
			></el-table-column>
			<el-table-column
				:show-overflow-tooltip="true"
				label="日涨幅"
				prop="increases"
				sortable
			></el-table-column>
		</el-table>
	</el-card>
</template>
<script>
const textList = [
	{
		key: 1,
		clickNumber: "346.3w+",
		title: "经济日报：财政政策要精准提升…",
		increases: 35,
	},
	{
		key: 2,
		clickNumber: "324.2w+",
		title: "双12遇冷，消费者厌倦了电商平…",
		increases: 22,
	},
	{
		key: 3,
		clickNumber: "318.9w+",
		title: "致敬坚守战“疫”一线的社区工作…",
		increases: 9,
	},
	{
		key: 4,
		clickNumber: "257.9w+",
		title: "普高还是职高？家长们陷入选择…",
		increases: 17,
	},
	{
		key: 5,
		clickNumber: "124.2w+",
		title: "人民快评：没想到“浓眉大眼”的…",
		increases: 37,
	},
];
const imageList = [
	{
		key: 1,
		clickNumber: "15.3w+",
		title: "杨涛接替陆慷出任外交部美大司…",
		increases: 15,
	},
	{
		key: 2,
		clickNumber: "12.2w+",
		title: "图集：龙卷风袭击美国多州房屋…",
		increases: 26,
	},
	{
		key: 3,
		clickNumber: "18.9w+",
		title: "52岁大姐贴钱照顾自闭症儿童八…",
		increases: 9,
	},
	{
		key: 4,
		clickNumber: "7.9w+",
		title: "杭州一家三口公园宿营取暖中毒",
		increases: 0,
	},
	{
		key: 5,
		clickNumber: "5.2w+",
		title: "派出所副所长威胁市民？警方调…",
		increases: 4,
	},
];
const videoList = [
	{
		key: 1,
		clickNumber: "367.6w+",
		title: "这是今日10点的南京",
		increases: 5,
	},
	{
		key: 2,
		clickNumber: "352.2w+",
		title: "立陶宛不断挑衅致经济受损民众…",
		increases: 17,
	},
	{
		key: 3,
		clickNumber: "348.9w+",
		title: "韩国艺人刘在石确诊新冠",
		increases: 30,
	},
	{
		key: 4,
		clickNumber: "346.3w+",
		title: "关于北京冬奥会，文在寅表态",
		increases: 12,
	},
	{
		key: 5,
		clickNumber: "271.2w+",
		title: "95后现役军人荣立一等功",
		increases: 2,
	},
];
export default {
	data() {
		return {
			type: "text",
			renderList: textList,
		};
	},
	methods: {
		typeChange(contentType) {
			switch (contentType) {
				case "text":
					this.renderList = textList;
					break;
				case "image":
					this.renderList = imageList;
					break;
				case "video":
					this.renderList = videoList;
					break;
			}
		},
	},
};
</script>

<style scoped lang="less">
.general-card {
	min-height: 388px;
}

.hot.el-radio-group {
	display: inline-flex;
	padding: 2px;
	line-height: 26px;
	background-color: #f2f3f5;
	border-radius: 2px;
}

.increases-cell {
	display: flex;
	align-items: center;
	span {
		margin-right: 4px;
	}
}
.content {
	margin-top: 10px;
}
</style>
