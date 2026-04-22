<template>
<div>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form  :model="inputForm"  ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="分类" prop="category.id"
                :rules="[
                 ]">
                   <el-tree-select      
                    ref="category"
                    value-key="id" 
                    :props="{
                      label: 'name',         // 显示名称
                      children: 'children'    // 子级字段名
                    }"
                    :data="dataScreenCategoryTreeData"
                    v-model="inputForm.category.id"
                    check-strictly
                    default-expand-all 
                    style="width:100%"
                    :clearable="true" 
                    :accordion="true" />
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="名称" prop="name" :rules="[{required: true, message:'名称不能为空', trigger:'blur'}]">
              <el-input v-model="inputForm.name" placeholder="请填写大屏名称" ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24" v-if="method === 'add'">
           <el-form-item label="尺寸">
             <el-row>
               <el-col :span="12">
                 <el-form-item label="宽" prop="width" :rules="[{required: true, message:'宽不能为空', trigger:'blur'}]">
                    <el-input-number v-model="inputForm.width" style="width:200px"  controls-position="right"></el-input-number>
                 </el-form-item>
               </el-col>
           <!-- </el-form-item>
            <el-form-item label="大屏名称" prop="name"> -->
                <el-col :span="12">
                  <el-form-item label="高" prop="height" :rules="[{required: true, message:'高不能为空', trigger:'blur'}]">
                    <el-input-number v-model="inputForm.height" style="width:200px"  controls-position="right" ></el-input-number>
                  </el-form-item>
                </el-col>
             </el-row>
            </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="启用" prop="status"
                :rules="[
                 ]">
                <el-select v-model="inputForm.status" placeholder="请选择"  style="width: 100%;">
                          <el-option
                            v-for="item in $dictUtils.getDictList('yes_no')"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                      </el-select>
           </el-form-item>
        </el-col>
        <el-col :span="24">
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
        <el-button  @click="visible = false" icon="circle-close">关闭</el-button>
        <el-button  type="primary" v-if="method != 'view'" @click="doSubmit()" icon="circle-check" v-noMoreClick>确定</el-button>
      </span>
    </template>
  </v-dialog>
</div>
</template>

<script>
  import dataScreenService from '@/api/datav/dataScreenService'
  import dataScreenCategoryService from '@/api/datav/dataScreenCategoryService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        dataScreenCategoryTreeData: [],
        inputForm: {
          id: '',
          category: {
            id: ''
          },
          width: 1920,
          height: 1080,
          component: '[]',
          detail: '',
          status: '',
          name: '',
          screenShot: '/img/bg/bg1.png',
          remarks: ''
        }
      }
    },
    mounted () {
      dataScreenCategoryService.treeData(this.inputForm.id).then((data) => {
        this.dataScreenCategoryTreeData = data
      })
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建大屏`
          this.inputForm = {
            id: '',
            category: {
              id: ''
            },
            width: 1920,
            height: 1080,
            component: '[]',
            detail: '',
            status: '',
            name: '',
            screenShot: '/img/bg/bg1.png',
            remarks: ''
          }
        } else if (method === 'edit') {
          this.title = '修改大屏'
        } else if (method === 'view') {
          this.title = '查看大屏'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            dataScreenService.queryById(this.inputForm.id).then((data) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        if (this.method === 'add') {
          this.inputForm.detail = `{"name":"${this.inputForm.name}","width":${this.inputForm.width},"height":${this.inputForm.height},"scale":1,"backgroundImage":"/img/bg/bg1.png","url":"","mark":{},"gradeShow":false,"gradeLen":30}`
        }
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            dataScreenService.save(this.inputForm).then((data) => {
              this.loading = false
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
            })
          }
        })
      }
    }
  }
</script>