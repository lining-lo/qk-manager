<template>
  <div class="clue-pool">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" class="search-form">
      <el-form-item label="线索ID">
        <el-input v-model="queryParams.clueId" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="queryParams.phone" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="渠道来源">
        <el-select v-model="queryParams.channel" placeholder="请选择" clearable>
          <el-option label="线上活动" :value="1" />
          <el-option label="推广介绍" :value="2" />
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
    <el-table
      :data="tableData"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column prop="id" label="线索ID" width="80" align="center" />
      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column prop="name" label="客户姓名" width="120" align="center" />
      <el-table-column prop="gender" label="性别" width="120" align="center">
        <template #default="{ row }">
          {{ row.gender === 1? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="120" align="center" />
      <el-table-column label="活动信息" width="300" align="center">
        <template #default="{ row }">
          {{ row.channel === 1 ? '线上活动' : '推广介绍' }} - {{ row.activityName }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="200" align="center" />
      <el-table-column prop="updateTime" label="更新时间" width="200" align="center" />
      <!-- 表格的操作列修改 -->
      <el-table-column label="操作" min-width="150" align="center">
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

    
    <!-- 添加查看对话框 -->
    <el-dialog
        :title="`查看线索(线索ID: ${viewForm.id})`"
        v-model="viewDialog.visible"
        width="800px"
      >
        <el-form
          ref="viewFormRef"
          :model="viewForm"
          label-width="100px"
          disabled
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="viewForm.phone" placeholder="请输入手机号" />
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
              <el-form-item label="活动信息" prop="activityId">
                <el-input v-model="viewForm.activityName" placeholder="活动信息" />
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
                  <el-option label="Java" :value="1" />
                  <el-option label="前端" :value="2" />
                  <el-option label="Python" :value="3" />
                  <el-option label="大数据" :value="4" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="意向等级" prop="level">
                <el-select v-model="viewForm.level" placeholder="请选择" style="width: 100%">
                  <el-option label="近期学习" :value="1" />
                  <el-option label="打算学（考虑中）" :value="2" />
                  <el-option label="进行了解" :value="3" />
                  <el-option label="打酱油" :value="4" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        

        <!-- 跟进历史记录表格 -->
        <div class="track-records">
          <div class="title">跟进历史</div>
          <el-table :data="viewForm.trackRecords" style="width: 100%; font-size: 11px;" border size="small">
            <el-table-column prop="level" label="意向等级" align="center" width="120">
              <template #default="{ row }">
                {{ getLevelLabel(row.level) }}
              </template>
            </el-table-column>
            <el-table-column prop="subject" label="意向学科" align="center" width="80">
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
          <el-button @click="viewDialog.visible = false">关 闭</el-button>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCluePoolPage } from '@/api/clue'
import { Search, Refresh, View, TopRight } from '@element-plus/icons-vue'
import { getClueById } from '@/api/clue'

// 查询参数
const queryParams = ref({
  clueId: '',
  phone: '',
  channel: '',
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
    const res = await getCluePoolPage(queryParams.value)
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
    channel: '',
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

// 初始化
onMounted(() => {
  getList()
})

// 查看对话框
const viewDialog = ref({
  visible: false
})

// 查看表单数据
const viewForm = ref({
  id: '',
  phone: '',
  channel: '',
  activityId: '',
  activityName: '',
  name: '',
  gender: '',
  age: '',
  wechat: '',
  qq: '',
  subject: '',
  level: '',
  trackRecords: []
})

// 查看按钮点击事件
const handleView = async (row) => {
  const res = await getClueById(row.id)
  viewForm.value = res
  viewDialog.value.visible = true
}

// 获取意向学科标签
const getSubjectLabel = (subject) => {
  const subjectMap = {
    //Java、前端、人工智能、大数据、Python、测试、新媒体、产品经理、UI设计
    1: 'Java',
    2: '前端',
    3: '人工智能',
    4: '大数据',
    5: 'Python',
    6: '测试',
    7: '新媒体',
    8: '产品经理',
    9: 'UI设计'
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
</script>

<style scoped>
.clue-pool {
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
  width: 240px;
}

.search-buttons {
  margin-right: 0 !important;
}

.divider {
  height: 1px;
  background-color: var(--el-border-color-light);
  margin-top: 10px;
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.track-records .title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  color: var(--el-text-color-primary);
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>