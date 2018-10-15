import java.sql.Connection;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserChoice {
	static Scanner input = new Scanner(System.in);
	static SQLAccess dao = new SQLAccess();

	public static void menuList()
	{
		System.out.println("Main Menu");
		System.out.println("Select one of the following options");
		System.out.println(
				"1. View all transactions\n" + 
						"2. View a transaction\n" + 
						"3. Create a transaction\n" +
						"4. Update a transaction\n" +
						"5. Remove a transaction\n" + 
				"6. Exit");
	}

	public static void viewAllTransactions() {
		try {
			Connection connection = dao.dbConnection();
			Collection<Transaction> trxns = dao.getAllTransactions(connection);
			for (Transaction trxn:trxns ){
				System.out.println(trxn.toString());
				Logger.getLogger("myLog").log(Level.INFO, trxn.toString());
			} 
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void viewOneTransaction() {
		try {
			Connection connection = dao.dbConnection();
			System.out.print("Enter ID to see transaction: ");
			String id = input.nextLine();

			dao.getSingleTrxn(id, connection);

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}
	}

	/*
	 * option 1: create 
	 * option 2: update
	 */
	public static void createOrUpdateTrxn(int option) {
		try {
			Connection connection = dao.dbConnection();
			Collection<Transaction> trxns = dao.getAllTransactions(connection);
			Transaction trxn = new Transaction();

			System.out.print("Enter ID: ");
			String id = input.nextLine();

			boolean isNew = true;
			isNew = checkId(trxns, id);
			boolean isCreate = isNew && (option == 1),    //the id is new and user choose create transaction
					needCreate = isNew && (option == 2),   //the id is new and user use update transaction
					isUpdate = !isNew && (option == 2),  //the id is not new and user use update transaction
					needUpdate = !isNew && (option == 1);  //the id is not new and user use create transaction

			if(isCreate) {
				trxn = generateTrxn(id);
				dao.createTrxns(trxn,connection);	
			}else if(needCreate) {
				System.out.println("ID not exists!");
				Logger.getLogger("myLog").log(Level.WARNING,"ID not exists!");
				System.out.println("Do you want to create transaction?(Y/N)");
				String choice = input.nextLine();
				if(choice.equals("Y")) {
					trxn = generateTrxn(id);
					dao.createTrxns(trxn,connection);	
				}else {
					return;
				}
			}
			if(isUpdate) {
				trxn = generateTrxn(id);
				dao.updateTrxns(trxn, connection);
			}else if(needUpdate) {
				System.out.println("ID exists!");
				Logger.getLogger("myLog").log(Level.WARNING,"ID exists!");
				System.out.println("Do you want to update transaction?(Y/N)");
				String choice = input.nextLine();
				if(choice.equals("Y")) {
					trxn = generateTrxn(id);
					dao.updateTrxns(trxn, connection);
				}else {
					menuList();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}
	}

	public static void removeOneTransaction() {
		try {
			Connection connection = dao.dbConnection();

			System.out.print("Enter ID to delete transaction: ");
			String id = input.nextLine();

			dao.removeTrxns(id, connection);

		}catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}
	}

	/**
	 * create a Transaction object
	 * @param id
	 * @return Transaction
	 */
	private static Transaction generateTrxn(String id) {
		Transaction trxn = new Transaction();
		trxn.setId(id);

		boolean isCorrect = true;
		String nameOnCard = null;
		do {
			System.out.print("Enter Name on Card: ");
			nameOnCard = input.nextLine();

			isCorrect = checkSpecialCharacter(nameOnCard);
			if(!isCorrect) {
				System.out.println("Invalid name. Please try again.\n");
				System.out.println();
			}
		}while(!isCorrect);

		trxn.setNameOnCard(nameOnCard);

		isCorrect = true;
		boolean isValid = true;

		String cardNumber = null;
		do {
			System.out.print("Enter Card Number: ");
			cardNumber = input.nextLine();	

			isCorrect = checkSpecialCharacter(cardNumber);
			isValid = checkValidCardNumber(cardNumber);

			if(!isCorrect || !isValid) {
				System.out.println("Invalid CardNumber. Please try again.\n");
			}	
		}while(!isCorrect || !isValid);

		trxn.setCardNumber(cardNumber);

		String newCardType = setNewCardType(cardNumber);
		trxn.setCardType(newCardType);

		isCorrect = true;
		isValid = true;

		String expDate = null;
		do {
			System.out.print("Exp Date as MM/YYYY: ");
			expDate = input.nextLine();

			isCorrect = checkSpecialCharacter(expDate);
			isValid = checkValidExpDate(expDate);
			if(!isCorrect || !isValid) {
				System.out.println("Invalid ExpDate. Please try again.\n");
			}	
		} while(!isCorrect || !isValid);

		trxn.setExpDate(expDate);	

		System.out.println("UnitPrice: ");
		double unitPrice = Integer.parseInt(input.nextLine());
		trxn.setUnitPrice(unitPrice);

		System.out.println("Quantity: ");
		int quantity = Integer.parseInt(input.nextLine());
		trxn.setQuantity(quantity);

		trxn.setNewTotalPrice(unitPrice, quantity);

		trxn.setNewCreatedOn();
		trxn.setNewCreatedBy();

		return trxn;
	}

	private static boolean checkId(Collection<Transaction> trxns, String id) {
		boolean isNew = true;
		for(Transaction trxn: trxns) {
			if(trxn.getId().equals(id)) 
				isNew = false;
		}
		return isNew;
	}
	private static boolean checkSpecialCharacter(String varchar) {
		boolean isCorrect = true;
		if( varchar.contains(";")||varchar.contains(":")||varchar.contains("!")
				||varchar.contains("@")||varchar.contains("#")||varchar.contains("$")
				||varchar.contains("%")||varchar.contains("^")||varchar.contains("*")
				||varchar.contains("+")||varchar.contains("?")||varchar.contains("<")
				||varchar.contains(">") ) {
			isCorrect = false;
		}
		return isCorrect;
	}
	private static boolean checkValidCardNumber(String cardNum) {
		boolean isValid = false;
		if( cardNum.length() == 16 && (cardNum.startsWith("51") ||
				cardNum.startsWith("52") || cardNum.startsWith("53") ||
				cardNum.startsWith("54") || cardNum.startsWith("55")) ) {
			isValid = true;
		}
		else if( cardNum.length() == 16 && cardNum.startsWith("4") ) {
			isValid = true;
		} else if( cardNum.length() == 15 && (cardNum.startsWith("34") 
				|| cardNum.startsWith("37")) ) {
			isValid = true;
		}
		return isValid;
	}
	public static String setNewCardType(String cardNum) {
		String cardType = "";
		if( cardNum.length() == 16 && (cardNum.startsWith("51") ||
				cardNum.startsWith("52") || cardNum.startsWith("53") ||
				cardNum.startsWith("54") || cardNum.startsWith("55")) ) {
			cardType = "MasterCard";
		}
		else if( cardNum.length() == 16 && cardNum.startsWith("4") ) {
			cardType = "Visa";
		}
		else if( cardNum.length() == 15 && (cardNum.startsWith("34") 
				|| cardNum.startsWith("37")) ) {
			cardType = "AmericanExpress";
		}
		return cardType;
	}

	private static boolean checkValidExpDate(String newExpDate) {
		boolean isValid = false;
		if( newExpDate.matches("(0[1-9]|1[0-2])/(20)(1[6789]|2[0-9]|3[01])") ) {
			isValid = true;
		}
		return isValid;
	}
}