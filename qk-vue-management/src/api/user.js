import request from '@/utils/request'

/**
 * 获取用户分页数据
 */
export const getUserPage = (params) =>  request.get('/users', { params })

/**
 * 获取所有用户列表
 */
export const getUserList = () =>  request.get('/users/list')

/**
 * 添加用户
 */
export const addUser = (data) =>  request.post('/users', data)

/**
 * 修改用户
 */
export const updateUser = (data) => request.put('/users', data)

/**
 * 删除用户
 */
export const deleteUser = (ids) =>  request.delete(`/users/${ids}`)


/**
 * 根据ID获取用户信息
 */
export const getUserById = (id) =>  request.get(`/users/${id}`)

/**
 * 根据角色标识获取用户列表
 */
export const getUserListByRoleLabel = (roleLabel) =>  request.get(`/users/role/${roleLabel}`)