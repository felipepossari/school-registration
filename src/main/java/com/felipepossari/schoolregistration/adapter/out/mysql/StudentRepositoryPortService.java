package com.felipepossari.schoolregistration.adapter.out.mysql;

import com.felipepossari.schoolregistration.adapter.out.mysql.entity.StudentEntity;
import com.felipepossari.schoolregistration.adapter.out.mysql.mapper.StudentRepositoryMapper;
import com.felipepossari.schoolregistration.adapter.out.mysql.repository.StudentRepository;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentRepositoryPortService implements StudentRepositoryPort {

    private final StudentRepositoryMapper mapper;
    private final StudentRepository repository;
    private final EntityManager entityManager;

    @Override
    public Student create(Student student) {
        var entity = repository.save(mapper.toEntity(student));
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Student> findById(Long id) {
        var studentOpt = repository.findById(id);
        return studentOpt.map(mapper::toDomain);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        var studentOpt = repository.findByEmail(email);
        return studentOpt.map(mapper::toDomain);
    }

    @Override
    public Optional<Student> findByEmailAndIdNot(String email, Long id) {
        var studentOpt = repository.findByEmailAndIdNot(email, id);
        return studentOpt.map(mapper::toDomain);
    }

    @Override
    public List<Student> findByFilter(StudentFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM student WHERE 1=1 ");
        sb.append(" ORDER BY name ASC LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(sb.toString(), StudentEntity.class);

        query.setParameter("offset", filter.getPage() * filter.getSize());
        query.setParameter("limit", filter.getSize());

        List<StudentEntity> students = query.getResultList();

        return mapper.toDomain(students);
    }

    @Override
    public void update(Student student) {
        repository.save(mapper.toEntity(student));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Student> findEagerByFielder(StudentFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s from StudentEntity s left join fetch s.courses c WHERE 1=1");

        if (filter.getStudentId() > 0) {
            sb.append(" AND s.id = :studentId");
        }

        if (filter.getCourseId() > 0) {
            sb.append(" AND c.id = :courseId");
        }

        if (filter.isWithoutEnrollment()) {
            sb.append(" AND c IS NULL");
        }

        Query query = entityManager.createQuery(sb.toString(), StudentEntity.class);

        if (filter.getStudentId() > 0) {
            query.setParameter("studentId", filter.getStudentId());
        }

        if (filter.getCourseId() > 0) {
            query.setParameter("courseId", filter.getCourseId());
        }

        List<StudentEntity> students = query
                .setFirstResult(filter.getPage() * filter.getSize())
                .setMaxResults(filter.getSize())
                .getResultList();

        return mapper.toDomain(students);
    }
}
