package TaskManager;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

public class TaskManagerMain {

    private static final Sked SKED = new Sked();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in))
        {
            label:
            while (true) {
                System.out.print("Выберите пункт меню: ");
                System.out.println();
                printMenu();
                if (scanner.hasNextLine()) {
                    String menu = scanner.nextLine();
                    switch (menu) {
                        case "1":            //Создаем новую задачу
                            addTask(scanner);
                            break;
                        case "2":            //Получить задачу на день
                            printTaskForDate(scanner);
                            break;
                        case "3":            //Удалить задачу по ключу
                            removeTask(scanner);
                            break;
                        case "0":            //Выход из дневника
                            break  label;}
                } else {
                    scanner.nextLine();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }
    //Метод "Сервисное меню"
    public static void printMenu() {
        System.out.print("1. Добавить задачу\n" +
                "2. Получить задачу на указанный день\n"+
                "3. Удалить задачу\n" +
                "0. Выход\n");
    }
// Метод создания и добавления задач
    private static void addTask(Scanner scanner) {
        String taskTitle = readString("Введите навание задачи: ", scanner);
        String taskDiscription = readString("Введите описание задачи: ", scanner);
        LocalDateTime taskDate = readDateTime (scanner);
        TaskType taskType = readTaskType (scanner);
        Repeatability repeatability = readRepeatability(scanner);
        Task task = switch (repeatability) {
            case ONCE -> new OnceRepeat(taskTitle, taskDiscription, taskDate, taskType);
            case DAILY -> new DailyRepeat(taskTitle, taskDiscription, taskDate, taskType);
            case WEEKLY -> new WeeklyRepeat(taskTitle, taskDiscription, taskDate, taskType);
            case MONTHLY -> new MonthlyRepeat(taskTitle, taskDiscription, taskDate, taskType);
            case YEARLY -> new YearlyPepeat(taskTitle, taskDiscription, taskDate, taskType);
        };
        SKED.addTask(task);
        System.out.println("*************************************************");
        System.out.println("Создана задача: ");
        System.out.printf("Тип задачи: %s.%nДата: %s.%nЗадача: %s.%n" +
                        "Что нужно сделать: %s.%n",
                taskType, taskDate, taskTitle, taskDiscription );
        System.out.println("*************************************************");
        System.out.println();
    }
    private static String readString(String message, Scanner scanner) {
        while (true) {
            System.out.println(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isBlank()) {
                System.out.println("Введено пустое значение!");
            } else {
                return readString;
            }
        }
    }
    private static LocalDateTime readDateTime(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);
    }
    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Дата выполнения задачи в формате дд.мм.гггг: ");
                String dateLine = scanner.nextLine();
                return LocalDate.parse(dateLine, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Не правильно введена дата!");
            }
        }
    }
    private static LocalTime readTime(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Время выполнения задачи формате чч:мм : ");
                String dateLine = scanner.nextLine();
                return LocalTime.parse(dateLine, TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Не правильно введено время!");
            }
        }
    }
    private static void printTaskForDate(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<Task> tasksForDate = SKED.getTasksForDate(localDate);
        System.out.println("*************************************************");
        System.out.println("Задачи на " + localDate.format(DATE_FORMAT) + ":");
        for (Task task: tasksForDate) {
            System.out.printf("Тип задачи: %s. Задача '%s' в %s. (%s)%n",
                    localizeType(task.getTaskType()), task.getTaskTitle(),
                    task.getTaskDateTime().format(TIME_FORMAT), task.getTaskDescription());
        }
        System.out.println("*************************************************");
    }
    private static TaskType readTaskType(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Введите тип задачи: ");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + ". " + localizeType(taskType));
                }
                System.out.println("Введите тип: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен неверный номер типа задачи!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static String localizeType(TaskType taskType) {
        return switch (taskType) {
            case PERSONAL -> "Персональная";
            case CORPORATE -> "Корпоративная";
        };
    }
        private static Repeatability readRepeatability(Scanner scanner) {
            while (true) {
                try {
                    System.out.println("Выберите повторяемость задачи: ");
                    for (Repeatability repeatability : Repeatability.values()) {
                        System.out.println(repeatability.ordinal() + ". " + localizeRepeatability(repeatability));
                    }
                    System.out.println("Введите повторяемость: ");
                    String ordinalLine = scanner.nextLine();
                    int ordinal = Integer.parseInt(ordinalLine);
                    return Repeatability.values()[ordinal];
                } catch (NumberFormatException e) {
                    System.out.println("Введен неверный номер типа задачи!");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Тип задачи не найден");
                }
            }
        }
    private static String localizeRepeatability(Repeatability repeatability) {
        return switch (repeatability) {
            case ONCE -> "Разовая";
            case DAILY -> "Ежедневная";
            case WEEKLY -> "Еженедельная";
            case MONTHLY -> "Ежемесячная";
            case YEARLY -> "Ежегодная";
        };
    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Список всех задач:");
        for (Task task : SKED.getAllTasks()) {
            System.out.printf("%s. Задача: %s, Тип задачи: (%s), Повторяемость: %s%n",
                    task.getId(), task.getTaskTitle(),
                    task.getTaskType(), task.getRepeatabilityType());
        }
        while (true) {
            try {
                System.out.print("Выберете задачу для удаления по id: ");
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine);
                SKED.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Не правильно указан id задачи для удаления!");
            }catch (TaskNotFoundException e) {
                System.out.println("Такая задача не найдена!");
            }
        }
    }
}
