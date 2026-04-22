<template>
    <div class="page">
      <el-form  :inline="true" class="query-form m-b-10"  v-if="searchVisible" ref="searchForm" :model="searchForm" @keyup.enter="refreshList()" @submit.prevent>
            <!-- 搜索框-->
         <el-form-item prop="name" label="名称：">
                <el-input  v-model="searchForm.name" placeholder="请输入名称" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()"  icon="search">查询</el-button>
            <el-button type="default" @click="resetSearch()"  icon="refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
    <div class="jp-table">
    <vxe-toolbar ref="dataScreenCategoryToolbar" :refresh="{query: refreshList}" export print custom>
      <template #buttons>
          <el-row>
            <el-button v-if="hasPermission('datav:dataScreenCategory:add')" type="primary"  icon="plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('datav:dataScreenCategory:edit')" type="warning"  icon="edit-filled" @click="edit()"
                 v-show="$refs.dataScreenCategory && $refs.dataScreenCategory.getCheckboxRecords().length == 1" >修改</el-button>
            <el-button v-if="hasPermission('datav:dataScreenCategory:del')" type="danger"    icon="del-filled" @click="del()"
                  v-show="$refs.dataScreenCategory && $refs.dataScreenCategory.getCheckboxRecords().length > 0" >删除</el-button>
          </el-row>
      </template>
    </vxe-toolbar>
    <div class="jp-table-body">
      <vxe-table
        border="inner"
        ref="dataScreenCategory"
        auto-resize
        resizable
        height="auto"
        row-id="id"
        size="small"
        show-header-overflow
        show-overflow
        highlight-hover-row
        :print-config="{}"
        :export-config="{}"
        :tree-config="{}"
        :loading="loading"
        :checkbox-config="{checkStrictly: true}"
        :data="dataList">
      <vxe-column type="checkbox" width="40px"> </vxe-column>
    <vxe-column
        field="name"
        align="left"
        tree-node 
        title="名称">
            <template #default="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('datav:dataScreenCategory:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('datav:dataScreenCategory:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
              <span v-else>{{scope.row.name}}</span>
            </template>
      </vxe-column>
    <vxe-column
        field="remarks"
        title="备注信息">
      </vxe-column>
      <vxe-column title="操作" width="400px" fixed="right" align="center">
        <template  #default="scope">
          <el-button type="primary"  v-if="hasPermission('datav:dataScreenCategory:view')" text  icon="view-filled" @click="view(scope.row.id)">查看</el-button>
          <el-button type="primary"  v-if="hasPermission('datav:dataScreenCategory:edit')" text  icon="edit-filled" @click="edit(scope.row.id)">修改</el-button>
          <el-button type="danger"  v-if="hasPermission('datav:dataScreenCategory:del')" text  icon="del-filled" @click="del(scope.row.id)">删除</el-button>
          <el-button type="primary"  v-if="hasPermission('datav:dataScreenCategory:add')" text  icon="plus" @click="addChild(scope.row.id, scope.row.name)">添加下级大屏分类</el-button>
        </template>
      </vxe-column>
    </vxe-table>
    </div>
    </div>
        <!-- 弹窗, 新增 / 修改 -->
    <DataScreenCategoryForm  ref="dataScreenCategoryForm" @refreshDataList="refreshList"></DataScreenCategoryForm>
  </div>
</template>

<script>
  import DataScreenCategoryForm from './DataScreenCategoryForm'
  import XEUtils from 'xe-utils'
  import dataScreenCategoryService from '@/api/datav/dataScreenCategoryService'
  export default {
    data () {
      return {
        searchVisible: true,
        searchForm: {
          name: ''
        },
        dataList: [],
        loading: false
      }
    },
    components: {
      DataScreenCategoryForm
    },
    mounted () {
      this.$nextTick(() => {
        // 将表格和工具栏进行关联
        const $table = this.$refs.dataScreenCategory
        const $toolbar = this.$refs.dataScreenCategoryToolbar
        $table.connect($toolbar)
      })
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        dataScreenCategoryService.treeData().then((data) => {
          this.dataList = data
          this.handleSearch()
          this.loading = false
        })
      },
      handleSearch () {
        let options = { children: 'children' }
        let searchProps = ['name']
        this.dataList = XEUtils.searchTree(this.dataList, item => searchProps.every(key => XEUtils.toValueString(this.searchForm[key]).trim() === '' || XEUtils.toValueString(item[key]).indexOf(this.searchForm[key]) > -1), options)
        this.$nextTick(() => {
          this.$refs.dataScreenCategory.setAllTreeExpand(true)
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
        // 新增下级
      addChild (id, name) {
        this.$refs.dataScreenCategoryForm.init('addChild', {id: '', parent: {id: id, name: name}})
      },
       // 新增
      add () {
        this.$refs.dataScreenCategoryForm.init('add', {id: '', parent: {id: '', name: ''}})
      },
      // 修改
      edit (id) {
        this.$refs.dataScreenCategoryForm.init('edit', {id: id, parent: {id: '', name: ''}})
      },
      // 查看
      view (id) {
        this.$refs.dataScreenCategoryForm.init('view', {id: id, parent: {id: '', name: ''}})
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.dataScreenCategory.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          dataScreenCategoryService.delete(ids).then((data) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      }
    }
  }
</script>