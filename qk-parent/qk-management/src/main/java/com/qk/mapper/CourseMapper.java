package com.qk.mapper;
import com.qk.domain.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    /**
     * 根据条件查询课程
     * @param subject   课程学科
     * @param name   课程名称
     * @param target   适应人群
     */
    List<Course> list(Integer subject, String name, Integer target);

    /**
     * 新增课程
     * @param course 封装课程信息
     */
    @Insert("INSERT INTO course(subject, name, price, target, description, create_time, update_time) VALUES(#{subject}, #{name}, #{price}, #{target}, #{description}, #{createTime}, #{updateTime})")
    void insert(Course course);

    /**
     * 更新课程信息
     * @param course 课程实体
     */
    void updateById(Course course);

    /**
     * 根据ID查询课程
     * @param id 课程ID
     * @return 课程实体
     */
    @Select("SELECT id, subject, name, price, target, description, create_time, update_time FROM course WHERE id = #{id}")
    Course getById(Integer id);

    /**
     * 根据ID删除课程
     * @param id 课程ID
     */
    @Delete("DELETE FROM course WHERE id = #{id}")
    void deleteById(Integer id);
}
