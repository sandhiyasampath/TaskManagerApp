package com.uttara.taskManager;

import java.io.File;
import java.util.Scanner;

public class StartApp {

	public static void main(String[] args) {
		try {
			Scanner sc1 = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
			int choice1 = 0;
			String catName;
			TaskModel model = new TaskModel();

			Logger.getInstance().log("Starting Task Manager..........");

			z: while (choice1 != 7) {
				sc1 = new Scanner(System.in);
				System.out.println();
				System.out.println("Press 1 to create category");
				System.out.println("Press 2 to load the category");
				System.out.println("Press 3 to remove the category");
				System.out.println("Press 4 to list the category");
				System.out.println("Press 5 to search the category");
				System.out.println("Press 6 to exit");
				if (!sc1.hasNextInt()) {
					System.err.println("Enter only integers");
				} else {
					choice1 = sc1.nextInt();
				}
				switch (choice1) {
				case 1:
					System.out.println("Creating category......");
					System.out.println("Enter Category name");
					catName = sc2.nextLine();
					// business validation
					// check category is unique

					// input validation
					while (!TaskUtil.validateCatName(catName)) {
						System.out.println(
								"Category name should be single word,start with letter,only alphanumeric....Enter another catagory name");
						catName = sc2.nextLine();
					}
					while (model.checkCategoryExists(catName)) {
						System.out.println("Enter unique category Name");
						catName = sc2.nextLine();
					}
					model.creatCategory(catName);
					System.out.println("Category created successfully");
					TaskUtil.taskToDo(catName);
					continue z;
				case 2:
					System.out.println("case - 2 for cat");

					System.out.println("Enter category name to load");
					String loadCat = sc2.nextLine();
					if (model.checkCategoryExists(loadCat))
						TaskUtil.taskToDo(loadCat);
					else
						System.out.println("Your searching category not exists to load");
					break;
				case 3:
					System.out.println("case - 3 for cat");

					System.out.println("Enter Category Name to Delete");
					String deteteCat = sc2.nextLine();
					if (model.checkCategoryExists(deteteCat)) {
						model.deleteCat(deteteCat);
						System.out.println("Category deleted successfully");
					} else
						System.out.println("Category not exsits to delete, add some category to delete");

					break;

				case 4:
					System.out.println("case - 4 for cat");

					File[] listCat = model.listCat();
					for (File fl : listCat) {
						if (fl.isFile() && fl.getName().endsWith(".todo")) {
							System.out.println(fl.getName());
						}
					}
					break;

				case 5:
					System.out.println("Enter Category Name to search");
					String searchCat = sc2.nextLine();
					if (model.searchCat(searchCat))
						System.out.println("Your searching Category " + searchCat + " exists");
					else
						System.out.println("Your searching Category Not Exists");
					break;

				case 6:
					System.out.println("Tata bye bye.........");
					System.exit(0);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}