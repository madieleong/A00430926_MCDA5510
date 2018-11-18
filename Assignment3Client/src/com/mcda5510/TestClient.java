package com.mcda5510;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mcda5510.entity.Transaction;
import com.mcda5510.service.TrxnWebServiceProxy;

public class TestClient {
	
	static Scanner input = new Scanner(System.in);

	
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


		return trxn;
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
	

	public static void main(String[] args) throws RemoteException {
		
		TrxnWebServiceProxy proxy = new TrxnWebServiceProxy();
		proxy.setEndpoint("http://dev.cs.smu.ca:8049/Assignment3/services/TrxnWebService");
		//proxy.setEndpoint("http://localhost:8080/Assignment3/services/TrxnWebService");
		proxy.initConnection();
		Transaction trxn = generateTrxn("2222");
		trxn = proxy.resetTrxn(trxn);
		proxy.createTrxns(trxn);
		proxy.getSingleTrxn("2222");
		trxn.setNameOnCard("BIGDOG");
		proxy.updateTrxns(trxn);
		proxy.createTrxns(trxn);
		proxy.removeTrxns("2222");
		proxy.createTrxns(trxn);
		//proxy.test();
		

		
		
		

	}

}
