package com.github.hantsy.ee8sample.repository;

import com.github.hantsy.ee8sample.domain.Task;
import com.github.hantsy.ee8sample.domain.support.AbstractRepository;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.toList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hantsy
 */
@Stateless
public class TaskRepository extends AbstractRepository<Task, Long> {

    @PersistenceContext
    EntityManager em;

    public List<Task> findByStatus(Task.Status status) {
        Objects.requireNonNull(status, "task status can not be null");
        
        return this.stream()
                .filter(t -> t.getStatus() == status)
                .sorted(Task.DEFAULT_COMPARATOR)
                .collect(toList());
    }

    @Override
    protected EntityManager entityManager() {
        return em;
    }

}
