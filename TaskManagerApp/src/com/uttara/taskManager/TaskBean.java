package com.uttara.taskManager;

import java.util.Date;

public class TaskBean implements Comparable<TaskBean> {
	private String taskName;
	private Date spldt;
	private String desc;
	private String status;
	private String tags;
	private int priority;
	private String createdDate;
	
	public TaskBean() {
		
	}

	public TaskBean(String taskName, String desc, String status, String tags, Date spldt, int priority,String createdDate) {
		super();
		this.taskName = taskName;
		this.spldt = spldt;
		this.desc = desc;
		this.status = status;
		this.tags = tags;
		this.priority = priority;
		this.createdDate = createdDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getSpldt() {
		return spldt;
	}

	public void setSpldt(Date spldt) {
		this.spldt = spldt;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TaskBean) {
			TaskBean b = (TaskBean)obj;
			if(this.taskName.equals(b.taskName)&&this.desc.equals(b.desc)&&this.spldt.equals(b.spldt)&&this.tags.equals(b.tags)&&this.priority==b.priority)
				return true;
			return false;
		}
		return false;
	}
	
	public int hashCode() {
		return(taskName+desc+priority+spldt+tags).hashCode();
	}

	public String toString() {
		return "TaskBean [taskName=" + taskName + ", spldt=" + spldt + ", desc=" + desc + ", tags=" + tags
				+ ", priority=" + priority + "]";
	}
	


	public int compareTo(TaskBean bean) {
		if(this.priority>bean.priority)
			return 1;
		if(this.priority==bean.priority)
			return 0;
		else
			return -1;

	}
	
}
