package com.maina.formdatav2.dto;

public class BasicResponse {

	public BasicResponse() {
	}
	public BasicResponse(String info, boolean status) {
		Info = info;
		Status = status;
	}
	public String Info;
    public boolean Status;
}
