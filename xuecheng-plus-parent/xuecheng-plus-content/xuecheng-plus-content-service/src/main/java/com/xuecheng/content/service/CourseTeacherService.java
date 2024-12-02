package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * <p>
 * 课程-教师关系表 服务类
 * </p>
 *
 * @author itcast
 * @since 2022-10-07
 */
public interface CourseTeacherService extends IService<CourseTeacher> {

    List<CourseTeacher> queryCourseTeacher(Long companyId, Long courseId);

    CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher);

    CourseTeacher updateCourseTeacher(CourseTeacher courseTeacher);

    void delCourseTeacher(Long courseId, Long id);
}
