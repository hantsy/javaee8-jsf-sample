package com.github.hantsy.ee8sample.web;

import com.github.hantsy.ee8sample.domain.Task;
import com.github.hantsy.ee8sample.service.TaskService;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;

/**
 *
 * @author hantsy
 *
 */
@Named("editTaskAction")
@ViewScoped()
public class EditTaskAction implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //@Inject
    private static final Logger LOG = Logger.getLogger(EditTaskAction.class.getName());

    @Inject
    private TaskService taskService;

    private Long taskId;

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void init() {
        LOG.log(Level.INFO, " get task of id @{0}", taskId);

        if (taskId == null) {
            task = new Task();
        } else {
            task = taskService.findById(taskId);
        }

    }

    public String save() {
        LOG.log(Level.INFO, "saving task@{0}", task);
        if (this.task.getId() == null) {
            this.task = taskService.save(task);
        } else {
            this.task = taskService.update(taskId, task);
        }
        FacesMessage info = new FacesMessage(FacesMessage.SEVERITY_INFO, "Task is saved successfully!", "Task is saved successfully!");
        FacesContext.getCurrentInstance().addMessage(null, info);

        return "/tasks.xhtml?faces-redirect=true";
    }

}
