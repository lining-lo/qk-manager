<template>
  <div class="clue">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" class="search-form">
      <el-form-item label="线索ID">
        <el-input v-model="queryParams.clueId" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="queryParams.phone" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="线索状态">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="待分配" :value="1" />
          <el-option label="待跟进" :value="2" />
          <el-option label="跟进中" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="线索来源">
        <el-select v-model="queryParams.channel" placeholder="请选择" clearable>
          <el-option label="线上活动" :value="1" />
          <el-option label="推广介绍" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="线索归属">
        <el-input v-model="queryParams.assignName" placeholder="请输入" clearable />
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
        <el-icon><Plus /></el-icon>新建线索
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
      <el-table-column prop="id" label="线索ID" width="80" align="center" />
      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column prop="name" label="客户姓名" width="100" align="center" />
      <el-table-column prop="channel" label="渠道来源" width="100" align="center">
        <template #default="{ row }">
          {{ row.channel === 1 ? '线上活动' : '推广介绍' }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="200" align="center" />
      <el-table-column prop="assignName" label="归属人" width="130" align="center" />
      <el-table-column prop="status" label="线索状态" width="130" align="center">
        <template #default="{ row }">
          {{ getStatusLabel(row.status) }}
        </template>
      </el-table-column>
      <el-table-column prop="nextTime" label="下次跟进时间" width="200" align="center" />
      <!-- 修改操作列的内容 -->
      <el-table-column label="操作" min-width="200" align="center">
        <template #default="{ row }">
          <!-- 待分配状态 -->
          <el-button 
            type="primary" 
            link 
            @click="handleAssign(row)"  
            v-if="canShowButtons(row).assign"
          >
            <el-icon><UserFilled /></el-icon>分配
          </el-button>
          <!-- 跟进中状态 -->
          <el-button 
            type="primary" 
            link 
            @click="handleFollow(row)"
            v-if="canShowButtons(row).follow"
          >
            <el-icon><Edit /></el-icon>跟进
          </el-button>
          <el-button 
            type="danger" 
            link 
            @click="handleFake(row)"
            v-if="canShowButtons(row).fake"
          >
            <el-icon><Warning /></el-icon>伪线索
          </el-button>
          <el-button 
            type="success" 
            link 
            @click="handleConvert(row)"
            v-if="canShowButtons(row).convert"
          >
            <el-icon><Promotion /></el-icon>转商机
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

    <!-- 添加线索对话框 -->
    <el-dialog
      title="添加线索"
      v-model="addDialog.visible"
      width="700px"
      @close="handleAddDialogClose"
    >
      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="addRules"
        label-width="80px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="addForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channel">
              <el-select v-model="addForm.channel" placeholder="请选择" style="width: 100%" @change="handleChannelChange">
                <el-option label="线上活动" :value="1" />
                <el-option label="推广介绍" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动信息" prop="activityId">
              <el-select v-model="addForm.activityId" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="item in activityList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="addForm.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
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
        <el-row :gutter="20">
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
      </el-form>
      <template #footer>
        <el-button @click="addDialog.visible = false"><el-icon><Close /></el-icon>取 消</el-button>
        <el-button type="primary" @click="handleAddSubmit"><el-icon><Check /></el-icon>确 定</el-button>
      </template>
    </el-dialog>

    <!-- 跟进线索对话框 -->
    <el-dialog
      :title="`跟进线索(线索ID: ${followForm.id})`"
      v-model="followDialog.visible"
      width="900px"
      @close="handleFollowDialogClose"
    >
      <el-form
        ref="followFormRef"
        :model="followForm"
        :rules="followRules"
        label-width="100px"
        
      >
        <!-- 跟进线索对话框中的表单部分 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone" required>
              <el-input v-model="followForm.phone" placeholder="请输入手机号" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channel" required>
              <el-select v-model="followForm.channel" placeholder="请选择" style="width: 100%" disabled>
                <el-option label="线上活动" :value="1" />
                <el-option label="推广介绍" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动信息" prop="activityId">
              <el-select v-model="followForm.activityId" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="item in activityList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="followForm.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 其他行也需要移除 disabled 属性 -->
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
            <el-form-item  label="意向学科" prop="subject" required>
              <el-select v-model="followForm.subject" placeholder="请选择" style="width: 100%">
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
          <el-col :span="12">
            <el-form-item label="意向等级" prop="level" required>
              <el-select v-model="followForm.level" placeholder="请选择" style="width: 100%">
                <el-option label="近期学习" :value="1" />
                <el-option label="打算学（考虑中）" :value="2" />
                <el-option label="进行了解" :value="3" />
                <el-option label="打酱油" :value="4" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
    
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="下次跟进" prop="nextTime" required>
              <el-date-picker
                v-model="followForm.nextTime"
                type="datetime"
                placeholder="请选择时间"
                style="width: 100%"
                value-format="YYYY-MM-DDTHH:mm:ss"
              />
            </el-form-item>
          </el-col>
        </el-row>
    
        <el-form-item label="跟进记录" prop="record" required>
          <el-input
            v-model="followForm.record"
            type="textarea"
            :rows="1"
            placeholder="请输入跟进记录"
          />
        </el-form-item>
      </el-form>
    
      <!-- 跟进历史记录表格 -->
      <div class="track-records">
        <div class="title">跟进历史</div>
        <el-table :data="trackRecords" style="width: 100%; font-size: 11px;" border size="small">
          <el-table-column prop="level" label="意向等级" align="center" width="100">
            <template #default="{ row }">
              {{ getLevelLabel(row.level) }}
            </template>
          </el-table-column>
          <el-table-column prop="subject" label="意向学科" align="center" width="150">
            <template #default="{ row }">
              {{ getSubjectLabel(row.subject) }}
            </template>
          </el-table-column>
          <el-table-column prop="assignName" label="跟进人" align="center" width="80" />
          <el-table-column prop="createTime" label="跟进时间" align="center" width="140" />
          <el-table-column prop="nextTime" label="下次跟进时间" align="center" width="140" />
          <el-table-column 
            prop="record" 
            label="跟进记录" 
            align="center" 
            show-overflow-tooltip
            min-width="120">
            <template #default="{ row }">
              <el-tooltip
                class="box-item"
                effect="dark"
                :content="row.record"
                placement="top"
                :show-after="100"
              >
                <span>{{ row.record?.slice(0, 10) + (row.record?.length > 10 ? '...' : '') }}</span>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </div>
    
      <template #footer>
        <el-button @click="followDialog.visible = false"><el-icon><Close /></el-icon>取 消</el-button>
        <el-button type="primary" @click="handleFollowSubmit"><el-icon><Check /></el-icon>确 定</el-button>
      </template>
    </el-dialog>

    
    <!-- 在 template 中添加分配对话框 -->
    <el-dialog
      title="分配线索"
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
        <el-form-item label="请选择人员" prop="userId" required>
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
        <el-button @click="assignDialog.visible = false"><el-icon><Close /></el-icon>取 消</el-button>
        <el-button type="primary" @click="handleAssignSubmit"><el-icon><Check /></el-icon>确 定</el-button>
      </template>
    </el-dialog>


    <!-- 在其他对话框后面添加伪线索对话框 -->
    <el-dialog
      title="伪线索上报"
      v-model="fakeDialog.visible"
      width="500px"
      @close="handleFakeDialogClose"
    >
      <el-form
        ref="fakeFormRef"
        :model="fakeForm"
        :rules="fakeRules"
        label-width="100px"
      >
        <el-form-item label="原因" prop="reason" required>
          <el-select v-model="fakeForm.reason" placeholder="请选择原因" style="width: 100%">
            <el-option label="空号" :value="1" />
            <el-option label="停机" :value="2" />
            <el-option label="竞品" :value="3" />
            <el-option label="无法联系" :value="4" />
            <el-option label="其他" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input
            v-model="fakeForm.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注说明"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="fakeDialog.visible = false"><el-icon><Close /></el-icon>取 消</el-button>
        <el-button type="primary" @click="handleFakeSubmit"><el-icon><Check /></el-icon>确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
// 1. 修改导入部分，添加 convertToBusiness API
import { getCluePage, addClue, getClueById, updateClue, assignClue, reportFakeClue, convertToBusiness } from '@/api/clue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 获取当前登录用户信息
const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

// 控制按钮显示的函数
const canShowButtons = (row) => {
  const isAdmin = userInfo.value.roleLabel === 'admin'
  const isClueOperator = userInfo.value.roleLabel === 'clue_operator'
  const isOwner = userInfo.value.id === row.userId

  return {
    assign: row.status === 1 && isAdmin,
    follow: (row.status === 2 || row.status === 3) && isClueOperator && isOwner,
    fake: (row.status === 2 || row.status === 3) && isClueOperator && isOwner,
    convert: (row.status === 2 || row.status === 3) && isClueOperator && isOwner
  }
}

import { getActivityList, getActivityListByType } from '@/api/activity'
import { Search, Plus, Edit, Refresh } from '@element-plus/icons-vue'
import { UserFilled, Warning, Promotion } from '@element-plus/icons-vue'
// 分配线索
// 导入部门和用户相关的 API
import { getUserListByRoleLabel } from '@/api/user'

// 2. 实现转商机方法
const handleConvert = (row) => {
  ElMessageBox.confirm('确认转为商机？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'success'
  }).then(async () => {
    try {
      await convertToBusiness(row.id)
      ElMessage.success('转换成功')
      getList() // 刷新列表
    } catch (error) {
      console.error(error)
    }
  }).catch(() => {
    ElMessage.warning('已取消')
  })
}



// 查询参数
const queryParams = ref({
  clueId: '',
  phone: '',
  status: '',
  channel: '',
  assignName: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)

// 获取状态标签
const getStatusLabel = (status) => {
  const statusMap = {
    1: '待分配',
    2: '待跟进',
    3: '跟进中',
    4: '伪线索',
    5: '转商机'
  }
  return statusMap[status] || ''
}

// 添加对话框
const addDialog = ref({
  visible: false
})

// 活动列表数据
const activityList = ref([])

// 获取活动列表
const getActivities = async (type) => {
  try {
    const res = await getActivityListByType(type)
    activityList.value = res
  } catch (error) {
    console.error(error)
    activityList.value = []
  }
}

// 监听渠道来源变化
const handleChannelChange = (value) => {
  addForm.value.activityId = '' // 清空已选择的活动
  if (value) {
    getActivities(value)
  } else {
    activityList.value = []
  }
}

// 添加表单
const addForm = ref({
  phone: '',
  channel: '',
  activityId: '', // 新增活动ID字段
  name: '',
  gender: '',
  age: '',
  wechat: '',
  qq: ''
})

// 初始化
onMounted(() => {
  getList()
})

// 添加表单校验规则
const addRules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  channel: [
    { required: true, message: '请选择渠道来源', trigger: 'change' }
  ],
  name: [
    { min: 1, max: 10, message: '客户姓名长度在1-10个字符之间', trigger: 'blur' }
  ],
  age: [
    { pattern: /^[1-9]\d*$/, message: '请输入大于0的整数', trigger: 'blur' }
  ],
  wechat: [
    { pattern: /^[a-zA-Z][a-zA-Z\d_-]{5,19}$/, message: '请输入正确的微信号', trigger: 'blur' }
  ],
  qq: [
    { pattern: /^[1-9][0-9]{4,10}$/, message: '请输入正确的QQ号', trigger: 'blur' }
  ]
}

// 跟进对话框
const followDialog = ref({
  visible: false
})

// 跟进表单
const followForm = ref({
  id: '',
  phone: '',
  channel: '',
  activityId: '',
  name: '',
  gender: '',
  age: '',
  wechat: '',
  qq: '',
  userId: '',
  status: 2,
  subject: '',
  level: '',
  nextTime: '',
  record: ''
})

// 跟进记录列表
const trackRecords = ref([])

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

// 获取意向等级标签
const getLevelLabel = (level) => {
  const levelMap = {
    1: '近期学习',
    2: '打算学（考虑中）',
    3: '进行了解',
    4: '打酱油'
  }
  return levelMap[level] || ''
}


// 跟进按钮点击事件
const handleFollow = async (row) => {
  const res = await getClueById(row.id)
  // 回显表单数据，移除清空 nextTime 的操作
  followForm.value = {
    ...res,
    record: '' // 只清空跟进记录，保留其他字段值
  }
  // 设置跟进记录列表
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
    activityId: '',
    name: '',
    gender: '',
    age: '',
    wechat: '',
    qq: '',
    userId: '',
    status: 2,
    subject: '',
    level: '',
    nextTime: '',
    record: ''
  }
  trackRecords.value = []
  followFormRef.value?.resetFields()
}

// 跟进表单校验规则
const followRules = {
  subject: [
    { required: true, message: '请选择意向学科', trigger: 'change' }
  ],
  level: [
    { required: true, message: '请选择意向等级', trigger: 'change' }
  ],
  nextTime: [
    { required: true, message: '请选择下次跟进时间', trigger: 'change' }
  ],
  record: [
    { required: true, message: '请输入跟进记录', trigger: 'blur' }
  ]
}

// 表单ref
const addFormRef = ref(null)
const followFormRef = ref(null)
const assignFormRef = ref(null)  // 添加这一行

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getCluePage(queryParams.value)
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
    clueId: '',
    phone: '',
    status: '',
    channel: '',
    assignName: '',
    page: 1,
    pageSize: 10
  }
  getList()
}

// 新增按钮点击事件
const handleAdd = () => {
  addDialog.value.visible = true
}

// 添加对话框关闭事件
const handleAddDialogClose = () => {
  addFormRef.value?.resetFields()
}

// 添加表单提交
const handleAddSubmit = async () => {
  await addFormRef.value.validate()
  await addClue(addForm.value)
  ElMessage.success('添加成功')
  addDialog.value.visible = false
  getList()
}

// 跟进按钮点击事件
// const handleFollow = async (row) => {
//   const res = await getClueById(row.id)
//   followForm.value = {
//     id: res.id,
//     subject: res.subject,
//     level: res.level,
//     nextTime: '',
//     record: ''
//   }
//   followDialog.value.visible = true
// }

// // 跟进对话框关闭事件
// const handleFollowDialogClose = () => {
//   followFormRef.value?.resetFields()
// }

// 跟进表单提交
const handleFollowSubmit = async () => {
  await followFormRef.value.validate()
  await updateClue(followForm.value)
  ElMessage.success('跟进成功')
  followDialog.value.visible = false
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

// 表格多选框选中数据
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}


// 分配对话框数据
const assignDialog = ref({
  visible: false
})

// 分配表单数据
const assignForm = ref({
  clueId: '', // 线索ID
  deptId: '', // 部门ID
  userId: ''  // 用户ID
})

// 分配表单校验规则
const assignRules = {
  deptId: [
    { required: true, message: '请选择部门', trigger: 'change' }
  ],
  userId: [
    { required: true, message: '请选择人员', trigger: 'change' }
  ]
}

// 用户列表
const userList = ref([])

// 根据部门ID获取用户列表
const getUsersList = async (deptId) => {
  try {
    const res = await getUserListByRoleLabel('clue_operator')
    userList.value = res
  } catch (error) {
    console.error(error)
  }
}

// 分配按钮点击事件
const handleAssign = (row) => {
  assignForm.value.clueId = row.id
  assignDialog.value.visible = true

  getUsersList(); //获取分配人员列表
}

// 分配对话框关闭事件
const handleAssignDialogClose = () => {
  assignForm.value = {
    clueId: '',
    deptId: '',
    userId: ''
  }
  userList.value = []
  assignFormRef.value?.resetFields()
}

// 分配表单提交
const handleAssignSubmit = async () => {
  await assignFormRef.value.validate()
  try {
    await assignClue(assignForm.value.clueId, assignForm.value.userId)
    ElMessage.success('分配成功')
    assignDialog.value.visible = false
    getList() // 刷新列表
  } catch (error) {
    console.error(error)
  }
}

const fakeDialog = ref({
  visible: false
})


// 伪线索表单数据
const fakeForm = ref({
  id: '',
  reason: '',
  remark: ''
})

// 伪线索表单校验规则
const fakeRules = {
  reason: [
    { required: true, message: '请选择原因', trigger: 'change' }
  ]
}

// 伪线索表单ref
const fakeFormRef = ref(null)

// 标记为伪线索
const handleFake = (row) => {
  fakeForm.value.id = row.id
  fakeDialog.value.visible = true
}

// 伪线索对话框关闭事件
const handleFakeDialogClose = () => {
  fakeForm.value = {
    id: '',
    reason: '',
    remark: ''
  }
  fakeFormRef.value?.resetFields()
}

// 伪线索表单提交
const handleFakeSubmit = async () => {
  await fakeFormRef.value.validate()
  try {
    await reportFakeClue(fakeForm.value.id, {
      reason: fakeForm.value.reason,
      remark: fakeForm.value.remark
    })
    ElMessage.success('标记成功')
    fakeDialog.value.visible = false
    getList() // 刷新列表
  } catch (error) {
    console.error(error)
  }
}

// 初始化
onMounted(() => {
  getList()
})
</script>

<style scoped>
.clue {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.search-form {
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: space-between;
}

.search-form :deep(.el-form-item) {
  margin: 0 10px 0 0;
  width: 240px;
  min-width: auto;
}

.search-form :deep(.el-input),
.search-form :deep(.el-select) {
  width: 100%;
}

.search-buttons {
  margin-right: 0 !important;
  white-space: nowrap;
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

.track-records {
  margin-top: 20px;
}

.track-records .title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  color: var(--el-text-color-primary);
}

:deep(.el-dialog__body) {
  padding: 20px;
}

.track-records :deep(.el-table) {
  font-size: 13px;
}

.track-records :deep(.el-table__cell) {
  padding: 8px 0;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>