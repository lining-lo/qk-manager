<template>
  <div class="business-pool">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" class="search-form">
      <el-form-item label="商机ID">
        <el-input v-model="queryParams.businessId" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="客户姓名">
        <el-input v-model="queryParams.name" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="queryParams.phone" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="意向学科">
        <el-select v-model="queryParams.subject" placeholder="请选择" clearable>
          <el-option label="AI智能应用开发(java)" :value="1" />
          <el-option label="AI大模型开发(python)" :value="2" />
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

    <!-- 表格区域 -->
    <el-table :data="tableData" style="width: 100%" v-loading="loading" border>
      <el-table-column prop="id" label="商机ID" width="80" align="center" />
      <el-table-column prop="name" label="客户姓名" width="90" align="center" />
      <el-table-column prop="phone" label="手机号" width="140" align="center" />
      <el-table-column prop="subject" label="意向学科" width="180" align="center">
        <template #default="{ row }">
          {{ getSubjectLabel(row.subject) }}
        </template>
      </el-table-column>
      <el-table-column prop="channel" label="渠道来源" width="140" align="center">
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
      <el-table-column prop="createTime" label="创建时间" width="200" align="center" />
      <el-table-column prop="updateTime" label="更新时间" width="200" align="center" />
      <el-table-column label="操作" min-width="100" align="center">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleView(row)">
            <el-icon><View /></el-icon>查看
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

    <el-dialog
        :title="`查看商机(商机ID: ${viewForm.id})`"
        v-model="viewDialog.visible"
        width="800px"
      >
        <el-form
          ref="viewFormRef"
          :model="viewForm"
          label-width="90px"
          disabled
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="viewForm.phone" placeholder="请输入手机号" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="客户姓名" prop="name">
                <el-input v-model="viewForm.name" placeholder="请输入客户姓名" />
              </el-form-item>
            </el-col>
          </el-row>
      
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="性别" prop="gender">
                <el-select v-model="viewForm.gender" placeholder="请选择" style="width: 100%">
                  <el-option label="男" :value="1" />
                  <el-option label="女" :value="2" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄" prop="age">
                <el-input v-model="viewForm.age" placeholder="请输入年龄" />
              </el-form-item>
            </el-col>
          </el-row>
      
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="微信号" prop="wechat">
                <el-input v-model="viewForm.wechat" placeholder="请输入微信号" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="QQ" prop="qq">
                <el-input v-model="viewForm.qq" placeholder="请输入QQ" />
              </el-form-item>
            </el-col>
          </el-row>
      
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="意向学科" prop="subject">
                <el-select v-model="viewForm.subject" placeholder="请选择" style="width: 100%">
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
              <el-form-item label="渠道来源" prop="channel">
                <el-select v-model="viewForm.channel" placeholder="请选择" style="width: 100%">
                  <el-option label="线上活动" :value="1" />
                  <el-option label="推广介绍" :value="2" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
      
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="学历" prop="degree">
                <el-select v-model="viewForm.degree" placeholder="请选择" style="width: 100%">
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
                <el-select v-model="viewForm.jobStatus" placeholder="请选择" style="width: 100%">
                  <el-option label="在职" :value="1" />
                  <el-option label="离职" :value="2" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
      
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="备注">
                <el-input
                  v-model="viewForm.remark"
                  type="textarea"
                  :rows="2"
                  placeholder="请输入备注信息"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      
        <!-- 跟进历史记录表格 -->
        <div class="track-records">
          <div class="title">跟进历史</div>
          <el-table :data="trackRecords" style="width: 100%; font-size: 12px;" border size="small">
            <el-table-column prop="createTime" label="跟进时间" align="center" width="180" />
            <el-table-column prop="keyItems" label="沟通重点" align="center" width="150" />
            <el-table-column prop="trackStatus" label="跟进状态" align="center" width="250">
              <template #default="{ row }">
                {{ getTrackStatusLabel(row.trackStatus) }} - 下次跟进时间: {{ row.nextTime }}
              </template>
            </el-table-column>
            <el-table-column prop="record" label="跟进记录" align="center" show-overflow-tooltip>
              <template #default="{ row }">
                <el-tooltip class="box-item" effect="dark" :content="row.record" placement="top" :show-after="100">
                  <span>{{ row.record?.slice(0, 10) + (row.record?.length > 10 ? '...' : '') }}</span>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column prop="assignName" label="跟进人" align="center" width="100" />
          </el-table>
        </div>
      
        <template #footer>
          <el-button @click="viewDialog.visible = false">关 闭</el-button>
        </template>
      </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBusinessPoolPage, getBusinessById } from '@/api/business'
import { Search, Refresh, View } from '@element-plus/icons-vue'

// 查询参数
const queryParams = ref({
  businessId: '',
  name: '',
  phone: '',
  subject: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getBusinessPoolPage(queryParams.value)
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
    name: '',
    phone: '',
    subject: '',
    page: 1,
    pageSize: 10
  }
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

// 获取意向学科标签
const getSubjectLabel = (subject) => {
  const subjectMap = {
    1: 'AI智能应用开发(java)',
    2: 'AI大模型开发(python)',
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

// 初始化
onMounted(() => {
  getList()
})

// 在 script 部分添加以下代码
const viewDialog = ref({
  visible: false
})

const viewForm = ref({})
const trackRecords = ref([])

// 查看按钮点击事件
const handleView = async (row) => {
  const res = await getBusinessById(row.id)
  viewForm.value = res
  trackRecords.value = res.trackRecords || []
  viewDialog.value.visible = true
}

// 获取跟进状态标签
const getTrackStatusLabel = (status) => {
  const statusMap = {
    1: '接通',
    2: '拒绝',
    3: '无人接听'
  }
  return statusMap[status] || ''
}
</script>

<style scoped>
.business-pool {
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
  margin: 10px 0;
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
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>