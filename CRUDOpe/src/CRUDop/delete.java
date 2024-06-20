package CRUDop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import in.JDBCUtil.JDBCUtil;

public class delete {

	public static void main(String[] args) {

		Connection connection = null;
		PreparedStatement preparedstatement = null;
		
		try {
			connection = JDBCUtil.getJdbcConnection();
			
			if(connection!=null) {
				Scanner sc=  new Scanner(System.in);
				
				String mysqlQuery = "delete from student_list where id = ?";
				preparedstatement = connection.prepareStatement(mysqlQuery);
				
				if(preparedstatement != null) {
					
					
					System.out.print("Enter id which you want to delete: ");
					int id = sc.nextInt();
					
//					System.out.print("Enter the updated marks: ");
//					String marks = sc.next();
					
//					preparedstatement.setString(1, marks);
					
					preparedstatement.setInt(1, id);
					
					
					
					int roweffected = preparedstatement.executeUpdate();
					
					if(roweffected == 1) {
						System.out.println("Row deleted..");
					}
					else {
						System.out.println("Row not deleted..");
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
//				sc.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
