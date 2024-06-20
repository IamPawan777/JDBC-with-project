package CRUDop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import in.JDBCUtil.JDBCUtil;

public class select {

	public static void main(String[] args) {
		
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet result = null;
		Scanner sc = new Scanner(System.in);	
		
		try {
			connection = JDBCUtil.getJdbcConnection();
			
			if(connection!=null) {				
				String mysqlQuery = "select * from student_list where id = ?";
				preparedstatement = connection.prepareStatement(mysqlQuery);
				
				if(preparedstatement != null) {
					System.out.println("Enter id which you want to get: ");
					int id = sc.nextInt();
					preparedstatement.setInt(1, id);
					
					result = preparedstatement.executeQuery();
					
					if(result != null) {
						if(result.next()) {
							System.out.println("ID\tNAME\tROLLNO\tMARKS\tEMAIL");
							System.out.println(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getInt(3)+"\t"+result.getDouble(4)+"\t"+result.getString(5));
						}
					}
//					
//					preparedstatement.setInt(1, 2002);
//					preparedstatement.setString(2, "Kunal");
//					preparedstatement.setInt(3, 222);
//					preparedstatement.setDouble(4, 555.67);
//					preparedstatement.setString(5, "kunal@gmail.com");
//					
//					int roweffected = preparedstatement.executeUpdate();
//					
//					if(roweffected == 1) {
//						System.out.println("Row Inserted..");
//					}
//					else {
//						System.out.println("Row not Inserted..");
//					}	

				}
			}
			
		} 
		catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				JDBCUtil.closeResourse(connection, preparedstatement, result);
				sc.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
