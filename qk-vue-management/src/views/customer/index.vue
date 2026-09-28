<template>
  <div class="customer">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" class="search-form">
      <el-form-item label="手机号">
        <el-input v-model="queryParams.phone" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="客户姓名">
        <el-input v-model="queryParams.name" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="渠道来源">
        <el-select v-model="queryParams.channel" placeholder="请选择" clearable>
          <el-option label="线上活动" :value="1" />
          <el-option label="推广介绍" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="意向学科">
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
        <el-icon><Plus /></el-icon>新增客户
      </el-button>
    </div>

    <!-- 表格区域 -->
    <el-table :data="tableData" style="width: 100%" v-loading="loading" border>
      <el-table-column prop="id" label="客户ID" width="80" align="center" />
      <el-table-column prop="name" label="客户姓名" width="100" align="center" />
      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column prop="channel" label="渠道来源" width="120" align="center">
        <template #default="{ row }">
          {{ row.channel === 1 ? '线上活动' : '推广介绍' }}
        </template>
      </el-table-column>
      <el-table-column prop="degree" label="学历" width="100" align="center">
        <template #default="{ row }">
          {{ getDegreeLabel(row.degree) }}
        </template>
      </el-table-column>
      <el-table-column prop="jobStatus" label="在职状态" width="100" align="center">
        <template #default="{ row }">
          {{ row.jobStatus === 1 ? '在职' : '离职' }}
        </template>
      </el-table-column>
      <el-table-column prop="subject" label="意向学科" width="170" align="center">
        <template #default="{ row }">
          {{ getSubjectLabel(row.subject) }}
        </template>
      </el-table-column>
      <el-table-column prop="courseName" label="意向课程" width="200" align="center" />
      <el-table-column prop="createTime" label="创建时间" width="200" align="center" />
      <el-table-column label="操作" min-width="100" align="center">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleUpdate(row)">
            <el-icon><Edit /></el-icon>修改
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

    <!-- 新增/修改对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="700px" @close="handleDialogClose">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channel">
              <el-select v-model="form.channel" placeholder="请选择" style="width: 100%">
                <el-option label="线上活动" :value="1" />
                <el-option label="推广介绍" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学历" prop="degree">
              <el-select v-model="form.degree" placeholder="请选择" style="width: 100%">
                <el-option label="高中" :value="1" />
                <el-option label="中专" :value="2" />
                <el-option label="大专" :value="3" />
                <el-option label="本科" :value="4" />
                <el-option label="硕士" :value="5" />
                <el-option label="博士" :value="6" />
                <el-option label="其他" :value="7" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择" style="width: 100%">
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="form.age" placeholder="请输入年龄" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="微信号" prop="wechat">
              <el-input v-model="form.wechat" placeholder="请输入微信号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="QQ" prop="qq">
              <el-input v-model="form.qq" placeholder="请输入QQ" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="在职状态" prop="jobStatus">
              <el-select v-model="form.jobStatus" placeholder="请选择" style="width: 100%">
                <el-option label="在职" :value="1" />
                <el-option label="离职" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="意向学科" prop="subject">
              <el-select v-model="form.subject" placeholder="请选择" style="width: 100%" @change="handleSubjectChange">
                <el-option label="AI智能应用开发(Java)" :value="1" />
                <el-option label="AI大模型开发(Python)" :value="2" />
                <el-option label="AI鸿蒙开发" :value="3" />
                <el-option label="AI大数据" :value="4" />
                <el-option label="AI嵌入式" :value="5" />
                <el-option label="AI测试" :value="6" />
                <el-option label="AI运维" :value="7" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="意向课程" prop="courseId">
              <el-select v-model="form.courseId" placeholder="请选择" style="width: 100%">
                <el-option 
                  v-for="item in courseList" 
                  :key="item.id" 
                  :label="item.name" 
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>

      <template #footer>
        <el-button @click="dialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCustomerPage, addCustomer, getCustomerById, updateCustomer } from '@/api/customer'
import { ElMessage } from 'element-plus'
import { Search, Plus, Edit, Refresh } from '@element-plus/icons-vue'
import { getCoursesBySubject } from '@/api/course'


// 添加课程列表数据
const courseList = ref([])
// 查询参数
const queryParams = ref({
  phone: '',
  name: '',
  channel: '',
  subject: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)

// 对话框数据
const dialog = ref({
  title: '',
  visible: false,
  type: 'add' // add或edit
})

// 表单数据
const form = ref({
  id: '',
  phone: '',
  channel: '',
  name: '',
  gender: '',
  age: '',
  wechat: '',
  qq: '',
  degree: '',
  jobStatus: '',
  subject: '',
  courseId: '',
  businessId: ''
})

// 表单校验规则
const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入客户姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  //增加年龄,微信,QQ的验证规则
  age: [
    { pattern: /^[0-9]{1,3}$/, message: '请输入正确的年龄', trigger: 'blur' } 
  ],
  wechat: [
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' } 
  ],
  qq: [
    { pattern: /^[1-9]\d{4,10}$/, message: '请输入正确的QQ', trigger: 'blur' } 
  ]
}

// 表单ref
const formRef = ref(null)

// 获取意向学科标签
const getSubjectLabel = (subject) => {
  const subjectMap = {
      1: 'AI智能应用开发(Java)',
      2: 'AI大模型开发(Python)',
      3: 'AI鸿蒙开发',
      4: 'AI大数据',
      5: 'AI嵌入式',
      6: 'AI测试',
      7: 'AI运维'
  }
  return subjectMap[subject] || ''
}

// 获取学历标签
const getDegreeLabel = (degree) => {
  const degreeMap = {
    1: '高中',
    2: '中专',
    3: '大专',
    4: '本科',
    5: '硕士',
    6: '博士',
    7: '其他'
  }
  return degreeMap[degree] || ''
}

// 查询列表
const getList = async () => {
  loading.value = true
  try {
    const res = await getCustomerPage(queryParams.value)
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
    phone: '',
    name: '',
    channel: '',
    subject: '',
    page: 1,
    pageSize: 10
  }
  getList()
}

// 新增按钮点击事件
const handleAdd = () => {
  dialog.value.type = 'add'
  dialog.value.title = '新增客户'
  dialog.value.visible = true
}

// 修改按钮点击事件
const handleUpdate = async (row) => {
  dialog.value.type = 'edit'
  dialog.value.title = '修改客户'
  const res = await getCustomerById(row.id)
  // 先加载课程列表
  if (res.subject) {
    const courseRes = await getCoursesBySubject(res.subject)
    courseList.value = courseRes
  }
  // 再设置表单数据
  form.value = res
  dialog.value.visible = true
}

// 对话框关闭事件
const handleDialogClose = () => {
  form.value = {
    id: '',
    phone: '',
    channel: '',
    name: '',
    gender: '',
    age: '',
    wechat: '',
    qq: '',
    degree: '',
    jobStatus: '',
    subject: '',
    courseId: '',
    businessId: ''
  }
  formRef.value?.resetFields()
}

// 表单提交
const handleSubmit = async () => {
  await formRef.value.validate()
  if (dialog.value.type === 'add') {
    await addCustomer(form.value)
    ElMessage.success('添加成功')
  } else {
    await updateCustomer(form.value)
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

// 添加学科变化处理函数
const handleSubjectChange = async (val) => {
  if (val) {
    const res = await getCoursesBySubject(val)
    courseList.value = res
    form.value.courseId = '' // 清空已选课程
  } else {
    courseList.value = []
    form.value.courseId = ''
  }
}

</script>

<style scoped>
.customer {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.search-form {
  background-color: var(--el-bg-color);
  border-radius: 4px;
}

.search-form :deep(.el-form-item) {
  margin-bottom: 0;
}

.search-form :deep(.el-input),
.search-form :deep(.el-select) {
  width: 200px;
}

.search-buttons {
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
</style>