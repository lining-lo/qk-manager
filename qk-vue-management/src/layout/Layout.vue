<template>
  <el-container class="layout-container">
    <!-- 头部 -->
    <el-header height="60px">
      <div class="header-left">
        <img src="/src/assets/logo.png" width="45px"><span class="system-title">轻客管家</span>
      </div>
      <div class="header-right">
        <el-avatar :size="30" :src="userAvatar" />
        <span class="username">{{ username }}</span> <span style="color: #fff;">|</span>
        <el-link type="primary" class="logout" :underline="false" @click="handleLogout"><el-icon><SwitchButton /></el-icon> &nbsp;退出登录</el-link>
      </div>
    </el-header>
    
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu :default-active="activeMenu" router unique-opened>
          <el-menu-item index="/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>

          <el-sub-menu index="/clue">
            <template #title>
              <el-icon><Service /></el-icon>
              <span>线索管理</span>
            </template>
            <el-menu-item index="/clue/list">
              <el-icon><Memo /></el-icon>
              <span>线索列表</span>
            </el-menu-item>
            <el-menu-item index="/clue/pool">
              <el-icon><Box /></el-icon>
              <span>线索池</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/business">
            <template #title>
              <el-icon><Compass /></el-icon>
              <span>商机管理</span>
            </template>
            <el-menu-item index="/business/list">
              <el-icon><Memo /></el-icon>
              <span>商机列表</span>
            </el-menu-item>
            <el-menu-item index="/business/pool">
              <el-icon><Box /></el-icon>
              <span>公海池</span>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/customer">
            <el-icon><User /></el-icon>
            <span>客户管理</span>
          </el-menu-item>

          <el-sub-menu index="/resource">
            <template #title>
              <el-icon><Collection /></el-icon>
              <span>资源管理</span>
            </template>
            <el-menu-item index="/resource/course">
              <el-icon><Reading /></el-icon>
              <span>课程管理</span>
            </el-menu-item>
            <el-menu-item index="/resource/activity">
              <el-icon><Calendar /></el-icon>
              <span>活动管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/system">
            <template #title>
              <el-icon><Odometer /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/system/department">
              <el-icon><OfficeBuilding /></el-icon>
              <span>部门管理</span>
            </el-menu-item>
            <el-menu-item index="/system/role">
              <el-icon><Position /></el-icon>
              <span>角色管理</span>
            </el-menu-item>
            <el-menu-item index="/system/user">
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/system/log">
              <el-icon><Tickets /></el-icon>
              <span>系统日志</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!-- 主要内容区 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const activeMenu = ref(route.path)
const userAvatar = ref('')
const username = ref('')

// 监听路由变化，更新activeMenu
watch(() => route.path, (newPath) => {
  activeMenu.value = newPath
})

onMounted(() => {
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
  userAvatar.value = userInfo.image || ''
  username.value = userInfo.name || ''
})

const handleLogout = () => {
  ElMessageBox.confirm('确认要退出登录吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    router.push('/login')
    ElMessage.success('退出登录成功')
  }).catch(() => {})
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-header {
  background: linear-gradient(90deg, #1890ff 0%, #36cfc9 100%);
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.system-title {
  font-family: 华文隶书;
  font-size: 40px;
  margin: 0;
  color: #fff;  /* 由于背景色变深，文字改为白色 */
}

.username {
  font-size: 16px;
  color: #fff;  /* 用户名也改为白色 */
}

.logout {
  font-size: 16px;
  color: #fff !important;  /* 退出登录链接也改为白色 */
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.username {
  font-size: 16px;
}

.logout {
  font-size: 16px;
}

.el-aside {
  background-color: #fff;
  border-right: 1px solid #dcdfe6;
}

.el-menu {
  border-right: none;
}

.el-main {
  background-color: #f5f7fa;
  padding: 20px;
}
</style>