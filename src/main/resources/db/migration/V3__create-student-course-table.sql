CREATE TABLE `student_course` (
  `student_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  KEY `course_id` (`course_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `course_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `student_course_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;