package com.example.zlv.dao;

import lombok.extern.slf4j.Slf4j;
import com.example.zlv.entity.pojo.Course;
import com.example.zlv.mapper.CourseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 学科表(course)数据DAO
 *
 * @author zlv
 * @since 2025-04-11 21:58:46
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class CourseDao extends ServiceImpl<CourseMapper, Course> {

}