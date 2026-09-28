<template>
  <div class="activity">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" class="search-form">
      <el-form-item label="渠道来源">
        <el-select v-model="queryParams.channel" placeholder="请选择" clearable>
          <el-option label="线上活动" :value="1" />
          <el-option label="推广介绍" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="活动类型">
        <el-select v-model="queryParams.type" placeholder="请选择" clearable>
          <el-option label="课程折扣" :value="1" />
          <el-option label="代金券" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="活动状态">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="未开始" :value="1" />
          <el-option label="进行中" :value="2" />
          <el-option label="已结束" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item class="search-buttons">
        <el-button type="primary" @click="handleQuery">
          <el-icon><Search /></el-icon>搜索
        </el-button>
        <el-button @click="resetQuery">
          <el-icon><Refresh /></el-icon>重置
        </el-button>
      </el-form-item>
    </el-form>

    <div class="divider"></div>

    <!-- 操作按钮区域 -->
    <div class="operation-area">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>创建活动
      </el-button>
    </div>

    <!-- 表格区域 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      v-loading="loading"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="index" label="序号" width="55" align="center" />
      <el-table-column prop="name" label="活动名称" width="200" align="center" />
      <el-table-column prop="channel" label="渠道来源" width="120" align="center">
        <template #default="{ row }">
          {{ row.channel === 1 ? '线上活动' : '推广介绍' }}
        </template>
      </el-table-column>
      <el-table-column prop="type" label="活动类型" width="120" align="center">
        <template #default="{ row }">
          {{ row.type === 1 ? '课程折扣' : '代金券' }}
        </template>
      </el-table-column>
      <el-table-column prop="description" label="活动明细" width="250" align="center">
        <template #default="{ row }">
          {{ row.type === 1 ? `课程折扣/${row.discount}折` : `课程代金券/${row.voucher}元`}}
        </template>
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="250" align="center" />
      <el-table-column prop="endTime" label="结束时间" width="250" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">
            <el-icon><Edit /></el-icon>修改
          </el-button>
          <el-button type="danger" link @click="handleDelete(row)">
            <el-icon><Delete /></el-icon>删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页区域 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="queryParams.page"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 30, 40]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加/修改活动对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="600px"
      @close="handleDialogClose"
      class="activity-dialog"
    >
      <el-form
        ref="activityFormRef"
        :model="activityForm"
        :rules="rules"
        label-width="80px"
        class="activity-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channel">
              <el-select v-model="activityForm.channel" placeholder="请选择" style="width: 100%">
                <el-option label="线上活动" :value="1" />
                <el-option label="推广介绍" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动名称" prop="name">
              <el-input v-model="activityForm.name" placeholder="请输入活动名称" />
            </el-form-item>
          </el-col>
        </el-row>
    
        <el-form-item label="活动日期" prop="dateRange">
          <el-date-picker
            v-model="activityForm.dateRange"
            type="datetimerange"
            range-separator="到"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            style="width: 100%"
          />
        </el-form-item>
    
        <el-form-item label="活动简介" prop="description">
          <el-input
            v-model="activityForm.description"
            type="textarea"
            placeholder="请输入活动简介"
            :rows="3"
            maxlength="100"
            show-word-limit
          />
        </el-form-item>
    
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动类型" prop="type">
              <el-select v-model="activityForm.type" placeholder="请选择" style="width: 100%">
                <el-option label="课程折扣" :value="1" />
                <el-option label="代金券" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item 
              :label="activityForm.type === 1 ? '课程折扣' : '代金券'" 
              :prop="activityForm.type === 1 ? 'discount' : 'voucher'"
            >
              <el-input-number 
                v-if="activityForm.type === 1"
                v-model="activityForm.discount" 
                :min="1" 
                :max="9.9" 
                :precision="1"
                :step="0.1"
                style="width: 100%" 
                placeholder="请输入"
              />
              <el-input-number
                v-else
                v-model="activityForm.voucher"
                :min="1"
                :max="1000"
                style="width: 100%"
                placeholder="请输入"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialog.visible = false"><el-icon><Close /></el-icon>取 消</el-button>
        <el-button type="primary" @click="handleSubmit"><el-icon><Check /></el-icon>确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getActivityPage, addActivity, updateActivity, deleteActivity } from '@/api/activity'
import { Search, Delete, Edit, Plus, Refresh } from '@element-plus/icons-vue'
import { getActivityById } from '@/api/activity'

// 查询参数
const queryParams = ref({
  channel: '',
  type: '',
  status: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)
const selectedIds = ref([])

// 对话框相关
const dialog = ref({
  visible: false,
  title: '',
  type: 'add' // add or edit
})

// 表单对象
const activityForm = ref({
  id: undefined,
  channel: '',
  name: '',
  dateRange: [],
  description: '',
  type: '',
  discount: null,
  voucher: null
})

// 表单ref
const activityFormRef = ref(null)

// 表单验证规则
const rules = {
  channel: [
    { required: true, message: '请选择渠道来源', trigger: 'change' }
  ],
  name: [
    { required: true, message: '请输入活动名称', trigger: 'blur' },
    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
  ],
  dateRange: [
    { required: true, message: '请选择活动日期', trigger: 'change' }
  ],
  description: [
    { required: true, message: '请输入活动简介', trigger: 'blur' },
    { min: 5, max: 100, message: '长度在 5 到 100 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择活动类型', trigger: 'change' }
  ]
}

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getActivityPage(queryParams.value)
    tableData.value = res.rows
    total.value = res.total
  } catch (error) {
    console.error(error)
  }
  loading.value = false
}

// 表格多选框选中数据
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 搜索按钮点击事件
const handleQuery = () => {
  queryParams.value.page = 1
  getList()
}

// 重置按钮点击事件
const resetQuery = () => {
  queryParams.value = {
    channel: '',
    type: '',
    status: '',
    page: 1,
    pageSize: 10
  }
  getList()
}

// 新增按钮点击事件
const handleAdd = () => {
  dialog.value.type = 'add'
  dialog.value.title = '创建活动'
  dialog.value.visible = true
  activityForm.value = {
    channel: '',
    name: '',
    dateRange: [],
    description: '',
    type: '',
    discount: null,
    voucher: null
  }
}

// 修改按钮点击事件
const handleEdit = async (row) => {
  try {
    const res = await getActivityById(row.id)
    dialog.value.type = 'edit'
    dialog.value.title = '修改活动'
    dialog.value.visible = true
    activityForm.value = {
      ...res,
      dateRange: [res.startTime, res.endTime],
      discount: res.type === 1 ? res.discount : null,
      voucher: res.type === 2 ? res.voucher : null
    }
  } catch (error) {
    console.error('获取活动数据失败:', error)
    ElMessage.error('获取活动数据失败')
  }
}

// 删除按钮点击事件
const handleDelete = (row) => {
  ElMessageBox.confirm('确认要删除该活动吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteActivity(row.id)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 批量删除按钮点击事件
const handleBatchDelete = () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的活动')
    return
  }
  ElMessageBox.confirm('确认要删除选中的活动吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteActivity(selectedIds.value.join(','))
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 对话框关闭事件
const handleDialogClose = () => {
  activityFormRef.value?.resetFields()
}

// 提交表单
const handleSubmit = async () => {
  await activityFormRef.value.validate()
  
  const submitData = {
    ...activityForm.value,
    startTime: activityForm.value.dateRange[0],
    endTime: activityForm.value.dateRange[1]
  }
  delete submitData.dateRange

  if (dialog.value.type === 'add') {
    await addActivity(submitData)
    ElMessage.success('添加成功')
  } else {
    await updateActivity(submitData)
    ElMessage.success('修改成功')
  }
  
  dialog.value.visible = false
  getList()
}

// 分页大小改变
const handleSizeChange = (val) => {
  queryParams.value.pageSize = val
  getList()
}

// 页码改变
const handleCurrentChange = (val) => {
  queryParams.value.page = val
  getList()
}

// 初始化
onMounted(() => {
  getList()
})
</script>

<style scoped>
.activity {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.search-form {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.search-form :deep(.el-form-item) {
  margin: 0 20px 0 0;
  min-width: 280px;
}

.search-buttons {
  margin-left: auto !important;
  min-width: auto !important;
  margin-right: 0 !important;
}

.divider {
  height: 1px;
  background-color: var(--el-border-color-light);
  margin: 20px 0;
}

.operation-area {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

:deep(.activity-dialog .el-dialog__body) {
  padding: 20px 5px;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}

.activity-form {
  width: 100%;
  padding: 0 5px;
}

:deep(.el-form-item__label) {
  font-weight: normal;
}

:deep(.el-dialog__body) {
  padding: 10px 0 30px;
}

:deep(.el-form-item) {
  margin-bottom: 22px;
}

:deep(.el-form-item__error) {
  padding-top: 4px;
}

:deep(.el-input-number .el-input__wrapper) {
  padding-left: 0;
  padding-right: 0;
}
</style>
