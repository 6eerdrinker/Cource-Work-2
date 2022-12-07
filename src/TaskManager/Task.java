package TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Task {
    private static int counter = 0;
    private final int id;
    private final String taskTitle;
    private final String taskDescription;
    private final LocalDateTime taskDateTime;
    private final TaskType taskType;

    public Task(String taskTitle, String taskDescription, LocalDateTime taskDateTime, TaskType taskType) {
        this.id = counter++;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDateTime = taskDateTime;
        this.taskType = taskType;
    }


    public int getId() {
        return id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    //создан метод вызова задач на определенную дату
    public abstract boolean appearsIn(LocalDate localDate);

    //создан метод повторяемости задач по типу повторяемости
    public abstract Repeatability getRepeatabilityType();
}