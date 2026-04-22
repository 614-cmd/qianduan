<template>
  <div class="index">
    <div class="header">
      <img class="bg"
           :src="`${publicPath}img/nav-img.png`"
           alt="" />
      <div class="title">
        <p>
          {{$website.name}}<br />
          <small>{{$website.subName}}</small>
        </p>
      </div>
      <navs @change="handleChange"></navs>
    </div>
    <el-scrollbar class="main">
      <router-view />
    </el-scrollbar>
  </div>
</template>
<script>
import navs from './nav/index.vue'
export default {
  name: "index",
  components: {
    navs
  },
  data () {
    return {
      publicPath: import.meta.env.VITE_APP_BASE,
    }
  },
  methods: {
    handleChange (item, index) {
      this.$router.push({ path: item.path })
    }
  }
}
</script>
<style lang="scss">
.index {
  height: 100%;
  .header {
    position: relative;
    height: 220px;
    .bg {
      width: 100%;
      height: 100%;
    }
    .nav {
      margin: 0 20px;
      width: 100%;
      position: absolute;
      bottom: 10px;
      border: none;
      .el-menu-item {
        background-color: rgba(0, 0, 0, 0) !important;
      }
    }
    .title {
      position: absolute;
      top: 60px;
      left: 20px;
      font-size: 48px;
      font-style: oblique;
      color: rgb(0, 186, 255);
      font-weight: bold;
      line-height: 35px;
    }
    .title small {
      font-size: 18px;
      color: #9cb4c2;
    }
  }
  .main {
    width: 100%;
    height: calc(100% - 220px);
  }
}
</style>