<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧数据后操作" :image-size="100"></el-empty>
	</template>
	<template v-else>
      <el-descriptions
        class="detail"
        :title="inputForm.name"
        :column="1"
        :size="size"
        border
      >
       <template #extra>
        <el-button icon="editPen" v-if="hasPermission('test:shop:testCategory:edit')" @click="edit" text></el-button>
      </template>
      <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              类型名
            </div>
          </template>
          {{inputForm.name}}
        </el-descriptions-item>
      <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              备注信息
            </div>
          </template>
          {{inputForm.remarks}}
        </el-descriptions-item>
      </el-descriptions>
  </template>
</template>

<script>
  export default {
    data () {
      return {
        inputForm: {
          id: '',
          name: '',
          remarks: '',
          parent: {
            name: ''
          },
          parentIds: '',
          sort: ''
        }
      }
    },
    methods: {
      //表单注入数据
      setData (data) {
        this.inputForm = this.recover(this.inputForm, data)
      },
      //跳转到查看数据
      edit () {
        this.$emit('toEdit')
      }
    }
  }
</script>

  
