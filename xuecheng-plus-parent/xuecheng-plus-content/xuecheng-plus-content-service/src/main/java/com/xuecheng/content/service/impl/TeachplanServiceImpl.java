package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 课程计划 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {

        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        //课程计划id
        Long id = teachplanDto.getId();
        //修改课程计划
        if(id!=null){
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else{
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            BeanUtils.copyProperties(teachplanDto,teachplanNew);

            teachplanMapper.insert(teachplanNew);

        }

    }

    /**
     * 根据课程计划id删除课程计划
     * @param id
     */
    public void delTeachPlan(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        LambdaQueryWrapper<Teachplan> qw = new LambdaQueryWrapper<>();
        Integer grade = teachplan.getGrade();
        if (grade > 1) {
            teachplanMapper.deleteById(id);
            return;
        }
        qw.eq(Teachplan::getParentid, id);
        Integer count = teachplanMapper.selectCount(qw);
        if (count > 0) {
            throw new XueChengPlusException("该目录关联其他子级菜单，无法删除");
        }
        teachplanMapper.deleteById(id);
    }

    /**
     * 根据课程id上移或下移目录
     * @param id
     */
    @Override
    public void moveDown(Long id) {
        //根据计划id查询课程信息
        Teachplan teachplan = teachplanMapper.selectById(id);
        /*
         * 1.查询当前计划对应的课程id
         * 2.查询当前课程对应的同一级目录
         * 3.查询需要交换的课程计划
         * 3.父级课程及上一级目录需要一样
         */
        LambdaQueryWrapper<Teachplan> qw = new LambdaQueryWrapper<>();
        qw.eq(Teachplan::getCourseId, teachplan.getCourseId());
        qw.eq(Teachplan::getGrade, teachplan.getGrade());
        qw.eq(Teachplan::getOrderby, teachplan.getOrderby() + 1);
        qw.eq(Teachplan::getParentid, teachplan.getParentid());
        Teachplan one = teachplanMapper.selectOne(qw);
        if (ObjectUtils.isEmpty(one)) {
            throw new XueChengPlusException("已经至于最底层，无法交换");
        }

        //交换两者的排序
        one.setOrderby(teachplan.getOrderby());
        teachplan.setOrderby(teachplan.getOrderby() + 1);
        teachplanMapper.updateById(one);
        teachplanMapper.updateById(teachplan);
    }

    @Override
    public void upDown(Long id) {
        //根据计划id查询课程信息
        Teachplan teachplan = teachplanMapper.selectById(id);
        /*
         * 1.查询当前计划对应的课程id
         * 2.查询当前课程对应的同一级目录
         * 3.查询需要交换的课程计划
         * 3.父级课程及上一级目录需要一样
         */
        LambdaQueryWrapper<Teachplan> qw = new LambdaQueryWrapper<>();
        qw.eq(Teachplan::getCourseId, teachplan.getCourseId());
        qw.eq(Teachplan::getGrade, teachplan.getGrade());
        qw.eq(Teachplan::getOrderby, teachplan.getOrderby() - 1);
        qw.eq(Teachplan::getParentid, teachplan.getParentid());
        Teachplan one = teachplanMapper.selectOne(qw);
        if (ObjectUtils.isEmpty(one)) {
            throw new XueChengPlusException("已经至于最底层，无法交换");
        }

        //交换两者的排序
        one.setOrderby(teachplan.getOrderby());
        teachplan.setOrderby(teachplan.getOrderby() - 1);
        teachplanMapper.updateById(one);
        teachplanMapper.updateById(teachplan);
    }



    /**
     * @description 获取最新的排序号
     * @param courseId  课程id
     * @param parentId  父课程计划id
     * @return int 最新排序号
     * @author Mr.M
     * @date 2022/9/9 13:43
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;
    }
}
