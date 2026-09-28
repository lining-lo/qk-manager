<template>
  <div class="course">
    <!-- 原有内容保持不变，只需将最外层的 class 从 app-container 改为 course -->
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" class="search-form">
      <el-form-item label="课程学科">
        <el-select v-model="queryParams.subject" placeholder="请选择" clearable>
          <el-option label="AI智能应用开发(Java)" :value="1" />
          <el-option label="AI大模型开发(Python)" :value="2" />
          <el-option label="AI鸿蒙开发" :value="3" />
          <el-option label="AI大数据" :value="4" />
          <el-option label="AI嵌入式" :value="5" />
          <el-option label="AI测试" :value="6" />
          <el-option label="AI运维" :value="7" />
        </el-select>
      </el-form-item>
      <el-form-item label="课程名称">
        <el-input v-model="queryParams.name" placeholder="请输入" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="适应人群">
        <el-select v-model="queryParams.target" placeholder="请选择" clearable>
          <el-option label="小白学员" :value="1" />
          <el-option label="初级程序员" :value="2" />
          <el-option label="中级程序员" :value="3" />
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
        <el-icon><Plus /></el-icon>添加课程
      </el-button>
    </div>

    <!-- 表格区域 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column type="index"  label="序号" width="55" align="center" />
      <el-table-column prop="subject" label="课程学科" width="200" align="center">
        <template #default="{ row }">
          {{ getSubjectLabel(row.subject) }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="课程名称" width="200" align="center" />
      <el-table-column prop="price" label="价格(元)" width="120" align="center" />
      <el-table-column prop="target" label="适用人群" width="150" align="center">
        <template #default="{ row }">
          <span v-if="row.target === 1">小白学员</span>
          <span v-if="row.target === 2">初级程序员</span>
          <span v-if="row.target === 3">中级程序员</span>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="课程介绍" align="center" width="300" show-overflow-tooltip />
      <el-table-column prop="updateTime" label="最后修改时间" align="center" width="180" />
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

    <!-- 添加/修改课程对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="500px"
      @close="handleDialogClose"
      class="course-dialog"
    >
      <el-form
        ref="courseFormRef"
        :model="courseForm"
        :rules="rules"
        label-width="80px"
        class="course-form"
      >
        <el-form-item label="课程学科" prop="subject">
          <el-select v-model="courseForm.subject" placeholder="请选择" style="width: 100%">
            <el-option label="AI智能应用开发(Java)" :value="1" />
            <el-option label="AI大模型开发(Python)" :value="2" />
            <el-option label="AI鸿蒙开发" :value="3" />
            <el-option label="AI大数据" :value="4" />
            <el-option label="AI嵌入式" :value="5" />
            <el-option label="AI测试" :value="6" />
            <el-option label="AI运维" :value="7" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="courseForm.name" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="适应人群" prop="target">
          <el-select v-model="courseForm.target" placeholder="请选择" style="width: 100%">
            <el-option label="小白学员" :value="1" />
            <el-option label="初级程序员" :value="2" />
            <el-option label="中级程序员" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程价格" prop="price">
          <el-input-number v-model="courseForm.price" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="课程介绍" prop="description">
          <el-input
            v-model="courseForm.description"
            type="textarea"
            placeholder="请输入课程介绍，100字以内"
            :rows="3"
            maxlength="100"
            show-word-limit
          />
        </el-form-item>
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
import { getCoursePage, addCourse, updateCourse, deleteCourse, getCourseById } from '@/api/course'
import { Search, Delete, Edit, Plus, Refresh } from '@element-plus/icons-vue'

// 查询参数
const queryParams = ref({
  name: '',
  subject: '',
  target: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)

// 对话框相关
const dialog = ref({
  visible: false,
  title: '',
  type: 'add' // add or edit
})

// 表单对象
const courseForm = ref({
  id: undefined,
  subject: '',
  name: '',
  price: 0,
  target: '',
  description: ''
})

// 表单ref
const courseFormRef = ref(null)

// 表单验证规则
const rules = {
  subject: [
    { required: true, message: '请选择课程学科', trigger: 'change' }
  ],
  name: [
    { required: true, message: '请输入课程名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  target: [
    { required: true, message: '请选择适应人群', trigger: 'change' }
  ],
  price: [
    { required: true, message: '请输入课程价格', trigger: 'blur' }
  ],
  description: [
    { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
  ]
}

// 获取学科标签 
const getSubjectLabel = (subject) => {
  const subjects = {
    1: 'AI智能应用开发(Java)',
    2: 'AI大模型开发(Python)',
    3: 'AI鸿蒙开发',
    4: 'AI大数据',
    5: 'AI嵌入式',
    6: 'AI测试',
    7: 'AI运维'
  }
  return subjects[subject] || ''
}

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getCoursePage(queryParams.value)
    tableData.value = res.rows
    total.value = res.total
  } catch (error) {
    console.error(error)
  }
  loading.value = false
}


// 搜索按钮点击事件
const handleQuery = () => {
  queryParams.value.page = 1
  getList()
}

// 重置按钮点击事件
const resetQuery = () => {
  queryParams.value = {
    name: '',
    subject: '',
    target: '',
    page: 1,
    pageSize: 10
  }
  getList()
}

// 新增按钮点击事件
const handleAdd = () => {
  dialog.value.type = 'add'
  dialog.value.title = '添加课程'
  dialog.value.visible = true
  courseForm.value = {
    subject: '',
    name: '',
    price: 0,
    target: '',
    description: ''
  }
  courseFormRef.value?.resetFields()
}

// 修改按钮点击事件
const handleEdit = async (row) => {
  try {
    const res = await getCourseById(row.id)
    dialog.value.type = 'edit'
    dialog.value.title = '修改课程'
    dialog.value.visible = true
    courseForm.value = { ...res }
  } catch (error) {
    console.error('获取课程数据失败:', error)
    ElMessage.error('获取课程数据失败')
  }
}

// 删除按钮点击事件
const handleDelete = (row) => {
  ElMessageBox.confirm('确认要删除该课程吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteCourse(row.id)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}


// 对话框关闭事件
const handleDialogClose = () => {
  courseFormRef.value?.resetFields()
}

// 提交表单
const handleSubmit = async () => {
  await courseFormRef.value.validate()
  
  if (dialog.value.type === 'add') {
    await addCourse(courseForm.value)
    ElMessage.success('添加成功')
  } else {
    await updateCourse(courseForm.value)
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

.course {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.search-form {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: flex-start;
}

.search-form :deep(.el-form-item) {
  margin: 0;
  min-width: 280px;
}

.search-buttons {
  margin-left: auto !important;
  min-width: auto !important;
}

:deep(.course-dialog .el-dialog__body) {
  padding: 30px 20px;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>