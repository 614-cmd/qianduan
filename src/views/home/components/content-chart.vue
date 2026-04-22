<template>
	<!-- <el-card shadow="never" :style="{ border: '0px solid ' }">  -->
	<el-row class="banner">
		<el-col :span="24">
			<el-calendar size="middle">
				<template #date-cell="{ data }">
					{{ moment(data.day).format("D") }} <br />
					<template
						v-for="(event, index) in calendarEvents"
						:key="index"
					>
						<div
							:style="{ color: event.color }"
							v-if="moment(data.day).format('D') == event.day"
						>
							<div class="doat">
								<el-icon size="28"> <doat></doat></el-icon>
							</div>
							{{ event.title }}
						</div>
					</template>
				</template>
			</el-calendar>
		</el-col>
	</el-row>
	<!-- </el-card> -->
</template>

<script>
export default {
	name: "chart",
	data() {
		return {
			calendarEvents: [
				{
					day: 1,
					color: "#5cadff",
					title: "月初例会",
				},
				{
					day: 7,
					color: "#ff9900",
					title: "项目例会",
				},
				{
					day: 19,
					color: "#19be6b",
					title: "产品研发",
				},
				{
					day: 21,
					color: "#ed4014",
					title: "公司会议",
				},
				{
					day: 27,
					color: "#fe84c0",
					title: "员工考核",
				},
			],
		};
	},
};
</script>

<style lang="less">
.banner {
	.el-calendar__title {
		color: #17233d;
		font-size: 16px;
	}
}
.doat {
	height: 28px;
	border-radius: 50%;
	text-align: center;
	vertical-align: middle;
	display: inline-block;
}
</style>
