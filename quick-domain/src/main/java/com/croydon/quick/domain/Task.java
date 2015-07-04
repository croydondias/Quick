package com.croydon.quick.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_gen")
	@SequenceGenerator(name = "task_seq_gen", sequenceName = "task_id_seq")
	private Long id;
	
	@JoinColumn
	private Long project_id;
	@JoinColumn
	private Long employee_id;
	
	private String name;
	private String description;
	private Boolean done;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
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
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", project_id=" + project_id + ", employee_id=" + employee_id + ", name=" + name
				+ ", description=" + description + ", done=" + done + "]";
	}
	
}
