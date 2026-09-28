import request from '@/utils/request'

/**
 * 分页查询客户列表
 */
export const getCustomerPage = (params) => request.get('/customers', { params })

/**
 * 添加客户
 */
export const addCustomer = (data) => request.post('/customers', data)

/**
 * 根据ID查询客户
 */
export const getCustomerById = (id) => request.get(`/customers/${id}`)

/**
 * 修改客户
 */
export const updateCustomer = (data) => request.put('/customers', data)