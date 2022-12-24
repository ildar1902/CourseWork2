package DailyPlanner;

import java.time.*;
import java.util.*;

public class Task {
    private String heading;
    private String description;
    private final TaskType taskType;
    private LocalDateTime dateTime;
    final private int id;
    private static int idCounter = 1;

    public Task(String heading, String description, TaskType taskType, LocalDateTime dateTime) {
        this.heading = heading;
        this.description = description;
        this.taskType = taskType;
        this.dateTime = dateTime;
        id = idCounter++;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
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
