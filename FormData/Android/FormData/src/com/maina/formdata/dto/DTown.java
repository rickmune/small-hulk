package com.maina.formdata.dto;

import java.util.UUID;

public class DTown extends DBase {

	public DTown() {
	}
	
	public DTown(UUID id, String name, UUID parentId, String description,
			String code, String locationStructureName, int locationStructureId) {
		super(id);
		Name = name;
		ParentId = parentId;
		Description = description;
		Code = code;
		LocationStructureName = locationStructureName;
		LocationStructureId = locationStructureId;
	}

	private String Name;
	private UUID ParentId;
	private String Description;
    private String Code;
    private String LocationStructureName;
    private int LocationStructureId;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public UUID getParentId() {
		return ParentId;
	}
	public void setParentId(UUID parentId) {
		ParentId = parentId;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getLocationStructureName() {
		return LocationStructureName;
	}
	public void setLocationStructureName(String locationStructureName) {
		LocationStructureName = locationStructureName;
	}
	public int getLocationStructureId() {
		return LocationStructureId;
	}
	public void setLocationStructureId(int locationStructureId) {
		LocationStructureId = locationStructureId;
	}
	
	
}
