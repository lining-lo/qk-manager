import request from '@/utils/request'

/**
 * 分页查询日志列表
 */
export const getLogPage = (params) => request.get('/logs', { params })