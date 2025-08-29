package com.example.repository;

public class IdGenerator {

	private long maxId;

	public IdGenerator(long maxId) {
		this.maxId = maxId;
	}

	public long nextId() {
		return ++maxId;
	}
}
