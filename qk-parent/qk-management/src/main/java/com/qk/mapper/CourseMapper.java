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
}
