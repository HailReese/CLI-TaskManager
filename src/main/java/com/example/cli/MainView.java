package com.example.cli;

import java.util.Scanner;

import com.example.repository.GsonTaskRepositoryImpl;
import com.example.repository.TaskRepository;
import com.example.service.TaskController;

public class MainView {
	private final Scanner scanner;
	private final TaskRepository TASK_REPOSITORY;
	private final TaskController TASK_CONTROLLER;
	private final TaskView TASK_VIEW;

	public MainView() {
		this.scanner = new Scanner(System.in);
		this.TASK_REPOSITORY = new GsonTaskRepositoryImpl();
		this.TASK_CONTROLLER = new TaskController(TASK_REPOSITORY);
		this.TASK_VIEW = new TaskView(TASK_CONTROLLER, scanner);
	}

	public void handleView() {
		TASK_VIEW.handleMainMenu();
	}
}
