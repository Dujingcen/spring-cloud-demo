package com.devchen.common.task.loader;

import com.devchen.common.task.dto.AbstractTaskDTO;
import com.devchen.common.task.handler.TaskHandler;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public abstract class TaskHandlerFactory<T extends AbstractTaskDTO> {

    private Map<String, TaskHandler<T>> taskMap = new HashMap<>();

    private final static Logger logger = Logger.getLogger(TaskHandlerFactory.class);

    @PostConstruct
    protected abstract void init();

    protected void registTask(TaskHandler<T> taskHandler) {
        String taskType = taskHandler.getTaskType().name();
        String taskJob = taskHandler.getTaskJob().name();
        String key = taskType + "_" + taskJob;
        if(taskMap.containsKey(key)) {
            logger.warn(String.format("[registTask] fail to register task due to exist same task handler. task type [%s]. task job [%s]. task handler [%s]",
                    taskType, taskJob, taskHandler.getClass().getName()));
        }
        taskMap.put(key, taskHandler);
        logger.warn(String.format("[registTask] register task handler success. task type [%s]. task job [%s]. task handler [%s]",
                taskType, taskJob, taskHandler.getClass().getName()));
    }

    public TaskHandler<T> getTaskHandler(T taskDTO) {
        String taskType = taskDTO.getTaskType();
        String taskJob = taskDTO.getTaskJob();
        String key = taskType + "_" + taskJob;
        return taskMap.get(key);
    }

}
