package com.xuecheng.content.controller.api;

import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/7/27 17:50
 * @Version V1.0
 */
@RestController
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService teacherService;
    @GetMapping("/courseTeacher/list/{courseId}")

    public List<CourseTeacher> queryCourseTeacher(@PathVariable Long courseId) {
        Long companyId = 1232141425L;
        return teacherService.queryCourseTeacher(companyId, courseId);
    }

    @ApiOperation("新增老师")
    @PostMapping("/courseTeacher")
    public CourseTeacher saveCourseTeacher(@RequestBody CourseTeacher courseTeacher) {
        return teacherService.saveCourseTeacher(courseTeacher);
    }

    @ApiOperation("修改老师")
    @PutMapping("/courseTeacher")
    public CourseTeacher updateCourseTeacher(@RequestBody CourseTeacher courseTeacher) {
        return teacherService.updateCourseTeacher(courseTeacher);
    }

    @DeleteMapping("/courseTeacher/course/{courseBaseId}/{courseTeacherId}")
    public void delCourseTeacher(@PathVariable Long courseBaseId, @PathVariable Long courseTeacherId) {
        teacherService.delCourseTeacher(courseBaseId, courseTeacherId);
    }
}
