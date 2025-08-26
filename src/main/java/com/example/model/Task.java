package com.example.model;

import java.time.LocalDateTime;

public class Task {

	public Task(String title, String description, int id) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = Status.TODO;
		this.createdDateTime = LocalDateTime.now();
	}

	private final int id;
	private String title;
	private String description;
	private Status status;
	private LocalDateTime createdDateTime;
	private LocalDateTime updatedDateTime;

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.updatedDateTime = LocalDateTime.now();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
		this.updatedDateTime = LocalDateTime.now();
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
		this.updatedDateTime = LocalDateTime.now();
	}

	public LocalDateTime getCreatedDateTime() {
		return this.createdDateTime;
	}

	public LocalDateTime getUpdatedDateTime() {
		return this.updatedDateTime;
	}
}
