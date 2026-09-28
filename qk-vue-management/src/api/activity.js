import request from '@/utils/request'

/**
 * 获取活动分页数据
 */
export const getActivityPage = (params) => request.get('/activities', { params })

/**
 * 删除活动
 */
export const deleteActivity = (id) => request.delete(`/activities/${id}`)

/**
 * 添加活动
 */
export const addActivity = (data) => request.post('/activities', data)

/**
 * 修改活动
 */
export const updateActivity = (data) => request.put('/activities', data)

/**
 * 根据ID获取活动信息
 */
export const getActivityById = (id) => request.get(`/activities/${id}`)

/**
 * 获取所有活动列表
 */
export const getActivityList = () => request.get('/activities/list')

/**
 * 根据类型获取活动列表
 * @param {number} type 活动类型：1-线上活动，2-推广介绍
 */
export const getActivityListByType = (type) => request.get(`/activities/type/${type}`)