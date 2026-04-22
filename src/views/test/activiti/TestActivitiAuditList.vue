<template>
    <div class="page">
      <el-form :inline="true" class="query-form m-b-10" ref="searchForm" :model="searchForm" @keyup.enter="refreshList()" @submit.prevent>
            <!-- 搜索框-->
         <el-form-item prop="user.id">
            <user-select :limit='1' placeholder="请选择员工" v-model="searchForm.user.id"></user-select>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" icon="search">查询</el-button>
            <el-button @click="resetSearch()" icon="refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

      <div class="jp-table">
        <vxe-toolbar ref="testActivitiAuditToolbar" :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:activiti:testActivitiAudit:add')" type="primary"  icon="plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:activiti:testActivitiAudit:edit')" type="warning" icon="edit" @click="edit()" :disabled="$refs.testActivitiAuditTable && $refs.testActivitiAuditTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:activiti:testActivitiAudit:del')" type="danger"   icon="delete" @click="del()" :disabled="$refs.testActivitiAuditTable && $refs.testActivitiAuditTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="testActivitiAuditTable"
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
        field="user.name"
        sortable
        title="员工">
            <template #default="{ row }">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:activiti:testActivitiAudit:edit')" @click="edit(row.id)">{{row.user && row.user.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:activiti:testActivitiAudit:view')"  @click="view(row.id)">{{row.user &&  row.user.name}}</el-link>
              <span v-else>{{row.user && row.user.name}}</span>
            </template>
      </vxe-column>
      <vxe-column
        field="office.name"
        sortable
        title="部门">
            <template #default="{ row }">
                {{row.office && row.office.name}}
            </template>
      </vxe-column>
    <vxe-column
        field="post"
        sortable
        title="岗位">
      </vxe-column>
    <vxe-column
        field="sex"
        sortable
        title="性别">
        <template #default="{ row }">
             {{ $dictUtils.getDictLabel("sex", row.sex, '-') }}
        </template>
      </vxe-column>
    <vxe-column
        field="content"
        sortable
        title="调整原因">
      </vxe-column>
    <vxe-column
        field="oldSalray"
        sortable
        title="调整前薪水">
      </vxe-column>
    <vxe-column
        field="newSalary"
        sortable
        title="调整后薪水">
      </vxe-column>
    <vxe-column
        field="exeDate"
        sortable
        title="生效时间">
      </vxe-column>
    <vxe-column
        field="remarks"
        sortable
        title="备注信息">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template   #default="{ row }">
          <el-button v-if="hasPermission('test:activiti:testActivitiAudit:view')" type="primary" text icon="view" size="small" @click="view(row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:activiti:testActivitiAudit:edit')" type="primary" text icon="edit" size="small" @click="edit(row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:activiti:testActivitiAudit:del')"  type="danger"  text icon="delete" size="small" @click="del(row.id)">删除</el-button>
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
    <TestActivitiAuditForm  ref="testActivitiAuditForm" @refreshDataList="refreshList"></TestActivitiAuditForm>
  </div>
</template>

<script>
  import TestActivitiAuditForm from './TestActivitiAuditForm'
  import testActivitiAuditService from '@/api/test/activiti/testActivitiAuditService'
  import UserSelect from '@/components/userSelect'
  export default {
    data () {
      return {
        searchForm: {
          user: {
            id: ''
          }
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
      UserSelect,
      TestActivitiAuditForm
    },
    created () {
    },    
    mounted () {
      this.$nextTick(() => {
        // 将表格和工具栏进行关联
        const $table = this.$refs.testActivitiAuditTable
        const $toolbar = this.$refs.testActivitiAuditToolbar
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
        testActivitiAuditService.list({
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
        this.$refs.testActivitiAuditForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testActivitiAuditTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testActivitiAuditForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testActivitiAuditForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testActivitiAuditTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          testActivitiAuditService.delete(ids).then((data) => {
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

