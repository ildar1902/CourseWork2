import DailyPlanner.*;
import exception.IdNotFoundException;
import service.TaskService;
import util.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");

    public static void main(String[] args) {
        System.out.println("    Курсовая работа. 2 курс. Создание ежедневника");
        System.out.println();
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.useDelimiter("\n");
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                            break;
                        case 3:
                            showTaskForDay(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void showTaskForDay(Scanner scanner) {
        do {
            System.out.println("Введите дату в формате " + LocalDate.now().format(Constant.DATE_FORMATTER) + ":");
            if (scanner.hasNext(DATE_PATTERN)) {
                LocalDate date = parseDate(scanner.next(DATE_PATTERN));
                if (date == null) {
                    System.out.println("Неверный формат даты!");
                    continue;
                }
                Collection<Task> tasksForDay = TaskService.getTasksForDay(date);
                if (tasksForDay.isEmpty()) {
                    System.out.println("Задачи на " + date.format(Constant.DATE_FORMATTER) + " не найдены!");
                } else {
                    System.out.println("задачи на " + date.format(Constant.DATE_FORMATTER) + ":\n");
                    for (Task task : tasksForDay) {
                        System.out.println(task);
                    }
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
    }


    private static void inputTask(Scanner scanner) {
        try {
            System.out.print("Введите название задачи: ");
            String heading = scanner.next();
            System.out.print("Введите описание задачи: ");
            String description = scanner.next();
            TaskType type = enterTaskType(scanner);
            LocalDateTime dateTime = enterDateTime(scanner);
            Repeatability repeatability = enterRepeatability(scanner);
            Task task = new Task(heading, description, type, dateTime, repeatability);
            TaskService.add(task);
            System.out.println("Задача успешно добавлена");
            System.out.println(task);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void printMenu() {
        System.out.println(" 1. Добавить задачу\n" +
                " 2. Удалить задачу\n" +
                " 3. Получить задачу на указанный день\n" +
                " 0. Выход");
    }

    private static TaskType enterTaskType(Scanner scanner) {
        TaskType type;
        do {
            System.out.println(
                    "Введите тип задачи:\n" +
                            " 1. Личная\n" +
                            " 2. Рабочая\n" +
                            " Тип задачи: ");
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                if (number != 1 && number != 2) {
                    System.out.println("Введите 1 или 2!");
                    continue;
                }
                if (number == 1) {
                    type = TaskType.PERSONAL;
                } else {
                    type = TaskType.WORKING;
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
        return type;
    }

    private static LocalDateTime enterDateTime(Scanner scanner) {
        LocalDateTime dateTime;
        do {
            System.out.println("Введите дату и время задачи в формате 25.12.2022 20:25: ");
            if (scanner.hasNext(DATE_TIME_PATTERN)) {
                dateTime = parseDateTime(scanner.next(DATE_TIME_PATTERN));
                if (dateTime == null) {
                    System.out.println("Неверный формат даты и времени!");
                    continue;
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
        return dateTime;
    }

    private static LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, Constant.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, Constant.DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static Repeatability enterRepeatability(Scanner scanner) {
        Repeatability repeatability;
        do {
            System.out.println("Выберите тип повторяемости:" +
                    "\n 1. Однократная " +
                    "\n 2. Ежедневная " +
                    "\n 3. Еженедельная " +
                    "\n 4. Ежемесячная " +
                    "\n 5. Ежегодная ");
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                if (number < 1 || number > 5) {
                    System.out.println("Введите цифру от 1 до 5!");
                    continue;
                }
                switch (number) {
                    default:
                    case 1:
                        repeatability = new OneTime();
                        break;
                    case 2:
                        repeatability = new Daily();
                        break;
                    case 3:
                        repeatability = new Weekly();
                        break;
                    case 4:
                        repeatability = new Monthly();
                        break;
                    case 5:
                        repeatability = new Yearly();
                        break;
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
        return repeatability;
    }

    private static void removeTask(Scanner scanner) {
        try {
            do {
                System.out.println("Введите id задачи: ");
                if (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    TaskService.deleteTaskById(id);
                    System.out.println("Задача с id " + id + " удалена!");
                    break;
                } else {
                    scanner.next();
                }
            } while (true);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}