package com.example.repository;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTaskRepositoryImpl implements TaskRepository {

	private static final String filePath = Paths.get("data", "WriterRepo.json").toString();
	private static final Type listTypeToken = new TypeToken<List<Writer>>() {
	}.getType();
	private final Gson gson = new Gson();

	@Override
	public Task getById(Long id) {
		List<Task> tasks = readFromJson();

		return tasks.stream()
				.filter(e -> e.getId() == id)
				.findFirst()
				.get();
	}

	@Override
	public List<Task> getAll() {
		return readFromJson();
	}

	@Override
	public Task save(Task entity) {
		try {
			List<Task> tasks = readFromJson();
			tasks.add(entity);
			Files.write(Paths.get(filePath), gson.toJson(tasks, listTypeToken).getBytes());
			return entity;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Task update(Task entity) {
		try {
			List<Task> tasks = readFromJson();

			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == entity.getId()) {
					tasks.set(i, entity);
					Files.write(Paths.get(filePath), gson.toJson(tasks, listTypeToken).getBytes());
					return entity;
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			List<Task> tasks = readFromJson();
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == id) {
					tasks.remove(i);
					Files.write(Paths.get(filePath), gson.toJson(tasks, listTypeToken).getBytes());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Task> readFromJson() {
		List<Task> tasks;
		if (!Files.exists(Paths.get(filePath))) {
			try {
				Files.createFile(Paths.get(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			tasks = new ArrayList<>();
		}

		try (Reader reader = new FileReader(filePath)) {
			tasks = gson.fromJson(reader, listTypeToken);
		} catch (IOException e) {
			tasks = new ArrayList<>();
		}

		if (tasks == null) {
			return tasks = new ArrayList<>();
		}
		return tasks;
	}

}
