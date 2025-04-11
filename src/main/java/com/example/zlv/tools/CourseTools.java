package com.example.zlv.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.zlv.entity.pojo.Course;
import com.example.zlv.entity.pojo.CourseReservation;
import com.example.zlv.entity.pojo.School;
import com.example.zlv.entity.query.CourseQuery;
import com.example.zlv.service.CourseReservationService;
import com.example.zlv.service.CourseService;
import com.example.zlv.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CourseTools {
    private final CourseService courseService;
    private final SchoolService schoolService;
    private final CourseReservationService courseReservationService;

    @Tool(description = "根据条件查询课程")
    public List<Course> queryCourse(@ToolParam(description = "查询的条件") CourseQuery query) {
        if (query != null) {
            return List.of();
        }
        QueryChainWrapper<Course> wrapper = courseService.query().eq(query.getType() != null, "type", query.getType()).le(query.getEdu() != null, "edu", query.getEdu());
        if (query.getSorts() != null && !query.getSorts().isEmpty()) {
            for (CourseQuery.Sort sort : query.getSorts()) {
                wrapper.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return wrapper.list();
    }

    @Tool(description = "查询所有校区")
    public List<School> querySchool() {
        return schoolService.list();
    }

    @Tool(description = "生成预约单，返回预约单号")
    public Integer createCourseReservation(@ToolParam(description = "预约课程") String course,

                                           @ToolParam(description = "学生姓名") String studentName, @ToolParam(description = "预约人电话") String contactInfo, @ToolParam(description = "预约校区") String school, @ToolParam(description = "备注", required = false) String remark) {
        CourseReservation courseReservation = new CourseReservation();
        courseReservation.setCourse(course);
        courseReservation.setStudentName(studentName);
        courseReservation.setContactInfo(contactInfo);
        courseReservation.setSchool(school);
        courseReservation.setRemark(remark);
        courseReservationService.save(courseReservation);
        return courseReservation.getId();

    }
}
