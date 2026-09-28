<template>
  <div class="user">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
      <el-form-item label="姓名">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="queryParams.deptId" placeholder="请选择" clearable>
          <el-option
            v-for="dept in deptOptions"
            :key="dept.id"
            :label="dept.name"
            :value="dept.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
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
        <el-icon><Plus /></el-icon>添加用户
      </el-button>
      <el-button type="danger" @click="handleBatchDelete" :disabled="!selectedIds.length">
        <el-icon><Delete /></el-icon>批量删除
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
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="姓名" width="150" align="center" />
      <el-table-column prop="username" label="用户名" width="150" align="center" />
      <el-table-column prop="image" label="头像" width="120" align="center">
        <template #default="{ row }">
          <el-avatar :size="32" :src="row.image" />
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="部门" width="150" align="center" />
      <el-table-column prop="roleName" label="角色" width="150" align="center" />
      <el-table-column prop="phone" label="手机号" width="150" align="center" />
      <el-table-column prop="status" label="状态" width="100" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="220" align="center" />
      <el-table-column label="操作"  align="center">
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

    <!-- 添加/修改用户对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="800px"
      @close="handleDialogClose"
      class="user-dialog"
    >
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="rules"
        label-width="80px"
        class="user-form"
      >
        <div class="form-row">
          <el-form-item label="用户名" prop="username" class="form-item">
            <el-input v-model="userForm.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="姓名" prop="name" class="form-item">
            <el-input v-model="userForm.name" placeholder="请输入姓名" />
          </el-form-item>
        </div>
        
        <div class="form-row">
          <el-form-item label="手机号" prop="phone" class="form-item">
            <el-input v-model="userForm.phone" placeholder="请输入手机号" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email" class="form-item">
            <el-input v-model="userForm.email" placeholder="请输入邮箱" />
          </el-form-item>
        </div>

        <div class="form-row">
          <el-form-item label="性别" prop="gender" class="form-item">
            <el-select v-model="userForm.gender" placeholder="请选择" style="width: 100%">
              <el-option label="男" :value="1" />
              <el-option label="女" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status" class="form-item">
            <el-select v-model="userForm.status" placeholder="请选择" style="width: 100%">
              <el-option label="正常" :value="1" />
              <el-option label="停用" :value="0" />
            </el-select>
          </el-form-item>
        </div>

        <div class="form-row">
          <el-form-item label="部门" prop="deptId" class="form-item">
            <el-select v-model="userForm.deptId" placeholder="请选择" style="width: 100%">
              <el-option
                v-for="dept in deptOptions"
                :key="dept.id"
                :label="dept.name"
                :value="dept.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="角色" prop="roleId" class="form-item">
            <el-select v-model="userForm.roleId" placeholder="请选择" style="width: 100%">
              <el-option
                v-for="role in roleOptions"
                :key="role.id"
                :label="role.name"
                :value="role.id"
              />
            </el-select>
          </el-form-item>
        </div>

        <div class="form-row">
          <el-form-item label="头像" prop="image" class="form-item-full">
            <div class="avatar-wrapper">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :show-file-list="false"
                name="image"
                :headers="{
                  'token': token
                }"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="userForm.image" :src="userForm.image" class="avatar" />
                <div v-else class="upload-placeholder">
                  <el-icon class="avatar-uploader-icon"><Plus /></el-icon>
                </div>
                <div class="upload-tip">图片上传，支持扩展名：.png / .jpg / .jpeg<br/>大小限制：2M</div>
              </el-upload>
            </div>
          </el-form-item>
        </div>

        <div class="form-row">
          <el-form-item label="备注" prop="remark" class="form-item-full">
            <el-input 
              v-model="userForm.remark" 
              type="textarea" 
              placeholder="请输入用户的备注信息，最多200字"
              :rows="3"
              maxlength="200"
              show-word-limit
            />
          </el-form-item>
        </div>
      </el-form>
      <template #footer>
        <el-button @click="dialog.visible = false">
          <el-icon><Close /></el-icon>取 消
        </el-button>
        <el-button type="primary" @click="handleSubmit">
          <el-icon><Check /></el-icon>确 定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserPage, addUser, updateUser, deleteUser, getUserById } from '@/api/user'
import { getDeptList } from '@/api/dept'
import { getRoleList } from '@/api/role'
import { Search, Delete, Edit, Plus, Refresh, Close, Check } from '@element-plus/icons-vue'

// 查询参数
const queryParams = ref({
  name: '',
  phone: '',
  deptId: '',
  status: '',
  page: 1,
  pageSize: 10
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)
const selectedIds = ref([])

// 部门选项
const deptOptions = ref([])

// 角色选项
const roleOptions = ref([])

// 对话框相关
const dialog = ref({
  visible: false,
  title: '',
  type: 'add' // add or edit
})

// 表单对象
const userForm = ref({
  id: undefined,
  username: '',
  password: '',
  name: '',
  phone: '',
  email: '',
  gender: 1,
  status: 1,
  deptId: undefined,
  roleId: undefined,
  image: '',
  remark: ''
})

// 表单ref
const userFormRef = ref(null)

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 查询数据
const getList = async () => {
  loading.value = true
  try {
    const res = await getUserPage(queryParams.value)
    tableData.value = res.rows
    total.value = res.total
  } catch (error) {
    console.error(error)
  }
  loading.value = false
}

// 获取部门列表
const getDepts = async () => {
  try {
    const res = await getDeptList()
    deptOptions.value = res
  } catch (error) {
    console.error(error)
  }
}

// 获取角色列表
const getRoles = async () => {
  try {
    const res = await getRoleList()
    roleOptions.value = res
  } catch (error) {
    console.error(error)
  }
}

// 查询按钮
const handleQuery = () => {
  queryParams.value.page = 1
  getList()
}

// 重置查询
const resetQuery = () => {
  queryParams.value = {
    name: '',
    phone: '',
    deptId: '',
    status: '',
    page: 1,
    pageSize: 10
  }
  handleQuery()
}

// 表格选择改变
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
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

// 添加用户
const handleAdd = () => {
  dialog.value.type = 'add'
  dialog.value.title = '添加用户'
  dialog.value.visible = true
  userForm.value = {
    id: undefined,
    username: '',
    password: '',
    name: '',
    phone: '',
    email: '',
    gender: 1,
    status: 1,
    deptId: undefined,
    roleId: undefined,
    image: '',
    remark: ''
  }
}

// 修改用户
const handleEdit = async (row) => {
  try {
    const res = await getUserById(row.id)
    dialog.value.type = 'edit'
    dialog.value.title = '修改用户'
    dialog.value.visible = true
    userForm.value = { ...res }
  } catch (error) {
    console.error('获取用户数据失败:', error)
    ElMessage.error('获取用户数据失败')
  }
}

// 删除用户
const handleDelete = (row) => {
  ElMessageBox.confirm('确认要删除该用户吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 批量删除
const handleBatchDelete = () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的用户')
    return
  }
  ElMessageBox.confirm('确认要删除选中的用户吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteUser(selectedIds.value.join(','))
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 提交表单
const handleSubmit = async () => {
  if (!userFormRef.value) return
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      if (dialog.value.type === 'add') {
        await addUser(userForm.value)
      } else {
        await updateUser(userForm.value)
      }
      ElMessage.success(dialog.value.type === 'add' ? '添加成功' : '修改成功')
      dialog.value.visible = false
      getList()
    }
  })
}

// 对话框关闭
const handleDialogClose = () => {
  if (userFormRef.value) {
    userFormRef.value.resetFields()
  }
}

// 头像上传成功
const handleAvatarSuccess = (res) => {
  userForm.value.image = res.data
}

// 头像上传前校验
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isPNG = file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG && !isPNG) {
    ElMessage.error('上传头像图片只能是 JPG 或 PNG 格式!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 2MB!')
    return false
  }
  return true
}

// 初始化
let token = ref('')

onMounted(() => {
  getList()
  getDepts()
  getRoles()
  token.value = localStorage.getItem('token')
})
</script>

<style scoped>
.user {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
  border-radius: 8px;
}

.operation-area {
  margin: 20px 0;
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

:deep(.user-dialog .el-dialog__body) {
  padding-top: 30px;
}

.user-form {
  width: 100%;
  padding: 0 20px;
}

.form-row {
  display: flex;
  justify-content: space-between;
}

.form-item {
  width: 48%;
}

.form-item-full {
  width: 100%;
}

.upload-tip {
  font-size: 12px;
  color: #999;
  margin-top: 8px;
  line-height: 1.5;
}

.avatar-uploader {
  text-align: left;
}

.avatar-uploader .avatar {
  width: 100px;
  height: 100px;
  border-radius: 4px;
  object-fit: cover;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 4px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  width: 100px;
  height: 100px;
}

.upload-placeholder {
  width: 100px;
  height: 100px;
  border: 1px dashed #dcdfe6;
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  position: relative;
}

.upload-tip {
  position: absolute;
  left: 120px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 12px;
  color: #999;
  line-height: 1.5;
  white-space: nowrap;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}
:deep(.el-button .el-icon) {
  margin-right: 4px;
}
/* 移除之前的 border-box 相关样式 */
</style>