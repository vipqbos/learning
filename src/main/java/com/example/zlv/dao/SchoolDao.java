package com.example.zlv.dao;

import lombok.extern.slf4j.Slf4j;
import com.example.zlv.entity.pojo.School;
import com.example.zlv.mapper.SchoolMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 校区表(school)数据DAO
 *
 * @author zlv
 * @since 2025-04-11 21:58:46
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class SchoolDao extends ServiceImpl<SchoolMapper, School> {

}