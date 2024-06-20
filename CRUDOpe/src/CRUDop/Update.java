package CRUDop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import in.JDBCUtil.JDBCUtil;

public class Update {

	public static void main(String[] args) {
		
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		Scanner sc = new Scanner(System.in);						// from user....
		
		try {
			connection = JDBCUtil.getJdbcConnection();
			
			if(connection!=null) {
//				Scanner sc=  new Scanner(System.in);
				
				String mysqlQuery = "update student_list set marks=? where id=?";
				preparedstatement = connection.prepareStatement(mysqlQuery);
				
				if(preparedstatement != null) {
					
					
//					preparedstatement.setInt(1, 2002);
//					preparedstatement.setString(2, "Kunal");
//					preparedstatement.setInt(3, 222);
//					preparedstatement.setDouble(4, 555.67);
//					preparedstatement.setString(5, "kunal@gmail.com");
					
					
					System.out.print("Enter id which you want to update: ");
					int id = sc.nextInt();
					
					System.out.print("Enter the updated marks: ");
					String marks = sc.next();
					
					preparedstatement.setString(1, marks);
					preparedstatement.setInt(2, id);
					
					
					
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
				sc.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



}
