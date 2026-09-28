import request from '@/utils/request'

// 获取首页报表数据
export const getOverview = () => request.get('/report/overview')