package DailyPlanner;

import java.time.*;
import java.util.*;

public class Task {

    private static int idCounter = 1;

    final private int id;
    private String heading;
    private String description;
    private TaskType taskType;
    private LocalDateTime dateTime;
    Repeatability repeatability;

    public Task(String heading, String description, TaskType taskType,
                LocalDateTime dateTime, Repeatability repeatability) {
        id = idCounter++;
        setHeading(heading);
        setDescription(description);
        setTaskType(taskType);
        setDateTime(dateTime);
        setRepeatability(repeatability);
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        if (StringUtils.isNullOrEmpty(heading)) {
            throw new IllegalArgumentException("не заполнен заголовок задачи!");
        }
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (StringUtils.isNullOrEmpty(description)) {
            throw new IllegalArgumentException("заполните описание задачи!");
        }
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        if (taskType == null) {
            taskType = TaskType.PERSONAL;
        }
        this.taskType = taskType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("введите верно дату и время задачи");
        }
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public Repeatability getRepeatability() {
        return repeatability;
    }

    public void setRepeatability(Repeatability repeatability) {
        if (repeatability == null) {
            repeatability = new OneTime();
        }
        this.repeatability = repeatability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(heading, task.heading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, id);
    }

}
