package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程-教师关系表 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    /**
     * 查询教师信息
     * @param companyId
     * @param courseId
     * @return
     */
    @Override
    public List<CourseTeacher> queryCourseTeacher(Long companyId, Long courseId) {
        //校验机构是否符合
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            XueChengPlusException.cast("课程不存在");
        }

        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        final LambdaQueryWrapper<CourseTeacher> qw = new LambdaQueryWrapper<>();
        qw.eq(CourseTeacher::getCourseId, courseId);
        final List<CourseTeacher> teachers = courseTeacherMapper.selectList(qw);
        return teachers;
    }

    @Override
    public CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher) {

        CourseTeacher teacher = new CourseTeacher();
        BeanUtils.copyProperties(courseTeacher,teacher);
        teacher.setCreateDate(LocalDateTime.now());
        if (teacher.getId() == null) {
            courseTeacherMapper.insert(teacher);
        } else {
            courseTeacherMapper.updateById(courseTeacher);
        }
        return teacher;
    }

    @Override
    public CourseTeacher updateCourseTeacher(CourseTeacher courseTeacher) {
        courseTeacherMapper.updateById(courseTeacher);
        return courseTeacher;
    }

    @Override
    public void delCourseTeacher(Long courseId, Long id) {
        final LambdaQueryWrapper<CourseTeacher> qw = new LambdaQueryWrapper<>();
        qw.eq(CourseTeacher::getCourseId, courseId);
        qw.eq(CourseTeacher::getId, id);
        courseTeacherMapper.delete(qw);
    }
}
