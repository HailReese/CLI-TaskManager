package com.example.service;

import java.util.List;

import com.example.model.Status;
import com.example.model.Task;
import com.example.repository.TaskRepository;

public class TaskController {

	private TaskRepository taskRepo;

	public TaskController(TaskRepository taskRepo) {
		this.taskRepo = taskRepo;
	}

	public Task create(String title, String description) {
		return taskRepo.save(new Task(title, description));
	}

	public List<Task> readAll() {
		return taskRepo.getAll();
	}

	public List<Task> readAll(int num) {
		Status status;

		switch (num) {
			case 0 -> status = Status.TODO;
			case 1 -> status = Status.IN_PROGRESS;
			case 2 -> status = Status.DONE;
			default -> status = Status.TODO;
		}

		return taskRepo.getAll().stream()
				.filter(e -> e.getStatus().equals(status))
				.toList();
	}

	public Task readById(Long id) {
		return taskRepo.getById(id);
	}

	public Task updateById(Long id, String title, String description) {
		Task entity = readById(id);
		if (entity != null) {
			entity.setTitle(title);
			entity.setDescription(description);
			return taskRepo.update(entity);
		}
		return null;
	}

	public Task updateById(Long id, Status status) {
		Task entity = readById(id);
		if (entity != null) {
			entity.setStatus(status);
			return taskRepo.update(entity);
		}
		return null;
	}

	public Task delete(Long id) {
		return taskRepo.deleteById(id);
	}

}
