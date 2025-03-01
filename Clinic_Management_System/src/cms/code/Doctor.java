package cms.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Doctor {
	
	static Scanner scn = new Scanner(System.in);
	
	public static void doctorDetail() {
		
		System.out.println("1.Add details\n2.View All doctors details\n3.Search doctor details\n4.Update details\n5.Delete doctor details\n6.Back\n");
		System.out.println("Enter the Choice to Perform :");
		int ch = scn.nextInt();
		
		switch(ch) {
		
		case 1 : Doctor.insertDoctorDetails(); break;
		case 2 : Doctor.viewAllDoctorDetails(); break;
		case 3 : Doctor.searchDoctorDetails(); break;
		case 4 : Doctor.updateDoctorDetails(); break;
		case 5 : Doctor.deleteDoctorDetails(); break;
		case 6 : Access.adminAccess(); break;
		default : System.out.println("Please Check your Choice !! ");
		}
	}
	
	public static void insertDoctorDetails() {        // 1. ADD 
		
		String phoneNumber = ""; 
		
		try {
		
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = " INSERT INTO doctors ( first_name, last_name, specialization, phone_number, email, consultation_fee ) VALUES (?,?,?,?,?,?) ;";
			
			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for both Static and Dynamic query..
																//  Support placeholder(?) , More Secure.
			
			System.out.println("********* ADDING DOCTOR DETAILS *********\n");
			
			System.out.print("Enter First name :");
			String firstName = scn.next();
			
			System.out.print("Enter Last name :");
			String lastName = scn.next();
			
			System.out.print("Enter specialization :");
			String specialization = scn.next();
			
			System.out.print("Enter phone Number :");
		    phoneNumber = scn.next();
			
			System.out.print("Enter email :");
			String email = scn.next();
		
			System.out.print("Enter consultation fees :");
			int fee = scn.nextInt();
			
			psmt.setString(1, firstName);
			psmt.setString(2, lastName);
			psmt.setString(3, specialization);
			psmt.setString(4, phoneNumber);
			psmt.setString(5, email);
			psmt.setInt(6, fee);
			
			// STEP 5 : Execute Query..
			int result = psmt.executeUpdate();
			
			// STEP 6 : Process the Result..
			if(result==1) {
				viewNewDoctorDetails(phoneNumber);
				System.out.println("\n Doctor details added Sucessfully !!\n");
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
			Doctor.insertDoctorDetails();
		}
		else {
			Access.adminAccess();
		}
		
	}// method ends
	
	
	public static void viewNewDoctorDetails(String phoneNumber) {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM doctors WHERE phone_number = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setString(1, phoneNumber);					 	
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\nDoctor_id |\t First_Name \t|  Last_name  |\t specialization \t|\t phone_number \t|\t email\t\t| consultation_fee");
			
			// Display.. 
			if(result.next()) // Check if new row is there and value present or not..  
			{     
				int doctor_Id= result.getInt(1);  				// Column 1
				String firstName = result.getString(2);		    // Column 2
				String lastName = result.getString(3);		    // Column 3
				String specialization = result.getString(4);    // Column 4
				String phone = result.getString(5);		        // Column 5
				String email = result.getString(6);				// Column 6
				int fee = result.getInt(7);	                    // column 7
						
				System.out.println("  "+doctor_Id+"\t\t"+firstName+"\t\t\t"+lastName+"\t\t"+specialization+"\t\t"+phone+"\t"+email+"\t\t"+fee);
			}
						
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}	
	}// method ends..
	
	
	public static void viewAllDoctorDetails() {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM doctors ;";

			// STEP 4 : Create Statement..
			Statement stmt = con.createStatement(); // Used for Simpler and one-time queries..
													// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			
			// STEP 5 : Execute Query..
			ResultSet result = stmt.executeQuery(query); // here want to pass query.. 
			
			System.out.println("\t----------------- DISPLAYING ALL DOCTOR DETAILS ------------------\n");
			
			// STEP 6 : Process the Result..
			System.out.println("\nDoctor_id |\t First_Name \t|  Last_name  |\t specialization \t|\t phone_number \t|\t email\t\t| consultation_fee");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int doctorId= result.getInt(1);  				// Column 1
				String firstName = result.getString(2);		// Column 2
				String lastName = result.getString(3);		// Column 3
				String specialization = result.getString(4);			    	// Column 4
				String phone = result.getString(5);			// Column 5
				String email = result.getString(6);				// Column 7
				int fee = result.getInt(7);	
						
				System.out.println("  "+doctorId+"\t\t"+firstName+"\t\t\t"+lastName+"\t\t"+specialization+"\t\t"+phone+"\t"+email+"\t\t"+fee);
			}
						
			// STEP 7 : Close the Connection..
			stmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();
	}
	
	
	
	public static void updateDoctorDetails() {
		
		int doctorId = 0;
		
		try {
		
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			
			System.out.println("******** UPDATE DOCTOR DETAILS *********\n");
			
			System.out.println("Enter the Doctor Id to update : ");
		    doctorId = scn.nextInt();
			
			System.out.println("\n1.First_name \n2.Last_name \n3.Specialization \n4.Phone_number \n5.Email \n6.Consultation_fee \n\nEnter your choice : ");
			int ch = scn.nextInt();
			
			String query = "";
			
			PreparedStatement psmt;
			int result = 0;
			
			// STEP 3 : Issue the Query..
			switch(ch) {
			
			case 1 : System.out.print("Enter First name :");
					 String firstName = scn.next();
					 query = " UPDATE doctors SET first_name = ? WHERE doctor_id = "+doctorId+" ; ";
					 
					 // STEP 4 : Create Statement..
					 psmt = con.prepareStatement(query); 
					 psmt.setString(1, firstName);
					 
					 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Doctor data updated Sucessfully !!");
		     		 }
					 psmt.close();
					 break;
					 
			case 2 : System.out.print("Enter Last name :");
			 		 String lastName = scn.next();
			 		 query = " UPDATE doctors SET last_name = ? WHERE doctor_id = "+doctorId+" ; ";
			 
			 		 // STEP 4 : Create Statement..
			 		 psmt = con.prepareStatement(query); 
			 		 psmt.setString(1, lastName);
			 		 
			 		 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Doctor data updated Sucessfully !!");
					 }
					 psmt.close();
			 		 break;
			 		 
			case 3:  System.out.print("Enter Specialization :");
					 String specialization = scn.next();
				     query = " UPDATE doctors SET specialization = ? WHERE doctor_id = " + doctorId + " ; ";

						// STEP 4 : Create Statement..
					 psmt = con.prepareStatement(query);
					 psmt.setString(1, specialization);
					 
					 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Doctor data updated Sucessfully !!");
		     		 }
					 psmt.close();
		 			 break;
		 			 
			case 4 : System.out.print("Enter phone number :");
	 		 		 String phone = scn.next();
	 		 		 query = " UPDATE doctors SET phone_number = ? WHERE doctor_id = "+doctorId+" ; ";
	 
	 		 		 // STEP 4 : Create Statement..
	 		 		 psmt = con.prepareStatement(query); 
	 		 		 psmt.setString(1, phone);
	 		 		 
	 		 	     // STEP 5 : Execute Query..
	 				 result = psmt.executeUpdate();
	 				 if(result==1) {
	     				System.out.println("\n Doctor data updated Sucessfully !!");
	     			 }
	 				 psmt.close();
	 		 		 break;
	 		 		 
			case 5 : System.out.print("Enter Doctor Email :");
	 		         String email = scn.next();
	 		         query = " UPDATE doctors SET email = ? WHERE doctor_id = "+doctorId+" ; ";

	 		         // STEP 4 : Create Statement..
	 		         psmt = con.prepareStatement(query); 
	 		         psmt.setString(1, email);
	 		         
	 		         // STEP 5 : Execute Query..
	 				 result = psmt.executeUpdate();
	 				 if(result==1) {
	     				System.out.println("\n Doctor data updated Sucessfully !!");
	     			 }
	 				 psmt.close();
	 		         break;
	 		         
			case 6 : System.out.print("Enter Consultation fee :");
	         		 int fee = scn.nextInt();
	         		 query = " UPDATE doctors SET consultation_fee = ? WHERE doctor_id = "+doctorId+" ; ";

	         		 // STEP 4 : Create Statement..
	         		 psmt = con.prepareStatement(query); 
	         		 psmt.setInt(1, fee);
	         		 
	         		 // STEP 5 : Execute Query..
	     			 result = psmt.executeUpdate();
	     			 if(result==1) {
	     				System.out.println("\n Doctor data updated Sucessfully !!");
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
		
		viewUpdatedDoctorDetails(doctorId);
			
	}// method ends


	public static void viewUpdatedDoctorDetails(int doctorId) {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM doctors WHERE doctor_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
			psmt.setInt(1, doctorId);										// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\nDoctor_id |\t First_Name \t|  Last_name  |\t specialization \t|\t phone_number \t|\t email\t\t| consultation_fee");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int doctor_Id= result.getInt(1);  				// Column 1
				String firstName = result.getString(2);		    // Column 2
				String lastName = result.getString(3);		    // Column 3
				String specialization = result.getString(4);    // Column 4
				String phone = result.getString(5);		        // Column 5
				String email = result.getString(6);				// Column 6
				int fee = result.getInt(7);	                    // column 7
						
				System.out.println("  "+doctor_Id+"\t\t"+firstName+"\t\t\t"+lastName+"\t\t"+specialization+"\t\t"+phone+"\t"+email+"\t\t"+fee);
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
	
	
	public static void searchDoctorDetails() {
		
		String query = "" ;
		PreparedStatement psmt ;
		ResultSet result ;
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("1. Search by Name\n2. Search by Phone Number\n Enter the Option : ");
			int ch = scn.nextInt();
			
			if(ch == 1) {
				System.out.println("Enter First_Name :");
				String fName = scn.next();
				System.out.println("Enter Last_Name :");
			    String l = scn.nextLine();
			    String lName = scn.nextLine();
					 
				// STEP 3 : Issue the Query..
				query = "SELECT * FROM doctors WHERE first_name = ? AND last_name = ? ;";
				    
				// STEP 4 : Create Statement..
				psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
				psmt.setString(1, fName);
				psmt.setString(2, lName);
					
				// STEP 5 : Execute Query..
				result = psmt.executeQuery(); 
				
				if (result.next()) 
				{ 
					String dbFirstName = result.getString("first_name"); 
					String dbLastName = result.getString("last_name");
				    
					if (fName.equals(dbFirstName) && lName.equals(dbLastName)) {
						
						System.out.println("Doctor details Found !! \n");
				    
				    // STEP 6 : Process the Result..
						System.out.println("\nDoctor_id |\t First_Name \t|  Last_name  |\t specialization \t|\t phone_number \t|\t email\t\t| consultation_fee");
						 
						int doctor_Id= result.getInt(1);  				// Column 1
						String firstName = result.getString(2);		    // Column 2
						String lastName = result.getString(3);		    // Column 3
						String specialization = result.getString(4);    // Column 4
						String phone = result.getString(5);		        // Column 5
						String email = result.getString(6);				// Column 6
						int fee = result.getInt(7);	                    // column 7
								
						System.out.println("  "+doctor_Id+"\t\t"+firstName+"\t\t\t"+lastName+"\t\t"+specialization+"\t\t"+phone+"\t"+email+"\t\t"+fee);
						
						// STEP 7 : Close the Connection..
						psmt.close();		
					}
				}
				else { System.out.println("Doctor details Not found "); }
			}
			else if(ch == 2)
			{
				 System.out.println("Enter the Phone_numeber : ");
				 String ph = scn.next();
					 
				// STEP 3 : Issue the Query..
				query = "SELECT * FROM doctors WHERE phone_number = ? ;";
					 
				// STEP 4 : Create Statement..
				psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
				psmt.setString(1, ph);
					
				// STEP 5 : Execute Query..
				result = psmt.executeQuery(); // here want to pass query.. 
				
				if(result.next()) {
					
					String dbPhoneNumber = result.getString("phone_number");
					
					if (ph.equals(dbPhoneNumber)) {
				
						// STEP 6 : Process the Result..
						System.out.println("\nDoctor_id |\t First_Name \t|\t Last_name \t|\t specialization \t|\t phone_number \t|\t email \t|\t consultation_fee");
					
						// Display.. 
				      
						int doctor_Id= result.getInt(1);  				// Column 1
						String firstName = result.getString(2);		    // Column 2
						String lastName = result.getString(3);		    // Column 3
						String specialization = result.getString(4);    // Column 4
						String phone = result.getString(5);		        // Column 5
						String email = result.getString(6);				// Column 6
						int fee = result.getInt(7);	                    // column 7
								
						System.out.println("\t"+doctor_Id+"\t"+firstName+"\t\t"+lastName+"\t\t"+specialization+"\t\t"+phone+"\t\t"+email+"\t\t"+fee);
						
						// STEP 7 : Close the Connection..
						psmt.close();
					}
					
				}else { System.out.println("Doctor details not found .."); }
				
			}
			
			else {
				System.out.println("Invalid Choice ..");
			}
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();
		
	}// method ends ..
	
	
	public static void deleteDoctorDetails(){
		
		int result = 0;
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("********** DELETING DOCTOR DETAILS ***********\n");
			System.out.println("Enter the Doctor Id you want to delete :");
			int doctorId = scn.nextInt();
			
			Doctor.viewUpdatedDoctorDetails(doctorId);
			
			// STEP 3 : Issue the Query..
			String query = "DELETE FROM doctors WHERE doctor_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
			psmt.setInt(1, doctorId);										// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			System.out.print("\nAre you confirm to delete this doctor details ? (Y/N) : ");
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
				System.out.println("\nDoctor details deleted Sucessfull !!");
			}
			
				
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		
		Access.adminAccess();	
	}

}
