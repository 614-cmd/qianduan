<template>
<splitpanes class="default-theme">
  <pane  size="20">
        <el-container class="jp-container">
          <el-header class="m-p-13-15">
              <el-input
                placeholder="请输入关键字过滤"
                clearable
                v-model="filterText">
              </el-input>
          </el-header>
          <el-main class="nopadding">
              <el-tree
                class="filter-tree"
                :data="testCarKindTreeData"
                :props="{
                      label: 'name',         // 显示名称
                      children: 'children'    // 子级字段名
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
                ref="testCarKindTree">
                <template #default="{node, data}">
                  <span class="custom-tree-node el-tree-node__label">
                    <span class="label" >
                      {{ node.label}}
                    </span>
                    <span class="do">
                      <el-icon @click.stop="addTreeNode(data)" :size="25"><plus></plus></el-icon>
                      <el-icon @click.stop="editTreeNode(data.id)" :size="25"><edit></edit></el-icon>
                      <el-icon @click.stop="delTreeNode(data.id)" color="red" :size="25"><delete></delete></el-icon>
                    </span>
                  </span>
                </template>
              </el-tree>
          </el-main>
          <el-footer style="height:51px;">
            <el-button type="primary" size="small" icon="DocumentAdd" @click="addTreeNode()"></el-button>
            <el-button type="danger" size="small"  plain icon="delete" @click="delTreeNode()"></el-button>
            <el-button type="default"  size="small"  icon="refresh" @click="refreshTree()"></el-button>
          </el-footer>
        </el-container>
    </pane>  
    <pane  size="80">
    <el-container class="jp-container">
    <el-header   v-if="searchVisible">
      <div class="left-panel">
        <el-form  :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter="refreshList()" @submit.prevent>
            <!-- 搜索框-->
         <el-form-item prop="name" label="品牌：">
                <el-input v-model="searchForm.name" placeholder="请输入品牌" clearable></el-input>
         </el-form-item>
         <el-form-item prop="kind.id" label="车系：">
    		     	<el-tree-select
                      ref="kind.id"
                      value-key="id" 
                      :props="{
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"
                      placeholder="请选择车系"
                      :data="testCarKindTreeData"
                      default-expand-all
                      v-model="searchForm.kind.id"
                      check-strictly 
                      style="width:100%"
                      :clearable="true" 
                      :accordion="true" />
         </el-form-item>
      </el-form>
    </div>
  <div class="right-panel">
        <el-button type="primary" @click="refreshList()" icon="search">查询</el-button>
        <el-button type="default" @click="resetSearch()" icon="refresh-right">重置</el-button>
  </div>
</el-header>
<el-main class="nopadding page2">
      <div class="jp-table">
        <vxe-toolbar ref="testCarToolbar" :refresh="{query: refreshList}" import export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:treetable:testCar:add')" type="primary"  icon="plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:treetable:testCar:edit')" type="warning" icon="edit-filled" @click="edit()" v-show="$refs.testCarTable && $refs.testCarTable.getCheckboxRecords().length === 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:treetable:testCar:del')" type="danger"   icon="del-filled" @click="del()" v-show="$refs.testCarTable && $refs.testCarTable.getCheckboxRecords().length > 0" plain>删除</el-button>
          </template>
          <template #tools>
			<vxe-button
				type="text"
				:title="
					searchVisible ? '收起检索' : '展开检索'
				"
				icon="vxe-icon-search"
				class="tool-btn"
				@click="searchVisible = !searchVisible"
			></vxe-button>
			<vxe-button
				type="text"
				title="下载导入模板"
				v-if="hasPermission('test:treetable:testCar:import')"
				icon="vxe-button--icon vxe-icon-file-excel"
				class="tool-btn m-l-0"
				@click="downloadTpl()"
			>
			</vxe-button>
		</template>
        </vxe-toolbar>
        <div class="jp-table-body">
        <vxe-table
            border="inner"
            auto-resize
            resizable
            height="auto"
            :loading="loading"
            size="small"
            ref="testCarTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
	:import-config="{
		importMethod: importMethod,
		types: ['csv', 'xls', 'xlsx'],
		remote: true,
	}"
	:export-config="{
		remote: true,
		filename: `车辆数据${moment(new Date()).format(
			'YYYY-MM-DD'
		)}`,
		sheetName: '车辆数据',
		exportMethod: exportMethod,
		types: ['xlsx'],
		modes: ['current', 'selected', 'all'],
	}"

            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
    <vxe-column
        field="name"
        sortable
        title="品牌">
            <template  #default="{ row }">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:treetable:testCar:edit')" @click="edit(row.id)">{{row.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:treetable:testCar:view')"  @click="view(row.id)">{{row.name}}</el-link>
              <span v-else>{{row.name}}</span>
            </template>
      </vxe-column>
      <vxe-column
        field="kind.name"
        sortable
        title="车系">
            <template #default="{ row }">
                {{row.kind && row.kind.name}}
            </template>
      </vxe-column>
    <vxe-column
        field="remarks"
        sortable
        title="简介">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template   #default="{ row }">
          <el-button v-if="hasPermission('test:treetable:testCar:view')" type="primary" text icon="view-filled" @click="view(row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:treetable:testCar:edit')" type="primary" text icon="edit-filled" @click="edit(row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:treetable:testCar:del')"  type="danger"  text icon="del-filled"  @click="del(row.id)">删除</el-button>
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
    </el-main>
	</el-container>
  </pane>  
 </splitpanes> 
        <!-- 弹窗, 新增 / 修改 -->
    <TestCarForm  ref="testCarForm" @refreshDataList="refreshList"></TestCarForm>
    <TestCarKindForm  ref="testCarKindForm" @refreshTree="refreshTree"></TestCarKindForm>
</template>

<script>
  import { Splitpanes, Pane } from 'splitpanes'
  import 'splitpanes/dist/splitpanes.css'
  import TestCarForm from './TestCarForm'
  import TestCarKindForm from './TestCarKindForm'
  import testCarService from '@/api/test/treetable/testCarService'
  import testCarKindService from '@/api/test/treetable/testCarKindService'
  export default {
    data () {
      return {
        searchVisible: true,
        searchForm: {
          name: '',
          kind: {
            id: ''
          }
        },
        filterText: '',
        testCarKindTreeData: [],
        selectTestCarKindName: '',
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: [{ column: "a.create_time", asc: false }],
        },
        loading: false
      }
    },
    components: {
      Splitpanes, 
      Pane,
      TestCarForm,
      TestCarKindForm
    },
    mounted () {
      this.$nextTick(() => {
        // 将表格和工具栏进行关联
        const $table = this.$refs.testCarTable
        const $toolbar = this.$refs.testCarToolbar
        $table.connect($toolbar)
      })
    }, 
    activated () {
      this.refreshTree()
      this.refreshList()
    },
    computed: {
    },
    watch: {
      filterText (val) {
        this.$refs.testCarKindTree.filter(val)
      }
    },
    methods: {
      filterNode (value, data) {
        if (!value) return true
        return data.name.indexOf(value) !== -1
      },
      refreshTree () {
        testCarKindService.treeData().then((data) => {
          this.testCarKindTreeData = data
        })
      },
      handleNodeClick (data) {
        this.searchForm.kind.id = data.id
        this.refreshList()
      },
      // 新增根节点
      addTreeNode (data) {
        var sort = 30
        if (data) {
           if (data.children && data.children.length > 0) {
            sort = data.children[data.children.length - 1].sort + 30
           }
        } else if (this.testCarKindTreeData && this.testCarKindTreeData.length > 0) {
           sort = this.testCarKindTreeData[this.testCarKindTreeData.length - 1].sort + 30
        }
        var newTestCarKindTreeData = {
           id: '',
           parent: {
             id: data ? data.id : "0",
           },
           sort: sort
          }
        if (data) {  
            this.$refs.testCarKindForm.init('addChild', newTestCarKindTreeData)
        } else {
            this.$refs.testCarKindForm.init('add', newTestCarKindTreeData)
        }
      },
      // 修改
      editTreeNode (id) {
        this.$refs.testCarKindForm.init('edit', {id: id, parent: {id: '', name: ''}})
      },
      delTreeNode (ids) {
        if(!ids){
          var CheckedNodes = this.$refs.testCarKindTree.getCheckedNodes()
          if(CheckedNodes.length == 0){
            this.$message.warning("请选择需要删除的项")
            return false;
          }
          ids = CheckedNodes.map(item => item.id).join(',')
        }
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          testCarKindService.delete(ids).then((data) => {
            this.$message.success(data)
            this.loading = false
            this.refreshTree()
            this.refreshList()
          })
        })
      },
      //树拖拽
      nodeDrop(draggingNode, dropNode, dropType){
        this.loading = true
        testCarKindService.drag({draggingNode: draggingNode.data, dropNode: dropNode.data, dropType: dropType}).then((data)=>{
            this.$message(data)
            this.loading = false
        })
      },
      // 获取数据列表
      refreshList () {
        this.loading = true
        testCarService.list({
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
	  sortChangeHandle(obj) {
        this.tablePage.orders = [];
        if (obj.order != null) {
          this.tablePage.orders = [{ column: obj.column.sortBy || this.$utils.toLine(obj.property),	asc: obj.order === "asc" }];
        } else {
          this.tablePage.orders = [{ column: "a.create_time", asc: false }];
        }
        this.refreshList();
      },
      // 新增
      add () {
        this.$refs.testCarForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testCarTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testCarForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testCarForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testCarTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          testCarService.delete(ids).then((data) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      // 下载模板
      downloadTpl() {
			this.loading = true
			testCarService
				.exportTemplate()
				.then((data) => {
					// 将二进制流文件写入excel表，以下为重要步骤
					this.$utils.downloadExcel(data, "车辆导入模板");
					this.loading = false
				})
				.catch(function (err) {
				    this.loading = false
					if (err.response) {
						console.log(err.response);
					}
				});
		},
		// 自定义服务端导入
		importMethod({ file }) {
			// 处理表单
			const formBody = new FormData();
			formBody.append("file", file);
			this.loading = true
			testCarService.importExcel(formBody).then((result) => {
				this.$message.success({
					dangerouslyUseHTMLString: true,
					message: result,
				});
				this.refreshList();
			});
		},
		// 自定义服务端导出
		exportMethod({ options }) {
			// 传给服务端的参数
			const params = {
				current: this.tablePage.currentPage,
				size: this.tablePage.pageSize,
				orders: this.tablePage.orders,
				...this.searchForm,
				filename: options.filename,
				sheetName: options.sheetName,
				isHeader: options.isHeader,
				original: options.original,
				mode: options.mode,
				selectIds:
					options.mode === "selected"
						? options.data.map((item) => item.id)
						: [],
				exportFields: options.columns.map(
					(column) => column.property && column.property.split(".")[0]
				),
			};
			this.loading = true;
			return testCarService
				.exportExcel(params)
				.then((data) => {
					// 将二进制流文件写入excel表，以下为重要步骤
					this.$utils.downloadExcel(data, options.filename);
					this.loading = false;
				})
				.catch(function (err) {
					if (err.response) {
						console.log(err.response);
					}
				});
		},
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.filterText = ''
        this.$refs.testCarKindTree.setCurrentKey(null)
        this.refreshList()
      }
    }
  }
</script>

