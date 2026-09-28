<template>
  <div class="login-container">
    <div class="login-box">
      <div class="system-title">轻客管家</div>
      <el-form :model="loginForm" >
        <el-form-item label="用户名">
          <el-input v-model="loginForm.username" placeholder="请输入用户名">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="密&nbsp;&nbsp;&nbsp;&nbsp;码">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-btn" @click="handleLogin" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import {loginApi} from '@/api/login'

const router = useRouter()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  const res = await loginApi(loginForm)
  
  localStorage.setItem('token', res.token)
  localStorage.setItem('userInfo', JSON.stringify({id: res.id, username: res.username, name: res.name, image: res.image, roleLabel: res.roleLabel}))

  ElMessage.success('登录成功')
  router.push('/home')
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url('@/assets/bg.jpg') center center / cover no-repeat;
  position: relative;
  overflow: hidden;
}

.login-container::before {
  content: '';
  position: absolute;
  width: 200%;
  height: 200%;
  top: -50%;
  left: -50%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0) 60%);
  animation: rotate 30s linear infinite;
}

.system-title {
  font-family: 华文隶书;
  font-size: 50px;
  text-align: center;
  margin-bottom: 30px;
}


@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.login-box {
  width: 450px;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  position: relative;
  z-index: 1;
}


.el-input {
  margin-bottom: 20px;
  width: 100%;
}

.el-form-item:last-child {
  margin-bottom: 0;
}
.el-button {
  width: 100%;
  height: 40px;
  font-size: 16px;
  background: linear-gradient(90deg, #1890ff, #36cfc9);
  border: none;
  transition: transform 0.3s ease;
}

.el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.login-btn {
  width: 100%;
  margin-top: 10px;
}

</style>