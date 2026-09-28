<template>
  <div class="home">
    <!-- 欢迎语 -->
    <div class="welcome-text-custom">
      欢迎您，进入轻客管家系统
    </div>

    <!-- 数据概览卡片 -->
    <div class="data-overview">
      <el-row :gutter="20">
        <!-- 线索概览 -->
        <el-col :span="12">
          <div class="overview-section">
            <div class="section-header">线索概览</div>
            <div class="section-content">
              <div class="data-item">
                <div class="item-label">总线索数</div>
                <div class="item-value">{{ overviewData.clueTotal }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">待分配线索数</div>
                <div class="item-value">{{ overviewData.clueWaitAllot }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">待跟进线索数</div>
                <div class="item-value">{{ overviewData.clueWaitFollow }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">跟进中线索数</div>
                <div class="item-value">{{ overviewData.clueFollowing }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">伪线索数</div>
                <div class="item-value">{{ overviewData.clueFalse }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">转商机线索数</div>
                <div class="item-value">{{ overviewData.clueConvertBusiness }}</div>
              </div>
            </div>
          </div>
        </el-col>
        <!-- 商机概览 -->
        <el-col :span="12">
          <div class="overview-section">
            <div class="section-header">商机概览</div>
            <div class="section-content">
              <div class="data-item">
                <div class="item-label">总商机数</div>
                <div class="item-value">{{ overviewData.businessTotal }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">待分配商机数</div>
                <div class="item-value">{{ overviewData.businessWaitAllot }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">待跟进商机数</div>
                <div class="item-value">{{ overviewData.businessWaitFollow }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">跟进中商机数</div>
                <div class="item-value">{{ overviewData.businessFollowing }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">伪商机数</div>
                <div class="item-value">{{ overviewData.businessFalse }}</div>
              </div>
              <div class="data-item">
                <div class="item-label">转客户商机数</div>
                <div class="item-value">{{ overviewData.businessConvertCustomer }}</div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 系统信息 -->
    <!-- 快捷入口区域 -->
    <div class="quick-entry">
      <el-row :gutter="24">
        <el-col :span="4" v-for="(item, index) in quickEntries" :key="index">
          <div class="entry-item" @click="$router.push(item.path)" style="background: #e2f4fb">
            <div class="entry-icon">
              <el-icon><component :is="item.icon" /></el-icon>
            </div>
            <div class="entry-info">
              <div class="entry-title">{{ item.title }}</div>
              <div class="entry-desc">{{ item.desc }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
</div>
</template> 
    
<!-- 引入新的图标 -->
<script setup>
  import { ref, onMounted } from 'vue'
  import { getOverview } from '@/api/report'

  // 数据概览
  const overviewData = ref({
    clueTotal: 0,
    clueWaitAllot: 0,
    clueWaitFollow: 0,
    clueFollowing: 0,
    clueFalse: 0,
    clueConvertBusiness: 0,
    businessTotal: 0,
    businessWaitAllot: 0,
    businessWaitFollow: 0,
    businessFollowing: 0,
    businessFalse: 0,
    businessConvertCustomer: 0
  })

  // 获取数据概览
  const getOverviewData = async () => {
    try {
      const res = await getOverview()
      overviewData.value = res
    } catch (error) {
      console.error('获取数据概览失败:', error)
    }
  }

  // 初始化
  onMounted(() => {
    getOverviewData()
  })

  // 快捷入口数据
  const quickEntries = [
    {
      title: '课程管理',
      desc: '课程信息维护',
      icon: 'Reading',
      path: '/resource/course'
    },
    {
      title: '活动管理',
      desc: '营销活动管理',
      icon: 'Calendar',
      path: '/resource/activity'
    },
    {
      title: '线索管理',
      desc: '潜在客户追踪',
      icon: 'Connection',
      path: '/clue/list'
    },
    {
      title: '商机管理',
      desc: '销售机会管理',
      icon: 'Opportunity',
      path: '/business/list'
    },
    {
      title: '客户管理',
      desc: '客户信息维护',
      icon: 'User',
      path: '/customer'
    },
    {
      title: '日志统计',
      desc: '系统操作记录',
      icon: 'DataLine',
      path: '/system/log'
    }
  ]
</script>
    
<style scoped>
.home {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 100px);
}

.welcome-section {
  background: linear-gradient(120deg, #1E88E5 0%, #1565C0 100%);
  border-radius: 8px;
  padding: 30px;
  margin-bottom: 20px;
  color: #fff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.welcome-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-text h1 {
  font-size: 28px;
  margin: 0;
  margin-bottom: 10px;
}

.welcome-text p {
  font-size: 16px;
  margin: 0;
  opacity: 0.9;
}

.welcome-time {
  text-align: right;
}

.welcome-time .time {
  font-size: 32px;
  font-weight: bold;
}

.welcome-time .date {
  font-size: 14px;
  opacity: 0.9;
}

.data-overview {
  margin-bottom: 20px;
}

.overview-section {
  background: #e2f4fb;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.section-header {
  font-size: 28px;
  text-align: center;
  font-weight: bold;
  color: #35cec9;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.section-content {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.data-item {
  text-align: center;
  font-weight:bolder;
  padding: 15px;
  background: #e2f4fb;
  border-radius: 6px;
  border: #606266 dashed 1px;
  transition: all 0.3s;
}

.data-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.item-label {
  font-size: 16px;
  color: #3c3e41;
  margin-bottom: 8px;
}

.item-value {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
}

.system-info {
  margin-bottom: 20px;
}

.system-card {
  height: 100%;
}

.card-header {
  font-weight: bold;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.info-item .label {
  color: #909399;
  width: 100px;
}

.info-item .value {
  color: #303133;
}

.help-list {
  display: flex;
  justify-content: space-around;
}

.help-item {
  text-align: center;
  cursor: pointer;
  padding: 20px;
  border-radius: 4px;
  transition: all 0.3s;
}

.help-item:hover {
  background-color: #f5f7fa;
}

.help-item .el-icon {
  font-size: 32px;
  color: #1976D2; 
  margin-bottom: 10px;
}

.help-item span {
  display: block;
  color: #606266;
}

.quick-entry {
  margin: 40px 0;
}

.entry-item {
  background: #fff;
  border-radius: 15px;
  padding: 24px;
  height: 160px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s;
  position: relative;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.entry-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #1976D2, #42A5F5);
  opacity: 0;
  transition: opacity 0.3s;
}

.entry-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.entry-item:hover::before {
  opacity: 1;
}

.entry-icon {
  font-size: 40px;
  color: #1976D2;
  margin-bottom: 16px;
}

.entry-info {
  text-align: center;
}

.entry-title {
  font-size: 18px;
  color: #303133;
  margin-bottom: 8px;
  font-weight: 500;
}

.entry-desc {
  font-size: 14px;
  color: #909399;
}
.welcome-text-custom {
  font-family: '华文隶书';
  color: #1890ff;
  font-size: 60px;
  text-align: center;
  margin-bottom: 40px;
}
</style>