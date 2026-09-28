<template>
  <div class="department">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
      <el-form-item label="部门名称">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入部门名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 100%">
          <el-option label="正常" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
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

    <!-- 操作按钮区域 -->
    <div class="operation-area">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>添加部门
      </el-button>
    </div>

    <!-- 表格区域 -->
    <el-table 
      :data="tableData" 
      style="width: 100%" 
      v-loading="loading"
      border
    >
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="350" align="center" />
      <el-table-column prop="status" label="状态" width="350" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="350" align="center" />
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

    <!-- 添加/修改部门对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="500px"
      @close="handleDialogClose"
      class="dept-dialog"
    >
      <el-form
        ref="deptFormRef"
        :model="deptForm"
        :rules="rules"
        label-width="80px"
        class="dept-form"
      >
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="deptForm.name" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="deptForm.status" placeholder="请选择" style="width: 100%">
            <el-option label="正常" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
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
import { getDeptPage, addDept, updateDept, deleteDept, getDeptById } from '@/api/dept'
import { Search, Delete, Edit, Plus, Refresh } from '@element-plus/icons-vue'  // 添加图标导入

// 查询参数
const queryParams = ref({
  name: '',
  status: '',
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
const deptForm = ref({
  id: undefined,
  name: '',
  status: 1
})

// 表单ref
const deptFormRef = ref(null)

// 表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getDeptPage(queryParams.value)
    tableData.value = res.rows
    total.value = res.total
  } catch (error) {
    console.error(error)
  }
  loading.value = false
}

// 查询按钮
const handleQuery = () => {
  queryParams.value.page = 1
  getList()
}

// 重置查询
const resetQuery = () => {
  queryParams.value.name = ''
  queryParams.value.status = ''
  handleQuery()
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

// 添加部门
const handleAdd = () => {
  dialog.value.type = 'add'
  dialog.value.title = '添加部门'
  dialog.value.visible = true
  deptForm.value = {
    id: undefined,
    name: '',
    status: 1
  }
}

// 修改部门
const handleEdit = async (row) => {
  try {
    const res = await getDeptById(row.id)
    dialog.value.type = 'edit'
    dialog.value.title = '修改部门'
    dialog.value.visible = true
    deptForm.value = { ...res }  // 使用后端返回的最新数据
  } catch (error) {
    console.error('获取部门数据失败:', error)
    ElMessage.error('获取部门数据失败')
  }
}

// 删除部门
const handleDelete = (row) => {
  ElMessageBox.confirm('确认要删除该部门吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteDept(row.id)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 提交表单
const handleSubmit = async () => {
  if (!deptFormRef.value) return
  await deptFormRef.value.validate(async (valid) => {
    if (valid) {
      if (dialog.value.type === 'add') {
        await addDept(deptForm.value)
      } else {
        await updateDept(deptForm.value)
      }
      ElMessage.success(dialog.value.type === 'add' ? '添加成功' : '修改成功')
      dialog.value.visible = false
      getList()
    }
  })
}

// 对话框关闭
const handleDialogClose = () => {
  if (deptFormRef.value) {
    deptFormRef.value.resetFields()
  }
}

// 初始化
onMounted(() => {
  getList()
})
</script>

<style scoped>
.department {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px; /* 添加圆角 */
}

.operation-area {
  margin: 20px 0;
}

.search-form {
  display: flex;
  align-items: center;
}

.search-form :deep(.el-form-item) {
  margin-right: 20px;
  min-width: 280px;
}

.search-buttons {
  margin-left: auto !important;
  min-width: auto !important;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.divider {
  height: 0.5px;
  background-color: #dcdfe6;
  margin: 10px 0;
  width: 100%;
}

:deep(.dept-dialog .el-dialog__body) {
  padding-top: 30px;
}

.dept-form {
  width: 80%;
  margin: 0 auto;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}
</style>