package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet result = null;
	
	public Doctor(Connection con) {
		this.con = con;
	}

	public void viewDoctor() {
		try {
			String query = "Select * from doctors";
			pstmt = con.prepareStatement(query);
			result = pstmt.executeQuery();
			
			System.out.println("DOCTORS: ");
			System.out.println("+-----------+--------------+------------------+");
			System.out.println("| Doctor Id | Name         | Specialization   |");
			System.out.println("+-----------+--------------+------------------+");
			while(result.next()) {
				int id = result.getInt(1);
				String name = result.getString(2);
				String specia = result.getString(3);
				System.out.printf("| %-9s | %-12s | %-16s |\n", id, name, specia);
				System.out.println("+-----------+--------------+------------------+");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id) {
		String query = "select* from Doctors where id = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			result = pstmt.executeQuery();
			System.out.println("+-----------+--------------+------------------+");
			System.out.println("| Doctor Id | Name         | Specialization   |");
			System.out.println("+-----------+--------------+------------------+");
			
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
