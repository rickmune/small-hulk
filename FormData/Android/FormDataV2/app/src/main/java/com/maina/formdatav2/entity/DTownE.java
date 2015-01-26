package com.maina.formdatav2.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class DTownE extends DBaseE {

	public DTownE() {
	}
			
	public DTownE(UUID id, String name, UUID parentId, String description,
			String code, String locationStructureName, int locationStructureId) {
		super(id);
		Name = name;
		ParentId = parentId;
		Description = description;
		Code = code;
		LocationStructureName = locationStructureName;
		LocationStructureId = locationStructureId;
	}

	@DatabaseField
	private String Name;
	@DatabaseField
	private UUID ParentId;
	@DatabaseField
	private String Description;
	@DatabaseField
    private String Code;
	@DatabaseField
    private String LocationStructureName;
	@DatabaseField
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
