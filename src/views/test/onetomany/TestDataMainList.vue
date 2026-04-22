<template>
    <div class="page">
      <el-form :inline="true" v-if="searchVisible" class="query-form m-b-10" ref="searchForm" :model="searchForm" @keyup.enter="refreshList()" @submit.prevent>
            <!-- 搜索框-->
         <el-form-item prop="user.id" label="用户：">
            <user-select :limit='1' placeholder="请选择用户" v-model="searchForm.user.id"></user-select>
         </el-form-item>
         <el-form-item prop="office.id" label="所属部门：">
            <office-select   v-model="searchForm.office.id" type="2"/>
         </el-form-item>
         <el-form-item prop="area" label="所属区域：">
                <el-input v-model="searchForm.area" placeholder="请输入所属区域" clearable></el-input>
         </el-form-item>
         <el-form-item prop="name" label="名称：">
                <el-input v-model="searchForm.name" placeholder="请输入名称" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" icon="search">查询</el-button>
            <el-button type="default" @click="resetSearch()" icon="refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

      <div class="jp-table">
        <vxe-toolbar ref="testDataMainToolbar" :refresh="{query: refreshList}" import export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:onetomany:testDataMain:add')" type="primary"  icon="plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:onetomany:testDataMain:edit')" type="warning" icon="edit-filled" @click="edit()" v-show="$refs.testDataMainTable && $refs.testDataMainTable.getCheckboxRecords().length === 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:onetomany:testDataMain:del')" type="danger"   icon="del-filled" @click="del()" v-show="$refs.testDataMainTable && $refs.testDataMainTable.getCheckboxRecords().length > 0" plain>删除</el-button>
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
				v-if="hasPermission('test:onetomany:testDataMain:import')"
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
            ref="testDataMainTable"
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
		filename: `票务代理数据${moment(new Date()).format(
			'YYYY-MM-DD'
		)}`,
		sheetName: '票务代理数据',
		exportMethod: exportMethod,
		types: ['xlsx'],
		modes: ['current', 'selected', 'all'],
	}"

            @sort-change="sortChangeHandle"
            :expand-config="{accordion: true, lazy: true,loadMethod:detail}"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
            <vxe-column type="expand" width="80" >
                <template #content="{ row }">
                    <el-tabs class="child-tab">
                        <el-tab-pane label="火车票">
                            <el-table :data="row.testDataChild1DTOList" class="child-table" border>
                                    <el-table-column
                                            prop="startarea"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="出发地">
                                    </el-table-column>
                                    <el-table-column
                                            prop="endarea"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="目的地">
                                    </el-table-column>
                                    <el-table-column
                                            prop="starttime"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="出发时间">
                                    </el-table-column>
                                    <el-table-column
                                            prop="endtime"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="到达时间">
                                    </el-table-column>
                                    <el-table-column
                                            prop="price"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="代理价格">
                                    </el-table-column>
                                    <el-table-column
                                            prop="remarks"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="备注信息">
                                    </el-table-column>
                            </el-table>
                        </el-tab-pane>
                        <el-tab-pane label="飞机票">
                            <el-table :data="row.testDataChild2DTOList" class="child-table" border>
                                    <el-table-column
                                            prop="startarea"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="出发地">
                                    </el-table-column>
                                    <el-table-column
                                            prop="endarea"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="目的地">
                                    </el-table-column>
                                    <el-table-column
                                            prop="starttime"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="出发时间">
                                    </el-table-column>
                                    <el-table-column
                                            prop="endtime"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="到达时间">
                                    </el-table-column>
                                    <el-table-column
                                            prop="price"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="代理价格">
                                    </el-table-column>
                                    <el-table-column
                                            prop="remarks"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="备注信息">
                                    </el-table-column>
                            </el-table>
                        </el-tab-pane>
                        <el-tab-pane label="汽车票">
                            <el-table :data="row.testDataChild3DTOList" class="child-table" border>
                                    <el-table-column
                                            prop="startarea"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="出发地">
                                    </el-table-column>
                                    <el-table-column
                                            prop="endarea"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="目的地">
                                    </el-table-column>
                                    <el-table-column
                                            prop="price"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="代理价格">
                                    </el-table-column>
                                    <el-table-column
                                            prop="starttime"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="出发时间">
                                    </el-table-column>
                                    <el-table-column
                                            prop="endtime"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="到达时间">
                                    </el-table-column>
                                    <el-table-column
                                            prop="remarks"
                                            show-overflow-tooltip
                                            align="center"
											header-align="center"
                                            label="备注信息">
                                    </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>
                </template>
            </vxe-column>
      <vxe-column
        field="user.name"
        sortable
        title="用户">
            <template #default="{ row }">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:onetomany:testDataMain:edit')" @click="edit(row.id)">{{row.user && row.user.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:onetomany:testDataMain:view')"  @click="view(row.id)">{{row.user &&  row.user.name}}</el-link>
              <span v-else>{{row.user && row.user.name}}</span>
            </template>
      </vxe-column>
      <vxe-column
        field="office.name"
        sortable
        title="所属部门">
            <template #default="{ row }">
                {{row.office && row.office.name}}
            </template>
      </vxe-column>
    <vxe-column
        field="area"
        sortable
        title="所属区域">
      </vxe-column>
    <vxe-column
        field="name"
        sortable
        title="名称">
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
        field="file"
        sortable
        title="身份证照片">
      </vxe-column>
    <vxe-column
        field="inDate"
        sortable
        title="加入日期">
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
          <el-button v-if="hasPermission('test:onetomany:testDataMain:view')" type="primary" text icon="view-filled" @click="view(row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:onetomany:testDataMain:edit')" type="primary" text icon="edit-filled" @click="edit(row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:onetomany:testDataMain:del')"  type="danger"  text icon="del-filled" @click="del(row.id)">删除</el-button>
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
    <TestDataMainForm  ref="testDataMainForm" @refreshDataList="refreshList"></TestDataMainForm>
  </div>
</template>

<script>
  import TestDataMainForm from './TestDataMainForm'
  import testDataMainService from '@/api/test/onetomany/testDataMainService'
  import UserSelect from '@/components/userSelect'
  import OfficeSelect from '@/components/officeSelect'
  export default {
    data () {
      return {
        searchVisible: true,
        searchForm: {
          user: {
            id: ''
          },
          office: {
            id: ''
          },
          area: '',
          name: ''
        },
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
      UserSelect,
      OfficeSelect,
      TestDataMainForm
    },
    created () {
    },    
    mounted () {
      this.$nextTick(() => {
        // 将表格和工具栏进行关联
        const $table = this.$refs.testDataMainTable
        const $toolbar = this.$refs.testDataMainToolbar
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
        testDataMainService.list({
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
        this.$refs.testDataMainForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testDataMainTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testDataMainForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testDataMainForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testDataMainTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          testDataMainService.delete(ids).then((data) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
        // 查看详情
      detail ({row}) {
        return new Promise(resolve => {
          testDataMainService.queryById(row.id).then((data) => {
            this.dataList.forEach((item) => {
              if (item.id === row.id) {
                item.testDataChild1DTOList = data.testDataChild1DTOList
                item.testDataChild2DTOList = data.testDataChild2DTOList
                item.testDataChild3DTOList = data.testDataChild3DTOList
              }
            })
            resolve()
          })
        })
      },
            // 下载模板
      downloadTpl() {
			this.loading = true
			testDataMainService
				.exportTemplate()
				.then((data) => {
					// 将二进制流文件写入excel表，以下为重要步骤
					this.$utils.downloadExcel(data, "票务代理导入模板");
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
			testDataMainService.importExcel(formBody).then((result) => {
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
			return testDataMainService
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
        this.refreshList()
      }
    }
  }
</script>

