package TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyPepeat extends Task {
    public YearlyPepeat(String taskTitle, String taskDescription,
                        LocalDateTime taskDateTime, TaskType taskType) {
        super(taskTitle, taskDescription, taskDateTime, taskType);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate) || (localDate.isAfter(taskDate)
                && localDate.getDayOfMonth() == taskDate.getDayOfMonth()
                && localDate.getMonth().equals(taskDate.getMonth()));
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.YEARLY;
    }
}
