import request from '@/utils/request'

/**
 * 获取商机分页数据
 */
export const getBusinessPage = (params) => request.get('/businesses', { params })

/**
 * 添加商机
 */
export const addBusiness = (data) => request.post('/businesses', data)

/**
 * 根据ID获取商机详情
 */
export const getBusinessById = (id) => request.get(`/businesses/${id}`)

/**
 * 跟进商机
 */
export const updateBusiness = (data) => request.put('/businesses', data)

/**
 * 分配商机
 */
export const assignBusiness = (businessId, userId) => request.put(`/businesses/assign/${businessId}/${userId}`)

/**
 * 踢回公海
 */
export const returnToPool = (id) => request.put(`/businesses/back/${id}`)

/**
 * 转客户
 */
export const convertToCustomer = (id) => request.post(`/businesses/toCustomer/${id}`)

/**
 * 获取商机公海池分页数据
 */
export const getBusinessPoolPage = (params) => request.get('/businesses/pool', { params })
