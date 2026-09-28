import request from '@/utils/request'

/**
 * 获取线索分页数据
 */
export const getCluePage = (params) => request.get('/clues', { params })

/**
 * 添加线索
 */
export const addClue = (data) => request.post('/clues', data)

/**
 * 根据ID获取线索详情
 */
export const getClueById = (id) => request.get(`/clues/${id}`)

/**
 * 跟进线索
 */
export const updateClue = (data) => request.put('/clues', data)

/**
 * 分配线索
 */
export const assignClue = (clueId, userId) => request.put(`/clues/assign/${clueId}/${userId}`)

/**
 * 伪线索上报
 */ 
export const reportFakeClue = (id, data) => request.put(`/clues/false/${id}`, data)

/**
 * 线索转换商机
 */
export const convertToBusiness = (id) => request.put(`/clues/toBusiness/${id}`)

/**
 * 获取线索池分页数据
 */
export const getCluePoolPage = (params) => request.get('/clues/pool', { params })