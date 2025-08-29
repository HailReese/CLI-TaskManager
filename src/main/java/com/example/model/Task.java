package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

	private long id;
	private String title;
	private String description;
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public Task(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = Status.TODO;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = createdAt;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.updatedAt = LocalDateTime.now();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
		this.updatedAt = LocalDateTime.now();
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
		this.updatedAt = LocalDateTime.now();
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	@Override
	public String toString() {
		return String.format(
				"ID: %d | Title: %s | Status: %s | Created: %s | Updated: %s",
				id, title, status, createdAt.format(FORMATTER), updatedAt.format(FORMATTER));
	}

	public String toStringFull() {
		return String.format(
				"ID: %d | Title: %s | Description: %s | Status: %s | Created: %s | Updated: %s",
				id, title, description, status, createdAt.format(FORMATTER), updatedAt.format(FORMATTER));
	}
}
