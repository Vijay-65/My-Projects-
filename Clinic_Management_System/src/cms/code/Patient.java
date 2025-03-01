package cms.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Patient {
	
	static Scanner scn = new Scanner(System.in);
	
	public static void patientDetail() {
		
		System.out.println("1.Add Patient.\n2.View All Records. 3.Search Patient.\n4.Update Patient\n 5.Delete Patient\n6.Back\n");
		System.out.println("Enter the Choice to Perform :");
		int ch = scn.nextInt();
		
		switch(ch) {
		
		case 1 : Patient.insertPatientDetails(); break;
		case 2 : Patient.viewAllPatientDetails(); break;
		case 3 : Patient.searchPatientDetails(); break;
		case 4 : Patient.updatePatientDetails(); break;
		case 5 : Patient.deletePatientDetails(); break;
		case 6 : Access.adminAccess(); break;
		default : System.out.println("Please Check your Choice !! ");
		}
	}
	
	public static void insertPatientDetails() {    // 1. Add Patient
		
		try {
		
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = " INSERT INTO patients ( first_name, last_name, age, gender, phone_number, email, address ) VALUES (?,?,?,?,?,?,?) ;";
			
			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for both Static and Dynamic query..
																//  Support placeholder(?) , More Secure.
			
			System.out.println("******* ADD NEW PATIENT DETAILS *********\n");
			
			System.out.print("Enter First name :");
			String firstName = scn.next();
			
			System.out.print("Enter Last name :");
			String lastName = scn.next();
			
			System.out.print("Enter age :");
			int age = scn.nextInt();
			
			System.out.print("Enter gender :");
			String gender = scn.next();
			
			System.out.print("Enter phone Number :");
			String phoneNumber = scn.next();
			
			System.out.print("Enter email :");
			String email = scn.next();
			
			System.out.print("Enter Address :");
			String s = scn.nextLine();
			String address = scn.nextLine();
			
			psmt.setString(1, firstName);
			psmt.setString(2, lastName);
			psmt.setInt(3, age);
			psmt.setString(4, gender);
			psmt.setString(5, phoneNumber);
			psmt.setString(6, email);
			psmt.setString(7, address);
			
			// STEP 5 : Execute Query..
			int result = psmt.executeUpdate();
			
			// STEP 6 : Process the Result..
			if(result==1) {
				Patient.viewNewPatientDetails(phoneNumber);
				System.out.println("\n Patient data added Sucessfully !!");
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
			Patient.insertPatientDetails();
		}
		else {
			Access.adminAccess();
		}
		
	}// method ends
	
	
	public static void viewNewPatientDetails(String phoneNumber) {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM patients WHERE phone_number = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setString(1, phoneNumber);					 	
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\n Patient_id |\t First_Name \t|  Last_name  |\t age \t|\t gender \t|\t phone_number \t|\t email \t|\t address");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int patientId= result.getInt(1);  				// Column 1
				String firstName = result.getString(2);		    // Column 2
				String lastName = result.getString(3);		    // Column 3
				String age = result.getString(4);   	 // Column 4
				String gender = result.getString(5);   	 // Column 5
				String phone = result.getString(6);		        // Column 5
				String email = result.getString(7);				// Column 6
				String address = result.getString(8);	           // column 7
						
				System.out.println("  "+patientId+"\t"+firstName+"\t"+lastName+"\t"+age+"\t\t"+gender+"\t\t"+phone+"\t\t"+email+"\t\t"+address);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void viewAllPatientDetails() {           // 2. Read all Patient
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM patients ;";

			// STEP 4 : Create Statement..
			Statement stmt = con.createStatement(); // Used for Simpler and one-time queries..
													// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			
			System.out.println("\t\t***************** DISPLAYING PATIENT DETAILS ********************");
			
			// STEP 5 : Execute Query..
			ResultSet result = stmt.executeQuery(query); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\npatient_id \t|\t First_Name \t|   Last_name \t|\t age \t|\t gender   |   phone_number \t|\t email \t|\t address");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int patientId= result.getInt(1);  				// Column 1
				String firstName = result.getString(2);		// Column 2
				String lastName = result.getString(3);		// Column 3
				int age = result.getInt(4);			    	// Column 4
				String gender = result.getString(5);			// Column 5
				String phone = result.getString(6);		        // Column 6
				String email = result.getString(7);				// Column 7
				String address = result.getString(8);	
						
				System.out.println("\t"+patientId+"\t\t"+firstName+"\t\t"+lastName+"\t\t"+age+"\t\t"+gender+"\t\t"+phone+"\t\t"+email+"\t\t"+address);
			}
						
			// STEP 7 : Close the Connection..
			stmt.close();
			con.close();	
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();
	}// Method ends 
	
	
	public static void updatePatientDetails() {       // 3. Update patient details 
		
		int patientId = 0;
		
		try {
		
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			
			System.out.println("******** UPDATE PATIENT DETAILS *********\n");
			
			System.out.println("Enter the patient Id to update : ");
		    patientId = scn.nextInt();
			
			System.out.println("\n1.First_name \n2.Last_name \n3.Age \n4.Gender \n5.Phone_number \n6.Email 7. Address \n\nEnter your choice : ");
			int ch = scn.nextInt();
			
			String query = "";
			
			PreparedStatement psmt;
			int result = 0;
			
			// STEP 3 : Issue the Query..
			switch(ch) {
			
			case 1 : System.out.print("Enter First name :");
					 String firstName = scn.next();
					 query = " UPDATE patients SET first_name = ? WHERE patient_id = "+patientId+" ; ";
					 
					 // STEP 4 : Create Statement..
					 psmt = con.prepareStatement(query); 
					 psmt.setString(1, firstName);
					 
					 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Patient data updated Sucessfully !!");
		     		 }
					 psmt.close();
					 break;
					 
			case 2 : System.out.print("Enter Last name :");
			 		 String lastName = scn.next();
			 		 query = " UPDATE patients SET last_name = ? WHERE patient_id = "+patientId+" ; ";
			 
			 		 // STEP 4 : Create Statement..
			 		 psmt = con.prepareStatement(query); 
			 		 psmt.setString(1, lastName);
			 		 
			 		 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Patient data updated Sucessfully !!");
					 }
					 psmt.close();
			 		 break;
			 		 
			case 3:  System.out.print("Enter age :");
					 int age = scn.nextInt();
				     query = " UPDATE patients SET age = ? WHERE patient_id = " + patientId + " ; ";

						// STEP 4 : Create Statement..
					 psmt = con.prepareStatement(query);
					 psmt.setInt(1, age);
					 
					 // STEP 5 : Execute Query..
					 result = psmt.executeUpdate();
					 if(result==1) {
		     				System.out.println("\n Patient data updated Sucessfully !!");
		     		 }
					 psmt.close();
		 			 break;
		 			 
			case 4 : System.out.print("Enter gender :");
	 		 		 String gender = scn.next();
	 		 		 query = " UPDATE patients SET gender = ? WHERE patient_id = "+patientId+" ; ";
	 
	 		 		 // STEP 4 : Create Statement..
	 		 		 psmt = con.prepareStatement(query); 
	 		 		 psmt.setString(1, gender);
	 		 		 
	 		 	     // STEP 5 : Execute Query..
	 				 result = psmt.executeUpdate();
	 				 if(result==1) {
	     				System.out.println("\n Patient data updated Sucessfully !!");
	     			 }
	 				 psmt.close();
	 		 		 break;
	 		 		 
			case 5 : System.out.print("Enter phone Number :");
	 		         String phone = scn.next();
	 		         query = " UPDATE patients SET phone_number = ? WHERE patient_id = "+patientId+" ; ";

	 		         // STEP 4 : Create Statement..
	 		         psmt = con.prepareStatement(query); 
	 		         psmt.setString(1, phone);
	 		         
	 		         // STEP 5 : Execute Query..
	 				 result = psmt.executeUpdate();
	 				 if(result==1) {
	     				System.out.println("\n Patient data updated Sucessfully !!");
	     			 }
	 				 psmt.close();
	 		         break;
	 		         
			case 6 : System.out.print("Enter Email :");
	         		 String email = scn.next();
	         		 query = " UPDATE patients SET email = ? WHERE patient_id = "+patientId+" ; ";

	         		 // STEP 4 : Create Statement..
	         		 psmt = con.prepareStatement(query); 
	         		 psmt.setString(1, email);
	         		 
	         		 // STEP 5 : Execute Query..
	     			 result = psmt.executeUpdate();
	     			 if(result==1) {
	     				System.out.println("\n Patient data updated Sucessfully !!");
	     			 }
	     			 psmt.close();
	         		 break;
	         		 
			case 7 : 
					 System.out.print("Enter address :");
	         		 String s = scn.nextLine();
	         		 String address = scn.nextLine();
	         		 query = " UPDATE patients SET address = ? WHERE patient_id = "+patientId+" ; ";

	         		 // STEP 4 : Create Statement..
	         		 psmt = con.prepareStatement(query); 
	         		 psmt.setString(1, address);
	         		 
	         		 // STEP 5 : Execute Query..
	     			 result = psmt.executeUpdate();
	     			 if(result==1) {
	     				System.out.println("\n Patient data updated Sucessfully !!");
	     			 }
	     			 psmt.close();
	         		 break;
	 		 		 
	        default : System.out.println("It's not a valid choice !!");
			}
			
			// STEP 7 : Close the Connection..
			con.close();	
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		viewUpdatedPatientDetails(patientId);	
	}// method ends


	public static void viewUpdatedPatientDetails(int patientId) {       
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM patients WHERE patient_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setInt(1, patientId);								
			
			// STEP 5 : Execute Query..
			ResultSet result = psmt.executeQuery(); // here want to pass query.. 
			
			// STEP 6 : Process the Result..
			System.out.println("\npatient_id \t|\t First_Name \t|\t Last_name \t|\t age \t|\t gender \t|\t phone_number \t|\t email \t|\t address");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int patient_Id= result.getInt(1);  				// Column 1
				String firstName = result.getString(2);		// Column 2
				String lastName = result.getString(3);		// Column 3
				int age = result.getInt(4);			    	// Column 4
				String gender = result.getString(5);			// Column 5
				String phone = result.getString(6);		        // Column 6
				String email = result.getString(7);				// Column 7
				String address = result.getString(8);	
						
				System.out.println("\t"+patient_Id+"\t\t"+firstName+"\t\t"+lastName+"\t\t"+age+"\t\t"+gender+"\t\t"+phone+"\t\t"+email+"\t\t"+address);
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
	
	
	public static void searchPatientDetails() {    //  4. SEARCH PATIENT..

		String query = "";
		PreparedStatement psmt;
		ResultSet result;

		try {
			// STEP 1 : Load the Driver..
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);

			System.out.println("1. Search by Name\n2. Search by Phone Number\n Enter the Option : ");
			int ch = scn.nextInt();

			if (ch == 1) {
				System.out.println("Enter First_Name :");
				String fName = scn.next();
				System.out.println("Enter Last_Name :");
				String l = scn.nextLine();
				String lName = scn.nextLine();

				// STEP 3 : Issue the Query..
				query = "SELECT * FROM patients WHERE first_name = ? AND last_name = ? ;";

				// STEP 4 : Create Statement..
				psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
				psmt.setString(1, fName);
				psmt.setString(2, lName);

				// STEP 5 : Execute Query..
				result = psmt.executeQuery();

				if (result.next()) {
					String dbFirstName = result.getString("first_name");
					String dbLastName = result.getString("last_name");

					if (fName.equals(dbFirstName) && lName.equals(dbLastName)) {

						System.out.println("Patient details Found !! \n");

						// STEP 6 : Process the Result..
						System.out.println("\n Patient_id |\t First_Name \t|\t Last_name \t|\t age \t|\t gender \t|\t phone_number \t|\t email \t|\t address");

						int patient_Id = result.getInt(1); // Column 1
						String firstName = result.getString(2); // Column 2
						String lastName = result.getString(3); // Column 3
						String age = result.getString(4); // Column 4
						String gender = result.getString(5);
						String phone = result.getString(6); // Column 5
						String email = result.getString(7); // Column 6
						String address = result.getString(8); // column 7

						System.out.println("\t" + patient_Id + "\t" + firstName + "\t\t" + lastName + "\t\t"
								+ age +"\t\t"+gender+ "\t\t" + phone + "\t\t" + email + "\t\t" + address);

						// STEP 7 : Close the Connection..
						psmt.close();
					}
				} else {
					System.out.println("Patient details Not found ");
				}
			} 
			else if (ch == 2) {
				System.out.println("Enter the Phone_numeber : ");
				String ph = scn.next();

				// STEP 3 : Issue the Query..
				query = "SELECT * FROM patients WHERE phone_number = ? ;";

				// STEP 4 : Create Statement..
				psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
				psmt.setString(1, ph);

				// STEP 5 : Execute Query..
				result = psmt.executeQuery(); // here want to pass query..

				if (result.next()) {

					String dbPhoneNumber = result.getString("phone_number");

					if (ph.equals(dbPhoneNumber)) {

						// STEP 6 : Process the Result..
						System.out.println("\n Patient_id |\t First_Name \t|\t Last_name \t|\t age \t|\t gender \t|\t phone_number \t|\t email \t|\t address");

						// Display..

						int patientId = result.getInt(1); // Column 1
						String firstName = result.getString(2); // Column 2
						String lastName = result.getString(3); // Column 3
						String age = result.getString(4); // Column 4
						String gender = result.getString(5);
						String phone = result.getString(6); // Column 5
						String email = result.getString(7); // Column 6
						String address = result.getString(8); // column 7

						System.out.println("\t" + patientId + "\t" + firstName + "\t\t" + lastName + "\t\t"
								+ age +"\t\t"+gender+ "\t\t" + phone + "\t\t" + email + "\t\t" + address);

						// STEP 7 : Close the Connection..
						psmt.close();
					}

				} else {
					System.out.println("Patient details not found ..");
				}

			}

			else {
				System.out.println("Invalid Choice ..");
			}
			con.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.getStackTrace();
		}
		Access.adminAccess();

	}// method ends ..
	
	
	public static void deletePatientDetails(){				//  5. DELETE patient details 
		
		int result = 0;
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("********** DELETING PATIENT DETAILS ***********\n");
			System.out.println("Enter the patient Id :");
			int patientId = scn.nextInt();
			
			Patient.viewUpdatedPatientDetails(patientId);
			
			// STEP 3 : Issue the Query..
			String query = "DELETE FROM patients WHERE patient_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); 
			psmt.setInt(1, patientId);								
													
			System.out.print("\nAre you confirm to delete this patient details ? (Y/N) : ");
			char c = scn.next().charAt(0);
			if(c=='Y' || c=='y') {
				
				// STEP 5 : Execute Query..
				 result = psmt.executeUpdate(); // here want to pass query.. 
			}
			else {
				System.out.println("Deletion cancelled !!");
			}
			
			// STEP 6 : Process the Result..
			if(result==1) {
				System.out.println("Patient details Deleted Sucessfull !!");
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

}// class ends
