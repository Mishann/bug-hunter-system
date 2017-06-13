package com.app.model;
// Generated 04.05.2017 21:58:56 by Hibernate Tools 5.1.0.Alpha1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Table(name = "answer", catalog = "testing_simulator_db")
public class Answer implements java.io.Serializable {

	private Integer id;
	private Task task;
	private String answer;
	private String variants;
	private String fileLink;

	public Answer() {
	}

	public Answer(Task task) {
		this.task = task;
	}

	public Answer(Task task, String answer, String variants, String fileLink) {
		this.task = task;
		this.answer = answer;
		this.variants = variants;
		this.fileLink = fileLink;
	}

//	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "task"))	
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Id	
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(mappedBy = "answer")
	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Column(name = "answer", length = 65535)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "variants")
	public String getVariants() {
		return this.variants;
	}

	public void setVariants(String variants) {
		this.variants = variants;
	}

	@Column(name = "file_link", length = 100)
	public String getFileLink() {
		return this.fileLink;
	}

	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}

}
