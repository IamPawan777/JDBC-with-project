package ReserveRoom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HotelReservationSystem {

	private static final String url = "jdbc:mysql://localhost:3306/hotal_reservation";
	private static final String username = "root";						// secure  |  without object | non changeable
	private static final String password = "IamPawan";
	
	
	@SuppressWarnings("null")
	public static void main(String[] args) throws InterruptedException {
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Scanner sc = new Scanner(System.in);
		
		try {
//			Class.forName("com.mysql.cj.jdbc.Drive");
			con = DriverManager.getConnection(url, username, password);
			
			while(true) {
				System.out.println();
				System.out.println("....WELCOME TO MY HOTEL....");
				System.out.println("1.Reserve a Room.");
				System.out.println("2.View Reservation.");
				System.out.println("3.Get Room Number.");
				System.out.println("4.Update Reservation.");
				System.out.println("5.Delete Reservation.");
				System.out.println("0.Exit");
				
				System.out.println("Choose an option: ");
				int choose = sc.nextInt();
				
				switch(choose) {
				case 1:
					reserveRoom(con, sc, pstmt);
					break;
				case 2:
					viewReservation(con, pstmt, result);
					break;
				case 3:
					getRoomNumber(con, sc, pstmt, result);
					break;					
				case 4:
					updateReservation(con, sc, pstmt);
					break;
				case 5:
					deleteReservation(con, sc, pstmt);
					break;
				case 0:
					exit();
					sc.close();
					return;
				default:
					System.out.println("Invalid choice. Try again.");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
//				pstmt.close();
//				result.close();
				sc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	
	private static void reserveRoom(Connection con, Scanner sc, PreparedStatement pstmt) {
		String query = "insert into reservations(guest_name, room_number, contact_number) values (?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(query);
			
			System.out.println("Enter guest name: ");
			String guestName = sc.next();
			pstmt.setString(1, guestName);
			System.out.println("Enter room number: ");
			int roomNumber = sc.nextInt();
			pstmt.setInt(2, roomNumber);
			System.out.println("Enter contact number: ");
			String contactNumber = sc.next();
			pstmt.setString(3, contactNumber);
			
			int rowaffected = pstmt.executeUpdate();
			if(rowaffected > 0)
				System.out.println("Reservation successful");
			else
				System.out.println("Reservation failed");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	
	
	private static void viewReservation(Connection con, PreparedStatement pstmt, ResultSet result) {
		String query = "Select *from reservations";
		
		try {
			pstmt = con.prepareStatement(query);
			result = pstmt.executeQuery();
			System.out.println("Reservation Id\t Guest Name\t Room Number\t Contact Number\t Reservation Date-Time");
			while(result.next()) {
				System.out.println("\t"+result.getInt(1)+"\t   "+result.getString(2)+"\t   "+result.getInt(3)+"\t\t   "+result.getString(4)+"\t  "+result.getTimestamp(5));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void getRoomNumber(Connection con, Scanner sc, PreparedStatement pstmt, ResultSet result) {
		String query = "Select * from reservations where guest_name	= ?";
		try {
			pstmt = con.prepareStatement(query);
			System.out.println("Guest name: ");
			String guest = sc.next();
			pstmt.setString(1,  guest);
			result = pstmt.executeQuery();
			if(result.next()) {
				System.out.println("Guest Name\tRoom Number");
				System.out.println(result.getString(2)+"\t\t"+result.getInt(3));
			}
			else {
				System.out.println("Reservation not found for given guest name! Please Try Again...");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void updateReservation(Connection con, Scanner sc, PreparedStatement pstmt) {
		String query = "UPDATE reservations SET guest_name=?, contact_number=? where guest_name=?";
		try {
			pstmt = con.prepareStatement(query);
			System.out.println("Enter guest name which you want to update: ");
			String name = sc.next();
			pstmt.setString(3, name);
			
			System.out.println("Update guest name: ");
			String updateName = sc.next();
			pstmt.setString(1, updateName);
			System.out.println("Update contact name: ");
			String number = sc.next();
			pstmt.setString(2, number);
			
			int rowaffected = pstmt.executeUpdate();
			if(rowaffected>0)
				System.out.println("Reservation updated Successfully.");
			else
				System.out.println("Reservation not found for given Guest name! Please Try Again...");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteReservation(Connection con, Scanner sc, PreparedStatement pstmt) {
		String query = "DELETE FROM reservations WHERE reservation_id=?";
		try {
			pstmt = con.prepareStatement(query);
			System.out.println("Enter reservation id which you want to delete ? ");
			int id = sc.nextInt();
			pstmt.setInt(1, id);
			
			int rowaffected = pstmt.executeUpdate();			
			if (rowaffected>0) 
				System.out.println("Reservation deleted Successfully.");
			else
				System.out.println("Reservation not found for given Id! Please Try Again...");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void exit() throws InterruptedException {
		System.out.println("Exiting System");
		int i = 5;
		while(i-- != 0) {
			System.out.print(". ");
			Thread.sleep(1000);
		}
		System.out.println();
		System.out.println("Thankyou for using \" Hotal Reservation System \" !!!");
	}

}
