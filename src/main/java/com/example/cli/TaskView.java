package com.example.cli;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.example.model.Status;
import com.example.model.Task;
import com.example.service.TaskController;

public class TaskView {

	private final TaskController TASK_CONTROLLER;
	private Scanner scanner;

	public TaskView(TaskController taskController, Scanner scanner) {
		this.TASK_CONTROLLER = taskController;
		this.scanner = scanner;
	}

	public void handleMainMenu() {
		while (true) {
			System.out.println("\n=== Меню управления задачами ===");
			System.out.println("1. Добавить новую задачу.");
			System.out.println("2. Вывести список задач.");
			System.out.println("3. Редактировать задачу по ID.");
			System.out.println("4. Удалить задачу по ID.");
			System.out.println("0. Выход.");

			System.out.print("\nВведите номер команды: ");

			try {
				int choice = scanner.nextInt();
				scanner.nextLine(); // очистка буфера

				switch (choice) {
					case 1 -> createTask();
					case 2 -> printTasks();
					case 3 -> updateTask();
					case 4 -> deleteTask();
					case 0 -> {
						System.out.println("\nВыход ...");
						return;
					}
					default -> System.out.println("\nТакой команды не существует. Попробуйте снова.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nОшибка ввода, введите номер команды.");
				scanner.nextLine();
			}
		}
	}

	private void createTask() {
		try {
			System.out.print("\nВведите название задачи: ");
			String title = scanner.nextLine().trim();
			if (title.isBlank()) {
				throw new IllegalArgumentException();
			}
			System.out.print("Введите описание задачи (опционально): ");
			String description = scanner.nextLine().trim();
			Task result = TASK_CONTROLLER.create(title, description);
			if (result != null) {
				System.out.println("\nЗадача успешно создана");
			} else {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("\nОшибка создания задачи, попробуйте снова.");
			System.out.println("Возврат в главное меню...");
		} catch (IllegalArgumentException e) {
			System.out.println("\nОшибка! Название не должно быть пустым");
			System.out.println("Возврат в главное меню...");
		}
	}

	private void printTasks() {
		System.out.println("\n=== Меню отображения задач ===");
		System.out.println("1. Вывести список всех задач.");
		System.out.println("2. Вывести список запланированных задач.");
		System.out.println("3. Вывести список задач в процессе.");
		System.out.println("4. Вывести список готовых задач.");
		System.out.println("5. Вывести задачу по ID.");
		System.out.println("0. Выход в главное меню.");

		System.out.print("\nВведите номер команды: ");

		try {
			int choice = scanner.nextInt();
			scanner.nextLine(); // очистка буфера

			switch (choice) {
				case 1, 2, 3, 4, 5 -> {
					printTasks(choice);
				}
				case 0 -> {
					System.out.println("\nВозврат в главное меню ...");
					return;
				}
				default -> {
					System.out.println("\nТакой команды не существует. Попробуйте снова.");
					System.out.println("Возврат в главное меню ...");
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("\nОшибка ввода. Попробуйте снова.");
			System.out.println("Возврат в главное меню ...");
			scanner.nextLine();
		}
	}

	private void printTasks(int num) throws InputMismatchException {
		List<Task> tasks;
		switch (num) {
			case 1, 2, 3, 4 -> {
				if (num == 1)
					tasks = TASK_CONTROLLER.readAll();
				else
					tasks = TASK_CONTROLLER.readAll(num - 2);

				if (!tasks.isEmpty()) {
					for (Task task : tasks)
						System.out.println(task.toString());
				} else {
					System.out.println("\nСписок пуст.");
				}
			}
			case 5 -> {
				System.out.print("\nВведите ID задачи: ");
				long id = scanner.nextLong();
				scanner.nextLine();

				Task task = TASK_CONTROLLER.readById(id);
				if (task != null) {
					System.out.println(task.toStringFull());
				} else {
					System.out.println("\nЗадачи с таким ID не существует. Попробуйте снова.");
				}
			}
		}

	}

	private void updateTask() {
		try {
			System.out.print("\nВведите ID задачи: ");
			long id = scanner.nextLong();
			scanner.nextLine(); // очистка буфера

			Task task = TASK_CONTROLLER.readById(id);

			if (task == null) {
				System.out.println("\nЗадачи с таким ID не существует.");
				System.out.println("Возврат в главное меню...");
				return;
			}

			System.out.println("\n=== Меню редактирования задачи ===");
			System.out.println("1. Обновить статус.");
			System.out.println("2. Изменить название.");
			System.out.println("3. Изменить описание.");
			System.out.println("4. Изменить все параметры задачи.");
			System.out.println("0. Выход в главное меню.");

			System.out.print("\nВведите номер команды: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // очистка буфера

			switch (choice) {
				case 1 -> {
					System.out.println("\n=== Меню выбора статуса ===");
					System.out.println("1. Задача в процессе выполнения.");
					System.out.println("2. Задача выполнена.");
					System.out.println("3. Задача запланирована.");
					System.out.println("0. Выход в главное меню.");
					System.out.print("\nВведите номер команды: ");

					int choice2 = scanner.nextInt();
					Status status = Status.TODO;
					scanner.nextLine(); // очистка буфера

					switch (choice2) {
						case 1 -> {
							status = Status.IN_PROGRESS;
						}
						case 2 -> {
							status = Status.DONE;
						}
						case 3 -> {
							status = Status.TODO;
						}
						case 0 -> {
							System.out.println("\nВозврат в главное меню ...");
							return;
						}
						default -> {
							System.out.println("\nТакой команды не существует. Попробуйте снова.");
							System.out.println("Возврат в главное меню...");
							return;
						}
					}
					TASK_CONTROLLER.updateById(id, null, null, status);
				}
				case 2 -> {
					System.out.print("\nВведите новое название для задачи: ");
					String title = scanner.nextLine().trim();
					scanner.nextLine();

					if (title.isBlank()) {
						throw new IllegalArgumentException("\nОшибка! Название не должно быть пустым");
					}

					TASK_CONTROLLER.updateById(id, title, null, null);
				}
				case 3 -> {
					System.out.print("\nВведите новое описание для задачи: ");
					String description = scanner.nextLine().trim();
					scanner.nextLine();

					TASK_CONTROLLER.updateById(id, null, description, null);
				}
				case 4 -> {
					System.out.println("\n=== Меню выбора статуса ===");
					System.out.println("1. Задача в процессе выполнения.");
					System.out.println("2. Задача выполнена.");
					System.out.println("3. Задача запланирована.");
					System.out.println("0. Выход в главное меню.");
					System.out.print("\nВведите номер команды: ");

					int choice2 = scanner.nextInt();
					Status status = Status.TODO;
					scanner.nextLine(); // очистка буфера

					switch (choice2) {
						case 1 -> {
							status = Status.IN_PROGRESS;
						}
						case 2 -> {
							status = Status.DONE;
						}
						case 3 -> {
							status = Status.TODO;
						}
						case 0 -> {
							System.out.println("\nВозврат в главное меню ...");
							return;
						}
						default -> {
							System.out.println("\nТакой команды не существует. Попробуйте снова.");
							System.out.println("Возврат в главное меню...");
							return;
						}
					}

					System.out.print("\nВведите новое название для задачи: ");
					String title = scanner.nextLine();

					if (title.isBlank()) {
						throw new IllegalArgumentException();
					}

					System.out.print("\nВведите новое описание для задачи: ");
					String description = scanner.nextLine();

					TASK_CONTROLLER.updateById(id, title, description, status);
				}
				case 0 -> {
					System.out.println("\nВозврат в главное меню ...");
					return;
				}
				default -> {
					System.out.println("\nТакой команды не существует. Попробуйте снова.");
					System.out.println("Возврат в главное меню...");
					return;
				}
			}
			System.out.println("\nЗадача успешно обновлена");
			System.out.println("Возврат в главное меню...");
		} catch (InputMismatchException e) {
			System.out.println("\nОшибка ввода. Попробуйте снова.");
			System.out.println("Возврат в главное меню...");
			scanner.nextLine();
		} catch (IllegalArgumentException e) {
			System.out.println("\nОшибка! Название не должно быть пустым");
			System.out.println("Возврат в главное меню...");
		}
	}

	private void deleteTask() {
		try {
			System.out.print("Введите ID задачи: ");
			long id = scanner.nextLong();
			scanner.nextLine();

			Task task = TASK_CONTROLLER.delete(id);

			if (task == null) {
				System.out.println("\nЗадачи с таким ID не существует.");
				System.out.println("Возврат в главное меню...");
				return;
			}
			System.out.println("\nЗадача успешно удалена.");
			System.out.println("Возврат в главное меню...");
		} catch (InputMismatchException e) {
			System.out.println("\nОшибка ввода. Попробуйте снова.");
			System.out.println("Возврат в главное меню...");
			scanner.nextLine();
		}
	}
}
