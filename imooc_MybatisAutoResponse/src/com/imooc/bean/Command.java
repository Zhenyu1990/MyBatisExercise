package com.imooc.bean;

import java.util.List;

public class Command {

	private String id;
	private String name;
	private String description;
	// 一对多自动回复内容列表
	private List<CommandContent> contList;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<CommandContent> getContList() {
		return contList;
	}
	
	public void setContList(List<CommandContent> contList) {
		this.contList = contList;
	}
	
}
