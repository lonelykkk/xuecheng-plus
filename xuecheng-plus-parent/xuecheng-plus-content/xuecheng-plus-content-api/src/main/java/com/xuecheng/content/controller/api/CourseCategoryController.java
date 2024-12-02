package com.xuecheng.content.controller.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author itcast
 */
@Api(tags = "查询课程分类信息",value = "课程分类信息")
@Slf4j
@RestController
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService categoryService;

    @ApiOperation("查询课程分类目录")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return categoryService.queryTreeNodes("1");
    }
}