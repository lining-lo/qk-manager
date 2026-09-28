<template>
  <div class="business-list">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" >
      <el-row :gutter="30">
        <el-col :span="4">
          <el-form-item label="商机ID" prop="businessId">
            <el-input v-model="queryParams.businessId" placeholder="请输入商机ID" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="客户姓名" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入客户姓名" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="商机状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 100%">
              <el-option label="待分配" :value="1" />
              <el-option label="待跟进" :value="2" />
              <el-option label="跟进中" :value="3" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="归属人" prop="assignName">
            <el-input v-model="queryParams.assignName" placeholder="请输入归属人" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="4" style="text-align: right">
          <el-form-item>
            <el-button type="primary" @click="handleQuery">
              <el-icon><Search /></el-icon>搜索
            </el-button>
            <el-button @click="resetQuery">
              <el-icon><Refresh /></el-icon>重置
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div class="divider"></div>

    <!-- 操作按钮区域 -->
    <div class="operation-area">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新建商机
      </el-button>
    </div>

    <!-- 表格区域 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column prop="id" label="商机ID" width="80" align="center" />
      <el-table-column prop="name" label="姓名" width="100" align="center" />
      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column prop="subject" label="意向学科" width="180" align="center">
        <template #default="{ row }">
          {{ getSubjectLabel(row.subject) }}
        </template>
      </el-table-column>
      <el-table-column prop="assignName" label="归属人" width="100" align="center" />
      <el-table-column prop="createTime" label="创建时间" width="190" align="center" />
      <el-table-column prop="status" label="状态" width="120" align="center">
        <template #default="{ row }">
          {{ getStatusLabel(row.status) }}
        </template>
      </el-table-column>
      <el-table-column prop="nextTime" label="下次跟进时间" width="190" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="{ row }">
          <el-button 
            v-if="row.status === 1 && canShowButtons(row).assign" 
            type="primary" 
            link 
            @click="handleAssign(row)"
          >
            <el-icon><UserFilled /></el-icon>分配
          </el-button>
          <el-button 
            v-if="canShowButtons(row).follow"
            type="primary" 
            link 
            @click="handleFollow(row)"
          >
            <el-icon><Edit /></el-icon>跟进
          </el-button>
          <el-button 
            v-if="canShowButtons(row).returnToPool"
            type="danger" 
            link 
            @click="handleReturnToPool(row)"
          >
            <el-icon><Warning /></el-icon>踢回公海
          </el-button>
          <el-button 
            v-if="canShowButtons(row).convertToCustomer"
            type="success" 
            link 
            @click="handleConvertToCustomer(row)"
          >
            <el-icon><Position /></el-icon>转客户
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

    <!-- 添加商机对话框 -->
    <el-dialog
      title="添加商机"
      v-model="addDialog.visible"
      width="600px"
      @close="handleAddDialogClose"
    >
      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="addRules"
        label-width="80px"
      >
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="addForm.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="addForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10">

          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="addForm.gender" placeholder="请选择" style="width: 100%">
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="addForm.age" placeholder="请输入年龄" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="微信号" prop="wechat">
              <el-input v-model="addForm.wechat" placeholder="请输入微信号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="QQ" prop="qq">
              <el-input v-model="addForm.qq" placeholder="请输入QQ" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="意向学科" prop="subject">
              <el-select v-model="addForm.subject" placeholder="请选择" style="width: 100%" @change="handleSubjectChange">
                <el-option label="AI智能应用开发(java)" :value="1" />
                <el-option label="AI大模型开发(python)" :value="2" />
                <el-option label="AI鸿蒙开发" :value="3" />
                <el-option label="AI大数据" :value="4" />
                <el-option label="AI嵌入式" :value="5" />
                <el-option label="AI测试" :value="6" />
                <el-option label="AI运维" :value="7" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="意向课程" prop="courseId">
              <el-select v-model="addForm.courseId" placeholder="请选择" style="width: 100%">
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


        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="学历" prop="degree">
              <el-select v-model="addForm.degree" placeholder="请选择" style="width: 100%">
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
          <el-col :span="12">
            <el-form-item label="在职状态" prop="jobStatus">
              <el-select v-model="addForm.jobStatus" placeholder="请选择" style="width: 100%">
                <el-option label="在职" :value="1" />
                <el-option label="离职" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>


        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channel">
              <el-select v-model="addForm.channel" placeholder="请选择" style="width: 100%">
                <el-option label="线上活动" :value="1" />
                <el-option label="推广介绍" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="addForm.remark"
                placeholder="请输入备注信息, 50个字以内"
              />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>

      <template #footer>
        <el-button @click="addDialog.visible = false"><el-icon><Close /></el-icon>取 消</el-button>
        <el-button type="primary" @click="handleAddSubmit"><el-icon><Check /></el-icon>确 定</el-button>
      </template>
    </el-dialog>

    <!-- 跟进商机对话框 -->
    <el-dialog
      :title="`跟进商机(商机ID: ${followForm.id})`"
      v-model="followDialog.visible"
      width="800px"
      @close="handleFollowDialogClose"
    >
      <el-form
        ref="followFormRef"
        :model="followForm"
        :rules="followRules"
        label-width="90px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="followForm.phone" placeholder="请输入手机号" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="followForm.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="followForm.gender" placeholder="请选择" style="width: 100%">
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="followForm.age" placeholder="请输入年龄" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="微信号" prop="wechat">
              <el-input v-model="followForm.wechat" placeholder="请输入微信号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="QQ" prop="qq">
              <el-input v-model="followForm.qq" placeholder="请输入QQ" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="意向学科" prop="subject">
              <el-select v-model="followForm.subject" placeholder="请选择" style="width: 100%" @change="handleFollowSubjectChange">
                <el-option label="AI智能应用开发(java)" :value="1" />
                <el-option label="AI大模型开发(python)" :value="2" />
                <el-option label="AI鸿蒙开发" :value="3" />
                <el-option label="AI大数据" :value="4" />
                <el-option label="AI嵌入式" :value="5" />
                <el-option label="AI测试" :value="6" />
                <el-option label="AI运维" :value="7" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="意向课程" prop="courseId">
              <el-select v-model="followForm.courseId" placeholder="请选择" style="width: 100%">
                <el-option 
                  v-for="item in followCourseList" 
                  :key="item.id" 
                  :label="item.name" 
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学历" prop="degree">
              <el-select v-model="followForm.degree" placeholder="请选择" style="width: 100%">
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
          <el-col :span="12">
            <el-form-item label="在职状态" prop="jobStatus">
              <el-select v-model="followForm.jobStatus" placeholder="请选择" style="width: 100%">
                <el-option label="在职" :value="1" />
                <el-option label="离职" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channel">
              <el-select v-model="followForm.channel" placeholder="请选择" style="width: 100%">
                <el-option label="线上活动" :value="1" />
                <el-option label="推广介绍" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input
                v-model="followForm.remark"
                type="textarea"
                :rows="1" 
                placeholder="请输入备注信息, 50个字以内" 
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 在此位置加一个横线分隔一下上下两个部分 -->
        <div class="divider"></div>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="跟进状态" prop="trackStatus">
              <el-select v-model="followForm.trackStatus" placeholder="请选择" style="width: 100%">
                <el-option label="接通" :value="1" />
                <el-option label="拒绝" :value="2" />
                <el-option label="无人接听" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="沟通重点">
              <el-select v-model="followForm.keyItems" multiple placeholder="请选择(支持多选)" style="width: 100%">
                <el-option label="课程" value="课程" />
                <el-option label="价格" value="价格" />
                <el-option label="位置" value="位置" />
                <el-option label="时间" value="时间" />
                <el-option label="师资" value="师资" />
                <el-option label="项目" value="项目" />
                <el-option label="薪资" value="薪资" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="下次跟进">
              <el-date-picker
                v-model="followForm.nextTime"
                type="datetime"
                placeholder="请选择下次跟进时间"
                style="width: 100%"
                value-format="YYYY-MM-DDTHH:mm:ss"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="沟通纪要" >
              <el-input
                v-model="followForm.record"
                placeholder="请输入跟进记录, 50字以内"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
      </el-form>

      <!-- 在此位置加一个横线分隔一下上下两个部分 -->
      <div class="divider"></div>

      <!-- 跟进历史记录表格 -->
      <div class="track-records">
        <div class="title">跟进历史</div>
        <el-table :data="trackRecords" style="width: 100%; font-size: 11px;" border size="small">
          <el-table-column prop="createTime" label="跟进时间" align="center" width="140" />
          <el-table-column prop="keyItems" label="沟通重点" align="center" width="150" />
          <el-table-column prop="trackStatus" label="跟进状态" align="center" width="100">
            <template #default="{ row }">
              {{ getTrackStatusLabel(row.trackStatus) }}
            </template>
          </el-table-column>
          <el-table-column prop="nextTime" label="下次跟进时间" align="center" width="150" />
          <el-table-column prop="record" label="沟通纪要" align="center" show-overflow-tooltip>
            <template #default="{ row }">
              <el-tooltip class="box-item" effect="dark" :content="row.record" placement="top" :show-after="100">
                <span>{{ row.record?.slice(0, 10) + (row.record?.length > 10 ? '...' : '') }}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="assignName" label="跟进人" align="center" width="80" />
        </el-table>
      </div>

      <template #footer>
        <el-button @click="followDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="handleFollowSubmit">确 定</el-button>
      </template>
    </el-dialog>

    <!-- 添加分配商机对话框 -->
    <el-dialog
      title="分配商机"
      v-model="assignDialog.visible"
      width="400px"
      @close="handleAssignDialogClose"
    >
      <el-form
        ref="assignFormRef"
        :model="assignForm"
        :rules="assignRules"
        label-width="100px"
      >
        <el-form-item label="请选择人员" prop="userId">
          <el-select v-model="assignForm.userId" placeholder="请选择人员" style="width: 100%">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="assignDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="handleAssignSubmit">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { ElMessage,ElMessageBox } from 'element-plus'
import { getBusinessPage, addBusiness, getBusinessById, updateBusiness, assignBusiness, returnToPool, convertToCustomer  } from '@/api/business'
import { getUserListByRoleLabel } from '@/api/user'
import { getCoursesBySubject } from '@/api/course'

// 获取当前登录用户信息
const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

// 控制按钮显示的函数
const canShowButtons = (row) => {
  const isAdmin = userInfo.value.roleLabel === 'admin'
  const isBusinessOperator = userInfo.value.roleLabel === 'business_operator'
  const isOwner = userInfo.value.id === row.userId

  return {
    // 只有管理员可以分配商机，且商机状态为待分配(1)
    assign: row.status === 1 && isAdmin,
    // 只有商机归属人本人可以跟进商机，且商机状态为待跟进(2)或跟进中(3)
    follow: (row.status === 2 || row.status === 3) && isBusinessOperator && isOwner,
    // 只有商机归属人本人可以将商机踢回公海，且商机状态为待跟进(2)或跟进中(3)
    returnToPool: (row.status === 2 || row.status === 3) && isBusinessOperator && isOwner,
    // 只有商机归属人本人可以将商机转为客户，且商机状态为待跟进(2)或跟进中(3)
    convertToCustomer: (row.status === 2 || row.status === 3) && isBusinessOperator && isOwner
  }
}

// 查询参数
const queryParams = ref({
  businessId: '',
  phone: '',
  name: '',
  status: '',
  assignName: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)

// 添加对话框
const addDialog = ref({
  visible: false
})

// 添加表单
const addForm = ref({
  phone: '',
  channel: '',
  name: '',
  gender: '',
  age: '',
  wechat: '',
  qq: '',
  subject: '',
  courseId: '',
  degree: '',
  jobStatus: '',
  remark: ''
})

// 课程列表
const courseList = ref([])

// 跟进对话框
const followDialog = ref({
  visible: false
})

// 跟进表单
const followForm = ref({
  id: '',
  phone: '',
  name: '',
  gender: '',
  age: '',
  wechat: '',
  qq: '',
  subject: '',
  courseId: '',
  channel: '',
  degree: '',
  jobStatus: '',
  remark: '',
  trackStatus: '',
  keyItems: [],
  nextTime: '',
  record: ''
})

// 跟进表单的课程列表
const followCourseList = ref([])

// 处理意向学科变化
const handleFollowSubjectChange = async (value) => {
  followForm.value.courseId = ''
  if (value) {
    const res = await getCoursesBySubject(value)
    followCourseList.value = res
  } else {
    followCourseList.value = []
  }
}

// 跟进记录列表
const trackRecords = ref([])

// 表单ref
const addFormRef = ref(null)
const followFormRef = ref(null)

// 添加分配对话框相关的响应式数据
const assignDialog = ref({
  visible: false
})

// 处理学科变化
const handleSubjectChange = async (val) => {
  if (val) {
    const res = await getCoursesBySubject(val)
    courseList.value = res
    addForm.value.courseId = '' // 清空已选课程
  } else {
    courseList.value = []
    addForm.value.courseId = ''
  }
}

const assignForm = ref({
  businessId: '', // 商机ID
  deptId: '', // 部门ID
  userId: ''  // 用户ID
})

const assignRules = {
  deptId: [
    { required: true, message: '请选择部门', trigger: 'change' }
  ],
  userId: [
    { required: true, message: '请选择人员', trigger: 'change' }
  ]
}

// 3. 添加部门和用户列表数据
const userList = ref([])
const assignFormRef = ref(null)

// 5. 根据部门ID获取用户列表
const getUserList = async (deptId) => {
  try {
    const res = await getUserListByRoleLabel('business_operator')
    userList.value = res
  } catch (error) {
    console.error(error)
  }
}

// 7. 分配按钮点击事件
const handleAssign = (row) => {
  assignForm.value.businessId = row.id
  assignDialog.value.visible = true
  
  getUserList(); // 加载用户列表
}

// 8. 分配对话框关闭事件
const handleAssignDialogClose = () => {
  assignForm.value = {
    businessId: '',
    deptId: '',
    userId: ''
  }
  userList.value = []
  assignFormRef.value?.resetFields()
}

// 9. 分配表单提交
const handleAssignSubmit = async () => {
  await assignFormRef.value.validate()
  try {
    await assignBusiness(assignForm.value.businessId, assignForm.value.userId)
    ElMessage.success('分配成功')
    assignDialog.value.visible = false
    getList() // 刷新列表
  } catch (error) {
    console.error(error)
  }
}

// 初始化
onMounted(() => {
  getList()
})

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getBusinessPage(queryParams.value)
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
    businessId: '',
    phone: '',
    name: '',
    status: '',
    assignName: '',
    page: 1,
    pageSize: 10
  }
  handleQuery()
}

// 新建按钮点击事件
const handleAdd = () => {
  addDialog.value.visible = true
}

// 添加对话框关闭事件
const handleAddDialogClose = () => {
  addForm.value = {
    phone: '',
    channel: '',
    name: '',
    gender: '',
    age: '',
    wechat: '',
    qq: '',
    subject: '',
    degree: '',
    jobStatus: '',
    remark: ''
  }
  addFormRef.value?.resetFields()
}

// 添加表单提交
const handleAddSubmit = async () => {
  await addFormRef.value.validate()
  try {
    await addBusiness(addForm.value)
    ElMessage.success('添加成功')
    addDialog.value.visible = false
    getList()
  } catch (error) {
    console.error(error)
  }
}

// 跟进按钮点击事件
const handleFollow = async (row) => {
  const res = await getBusinessById(row.id)
  // 加载意向课程列表
  if (res.subject) {
    await handleFollowSubjectChange(res.subject)
  }

  followForm.value = {
    ...res,
    keyItems: [],
    record: '',
    nextTime: '', // 清空下次跟进时间
  }
  
  trackRecords.value = res.trackRecords || []

  followFormRef.value?.resetFields()

  followDialog.value.visible = true  
}

// 跟进对话框关闭事件
const handleFollowDialogClose = () => {
  followForm.value = {
    id: '',
    phone: '',
    channel: '',
    name: '',
    gender: '',
    age: '',
    wechat: '',
    qq: '',
    subject: '',
    degree: '',
    jobStatus: '',
    trackStatus: '',
    keyItems: [],
    nextTime: '',
    record: ''
  }
  trackRecords.value = []
  followFormRef.value?.resetFields()
}

// 跟进表单提交
const handleFollowSubmit = async () => {
  await followFormRef.value.validate()
  try {
    await updateBusiness(followForm.value)
    ElMessage.success('跟进成功')
    followDialog.value.visible = false
    getList()
  } catch (error) {
    console.error(error)
  }
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

// 添加工具函数
const getStatusLabel = (status) => {
  switch (status) {
    case 1:
      return '待分配'
    case 2:
      return '待跟进'
    case 3:
      return '跟进中'
    case 4:
      return '已回收'
    case 5:
      return '转客户'
    default:
      return '未知状态'
  }
}

const getSubjectLabel = (subject) => {
  switch (subject) {
    case 1:
      return 'AI智能应用开发(Java)'
    case 2:
      return 'AI大模型开发(Python)'
    case 3:
      return 'AI鸿蒙开发'
    case 4:
      return 'AI大数据'
    case 5:
      return 'AI嵌入式'
    case 6:
      return 'AI测试'
    case 7:
      return 'AI运维'
    default:
      return '未知学科'
  }
}

const getTrackStatusLabel = (status) => {
  switch (status) {
    case 1:
      return '接通'
    case 2:
      return '拒绝'
    case 3:
      return '无人接听'
    default:
      return '未知状态'
  }
}

// 添加表单校验规则
const addRules = {
  name: [
    { required: true, message: '请输入客户姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  gender: [
    { required: false, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { pattern: /^\d+$/, message: '年龄必须为数字', trigger: 'blur' }
  ],
  wechat: [
    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
  ],
  qq: [
    { pattern: /^\d{1,20}$/, message: '请输入正确的QQ号格式', trigger: 'blur' }
  ],
  remark: [
    { max: 50, message: '最多输入50个字符', trigger: 'blur' }
  ]
}

// 跟进表单校验规则
const followRules = {
  name: [
    { required: true, message: '请输入客户姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  gender: [
    { required: false, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { pattern: /^\d+$/, message: '年龄必须为数字', trigger: 'blur' }
  ],
  wechat: [
    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
  ],
  qq: [
    { pattern: /^\d{1,20}$/, message: '请输入正确的QQ号格式', trigger: 'blur' }
  ],
  remark: [
    { max: 50, message: '最多输入50个字符', trigger: 'blur' }
  ],
  // 跟进特有字段的校验规则
  trackStatus: [
    { required: true, message: '请选择跟进状态', trigger: 'change' }
  ]
}

// 踢回公海
const handleReturnToPool = async (row) => {
  try {
    await ElMessageBox.confirm('确认将该商机踢回公海吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await returnToPool(row.id)
    ElMessage.success('操作成功')
    getList()
  } catch (error) {
    console.error(error)
  }
}

// 转客户
const handleConvertToCustomer = async (row) => {
  try {
    await ElMessageBox.confirm('确认将该商机转为客户吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await convertToCustomer(row.id)
    ElMessage.success('操作成功')
    getList()
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
.business-list {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.divider {
  height: 1px;
  background-color: #EBEEF5;
  margin: 10px 0;
}

.operation-area {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.track-records {
  margin-top: 20px;
}

.track-records .title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

.divider {
  border: 1px dashed #dbdee3;
  margin-top: 0px;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>