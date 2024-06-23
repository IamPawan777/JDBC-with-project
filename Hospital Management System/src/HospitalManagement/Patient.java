package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection con;
	private Scanner sc;
	private PreparedStatement pstmt;
	private ResultSet result;
	
	public Patient(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	public void addPatient() {
		String query = "insert into patients (name, age, sex) values (?,?,?)";
		try {
			pstmt = con.prepareStatement(query);
			System.out.print("Enter Patient Name: ");
			String name = sc.next();
			pstmt.setString(1, name);
			System.out.print("Enter Patient Age: ");
			int age = sc.nextInt();
			pstmt.setInt(2, age);
			System.out.print("Enter Patient Gender: ");
			String genter = sc.next();
			pstmt.setString(3, genter);
			
			int rowAffected = pstmt.executeUpdate();
			if(rowAffected > 0) {
				System.out.print("Patient add Successfully!");
				System.out.println();				
			}
			else {
				System.out.print("Failed to add Patient!");
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void viewPatient() {
		String query = "Select* from patients";
		try {
			pstmt = con.prepareStatement(query);
			result = pstmt.executeQuery();
			System.out.println("PATIENT: ");
			System.out.println("+------------+--------------+------------+-------------+");
			System.out.println("| Patient Id | Name         | Age        | Sex         |" );
			System.out.println("+------------+--------------+------------+-------------+");
			while(result.next()) {
				int id = result.getInt(1);
				String name = result.getString(2);
				int age = result.getInt(3);
				String gender = result.getString(4);
				System.out.printf("| %-10s | %-12s | %-10s | %-11s |\n", id, name, age, gender);
				System.out.println("+------------+--------------+------------+-------------+");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean getPatientById(int id) {
		String query = "Select * from Patients where id =?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			result = pstmt.executeQuery();
			if(result.next())
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
