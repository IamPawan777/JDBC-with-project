package in.JDBCUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
	
	//task to get the properties from the Application file 
	public static Connection getJdbcConnection() throws IOException, SQLException {
		FileInputStream FIS = new FileInputStream("Application.properties");
		Properties P = new Properties();
		P.load(FIS);
		
		String url = P.getProperty("url");
		String user = P.getProperty("user");
		String password = P.getProperty("password");
		
		System.out.println(url);
		System.out.println(user);
		System.out.println(password);
		
		// start....>
		//  loading the driver with connection
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}
	
	
	// resourses close
	public static void closeResourse(Connection connection, PreparedStatement preparedstatement) throws SQLException  {
		if(connection != null) {
			connection.close();
		}
		if(preparedstatement != null) {
			preparedstatement.close();
		}
//		if(resultset!=null) {
//			resultset.close();
//		}
	}

}
