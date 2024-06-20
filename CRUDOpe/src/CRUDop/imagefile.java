package CRUDop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import in.JDBCUtil.JDBCUtil;

public class imagefile {
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			con = JDBCUtil.getJdbcConnection();
			
			System.out.println("1 for upload image. 2 for get image.");
			int val = sc.nextInt();
			
			switch(val) {
			case 1:
				uploadImage(con, pstmt);
				break;
			case 2:
				getImage(con, pstmt, result);
				break;
			default:
				System.out.println("Please choose the correct option..");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				JDBCUtil.closeResourse(con, pstmt);
				sc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	
// function to upload image on database.
	public static void uploadImage(Connection con, PreparedStatement pstmt) {
		String query = "insert into imageDB (image) value (?)";
		String imagePath = "P:\\Language\\Advance Java\\cartoon.jpg";
		try {
			con = JDBCUtil.getJdbcConnection();
			
			FileInputStream FIS = new FileInputStream(imagePath);
			byte[] imageData = new byte[FIS.available()];				// byte available
			FIS.read(imageData);
			
			pstmt = con.prepareStatement(query);
			pstmt.setBytes(1, imageData);
			
			int rowaffected = pstmt.executeUpdate();
			
			if(rowaffected > 0) {
				System.out.println("image uploaded Successfully.");
			}
			else {
				System.out.println("image not uploaded.");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
// function to get image in folder from database 
	
	public static void getImage(Connection con, PreparedStatement pstmt, ResultSet result) {
		String folderPath = "P:\\Language\\Advance Java\\";
		String query = "Select image from imageDB where image_id = ?";
		
		try {
			con = JDBCUtil.getJdbcConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, 1);								//only one image that's why 1 written
			
			result = pstmt.executeQuery();
			
			if(result.next()) {
				byte[] imageDate = result.getBytes(1);
				String imagePath = folderPath+"myImage.jpg";
				FileOutputStream FOS = new FileOutputStream(imagePath);
				FOS.write(imageDate);
				System.out.println("Now can check image..");
			}
			else {
				System.out.println("Image not found!");
			}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
