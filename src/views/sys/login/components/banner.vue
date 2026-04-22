<template>
	<div class="banner">
		<div class="banner-inner">
			<el-carousel class="carousel" :interval="5000" arrow="always">
				<el-carousel-item
					v-for="item in carouselItem"
					:key="item.slogan"
				>
					<div :key="item.slogan" class="carousel-item">
						<img class="carousel-image" :src="item.image" />
						<div class="carousel-title">{{ item.slogan }}</div>
						<div class="carousel-sub-title">
							<div v-html="item.subSlogan"></div>
						</div>
					</div>
				</el-carousel-item>
			</el-carousel>
		</div>
	</div>
</template>

<script>
import image1 from "@/assets/img/login/login3.png";
import image2 from "@/assets/img/login/login2.png";
import image3 from "@/assets/img/login/login4.png";

export default {
	computed: {
		carouselItem() {
			return [
				{
					slogan: "SpringBoot低代码快速开发",
					subSlogan:
						"PC/移动代码生成，大屏/图表/报表/首页设计器，数据权限，OSS文件存储",
					image: image3,
				},
				{
					slogan: "单体架构",
					subSlogan: "可视化工作流/动态表单/多租户/多语言",
					image: image1,
				},
				{
					slogan: "Spring Boot& Spring Security & Mybatis-Plus",
					subSlogan: "Vue3 & Element Plus & Vite 构建 ",
					image: image2,
				},
			];
		},
	},
};
</script>

<style lang="less">
.banner {
	display: flex;
	align-items: center;
	justify-content: center;

	&-inner {
		flex: 1;
		height: 100%;
	}
}

.carousel {
	height: 100% !important;

	.el-carousel__container {
		position: relative;
		height: 100% !important;
	}
	&-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		height: 100%;
	}

	&-title {
		color: #f7f8fa;
		font-weight: 500;
		font-size: 24px;
		line-height: 28px;
	}

	&-sub-title {
		margin-top: 8px;
		color: #fff;
		font-size: 18px;
		line-height: 22px;
	}

	&-image {
		width: 60%;
		margin-top: 30px;
	}
}
</style>
