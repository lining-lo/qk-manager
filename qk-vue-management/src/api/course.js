import request from '@/utils/request'


/**
 * 获取所有活动列表
 */
export const getCourseList = () => request.get('/courses/list')

/**
 * 获取课程分页数据
 */
export const getCoursePage = (params) => request.get('/courses', { params })

/**
 * 删除课程
 */
export const deleteCourse = (ids) => request.delete(`/courses/${ids}`)

/**
 * 添加课程
 */
export const addCourse = (data) => request.post('/courses', data)

/**
 * 修改课程
 */
export const updateCourse = (data) => request.put('/courses', data)

/**
 * 根据ID获取课程信息
 */
export const getCourseById = (id) => request.get(`/courses/${id}`)

/**
 * 根据学科查询课程列表
 */
export const getCoursesBySubject = (subject) => request.get(`/courses/subject/${subject}`)