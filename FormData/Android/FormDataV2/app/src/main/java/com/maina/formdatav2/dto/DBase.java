package com.maina.formdatav2.dto;

import java.util.UUID;

public class DBase {

	public DBase() {
	}

	public DBase(UUID id) {
		Id = id;
	}

	private UUID Id;

	public UUID getId() {
		return Id;
	}

	public void setId(UUID id) {
		Id = id;
	}
}
