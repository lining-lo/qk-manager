import request from '@/utils/request'

// 获取部门列表
export const getDeptList = () => request.get('/depts/list')

// 分页查询部门
export const getDeptPage = params => request.get('/depts', { params })

// 删除部门
export const deleteDept = id => request.delete(`/depts/${id}`)

// 添加部门
export const addDept = data => request.post('/depts', data)

// 根据ID查询部门
export const getDeptById = id => request.get(`/depts/${id}`)

// 修改部门
export const updateDept = data => request.put('/depts', data)