package com;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/new","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertCard(String Card_Holder, String Card_Number, String Expire_Date, String CVV) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into card_details(`CardID`,`Card_Holder`,`Card_Number`,`Expire_Date`,`CVV`)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Card_Holder);
			preparedStmt.setString(3, Card_Number);
			preparedStmt.setString(4, Expire_Date);
			preparedStmt.setString(5, CVV);

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayment = readCard();
			output = "{\"status\":\"success\", \"data\": \"" +
			newPayment + "\"}";
		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while inserting the Card.\"}";


			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readCard() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Card Holder</th><th>Card Number</th><th> Expire Date</th><th>CVV</th><th>Update</th><th>Delete</th></tr>";
			String query = "select * from card_details";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String CardID = Integer.toString(rs.getInt("CardID"));
				String Card_Holder = rs.getString("Card_Holder");
				String Card_Number = rs.getString("Card_Number");
				String Expire_Date = rs.getString("Expire_Date");
				String CVV = rs.getString("CVV");

				output += "<tr><td><input id=\'hidPaymentIDUpdate\' name=\'hidPaymentIDUpdate\' type=\'hidden\' value=\'"+ CardID + "'>" + Card_Holder + "</td>";
				output += "<td>" + Card_Number + "</td>";
				output += "<td>" + Expire_Date + "</td>";
				output += "<td>" + CVV + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
				+ "class='btnUpdate btn btn-secondary' data-cardid='" + CardID + "'></td>"
				+ "<td><input name='btnRemove' type='button' value='Remove' "
				+ "class='btnRemove btn btn-danger' data-cardid='" + CardID + "'></td></tr>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Card Details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateCard(String CardID, String Card_Holder, String Card_Number, String Expire_Date, String CVV) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE card_details SET Card_Holder=?,Card_Number=?,Expire_Date=?,CVV=? WHERE CardID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Card_Holder);
			preparedStmt.setString(2, Card_Number);
			preparedStmt.setString(3, Expire_Date);
			preparedStmt.setString(4, CVV);
			preparedStmt.setInt(5, Integer.parseInt(CardID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readCard();
			output = "{\"status\":\"success\", \"data\": \"" +
			newPayment + "\"}";
		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Card.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteCard(String CardID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from card_details where CardID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(CardID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readCard();
			output = "{\"status\":\"success\", \"data\": \"" +
			newPayment + "\"}";	
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Card.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}

