<template>
	<el-calendar class="page">
		<template #date-cell="{ data }">
			<p
				style="height: 100%; width: 100%"
				@click.stop="selectHandler(data)"
				:class="data.isSelected ? 'is-selected' : ''"
			>
				{{ data.day.split("-").slice(1).join("-") }}
				<br /><br />
				<template v-for="(event, index) in calendarEvents">
					<el-tag
						:key="index"
						@click.stop="handleEventClick(event)"
						v-if="
							moment(data.day).format('YYYY-MM-DD HH:mm:ss') ===
							event.startDate
						"
					>
						{{ event.title }}
					</el-tag>
				</template>
			</p>
		</template>
	</el-calendar>
	<MyCalendarForm
		ref="myCalendarForm"
		@refreshDataList="refreshList"
	></MyCalendarForm>
</template>
<script>
import MyCalendarForm from "./MyCalendarForm";
import myCalendarService from "@/api/calendar/myCalendarService";
export default {
	data() {
		return {
			startDate: new Date(),
			endDate: new Date(),
			calendarEvents: [],
		};
	},
	components: {
		MyCalendarForm,
	},
	activated() {
		this.refreshList();
	},
	methods: {
		// 选择月份
		selectHandler(data) {
			this.startDate = data.day;
			this.endDate = data.day;
			this.$refs.myCalendarForm.init(
				"add",
				"",
				this.startDate,
				this.endDate
			);
		},
		handleEventClick(info) {
			this.$refs.myCalendarForm.init("edit", info.id);
		},
		refreshList() {
			myCalendarService.list().then((data) => {
				this.calendarEvents = data;
			});
		},
	},
};
</script>
<style>
.is-selected {
	color: #1989fa;
}
</style>
