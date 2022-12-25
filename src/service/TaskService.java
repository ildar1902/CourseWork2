package service;

import exception.IdNotFoundException;
import DailyPlanner.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TaskService {
    private static final Map<Integer, Task> TASKS = new HashMap<>();

    private TaskService() {
    }

    public static void add(Task task) {
        TASKS.put(task.getId(), task);
    }

    public static Collection<Task> getTasksForDay(LocalDate date) {
        Collection<Task> tasksForDay = new LinkedList<>();
        Collection<Task> allTasks = TASKS.values();
        for (Task task : allTasks) {
            LocalDateTime currentDateTime = task.getDateTime();
            if (currentDateTime.toLocalDate().equals(date)) {
                tasksForDay.add(task);
                continue;
            }
            LocalDateTime nextDateTime = currentDateTime;
            do {
                nextDateTime = task.getRepeatability().nextTime(nextDateTime);
                if (nextDateTime == null) {
                    break;
                }
                if (nextDateTime.toLocalDate().equals(date)) {
                    tasksForDay.add(task);
                    break;
                }
            } while (nextDateTime.toLocalDate().isBefore(date));
        }
        return tasksForDay;
    }

    public static void deleteTaskById(int id) throws IdNotFoundException {
        if (TASKS.remove(id) == null) {
            throw new IdNotFoundException("запрашиваемый id не найден");
        }
    }

}