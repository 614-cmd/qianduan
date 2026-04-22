<template>
  	<splitpanes class="default-theme">
		<pane size="15">
			<el-container class="jp-container">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="请输入关键字过滤"
						clearable
						v-model="filterText"
					>
					</el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
            v-loading="treeLoading"
						class="filter-tree"
						:data="dataScreenCategoryTreeData"
						:props="{
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						default-expand-all
						highlight-current
						node-key="id"
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						@node-click="handleNodeClick"
						draggable
						check-strictly
						show-checkbox
						@node-drop="nodeDrop"
						ref="screenKindTree"
					>
					</el-tree>
				</el-main>
				<el-footer style="height: 51px">
					<el-button
						type="default"
						size="small"
						icon="refresh"
						@click="refreshTree()"
					></el-button>
				</el-footer>
			</el-container>
		</pane>
    <pane size="85">
			<el-container class="jp-container">
				<el-header>
					<div class="left-panel">
            <el-button v-if="hasPermission('datav:dataScreen:add')" type="default"  icon="plus" @click="add()">新建大屏</el-button>
					</div>
					<div class="right-panel">
            <el-form  :inline="true"  v-if="searchVisible" ref="searchForm" :model="searchForm" @keyup.enter="refreshList()" @submit.prevent>
            <!-- 搜索框-->
            <el-form-item prop="categoryId" label="类型：">
                <el-tree-select      
                      ref="category"
                      value-key="id" 
                      :props="{
                        label: 'name',         // 显示名称
                        children: 'children'    // 子级字段名
                      }"
                      :data="dataScreenCategoryTreeData"
                      v-model="searchForm.categoryId"
                      check-strictly 
                      default-expand-all
                      style="width:100%"
                      :clearable="true" 
                      :accordion="true" />
            </el-form-item>
            <el-form-item prop="name" label="大屏名称：">
                <el-input  v-model="searchForm.name" placeholder="请输入大屏名称" clearable></el-input>
            </el-form-item>
          </el-form>
            <el-button type="primary" @click="refreshList()"  icon="search">查询</el-button>
            <el-button type="default" @click="resetSearch()"  icon="refresh-right">重置</el-button>
					</div>
				</el-header>
				<el-main class="nopadding page2">
					<div class="jp-table">

      <div class="bg-white top" style="padding:0px">
      `<div class="el-scrollbar__wrap" style="padding:10px;height:calc(100% - 70px)">
        <div class="el-scrollbar__view">
        <el-row v-loading="loading">
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4"  v-for="(datascreen, index) in dataList" :key="index">
            <el-card style="margin: 8px" :body-style="{ padding: '0px' }" shadow="always">
              <img :src="datascreen.screenShot" class="image">
                <h3 class="jp-card-label">
                  <el-row>
                    <el-col :span="16">
                        <div class="left">{{datascreen.name}}</div>
                    </el-col>
                    <el-col :span="8">
                        <el-link :underline="false" class="el-icon-edit-outline right" @click="design(datascreen.id)"><el-icon style=" margin-right: 5px;"><edit-filled></edit-filled></el-icon> 设计</el-link>
                    </el-col>
                    </el-row>
                </h3>
                
              <ul class="jp-card-actions">
                  <li style="width: 25%">
                    <el-link :underline="false" @click="edit(datascreen.id)"><el-icon><edit></edit></el-icon>   编辑</el-link>
                  </li>
                  <li style="width: 25%">
                    <el-link :underline="false" @click="copy(datascreen.id)"><el-icon><document-copy></document-copy></el-icon> 复制</el-link>
                  </li>
                  <li style="width: 25%">
                    <el-link :underline="false"  @click="del(datascreen.id)"><el-icon><delete></delete></el-icon> 删除</el-link>
                  </li>
                  <li style="width: 25%">
                    <el-link :underline="false"  @click="view(datascreen.id)"><el-icon><View /></el-icon> 预览</el-link>
                  </li>
              </ul>
            </el-card>
          </el-col>
        </el-row>
        </div>`
      </div>
        <vxe-pager
          background
          size="small"
          :current-page="tablePage.currentPage"
          :page-size="tablePage.pageSize"
          :total="tablePage.total"
          :page-sizes="[10, 20, 100, 1000, {label: '全量数据', value: 1000000}]"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
          @page-change="currentChangeHandle">
        </vxe-pager>
          <!-- 弹窗, 新增 / 修改 -->
      <DataScreenForm  ref="dataScreenForm" @refreshDataList="refreshList"></DataScreenForm>
  </div>
  </div>
        </el-main>
      </el-container>
    </pane>
    </splitpanes>
</template>

<script>
  import { Splitpanes, Pane } from "splitpanes";
  import "splitpanes/dist/splitpanes.css";
  import DataScreenForm from './DataScreenForm'
  import dataScreenService from '@/api/datav/dataScreenService.js'
  import dataScreenCategoryService from '@/api/datav/dataScreenCategoryService'
  export default {
    data () {
      return {
        searchVisible: true,
        filterText: '',
        searchForm: {
          categoryId: '',
          name: ''
        },
        dataList: [],
        dataScreenCategoryTreeData: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 12,
          orders: [{ column: "create_time", asc: false }],
        },
        loading: false,
        treeLoading: false
      }
    },
    components: {
      Splitpanes,
		  Pane,
      DataScreenForm
    },
    activated () {
      this.refreshTree()
      this.refreshList()
    },
    watch: {
      filterText(val) {
        this.$refs.screenKindTree.filter(val);
      },
    },
    methods: {
      filterNode(value, data) {
        if (!value) return true;
        return data.name.indexOf(value) !== -1;
      },
      refreshTree() {
        this.treeLoading = true
	      dataScreenCategoryService.treeData().then((data) => {
          this.dataScreenCategoryTreeData = data
          this.treeLoading = false
        })
      },
      // 获取数据列表
      refreshList () {
        this.loading = true
        dataScreenService.list({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          ...this.searchForm
        }).then((data) => {
          this.dataList = data.records
          this.tablePage.total = data.total
          this.loading = false
        })
      },
      // 当前页
      currentChangeHandle ({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.refreshList()
      },
      // 排序
      sortChangeHandle (column) {
        this.tablePage.orders = []
        if (column.order != null) {
          this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        }
        this.refreshList()
      },
      // 新增
      add () {
        this.$refs.dataScreenForm.init('add', '')
      },
      // 修改
      edit (id) {
        this.$refs.dataScreenForm.init('edit', id)
      },
      // 设计
      design (id) {
        window.open('datav.html#/build/' + id, '_blank')
      },
      handleNodeClick(data) {
        this.searchForm.categoryId = data.id;
        this.refreshList();
      },
      // 查看
      view (id) {
        window.open('datav.html#/view/' + id, '_blank')
      },
      // 删除
      del (id) {
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          dataScreenService.delete(id).then((data) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      // 复制
      copy (id) {
        this.$confirm(`确定复制所选大屏吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          dataScreenService.copy(id).then((data) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>
<style lang="scss" scoped>
.jp-card {
    height: 246px;
    font-size: 14px;
    line-height: 1.5;
    color: rgba(0,0,0,.65);
}
.page2 .jp-table {
    height: auto;
}
.add-border {
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 100%;
    width: 100%;
    border: 1px dashed #dcdfe6;
}
.add-border a i {
    display: block;
    text-align: center;
    font-size: 50px;
    margin-bottom: 10px;
    color: #65a5f9;
}
.add-border a p {
    color: #65a5f9;
}
.jp-card-actions {
    border-top: 1px solid #e8e8e8;
    background: #f5f8fa;
    zoom: 1;
    margin: 0;
    padding: 0;
    display: block;
    list-style-type: disc;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    list-style: none;
    .el-icon {
        margin-right: 5px;
    }
}

.jp-card-actions:after {
    clear: both;
}

.jp-card-actions:after, .jp-card-actions:before {
    content: "";
    display: table;
}
.jp-card-actions>li {
    float: left;
    text-align: center;
    margin: 12px 0;
    color: rgba(0,0,0,.45);
}
.jp-card-actions>li:not(:last-child) {
    border-right: 1px solid #e8e8e8;
}

.right-panel{
  .el-form {
    .el-form-item {
      margin-bottom: 9px;
      margin-top: 9px;
    }
  }
}
.image{
  width: 100%;
  height: 156px;
  overflow: hidden;
}
.bottom{
    background-color: #f5f7fa;
    height: 50px;
    line-height: 50px;
    padding: 0 15px;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    font-size: 12px;
}
.jp-card-label{

    font-size: 14px; 
    padding-left:10px;
    padding-right:10px;
    height: 36px;
    margin: 0;
    .left{
      line-height: 36px;
      font-weight: 400;
      overflow: hidden;
      text-overflow:ellipsis;
      color: rgba(0, 0, 0, .55) !important;
      white-space: nowrap;
    }
    .right {
      line-height: 38px;
      justify-content: center;
      text-decoration: none;
      float: right;
    }
}

</style>