package cms.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Feedback {
	
	static Scanner scn = new Scanner(System.in);
	
	public static void FeedbackDetail() {
		
		System.out.println("\n------------ FEEDBACK ----------");
		System.out.println("\n1.Add Feedback\n2.View FeedBack details\n3.Delete Feedback\n4.Back\n\nEnter Choice : ");
		int ch = scn.nextInt();
		switch(ch){
		case 1 : Feedback.AddFeedbackDetails();break;
		case 2 : Feedback.viewAllFeedbackDetails();break;
		case 3 : Feedback.deleteFeedbackDetails();break;
		case 4 : Access.adminAccess(); break;
		default : System.out.println(" Please Check you choice !! ");
		}
	}

	private static void AddFeedbackDetails() {
		
		try {

			// STEP 1 : Load the Driver..
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);

			// STEP 3 : Issue the Query..
			String query = " INSERT INTO feedback ( patient_id, doctor_id, feedback, rating  ) VALUES (?,?,?,?) ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for both Static and Dynamic query..
																	// Support placeholder(?) , More Secure.

			System.out.println("********* ADDING FEEDBACK DETAILS *********\n");

			System.out.print("Enter Patient ID :");
			String patientId = scn.next();

			System.out.print("Enter Doctor ID :");
			String doctorId = scn.next();

			System.out.print("Enter Feedback :");
			String f = scn.nextLine();
			String feedback = scn.nextLine();

			System.out.print(" Rating :");
			int rating = scn.nextInt();

			psmt.setString(1, patientId);
			psmt.setString(2, doctorId);
			psmt.setString(3, feedback);
			psmt.setInt(4, rating);

			// STEP 5 : Execute Query..
			int result = psmt.executeUpdate();

			// STEP 6 : Process the Result..
			if (result == 1) {
				
				System.out.println("\n Feedback added Sucessfull !!\n");
			}

			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		System.out.print("\nDo you want to Continue ? (Y/N) : ");
		char c = scn.next().charAt(0);
		if(c=='Y' || c=='y') {
			Feedback.AddFeedbackDetails();
		}
		else {
			Access.adminAccess();
		}
	}
	
	
	
	public static void viewAllFeedbackDetails() {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM feedback ;";

			// STEP 4 : Create Statement..
			Statement stmt = con.createStatement(); // Used for Simpler and one-time queries..
													// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			
			// STEP 5 : Execute Query..
			ResultSet result = stmt.executeQuery(query); // here want to pass query.. 
			
			System.out.println("\t----------------- DISPLAYING FEEDBACK DETAILS ------------------\n");
			
			// STEP 6 : Process the Result..
			System.out.println("\n  feedback_id  |  patient_id  |  doctor_id  |\t\t Feedback \t\t |   rating  ");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int feedbackId= result.getInt(1);  				// Column 1
				String patientId = result.getString(2);		// Column 2
				String doctorId = result.getString(3);		// Column 3
				String feedback = result.getString(4);			    	// Column 4
				int rating = result.getInt(5);	
						
				System.out.println("   \t"+feedbackId+"\t"+patientId+"\t\t"+doctorId+"\t\t"+feedback+"\t\t"+rating);
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
	
	
	public static void deleteFeedbackDetails(){
		
		int result = 0;
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("********** DELETING FEEDBACK DETAILS ***********\n");
			System.out.println("Enter the feedback Id you want to delete :");
			int feedbackId = scn.nextInt();
			
			// STEP 3 : Issue the Query..
			String query = "DELETE FROM feedback WHERE feedback_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
			psmt.setInt(1, feedbackId);										// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			System.out.print("\nAre you confirm to delete this Feedback Details ? (Y/N) : ");
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
				System.out.println("\nFeedback deleted Sucessfull !!");
			}
				
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
	}// method ends

}
