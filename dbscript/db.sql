DROP DATABASE IF EXISTS `itheima`;
CREATE DATABASE IF NOT EXISTS `itheima`;
USE `itheima`;

-- 导出表 itheima.course结构
DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course`(
    `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '学科名称',
    `edu` int NOT NULL DEFAULT '0' COMMENT '学历背景需要：0-无，1-初中，2-高中，3-大专，4本科以上',
    `type` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '课程类型：编程、设计、自媒体、其它',
    `price` bigint NOT NULL DEFAULT '0' COMMENT '课程价格',
    `duration` int unsigned NOT NULL DEFAULT '0' COMMENT '学习时长，单位：天',
    PRIMARY KEY (`id`)
) ENGINE= InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET =utf8mb4
COLLATE=utf8mb4_general_ci COMMENT='学科表';

-- 导出的表
DELETE FROM `course`;
INSERT INTO `course` (`name`,`edu`,`type`,`price`,`duration`)
VALUES
('JAVAEE',4,'编程',21999,108),
('鸿蒙应用开发',4,'编程',20999,98),
('AI人工智能',4,'编程',24999,100),
('Python大数据开发',4,'编程',23999,102),
('跨境电商',4,'自媒体',12999,68),
('新媒体运营',4,'自媒体',10999,61),
('UI设计',4,'设计',11999,66);

-- 表 itheima.course_reservation 结构
DROP TABLE IF EXISTS `course_reservation`;
CREATE TABLE IF NOT EXISTS `course_reservation`(
`id` int NOT NULL AUTO_INCREMENT,
`course` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '预约课程',
`student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学生姓名',
`contact_info`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系方式',
`school` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约校区',
`remark` text CHARACTER  SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT '校区表';


-- 表数据
DELETE FROM `course_reservation`;
INSERT INTO `course_reservation` (`course`,`student_name`,`contact_info`,`school`,`remark`)
VALUES ('新媒体运营','张三','13899762334','广东校区','安排一个好点的老师');

-- 表 itheima.school 结构
DROP TABLE IF EXISTS `school`;
CREATE TABLE IF NOT EXISTS `school` (
`id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
`name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '校区名称',
`city` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '校区所在城市',
PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT '校区表';

-- 数据
DELETE FROM `school`;
INSERT INTO `school` (`name`,`city`)
VALUES
('昌平校区','北京'),
('顺义校区','北京'),
('杭州校区','杭州'),
('上海校区','上海校区'),
('南京校区','南京'),
('西安校区','西安'),
('郑州校区','郑州'),
('广东校区','广东'),
('深圳校区','深圳');