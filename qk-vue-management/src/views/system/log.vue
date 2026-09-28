<template>
  <div class="log">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
      <el-form-item label="操作模块">
        <el-input
          v-model="queryParams.module"
          placeholder="请输入操作模块"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作类型">
        <el-input
          v-model="queryParams.operate"
          placeholder="请输入操作类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人">
        <el-input
          v-model="queryParams.operateUserName"
          placeholder="请输入操作人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item class="search-buttons">
        <el-button type="primary" @click="handleQuery">
          <el-icon><Search /></el-icon>搜索
        </el-button>
        <el-button @click="resetQuery">
          <el-icon><Refresh /></el-icon>清空
        </el-button>
      </el-form-item>
    </el-form>

    <div class="divider"></div>

    <!-- 表格区域 -->
    <el-table :data="tableData" style="width: 100%" size="small" v-loading="loading" border>
      <el-table-column type="index" label="序号" width="60" align="center" />
      <el-table-column prop="operateUserName" label="操作人" width="80" align="center" />
      <el-table-column prop="operateTime" label="操作时间" width="160" align="center" />
      <el-table-column prop="module" label="操作模块" width="100" align="center" />
      <el-table-column prop="operate" label="操作类型" width="100" align="center" />
      <el-table-column prop="className" label="类名" width="240" align="center" show-overflow-tooltip />
      <el-table-column prop="methodName" label="方法名" width="120" align="center" />
      <el-table-column prop="methodParams" label="请求参数" min-width="200" align="center" show-overflow-tooltip />
      <el-table-column prop="returnValue" label="返回值" min-width="200" align="center" show-overflow-tooltip />
      <el-table-column prop="costTime" label="耗时(ms)" width="80" align="center" />
    </el-table>

    <!-- 分页区域 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="queryParams.page"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getLogPage } from '@/api/log'
import { Search, Refresh } from '@element-plus/icons-vue'

// 查询参数
const queryParams = ref({
  module: '',
  operate: '',
  operateUserName: '',
  page: 1,
  pageSize: 20
})

// 重置查询
const resetQuery = () => {
  queryParams.value = {
    module: '',
    operate: '',
    operateUserName: '',
    page: 1,
    pageSize: 20
  }
  handleQuery()
}

// 查询按钮
const handleQuery = () => {
  queryParams.value.page = 1
  getList()
}

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)

// 查询列表
const getList = async () => {
  loading.value = true
  try {
    const res = await getLogPage(queryParams.value)
    tableData.value = res.rows
    total.value = res.total
  } catch (error) {
    console.error(error)
  }
  loading.value = false
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
.log {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.search-form {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.search-form :deep(.el-form-item) {
  margin-right: 20px;
  min-width: 280px;
}

.search-buttons {
  margin-left: auto !important;
  min-width: auto !important;
}

.divider {
  height: 0.5px;
  background-color: #dcdfe6;
  margin: 10px 0;
  width: 100%;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>