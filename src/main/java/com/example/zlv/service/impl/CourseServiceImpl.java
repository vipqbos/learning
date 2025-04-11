package com.example.zlv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlv.entity.pojo.Course;
import com.example.zlv.mapper.CourseMapper;
import com.example.zlv.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 学科表服务接口实现
 *
 * @author zlv
 * @since 2025-04-11 21:58:46
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}