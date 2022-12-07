package TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyRepeat extends Task {
    public WeeklyRepeat(String taskTitle, String taskDescription,
                        LocalDateTime taskDateTime, TaskType taskType) {
        super(taskTitle, taskDescription, taskDateTime, taskType);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate)||localDate.isAfter(taskDate) &&
                localDate.getDayOfWeek().equals(taskDate.getDayOfWeek());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.WEEKLY;
    }
}
