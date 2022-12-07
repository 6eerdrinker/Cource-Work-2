package TaskManager;

import java.time.LocalDate;
import java.util.*;

public class Sked {
    private final Map<Integer, Task> tasks = new HashMap<>();

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Sked() {
    }

    //Метод добавления задачи
    public void addTask(Task task) {
        this.tasks.put(task.getId(), task);
    }


    // Метод удаления задачи по id
    public void removeTask(int id) throws TaskNotFoundException {
        if (this.tasks.containsKey(id)) {
            this.tasks.remove(id);
        } else {
            throw new TaskNotFoundException();
        }
    }

    // Метод получения списка задач на день
    public Collection<Task> getTasksForDate(LocalDate date) {
        List<Task> tasksForDate = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.appearsIn(date)) {
                tasksForDate.add(task);
            }
        }
        return tasksForDate;
    }

    //Метод получения списка всех задач
    public Collection<Task> getAllTasks() {
        return this.tasks.values();
    }

}
