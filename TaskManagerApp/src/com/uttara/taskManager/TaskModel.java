package com.uttara.taskManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TaskModel {

	public boolean checkCategoryExists(String catName) {
		return new File(catName + ".todo").exists();
	}
	public boolean creatCategory(String catName) {
		File f = new  File(catName+".todo");
		try {
			if(f.exists())
				return false;
			else {
				f.createNewFile();
				return true;
			}
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public String addTask(TaskBean task, String catName) {
		BufferedWriter bw = null;
		BufferedReader br = null;
		File path = new File(catName + ".todo");
		try {
			if (path.exists()) {
				br = new BufferedReader(new FileReader(path));
				String line;
				while ((line = br.readLine()) != null) {
					String[] tasks = line.split(":");
					if (task.getTaskName().equals(tasks[0]))
						return Constants.FAILED;
				}

			}
			bw = new BufferedWriter(new FileWriter(catName + ".todo", true));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String plannedDate = sdf.format(task.getSpldt());
			bw.write(task.getTaskName() + ":" + task.getDesc() + ":" + task.getStatus() + ":" + plannedDate + ":"
					+ task.getPriority() + ":" + task.getTags() + ":" + task.getCreatedDate());
			bw.newLine();

			return Constants.SUCCESS;
		}

		catch (IOException e) {

			e.printStackTrace();
			return "oops something happened message = " + e.getMessage();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<TaskBean> listTask(String catName) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(catName + ".todo"));
			String line;
			TaskBean task;
			List<TaskBean> tasks = new ArrayList<TaskBean>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while ((line = br.readLine()) != null) {

				String[] sa = line.split(":");
				int priority = Integer.parseInt(sa[4]);
				task = new TaskBean(sa[0], sa[1], sa[2], sa[5], sdf.parse(sa[3]), priority, sa[6]);
				tasks.add(task);
			}
			return tasks;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean removeTask(String taskName, String catName) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean result = false;
		try {
			br = new BufferedReader(new FileReader(catName + ".todo"));
			String line;
			List<String> tasks = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				tasks.add(line);

			}
			br.close();
			bw = new BufferedWriter(new FileWriter(catName + ".todo"));
			Iterator<String> it = tasks.iterator();
			z: while (it.hasNext()) {
				String task = it.next();
				String[] name = task.split(":");
				if (name[0].equals(taskName)) {
					tasks.remove(task);
					result = true;
					break z;
				}
			}
			for (String write : tasks) {
				bw.write(write);
				bw.newLine();
			}
			return result;
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<TaskBean> searchTask(String taskName, String catName) {
		BufferedReader br = null;
		TaskBean task = null;
		List<TaskBean> occTask = new ArrayList<TaskBean>();
		try {
			br = new BufferedReader(new FileReader(catName + ".todo"));
			String line;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while ((line = br.readLine()) != null) {
				String[] name = line.split(":");
				int priority = Integer.parseInt(name[4]);
				for (int i = 0; i < name.length; i++) {
					if (name[i].equals(taskName)) {

						task = new TaskBean(name[0], name[1], name[2], name[5], sdf.parse(name[3]), priority, name[6]);
						occTask.add(task);
					}
				}
			}
			return occTask;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public String update(TaskBean old, TaskBean New, String catName) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			br = new BufferedReader(new FileReader(catName + ".todo"));
			String oldTask = old.getTaskName();
			String line;
			String newDate = format.format(New.getSpldt());
			List<String> updt = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				String[] tskNm = line.split(":");
				if (oldTask.equals(tskNm[0])) {
				
					line = New.getTaskName() + ":" + New.getDesc() + ":" + New.getStatus() + ":" + newDate+ ":"
							+ New.getPriority() + ":" + New.getTags() + ":" + old.getCreatedDate();
				}
				updt.add(line);
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(catName + ".todo"));
			for (String write : updt) {
				bw.write(write);
				bw.newLine();
			}
			return Constants.UPDATESTATUS;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean deleteCat(String catName) {
		boolean result = false;
		File f = new File(catName + ".todo");
		if (f.exists()) {
			f.delete();
			result = true;
		}
		return result;

	}

	public File[] listCat() {
		File path = new File("/home/abhi/eclipse-workspace/TaskManagerApp");
		if (path.isDirectory()) {
			File[] paths = path.listFiles();
			return paths;
		} else
			return null;
	}

	public boolean searchCat(String catName) {
		File isExists = new File(catName + ".todo");
		if (isExists.exists())
			return true;
		else
			return false;
	}

}
