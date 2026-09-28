import request from '@/utils/request'

// 获取角色列表
export const getRoleList = () => request.get('/roles/list')

// 分页查询角色
export const getRolePage = params => request.get('/roles', { params })

// 删除角色
export const deleteRole = id => request.delete(`/roles/${id}`)

// 添加角色
export const addRole = data => request.post('/roles', data)

// 根据ID查询角色
export const getRoleById = id => request.get(`/roles/${id}`)

// 修改角色
export const updateRole = data => request.put('/roles', data)