CREATE TABLE `student_course` (
  `student_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  UNIQUE KEY `course_student_unique` (`student_id`,`course_id`),
  CONSTRAINT `course_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `student_course_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;