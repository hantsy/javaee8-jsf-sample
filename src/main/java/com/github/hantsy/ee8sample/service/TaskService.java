/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.service;

import com.github.hantsy.ee8sample.domain.Task;
import com.github.hantsy.ee8sample.repository.TaskRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author hantsy
 */
@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository tasks;

    public Task findById(Long id) {
        return this.tasks.findOptionalById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> findByStatus(Task.Status status) {
        return this.tasks.findByStatus(status);
    }

    public Task save(Task data) {
        return this.tasks.save(data);
    }

    public Task update(Long id, Task data) {
        Task _existed = findById(id);
        _existed.setName(data.getName());
        _existed.setDescription(data.getDescription());
        return this.tasks.save(_existed);
    }

    public void deleteById(Long id) {
        Task _existed = findById(id);
        this.tasks.delete(_existed);
    }

    public void updateStatus(Long id, Task.Status status) {
        Task _existed = findById(id);
        _existed.setStatus(status);
        this.tasks.save(_existed);
    }

}
