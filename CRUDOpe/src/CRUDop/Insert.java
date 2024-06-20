package CRUDop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import in.JDBCUtil.JDBCUtil;

public class Insert {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedstatement = null;
//		Scanner sc = new Scanner(System.in);						// from user....
		
		try {
			connection = JDBCUtil.getJdbcConnection();
			
			if(connection!=null) {
//				statement = connection.createStatement();
//				String myInsertQuery = "insert into student_list(id, name, rollNo, Marks, Email) values(1001,'Vinod', 111, 999, 'vinod@gmail.com' )";
//				
//				int roweffected = statement.executeUpdate(myInsertQuery);
//				
//				if(roweffected == 1) {
//					System.out.print("row inserted");
//				}
//				else {
//					System.out.println("row not inserted");
//				}
				
				String mysqlQuery = "insert into student_list(id, name, rollNo, Marks, Email) values(?,?,?,?,?)";
				preparedstatement = connection.prepareStatement(mysqlQuery);
				
				if(preparedstatement != null) {
					
					preparedstatement.setInt(1, 2002);
					preparedstatement.setString(2, "Kunal");
					preparedstatement.setInt(3, 222);
					preparedstatement.setDouble(4, 555.67);
					preparedstatement.setString(5, "kunal@gmail.com");
					
					int roweffected = preparedstatement.executeUpdate();
					
					if(roweffected == 1) {
						System.out.println("Row Inserted..");
					}
					else {
						System.out.println("Row not Inserted..");
					}
				}
			}
			
		} 
		catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				JDBCUtil.closeResourse(connection, preparedstatement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
