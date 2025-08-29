package com.example.cli;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
			System.out.println("4. Вывести задачу по id.");
			System.out.println("5. Редактировать задачу по id.");
			System.out.println("6. Удалить задачу по id.");
			System.out.println("0. Выход.");

			System.out.print("\nВведите номер команды: ");

			try {

				int choice = scanner.nextInt();
				scanner.nextLine(); // очистка буфера

				switch (choice) {
					case 1 -> createTask();
					case 2 -> printTasks();
					// case 3 -> showTaskById();
					// case 4 -> editTask();
					// case 5 -> deleteTask();
					// case 6 -> editPosts();
					case 0 -> {
						System.out.println("Выход ...");
						return;
					}
					default -> System.out.println("Такой команды не существует. Попробуйте снова.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Ошибка ввода, введите номер команды.");
				scanner.nextLine();
			}
		}
	}

	private void createTask() {
		try {
			System.out.print("\nВведите название задачи: ");
			String title = scanner.nextLine();
			if (title.isBlank()) {
				new NullPointerException("Ошибка! Название не должно быть пустым");
			}
			System.out.print("Введите описание задачи (опционально): ");
			String description = scanner.nextLine();
			Task result = TASK_CONTROLLER.create(title, description);
			if (result != null) {
				System.out.println("\nЗадача успешно создана");
			} else {
				System.out.println("\nОшибка создания задачи, попробуйте снова.");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void printTasks() {
		while (true) {
			System.out.println("\n1. Вывести список всех задач.");
			System.out.println("2. Вывести список запланированных задач.");
			System.out.println("3. Вывести список задач в процессе.");
			System.out.println("4. Вывести список готовых задач.");
			System.out.println("5. Вывести задачу по id.");
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
						System.out.println("Выход в главное меню ...");
						return;
					}
					default -> System.out.println("Такой команды не существует. Попробуйте снова.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Ошибка ввода. Попробуйте снова.");
				scanner.nextLine();
			}

		}
	}

	private void printTasks(int num) throws InputMismatchException {
		List<Task> tasks;
		switch (num) {
			case 1, 2, 3, 4 -> {

			}
			case 5 -> {
				try {
					System.out.print("\nВведите ID задачи: ");
					long id = scanner.nextLong();
					scanner.nextLine();

					System.out.println(TASK_CONTROLLER.readById(id));
					return;
				} catch (NullPointerException e) {
					System.out.println("Задачи с таким ID не существует. Попробуйте снова.");
				}
			}
		}
		if (num == 1) {
			tasks = TASK_CONTROLLER.readAll();
		} else {
			tasks = TASK_CONTROLLER.readAll(num - 2);
		}

		if (!tasks.isEmpty()) {
			for (Task task : tasks)
				System.out.println(task.toString());
		} else {
			System.out.println("Список пуст.");
		}
	}
}
