<template>
	<div class="container">
		<div class="left-side">
			<div class="panel">
				<Banner />
				<!-- <DataPanel /> -->
				<ContentChart />
			</div>
			<el-row :gutter="16" style="margin-top: 16px">
				<el-col :span="12">
					<CateforiesPercent />
				</el-col>
				<el-col :span="12">
					<Carousel />
				</el-col>
			</el-row>
		</div>
		<div class="right-side">
			<el-row :gutter="16">
				<el-col :span="24">
					<div class="panel moduler-wrap">
						<QuickOperation />
					</div>
				</el-col>
				<!-- <el-col :span="24">
          <div class="panel moduler-wrap">
            <RecentlyVisited />
          </div>
        </el-col> -->
				<el-col class="panel" :span="24">
					<Announcement />
				</el-col>
				<el-col class="panel" :span="12">
					<Docs />
				</el-col>
				<el-col class="panel" :span="12">
					<Wx />
				</el-col>
			</el-row>
		</div>
		<el-dialog v-model="visible">
			<template #header>
				<div class="my-header">
					<h2 style="padding: 0px 50px">
						Jeeplus Vue3 近期更新内容。
					</h2>
				</div>
			</template>
			<div style="padding: 0px 50px">
				<br />
				<h3>2024 /09/03 9.48版本发布</h3>
				1 validation-api 升级为 jakarta.validation-api<br />

				2 springboot 升级为3.3.3 <br />

				3 修复登录密码错误时抛出异常信息未封装的bug <br />

				4 调整配置类的目录结构 <br />

				5 升级代码生成器和模板 <br />

				6 修改jdk的compile level 为17 <br />
				<br />
				<br />
				<h3>2024 /08/25 9.47版本发布</h3>
				1 修复导出模板图标不显示的bug。<br />

				2 修复导出Excel时，包含时间、字典时，导出失败的bug。 <br />

				3 修复工作流表单富文本编辑器显示错乱的bug。 <br />

				4 修复移动端uniapp不兼容微信小程序的bug。 <br />

				<br/>
				<h3>2024 /07/30 9.46版本发布</h3>

				修复数据库库表管理--》表编辑bug
				<br />
				<br />
				<h3>2024 /07/02 9.45版本发布</h3>
				1 从springboot2.0 升级到 springboot3.3.0，jdk8升级到jdk17 <br />
				2
				移除jeeplus定制修改版本的多数据源，改为官方版本，便于升级维护，同时将mybatis
				plus升级为mybatis-plus-spring-boot3-starter <br />
				3 工作流从flowable6.7升级为flowable7.0.1 <br />
				4 工作流新增功能如下
				<div style="margin-left: 20px">
					<li>
						动态流程表单可以选择关联数据库物理表,将表单数据存储到用户指定的表中，方便做数据统计之类的工作。
						<br />
					</li>
					<li>流程设计-》表单设置-》可读可写，增加全选功能。</li>
					<li>
						流程表单，包含折叠面板，inline,card,无法解析出字段的bug.
					</li>
					<li>
						增加对工作流子流程的支持，同时修复子流程图无法在流程设计器显示的bug。
					</li>
					<li>增加 加签 减签 功能</li>
					<li>
						当流程存在驳回等反复动作时，修复历史流程图错乱的bug.
					</li>
				</div>
				5 修复字典值排序bug,字典值类型从varchar 修改为 int类型。 <br />
				6 修复系统超时弹出多个窗口的bug， 有多个超时请求只弹出一个弹窗。
				<br />
				7 修复基本设置里面不能修改密码的bug. <br />
				8 优化用户体验，人员选择弹窗增加全选功能。 <br />
				9
				修复每次修改都必须刷新才能正常访问的bug，bug详细：每次前台更新代码后，接口访问报undifine异常，必须刷新才行。
				<br />
				10 组件中的部分css代码 改成less <br />
				11 修复 控制台 isCE报错 <br />
				12 修复控制台grid 不能编辑拖拽的bug。 <br />
				13 修复表单设计器按所属数据库排序报错的bug。 <br />
				14
				优化用户体验，创建新菜单后，无需退出重新登录，刷新即可在菜单栏看见新菜单。
				<br />
				15 上传文件改成随机数命名文件，避免重名文件覆盖。 <br />
				16 修复日志系统不能记录用户传递参数的bug。 <br />
				17 修复报表word不能导出的bug <br />
				18 数据库链接池由druid 改成 hikari <br />
				19 swagger升级成 springdoc-openapi <br />
				20 修复返回 上一页 函数bug <br />
			</div>
		</el-dialog>
	</div>
</template>

<script>
import Banner from "./components/banner.vue";
// import DataPanel from "./components/data-panel.vue";
import ContentChart from "./components/content-chart.vue";
// import PopularContent from "./components/popular-content.vue";
import CateforiesPercent from "./components/categories-percent.vue";
// import RecentlyVisited from "./components/recently-visited.vue";
import QuickOperation from "./components/quick-operation.vue";
// import TodoList from './components/todoList.vue'
import Announcement from "./components/announcement.vue";
import Carousel from "./components/carousel.vue";
import Docs from "./components/docs.vue";
import Wx from "./components/wx.vue";
// import Footer from "@/components/footer/index.vue";
export default {
	name: "Dashboard", // If you want the include property of keep-alive to take effect, you must name the component
	components: {
		Banner,
		// DataPanel,
		ContentChart,
		// PopularContent,
		CateforiesPercent,
		// RecentlyVisited,
		QuickOperation,
		Announcement,
		Carousel,
		Docs,
		Wx,
		// Footer,
	},
	mounted() {
		this.visible = true;
	},
	data() {
		return {
			visible: false,
			text: ``,
		};
	},
};
</script>

<style lang="less" scoped>
.adminui-topbar + .container {
	padding: 12px 12px 8px 12px;
}
.container {
	padding: 8px 12px;
	display: flex;
}

.left-side {
	flex: 1;
	overflow: hidden;
	.panel {
		background-color: #fff;
	}
}

.right-side {
	width: 680px;
	margin-left: 16px;
}

.panel {
	border-radius: 4px;
	overflow: hidden;
	margin-bottom: 16px;
}
:deep(.panel-border) {
	margin-bottom: 0;
	border-bottom: 1px solid rgb(242, 243, 245);
}
.moduler-wrap {
	border-radius: 4px;
	background-color: #fff;
	:deep(.text) {
		font-size: 14px;
		text-align: center;
		color: #515a6e;
	}

	:deep(.wrapper) {
		margin-bottom: 8px;
		text-align: center;
		cursor: pointer;

		&:last-child {
			.text {
				margin-bottom: 0;
			}
		}
		&:hover {
			.text {
				color: var(--el-color-primary);
			}
		}
	}

	:deep(.icon) {
		display: inline-block;
		margin-bottom: 4px;
		line-height: 32px;
		font-size: 16px;
		text-align: center;
		background-color: rgb(247, 248, 250);
		border-radius: 4px;
	}
}
</style>

<style lang="less" scoped>
// responsive
.mobile {
	.container {
		display: block;
		margin-bottom: 10px;
	}
	.right-side {
		// display: none;
		width: 100%;
		margin-left: 0;
		margin-top: 16px;
	}
}
</style>
