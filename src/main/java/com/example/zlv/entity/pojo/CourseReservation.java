package com.example.zlv.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (course_reservation)实体类
 *
 * @author zlv
 * @description 由 Mybatisplus Code Generator 创建
 * @since 2025-04-11 21:58:46
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("course_reservation")
public class CourseReservation extends Model<CourseReservation> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 预约课程
     */
    private String course;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 联系方式
     */
    private String contactInfo;
    /**
     * 预约校区
     */
    private String school;
    /**
     * 备注
     */
    private String remark;

}