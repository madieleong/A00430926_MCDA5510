import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQLAccess {

	public Connection dbConnection() throws Exception {

		Connection connection = null;
		try {
			//Driver Registration
			Class.forName("com.mysql.cj.jdbc.Driver");

			//establishing connection
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/transactions?"    
					+ "user=root&password=a1b2c3d4"   
					+"&useSSL=false"              
					+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		}catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}finally {

		}
		return connection;
	}

	public Collection<Transaction> getAllTransactions(Connection connection) {
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = new ArrayList<Transaction>();
		try {
			statement =connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM transactions.transactions");
			results = readTrxns(resultSet);

			if(resultSet != null) {
				resultSet.close();
			}
			if(statement != null) {
				statement.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}
		finally{
			statement = null;
			resultSet = null;
		}
		return results;
	}

	public void getSingleTrxn(String id, Connection connection) {
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM transactions WHERE id = " + id);

			if(resultSet.next())
			{
				Transaction trxn = new Transaction();
				trxn.setId(resultSet.getString("ID"));
				trxn.setNameOnCard(resultSet.getString("NameOnCard"));			
				trxn.setCardNumber(resultSet.getString("CardNumber"));
				trxn.setCardType(resultSet.getString("CardType"));
				trxn.setExpDate(resultSet.getString("ExpDate"));
				trxn.setUnitPrice(resultSet.getDouble("UnitPrice"));
				trxn.setQuantity(resultSet.getInt("Quantity"));
				trxn.setTotalPrice(resultSet.getDouble("TotalPrice"));
				trxn.setCreatedOn(resultSet.getString("CreatedOn"));
				trxn.setCreatedBy(resultSet.getString("CreatedBy"));

				System.out.println(trxn.toString());
				Logger.getLogger("myLog").log(Level.INFO, trxn.toString() );

				if(resultSet != null) {
					resultSet.close();
				}
				if(statement != null) {
					statement.close();
				}
			}

		}	catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}
	}

	public void createTrxns(Transaction trxn, Connection connection) {
		PreparedStatement statement = null;
		try {
			String insertSql = "INSERT INTO transactions(ID, NameOnCard, CardNumber, "
					+ "CardType, ExpDate, UnitPrice, Quantity, TotalPrice,"
					+ " CreatedOn, CreatedBy)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(insertSql);
			statement.setString(1, trxn.getId());
			statement.setString(2, trxn.getNameOnCard());
			statement.setString(3, trxn.getCardNumber());
			statement.setString(4, trxn.getCardType());
			statement.setString(5, trxn.getExpDate());
			statement.setDouble(6, trxn.getUnitPrice());
			statement.setInt(7, trxn.getQuantity());
			statement.setDouble(8, trxn.getTotalPrice());
			statement.setString(9,trxn.getCreatedOn());
			statement.setString(10, trxn.getCreatedBy());

			int rowsInserted = statement.executeUpdate();
			if(rowsInserted > 0) {
				System.out.println("A new user was successfully inserted!");
				Logger.getLogger("myLog").log(Level.INFO, "A new user was successfully inserted!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
		}
	}
	public void updateTrxns(Transaction trxn, Connection connection) {
		PreparedStatement statement = null;
		try {
			String updateSql = "UPDATE transactions SET NameOnCard=?, CardNumber=?," + 
					"CardType=?, ExpDate=?, UnitPrice=?, Quantity=?, TotalPrice=?," + 
					"CreatedOn=?, CreatedBy=? WHERE ID =?";
			statement = connection.prepareStatement(updateSql);
			statement.setString(10, trxn.getId());
			statement.setString(1, trxn.getNameOnCard());
			statement.setString(2, trxn.getCardNumber());
			statement.setString(3, trxn.getCardType());
			statement.setString(4, trxn.getExpDate());
			statement.setDouble(5, trxn.getUnitPrice());
			statement.setInt(6, trxn.getQuantity());
			statement.setDouble(7, trxn.getTotalPrice());
			statement.setString(8,trxn.getCreatedOn());
			statement.setString(9, trxn.getCreatedBy());

			int rowsUpdated = statement.executeUpdate();
			if(rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully!");
				Logger.getLogger("myLog").log(Level.INFO, "An existing user was updated successfully!");
			}	
		}catch(SQLException e) {
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void removeTrxns(String id, Connection connection) {
		PreparedStatement statement = null;
		try {
			String deleteSql = "DELETE FROM transactions WHERE id = " + id; 
			statement = connection.prepareStatement(deleteSql);

			int rowsDeleted = statement.executeUpdate();
			if(rowsDeleted > 0) {
				System.out.println("A user was deleted successfully!");
				Logger.getLogger("myLog").log(Level.INFO, "A user was deleted successfully!");
			}
		}catch(SQLException e) {
			Logger.getLogger("myLog").log(Level.SEVERE, "Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private Collection<Transaction> readTrxns(ResultSet resultSet) throws SQLException {
		Collection<Transaction> results = new ArrayList<Transaction>();

		//ResultSet is initially before the first data set
		while(resultSet.next()) {
			Transaction trxn = new Transaction();
			trxn.setId(resultSet.getString("ID"));
			trxn.setNameOnCard(resultSet.getString("NameOnCard"));			
			trxn.setCardNumber(resultSet.getString("CardNumber"));
			trxn.setCardType(resultSet.getString("CardType"));
			trxn.setExpDate(resultSet.getString("ExpDate"));
			trxn.setUnitPrice(resultSet.getDouble("UnitPrice"));
			trxn.setQuantity(resultSet.getInt("Quantity"));
			trxn.setTotalPrice(resultSet.getDouble("TotalPrice"));
			trxn.setCreatedOn(resultSet.getString("CreatedOn"));
			trxn.setCreatedBy(resultSet.getString("CreatedBy"));

			results.add(trxn);
		}
		return results;
	}


}
