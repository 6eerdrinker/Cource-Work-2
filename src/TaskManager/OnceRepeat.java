package TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OnceRepeat extends Task {
    public OnceRepeat(String taskTitle, String taskDescription,
                      LocalDateTime taskDateTime, TaskType taskType) {
        super(taskTitle, taskDescription, taskDateTime, taskType);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return localDate.equals(this.getTaskDateTime().toLocalDate());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.ONCE;
    }
}
