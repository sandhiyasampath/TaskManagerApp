package com.uttara.taskManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskUtil {
	public static boolean validateCatName(String name) {
		if (name == null)
			return false;
		if (name.trim().equals(""))
			return false;
		if (!Character.isLetter(name.charAt(0)))
			return false;
		if (name.split(" ").length > 1)
			return false;
		// Business validation
		for (int i = 1; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isLetter(c) || Character.isDigit(c)))
				return false;
		}
		return true;
	}

	public static void taskToDo(String catName) {
		try {
			Scanner sc1 = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
			int choice2 = 0, priority = 0, choice3 = 0, nwprty = 0;
			String taskName, desc, tags, spldt,keyWordToSearch;
			TaskModel model = new TaskModel();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Logger.getInstance().log("Starting Task Manager..........");

			while (choice2 != 6) {
				sc1 = new Scanner(System.in);
				System.out.println();
				System.out.println("Press 1 to create task");
				System.out.println("Press 2 to update task");
				System.out.println("Press 3 to remove task");
				System.out.println("Press 4 to list task");
				System.out.println("Press 5 to search task");
				System.out.println("Press 6 to go back");
				if (!sc1.hasNextInt()) {
					System.err.println("Enter only integers");
				} else {

					choice2 = sc1.nextInt();
				}

				switch (choice2) {
				case 1:
					System.out.println("case - 1 for task");

					Logger.getInstance().log("Creating Task.........");
					System.out.println("Enter task name");
					taskName = sc2.nextLine();
					System.out.println("Enter Description about the task");
					desc = sc2.nextLine();
					System.out.println("Enter tags(comma seperated tags)");
					tags = sc2.nextLine();
					System.out.println("Enter Planed End date(dd/mm/yyyy)");
					spldt = sc2.nextLine();
					System.out.println("Enter priority (1-very low, 10- very high)");
					if (!sc1.hasNextInt()) {
						sc1 = new Scanner(System.in);
						System.err.println("Enter only integers");
					} else {
						priority = sc1.nextInt();
					}

					Date dt1 = sdf.parse(spldt);
					DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String strDate = formatter.format(date);
					System.out.println(strDate);
					TaskBean bean = new TaskBean(taskName, desc, "in-progress", tags, dt1, priority, strDate);

					String result = model.addTask(bean, catName);
					if (result.equals(Constants.FAILED))
						System.out.println("Task Name should be unique");
					else if (result.equals(Constants.SUCCESS))
						System.out.println("Task " + taskName + " got added successfully");
					else
						System.out.println("Task addition faile because " + result);

					break;
				case 2:
					System.out.println("case - 2 for task");
					System.out.println("Enter task name, you want to edit or update");
					String updtTaskName = sc2.nextLine();
					TaskBean oldTask = null;
					List<TaskBean> schTsk = model.searchTask(updtTaskName, catName);
					if (schTsk.isEmpty() || schTsk == null) {
						System.out.println("Task is not exists to edit or update ");
						break;
					} else {
						for (TaskBean tsk : schTsk) {
							oldTask = tsk;
						}
					}
					while (choice3 == 0) {
						sc1 = new Scanner(System.in);
						System.out.println("What you want to edit");
						System.out.println();
						System.out.println("Press 1 to edit Task name");
						System.out.println("Press 2 to edit Description");
						System.out.println("Press 3 to edit Tags");
						System.out.println("Press 4 to edit Planned end date");
						System.out.println("Press 5 to edit priority");
						System.out.println("Press 6 to edit status");
						System.out.println("press 7 to goback");
						if (!sc1.hasNextInt()) {
							System.err.println("Enter only integers");
						} else {

							choice3 = sc1.nextInt();
						}
					}
					TaskBean newTask;
					String status;
					if (oldTask != null) {
						switch (choice3) {
						case 1:
							System.out.println("case - 1 for task in case 2");

							System.out.println("Enter new Task Name to update");
							String nwTsk = sc2.nextLine();
							newTask = new TaskBean(nwTsk, oldTask.getDesc(), oldTask.getStatus(), oldTask.getTags(),
									oldTask.getSpldt(), oldTask.getPriority(), oldTask.getCreatedDate());
							status = model.update(oldTask, newTask, catName);
							if (status == Constants.UPDATESTATUS)
								System.out.println("Task updated successfully");
							else
								System.out.println("oops something happened wrong " + status);
							break;
						case 2:
							System.out.println("case - 2 for task in case 2");

							System.out.println("Enter new Task Description to update");
							String nwDisc = sc2.nextLine();
							newTask = new TaskBean(oldTask.getTaskName(), nwDisc, oldTask.getStatus(),
									oldTask.getTags(), oldTask.getSpldt(), oldTask.getPriority(),
									oldTask.getCreatedDate());
							status = model.update(oldTask, newTask, catName);
							if (status == Constants.UPDATESTATUS)
								System.out.println("Task updated successfully");
							else
								System.out.println("oops something happened wrong " + status);

							break;

						case 3:
							System.out.println("case - 3 for task in case 2");

							System.out.println("Enter new tag to update");
							String nwtg = sc2.nextLine();
							newTask = new TaskBean(oldTask.getTaskName(), oldTask.getDesc(), oldTask.getStatus(), nwtg,
									oldTask.getSpldt(), oldTask.getPriority(), oldTask.getCreatedDate());
							status = model.update(oldTask, newTask, catName);
							if (status == Constants.UPDATESTATUS)
								System.out.println("Task updated successfully");
							else
								System.out.println("oops something happened wrong " + status);

							break;

						case 4:
							System.out.println("case - 4 for task in case 2");

							System.out.println("Enter new Planned End date to update");
							String nwdt = sc2.nextLine();
							Date nwpled = sdf.parse(nwdt);
							newTask = new TaskBean(oldTask.getTaskName(), oldTask.getDesc(), oldTask.getStatus(),
									oldTask.getTags(), nwpled, oldTask.getPriority(), oldTask.getCreatedDate());
							status = model.update(oldTask, newTask, catName);
							if (status == Constants.UPDATESTATUS)
								System.out.println("Task updated successfully");
							else
								System.out.println("oops something happened wrong " + status);

							break;

						case 5:
							System.out.println("case - 5 for task in case 2");

							System.out.println("Enter new priority to update");
							while (nwprty == 0) {
								if (!sc1.hasNextInt()) {
									System.err.println("Enter only integers");
								} else {
									nwprty = sc1.nextInt();
								}
							}
							newTask = new TaskBean(oldTask.getTaskName(), oldTask.getDesc(), oldTask.getStatus(),
									oldTask.getTags(), oldTask.getSpldt(), nwprty, oldTask.getCreatedDate());
							status = model.update(oldTask, newTask, catName);
							if (status == Constants.UPDATESTATUS)
								System.out.println("Task updated successfully");
							else
								System.out.println("oops something happened wrong " + status);

							break;

						case 6:
							System.out.println("case - 6 for task in case 2");

							System.out.println("Enter new status to update");
							String nwSts = sc2.nextLine();
							newTask = new TaskBean(oldTask.getTaskName(), oldTask.getDesc(), nwSts, oldTask.getTags(),
									oldTask.getSpldt(), oldTask.getPriority(), oldTask.getCreatedDate());
							status = model.update(oldTask, newTask, catName);
							if (status == Constants.UPDATESTATUS)
								System.out.println("Task updated successfully");
							else
								System.out.println("oops something happened wrong " + status);

							break;

						case 7:
							System.out.println("case - 7 for task in case 2");

							System.out.println("going back....");
							break;
						default:
							break;
						}
					}
					break;
				case 3:
					System.out.println("case - 2 for task");

					System.out.println("Enter a task name you want to remove");
					String rmvTaskName = sc2.nextLine();

					List<TaskBean> name = model.searchTask(rmvTaskName, catName);
					if (name != null) {
						boolean isExist = model.removeTask(rmvTaskName, catName);
						if (isExist == false)
							System.out.println("This task is not exist to delete");
						else
							System.out.println("The Task " + rmvTaskName + " removed successfully");
					} else
						System.out.println("Task " + rmvTaskName + " Not exists to delete");
					break;
				case 4:
					System.out.println("case - 4 for task");
					List<TaskBean> listOfTasks = model.listTask(catName);
					if (listOfTasks.isEmpty() || listOfTasks == null)
						System.out.println("Add some task to list");
					else {
						for (TaskBean task : listOfTasks) {
							System.out.println("Task name:" + task.getTaskName() + " Description:" + task.getDesc()
									+ " Status:" + task.getStatus() + " Priority:" + task.getPriority() + " Tags:"
									+ task.getTags() + " Planned date:" + task.getSpldt());
						}
					}
					break;
				case 5:
					System.out.println("case - 5 for task");

                    System.out.println("Enter the keyword to search");
					keyWordToSearch = sc2.nextLine();
					List<TaskBean> task = model.searchTask(keyWordToSearch, catName);
					if (task.isEmpty() || task == null) {
						System.out.println("Your searching task is not exists");
					} else {
						System.out.println("Task " + keyWordToSearch + " exists and see the task details below");
						for (TaskBean b : task)
							System.out.println("Task name:" + b.getTaskName() + " Description:" + b.getDesc()
									+ " Status:" + b.getStatus() + " Priority:" + b.getPriority() + " Tags:"
									+ b.getTags() + " Planned date:" + b.getSpldt());
					}

					break;
				case 6:
					System.out.println("case - 6 for task");

					System.out.println("going back....");
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
