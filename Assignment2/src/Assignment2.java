import java.util.InputMismatchException;
import java.util.Scanner;


public class Assignment2 {

	private static Scanner input = null;;

	public static void main(String[] args) {
		System.setProperty("java.util.logging.config.file",
				"./logging.properties");
		

		input = new Scanner(System.in);

		boolean userExit = true;
		
		do {
			try {
				UserChoice.menuList();

				int option = Integer.parseInt(input.nextLine());

				if (option == 1) {
					UserChoice.viewAllTransactions();
				} else if (option == 2) {
					UserChoice.viewOneTransaction();
				} else if (option == 3) {
					UserChoice.createOrUpdateTrxn(1);
				} else if (option == 4) {
					UserChoice.createOrUpdateTrxn(2);
				} else if (option == 5) {
					UserChoice.removeOneTransaction();
				} else if (option == 6) {
					userExit = false;
				} else {
					throw new IndexOutOfBoundsException();
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input.\n" + e + "\nPlease enter a number from 1 to 6.");
				input.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input.\n" + e + "\nPlease enter a number from 1 to 6.");
				input.nextLine();
			}

			System.out.println();
		} while (userExit);

		System.out.println("Thank you, Goodbye!");
	}
}