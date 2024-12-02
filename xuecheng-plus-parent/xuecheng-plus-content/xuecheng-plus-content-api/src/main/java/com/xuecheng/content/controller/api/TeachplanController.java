package com.xuecheng.content.controller.api;

import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 课程计划编辑接口
 * @date 2022/9/6 11:29
 */
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
@RestController
public class TeachplanController {
    @Autowired
    TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId", name = "课程基础Id值", required = true, dataType = "Long", paramType = "path")
    @GetMapping("teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto teachplan) {
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("根据id删除课程计划")
    @DeleteMapping("/teachplan/{id}")
    public void delTeachPlan(@PathVariable Long id) {
        teachplanService.delTeachPlan(id);
    }

    //Request URL: http://localhost:8601/api/content/teachplan/movedown/43
    // Method: POST
    @ApiOperation("下移")
    @PostMapping("/teachplan/movedown/{id}")
    public void moveDown(@PathVariable Long id) {
        teachplanService.moveDown(id);
    }

    //Request URL: http://localhost:8601/api/content/teachplan/moveup/43
    //Request Method: POST
    @ApiOperation("上移")
    @PostMapping("/teachplan/moveup/{id}")
    public void upDown(@PathVariable Long id) {
        teachplanService.upDown(id);
    }

}