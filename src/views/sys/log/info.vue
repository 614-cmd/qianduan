<template>
	<el-main style="padding: 0 20px">
		<el-descriptions :column="1" border size="small">
			<el-descriptions-item label="系统功能">{{
				data.title
			}}</el-descriptions-item>
			<el-descriptions-item label="请求接口">{{
				data.requestUri
			}}</el-descriptions-item>
			<el-descriptions-item label="提交方式">{{
				data.requestType
			}}</el-descriptions-item>
			<el-descriptions-item label="请求耗时"
				>{{ data.recordTime }}毫秒</el-descriptions-item
			>
			<el-descriptions-item label="访问时间">{{
				data.createTime
			}}</el-descriptions-item>
		</el-descriptions>
		<el-collapse v-model="activeNames" style="margin-top: 20px">
			<el-collapse-item title="请求后台方法" name="1">
				<div class="code">
					{{ data.method }}
				</div>
			</el-collapse-item>
			<el-collapse-item title="请求参数" name="2">
				<div class="code">
					{{ data.params }}
				</div>
			</el-collapse-item>
			<el-collapse-item title="返回结果" name="3">
				<div class="code">
					{{ data.result }}
				</div>
			</el-collapse-item>
			<el-collapse-item v-if="data.exception" title="异常信息" name="4">
				<div class="code-err">
					{{ data.exception }}
				</div>
			</el-collapse-item>
		</el-collapse>
	</el-main>
</template>

<script>
export default {
	data() {
		return {
			data: {},
			activeNames: ["1"],
			typeMap: {
				info: "info",
				warn: "warning",
				error: "error",
			},
		};
	},
	methods: {
		setData(data) {
			this.data = data;
		},
	},
};
</script>

<style scoped>
.code {
	background: #848484;
	padding: 15px;
	color: #fff;
	font-size: 12px;
	border-radius: 4px;
}
.code-err {
	background: #fcf9f9;
	padding: 15px;
	color: rgb(228, 13, 13);
	font-size: 12px;
	border-radius: 4px;
}
</style>
