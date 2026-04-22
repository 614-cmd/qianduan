<template>
    <div class="page">
      <el-form :inline="true" class="query-form m-b-10" ref="searchForm" :model="searchForm" @keyup.enter="refreshList()" @submit.prevent>
            <!-- 搜索框-->
          <el-form-item>
            <el-button type="primary" @click="refreshList()" icon="search">查询</el-button>
            <el-button @click="resetSearch()" icon="refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

      <div class="jp-table">
        <vxe-toolbar ref="testActivitiLeaveToolbar" :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:activiti:testActivitiLeave:add')" type="primary"  icon="plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:activiti:testActivitiLeave:edit')" type="warning" icon="edit" @click="edit()" :disabled="$refs.testActivitiLeaveTable && $refs.testActivitiLeaveTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:activiti:testActivitiLeave:del')" type="danger"   icon="delete" @click="del()" :disabled="$refs.testActivitiLeaveTable && $refs.testActivitiLeaveTable.getCheckboxRecords().length === 0" plain>删除</el-button>
          </template>
        </vxe-toolbar>
        <div class="jp-table-body">
        <vxe-table
            border
            auto-resize
            resizable
            height="auto"
            :loading="loading"
            size="small"
            ref="testActivitiLeaveTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
    <vxe-column
        field="leaveType"
        sortable
        title="请假类型">
        <template #default="{ row }">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:activiti:testActivitiLeave:edit')" @click="edit(row.id)"> {{ $dictUtils.getDictLabel("oa_leave_type", row.leaveType, '-') }}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:activiti:testActivitiLeave:view')"  @click="view(row.id)"> {{ $dictUtils.getDictLabel("oa_leave_type", row.leaveType, '-') }}</el-link>
              <span v-else>{{ $dictUtils.getDictLabel("oa_leave_type", scope.row.leaveType, '-') }}</span>
        </template>
      </vxe-column>
    <vxe-column
        field="startTime"
        sortable
        title="请假开始时间">
      </vxe-column>
    <vxe-column
        field="endTime"
        sortable
        title="请假结束时间">
      </vxe-column>
    <vxe-column
        field="reason"
        sortable
        title="请假事由">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template   #default="{ row }">
          <el-button v-if="hasPermission('test:activiti:testActivitiLeave:view')" type="primary" text icon="view" size="small" @click="view(row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:activiti:testActivitiLeave:edit')" type="primary" text icon="edit" size="small" @click="edit(row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:activiti:testActivitiLeave:del')"  type="danger"  text icon="delete" size="small" @click="del(row.id)">删除</el-button>
        </template>
      </vxe-column>
    </vxe-table>
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
    </div>
    </div>
        <!-- 弹窗, 新增 / 修改 -->
    <TestActivitiLeaveForm  ref="testActivitiLeaveForm" @refreshDataList="refreshList"></TestActivitiLeaveForm>
  </div>
</template>

<script>
  import TestActivitiLeaveForm from './TestActivitiLeaveForm'
  import testActivitiLeaveService from '@/api/test/activiti/testActivitiLeaveService'
  export default {
    data () {
      return {
        searchForm: {
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false
      }
    },
    components: {
      TestActivitiLeaveForm
    },
    created () {
    },    
    mounted () {
      this.$nextTick(() => {
        // 将表格和工具栏进行关联
        const $table = this.$refs.testActivitiLeaveTable
        const $toolbar = this.$refs.testActivitiLeaveToolbar
        $table.connect($toolbar)
      })
    }, 
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        testActivitiLeaveService.list({
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
        this.$refs.testActivitiLeaveForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testActivitiLeaveTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testActivitiLeaveForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testActivitiLeaveForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testActivitiLeaveTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          testActivitiLeaveService.delete(ids).then((data) => {
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

