package com.felipepossari.schoolregistration.adapter.out.mysql;

import com.felipepossari.schoolregistration.adapter.out.mysql.entity.CourseEntity;
import com.felipepossari.schoolregistration.adapter.out.mysql.mapper.CourseRepositoryMapper;
import com.felipepossari.schoolregistration.adapter.out.mysql.repository.CourseRepository;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseRepositoryPortService implements CourseRepositoryPort {

    private final CourseRepositoryMapper mapper;
    private final CourseRepository repository;
    private final EntityManager entityManager;

    @Override
    public Course create(Course course) {
        var entity = repository.save(mapper.toEntity(course));
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Course> findById(Long id) {
        var courseOpt = repository.findById(id);
        if (courseOpt.isPresent()) {
            return Optional.of(mapper.toDomain(courseOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> findByName(String name) {
        var courseOpt = repository.findByName(name);
        if (courseOpt.isPresent()) {
            return Optional.of(mapper.toDomain(courseOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> findByNameAndIdNot(String name, Long id) {
        var courseOpt = repository.findByNameAndIdNot(name, id);
        if (courseOpt.isPresent()) {
            return Optional.of(mapper.toDomain(courseOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findByFilter(CourseFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM course WHERE 1=1 ");
        sb.append(" ORDER BY name ASC LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(sb.toString(), CourseEntity.class);

        query.setParameter("offset", filter.getPage() * filter.getSize());
        query.setParameter("limit", filter.getSize());

        List<CourseEntity> courses = query.getResultList();

        return mapper.toDomain(courses);
    }

    @Override
    public void update(Course course) {
        repository.save(mapper.toEntity(course));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Course> findEagerByFilter(CourseFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c from CourseEntity c left join fetch c.students s WHERE 1=1");

        if (filter.getCourseId() > 0) {
            sb.append(" AND c.id = :courseId");
        }

        if (filter.getStudentId() > 0) {
            sb.append(" AND s.id = :studentId");
        }

        if (filter.isWithoutEnrollment()) {
            sb.append(" AND s IS NULL");
        }

        Query query = entityManager.createQuery(sb.toString(), CourseEntity.class);

        if (filter.getCourseId() > 0) {
            query.setParameter("courseId", filter.getCourseId());
        }

        if (filter.getStudentId() > 0) {
            query.setParameter("studentId", filter.getStudentId());
        }

        List<CourseEntity> courses = query
                .setFirstResult(filter.getPage() * filter.getSize())
                .setMaxResults(filter.getSize())
                .getResultList();

        return mapper.toDomain(courses);
    }
}
