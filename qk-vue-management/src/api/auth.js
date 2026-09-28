import request from '@/utils/request'

// 登录接口
export const login = (data) => request.post('/login', data)