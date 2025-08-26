package com.example.repository;

public class IdGenerator {

	public IdGenerator(long maxId) {
		this.maxId = maxId;
	}

	private long maxId;

	public long nextId() {
		return ++maxId;
	}
}
