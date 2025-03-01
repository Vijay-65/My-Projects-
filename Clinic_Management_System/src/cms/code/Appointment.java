package cms.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Appointment {
	
	static Scanner scn = new Scanner(System.in);

	public static void appointmentDetail() {
		
		System.out.println("\n1.Add Appointment.\n2.View appointment details.\n3.Search appointment\n4.Update appointment details\n5.Delete Appointments\n6.Back\n");
		System.out.println("Enter the Choice to Perform :");
		int ch = scn.nextInt();
		
		switch(ch) {
		
		case 1 : Appointment.insertAppointmentDetails(); break;
		case 2 : Appointment.viewAllAppointmentDetails(); break;
		case 3 : Appointment.searchAppointmentDetails(); break;
		case 4 : Appointment.updateAppointmentDetails(); break;
		case 5 : Appointment.deleteAppointmentDetails(); break;
		case 6 : Access.adminAccess(); break;
		default : System.out.println("Please Check your Choice !! ");
		}
	}
	
	
	public static void insertAppointmentDetails() {        // 1. ADD  
		
		try {
		
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = " INSERT INTO appointments ( patient_id, doctor_id, appointment_date, appointment_time, status ) VALUES (?,?,?,?,?) ;";
			
			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for both Static and Dynamic query..
																//  Support placeholder(?) , More Secure.
			
			System.out.println("\n********* ADDING APPOINTMENT DETAILS *********\n");
			
			System.out.print("Enter patient Id :");
			int patientId = scn.nextInt();
			
			System.out.print("Enter doctor Id :");
			int doctorId = scn.nextInt();
			
			System.out.print("Enter appointment Date :");
			String date = scn.next();
			
			System.out.print("Enter appointment Time :");
		    String time = scn.next();
			
			System.out.print("Enter status :");
			String status = scn.next();
		
			psmt.setInt(1, patientId);
			psmt.setInt(2, doctorId);
			psmt.setString(3, date);
			psmt.setString(4, time);
			psmt.setString(5, status);
			
			// STEP 5 : Execute Query..
			int result = psmt.executeUpdate();
			
			// STEP 6 : Process the Result..
			if(result==1) {
				viewNewAppointmentDetails(patientId,date);
				System.out.println("\n Appointment details added Sucessfull !!\n");
			}
			
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		System.out.print("\nDo you want to Continue ? (Y/N) : ");
		char c = scn.next().charAt(0);
		if(c=='Y' || c=='y') {
			Appointment.insertAppointmentDetails();
		}
		else {
			Access.adminAccess();
		}
		
	}// method ends
	
	
	public static void viewNewAppointmentDetails(int patientId, String date) {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM appointments WHERE patient_id = ? AND appointment_date = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setInt(1, patientId);
			psmt.setString(2, date);
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\n Appointment_id |\t Patient_id \t|  Doctor_id  |\t Appointment_date \t|\t Appointment_Time \t|\t status ");
			
			// Display.. 
			if(result.next()) // Check if new row is there and value present or not..  
			{     
				int appointId= result.getInt(1);  				// Column 1
				int patient_id = result.getInt(2);		    // Column 2
				int doctor_id = result.getInt(3);		    // Column 3
				String date1 = result.getString(4);    // Column 4
				String time = result.getString(5);		        // Column 5
				String status = result.getString(6);				// Column 6
				                 		
				System.out.println("\t  "+appointId+"\t    "+patient_id+"\t\t"+doctor_id+"\t"+date1+"\t\t"+time+"\t\t"+status);
			}
						
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}	
		
	}// method ends..
	
	
	public static void viewAllAppointmentDetails() {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM appointments ;";

			// STEP 4 : Create Statement..
			Statement stmt = con.createStatement(); // Used for Simpler and one-time queries..
													// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			// STEP 5 : Execute Query..
			ResultSet result = stmt.executeQuery(query); // here want to pass query.. 
			
			System.out.println("\t----------------- DISPLAYING APPOINTMENT DETAILS ------------------\n");
			
			// STEP 6 : Process the Result..
			System.out.println("\n Appointment_id |\t Patient_id \t|  Doctor_id  |\t Appointment_date \t|\t Appointment_Time \t|\t status ");
						
			// Display..
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int appointId= result.getInt(1);  				// Column 1
				int patient_id = result.getInt(2);		    // Column 2
				int doctor_id = result.getInt(3);		    // Column 3
				String date1 = result.getString(4);   		 // Column 4
				String time = result.getString(5);		        // Column 5
				String status = result.getString(6);				// Column 6			                  
				System.out.println("  "+appointId+"\t"+patient_id+"\t"+doctor_id+"\t"+date1+"\t\t"+time+"\t\t"+status);
			}
						
			// STEP 7 : Close the Connection..
			stmt.close();
			con.close();	
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();
	}// method ends 
	
	
	public static void updateAppointmentDetails() {
		
		int appointId = 0;
		
		try {
		
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("******** UPDATE APPOINTMENT DETAILS *********\n");
			
			System.out.println("Enter the Appointment Id to update : ");
		    appointId = scn.nextInt();
			
			System.out.println("\n1.Patient_Id \n2.Doctor_Id \n3.Appointment Date \n4.Appointment Time \n5.Status \n\nEnter your choice : ");
			int ch = scn.nextInt();
			
			String query = "";
			
			PreparedStatement psmt;
			int result = 0;
			
			// STEP 3 : Issue the Query..
			switch(ch) {
			
			case 1 : System.out.print("Enter patient Id :");
					 int patient_id = scn.nextInt();
					 query = " UPDATE appointments SET patient_id = ? WHERE appointment_id = ? ; ";
					 
					 // STEP 4 : Create Statement..
					 psmt = con.prepareStatement(query); 
					 psmt.setInt(1, patient_id);
					 psmt.setInt(2, appointId);
					 
					 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n PatientID updated Sucessfully !!");
		     		 }
					 psmt.close();
					 break;
					 
			case 2 : System.out.print("Enter doctor Id :");
			 		 int doctorId = scn.nextInt();
			 		 query = " UPDATE appointments SET doctor_id = ? WHERE appointment_id = ? ; ";
			 
			 		 // STEP 4 : Create Statement..
			 		 psmt = con.prepareStatement(query); 
			 		 psmt.setInt(1, doctorId );
			 		 psmt.setInt(2, appointId);
			 		 
			 		 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Doctor ID updated Sucessfully !!");
					 }
					 psmt.close();
			 		 break;
			 		 
			case 3:  System.out.print("Enter Appointment Date :");
					 String date = scn.next();
				     query = " UPDATE appointments SET appointment_date = ? WHERE appointment_id = ? ; ";

						// STEP 4 : Create Statement..
					 psmt = con.prepareStatement(query);
					 psmt.setString(1, date);
					 psmt.setInt(2, appointId);
					 
					 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Appointment date updated Sucessfully !!");
		     		 }
					 psmt.close();
		 			 break;
		 			 
			case 4 : System.out.print("Enter Appointment Time :");
	 		 		 String time = scn.next();
	 		 		 query = " UPDATE appointments SET appointment_time = ? WHERE appointment_id = ? ; ";
	 
	 		 		 // STEP 4 : Create Statement..
	 		 		 psmt = con.prepareStatement(query); 
	 		 		 psmt.setString(1, time);
	 		 		 psmt.setInt(2, appointId);
	 		 		 
	 		 	     // STEP 5 : Execute Query..
	 				 result = psmt.executeUpdate();
	 				 if(result==1) {
	     				System.out.println("\n Appointment time updated Sucessfully !!");
	     			 }
	 				 psmt.close();
	 		 		 break;
	 		 		 
			case 5 : System.out.print("Enter Status :");
	 		         String status = scn.next();
	 		         query = " UPDATE appointments SET status = ? WHERE appointment_id = ? ; ";

	 		         // STEP 4 : Create Statement..
	 		         psmt = con.prepareStatement(query); 
	 		         psmt.setString(1, status);
	 		         psmt.setInt(2, appointId);
	 		         
	 		         // STEP 5 : Execute Query..
	 				 result = psmt.executeUpdate();
	 				 if(result==1) {
	     				System.out.println("\n Status updated Sucessfully !!");
	     			 }
	 				 psmt.close();
	 		         break;
	 		 		 
	        default : System.out.println("It's not a valid choice !!");
			}				// Used for both Static and Dynamic query. //  Support placeholder(?) , More Secure
			// STEP 7 : Close the Connection..
			con.close();	
		}
		
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		viewUpdatedAppointmentDetails(appointId);
			
	}// method ends
	
	
	public static void viewUpdatedAppointmentDetails(int appointId) {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM appointments WHERE appointment_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setInt(1, appointId);								
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\n Appointment_id |\t Patient_id \t|  Doctor_id  |\t Appointment_date \t|\t Appointment_Time \t|\t status ");
						
			// Display.. 
			if(result.next()) // Check if new row is there and value present or not..  
			{     
				int appoint_Id= result.getInt(1);  				// Column 1
				int patient_id = result.getInt(2);		    // Column 2
				int doctor_id = result.getInt(3);		    // Column 3
				String date1 = result.getString(4);    // Column 4
				String time = result.getString(5);		        // Column 5
				String status = result.getString(6);				// Column 6
							                  					
				System.out.println("  "+appoint_Id+"\t"+patient_id+"\t"+doctor_id+"\t"+date1+"\t\t"+time+"\t\t"+status);
			}
						
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();
		
	}// method ends..
	
	
	public static void searchAppointmentDetails() {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("Enter Appointment Date ");
			String date = scn.next();
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM appointments WHERE  appointment_date = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setString(1, date);
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\n Appointment_id |\t Patient_id \t|  Doctor_id  |\t Appointment_date \t|\t Appointment_Time \t|\t status ");
			
			// Display.. 
			if(!result.next()) // Check if new row is there and value present or not..  
			{
				System.out.println("\nNo Appointments for this date..");
			}
			else {
				
				while(result.next()) 
				{
				int appointId= result.getInt(1);  				// Column 1
				int patient_id = result.getInt(2);		    // Column 2
				int doctor_id = result.getInt(3);		    // Column 3
				String date1 = result.getString(4);    // Column 4
				String time = result.getString(5);		        // Column 5
				String status = result.getString(6);				// Column 6
				                  // column 7
						
				System.out.println("  "+appointId+"\t"+patient_id+"\t"+doctor_id+"\t"+date1+"\t\t"+time+"\t\t"+status);
				}
			}
						
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();
	}// method ends..
	

	public static void deleteAppointmentDetails(){
		
		int result = 0;
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("********** DELETING APPOINTMENT DETAILS ***********\n");
			System.out.println("Enter Patient Id you want to delete :");
			int patientId = scn.nextInt();
			
			// Appointment.
			
			// STEP 3 : Issue the Query..
			String query = "DELETE FROM appointments WHERE patient_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setInt(1, patientId);								
													
			System.out.print("\nAre you confirm to delete this Patient Appointment ? (Y/N) : ");
			char c = scn.next().charAt(0);
			if(c=='Y' || c=='y') {
				
				// STEP 5 : Execute Query..
				 result = psmt.executeUpdate(); // here want to pass query.. 
			}
			else {
				System.out.println("\nDeletion cancelled !!");
			}
			
			// STEP 6 : Process the Result..
			if(result==1) {
				System.out.println("\n Patient Appointment deleted Sucessfull !!");
			}
				
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}	
		Access.adminAccess();
	}// method ends
	
}// Class ends 
	