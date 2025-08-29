package com.example.repository;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class GsonTaskRepositoryImpl implements TaskRepository {

	private final IdGenerator ID_GENERATOR;
	private static final DateTimeFormatter ISO_DTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(LocalDateTime.class,
					(JsonSerializer<LocalDateTime>) (src, type, ctx) -> new JsonPrimitive(src.format(ISO_DTF)))
			.registerTypeAdapter(LocalDateTime.class,
					(JsonDeserializer<LocalDateTime>) (json, type, ctx) -> LocalDateTime.parse(json.getAsString(),
							ISO_DTF))
			.setPrettyPrinting()
			.create();
	private static final String FILE_PATH = Paths.get("data", "TaskRepo.json").toString();
	private static final Type LIST_TYPE_TOKEN = new TypeToken<List<Task>>() {
	}.getType();

	public GsonTaskRepositoryImpl() {
		this.ID_GENERATOR = new IdGenerator(getMaxId());
	}

	@Override
	public Task getById(Long id) {
		List<Task> tasks = readFromJson();

		return tasks.stream()
				.filter(e -> e.getId() == id)
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Task> getAll() {
		return readFromJson();
	}

	@Override
	public Task save(Task entity) {
		try {
			List<Task> tasks = readFromJson();
			// generate uniq id for a new task
			if (entity.getId() == 0L) {
				entity.setId(ID_GENERATOR.nextId());
			}
			tasks.add(entity);
			Files.write(Paths.get(FILE_PATH), GSON.toJson(tasks, LIST_TYPE_TOKEN).getBytes());
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
					Files.write(Paths.get(FILE_PATH), GSON.toJson(tasks, LIST_TYPE_TOKEN).getBytes());
					return entity;
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Task deleteById(Long id) {
		try {
			List<Task> tasks = readFromJson();
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == id) {
					Task task = tasks.remove(i);
					Files.write(Paths.get(FILE_PATH), GSON.toJson(tasks, LIST_TYPE_TOKEN).getBytes());
					return task;
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	private List<Task> readFromJson() {
		List<Task> tasks;

		// checking existence of the json data file
		if (!Files.exists(Paths.get(FILE_PATH))) {
			try {
				Files.createFile(Paths.get(FILE_PATH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			tasks = new ArrayList<>();
		}

		// reading tasks from data file
		try (Reader reader = new FileReader(FILE_PATH)) {
			tasks = GSON.fromJson(reader, LIST_TYPE_TOKEN);
		} catch (IOException e) {
			tasks = new ArrayList<>();
		}

		// checking for null
		if (tasks == null) {
			return tasks = new ArrayList<>();
		}
		return tasks;
	}

	private long getMaxId() {
		List<Task> tasks = readFromJson();
		if (tasks.size() > 0) {
			return tasks.get(tasks.size() - 1).getId();
		}
		return 0;
	}

}
