<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="商品名称" prop="name"
                :rules="[
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写商品名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="所属类型" prop="category.id"
                :rules="[
                 ]">
            <el-tree-select
                  value-key="id" 
                  :props="{
                      label: 'name',         // 显示名称
                      children: 'children'    // 子级字段名
                    }"
                  placeholder="请选择所属类型"
                  default-expand-all
                  :data="categoryTreeData"
                  v-model="inputForm.category.id"
                  check-strictly 
                  style="width:100%"
                  :clearable="true" 
                  :accordion="true" />
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="价格" prop="price"
                :rules="[
                 ]">
              <el-input v-model="inputForm.price" placeholder="请填写价格"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
           </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <template #footer>    
        <span class="dialog-footer">
          <el-button @click="visible = false" icon="circle-close">关闭</el-button>
          <el-button  type="primary" v-if="method != 'view'" @click="doSubmit()" icon="circle-check" v-noMoreClick>确定</el-button>
        </span>
    </template>    
  </v-dialog>
</template>

<script>
  import testGoodsService from '@/api/test/shop/testGoodsService'
  import testCategoryService from '@/api/test/shop/testCategoryService'   
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
       categoryTreeData: [],
        inputForm: {
          id: '',
          name: '',
          category: {
            id: ''
          },
          price: '',
          remarks: ''
        }
      }
    },
    components: {
    },
    created () {
      testCategoryService.treeData().then((data) => {
          this.categoryTreeData = data
      })
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建商品`
        } else if (method === 'edit') {
          this.title = '修改商品'
        } else if (method === 'view') {
          this.title = '查看商品'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            testGoodsService.queryById(this.inputForm.id).then((data) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            testGoodsService.save(this.inputForm).then((data) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>

  
