package HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointments {
	private static final String url = "jdbc:mysql://localhost:3306/hospital_managemant";
	private static final String username = "root";
	private static final String password = "IamPawan";
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Scanner sc = new Scanner(System.in);		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			Patient patient = new Patient(con, sc);
			Doctor doctor = new Doctor(con);
			
			while(true) {
				System.out.println("..HOSPITAL MANAGEMENT SYSTEM..");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patient");
				System.out.println("3. View Patient");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				
				System.out.println("Enter your choice :");
				int choice = sc.nextInt();
				switch(choice) {
					case 1:
						patient.addPatient();
						System.out.println();
						break;
					case 2:
						patient.viewPatient();
						System.out.println();
						break;
					case 3:
						doctor.viewDoctor();
						System.out.println();
						break;
					case 4:
						bookAppointment(con, pstmt, result, sc, patient, doctor);
						System.out.println();
						break;
					case 5:
						System.out.println("ThankYou for using Hospital Management System !!!");
						return;
					default:
						System.out.println("Enter valid choice !!!");
						break;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
				sc.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static void bookAppointment(Connection con, PreparedStatement pstmt, ResultSet result, Scanner sc, Patient pa, Doctor dc) {
		System.out.println("Enter Patient Id: ");
		int patientId = sc.nextInt();
		System.out.println("Enter Doctor Id: ");
		int doctocId = sc.nextInt();
		System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
		String appoiDate = sc.next();
		
		if(pa.getPatientById(patientId) && dc.getDoctorById(doctocId)) {
			if(checkDoctorAvailability(doctocId, appoiDate, con, pstmt, result)) {
				String appoi_query = "insert into appointments(patient_id, doctor_id, appointment_date) values (?, ?, ?)";
				
				try {
					pstmt = con.prepareStatement(appoi_query);
					pstmt.setInt(1, patientId);
					pstmt.setInt(2, doctocId);
					pstmt.setString(3, appoiDate);
					int rowAffected = pstmt.executeUpdate();
					if(rowAffected > 0) {
						System.out.println("Appointment Booked");
						System.out.println();
					}
					else
						System.out.println("Failed to Book Appointment !!!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Doctor not available on this date !!!");
			}
		}
		else {
			System.out.println("Either doctor or patient doesn't available !!!");
		}
	}
	
	public static boolean checkDoctorAvailability(int doctocId, String appoitDate, Connection con, PreparedStatement pstmt, ResultSet result) {
		String query = "Select count(*) from appointments where doctor_id = ? AND appointment_date = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, doctocId);
			pstmt.setString(2, appoitDate);
			
			result = pstmt.executeQuery();
			
			if(result.next()) {
				int count = result.getInt(1);
				if(count == 0)
					return true;
				else
					return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
