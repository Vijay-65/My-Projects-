package cms.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
	
	static Scanner scn = new Scanner(System.in);
	
	public static void start() {
		System.out.println("\t\t\t\t-----------------------------------------");
		System.out.println("\t\t\t\t|  WELCOME TO CLINIC MANAGEMENT SYSTEM  |");
		System.out.println("\t\t\t\t-----------------------------------------\n");
	}
	
	
	public static void InsertAdminDetails() {
		
		String encryptedPassword = "";

		try
		{
			// STEP 1 : Load the Driver..
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("Enter your username: ");
			String username = scn.next();

			System.out.println("Enter your password: ");
			String password = scn.next();
			
			System.out.println("Re-enter your password to confirm :");
			String repassword = scn.next();
			
			if(password.equals(repassword)) {
				encryptedPassword = password;
			}
			else {
				System.out.println("-----Password not matched! Kindly Re-enter.-----");
				Admin.InsertAdminDetails();
			}

			// Encrypt the password using SHA-256
			 encryptedPassword = encryptPassword(password);

			// STEP 3 : Issue the Query..
			String query = "INSERT INTO admin (username, password) VALUES (?, ?);";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query);
			psmt.setString(1, username); // Set the user name
			psmt.setString(2, encryptedPassword); // Set the encrypted password

			// STEP 5 : Execute Query..
			int rowsInserted = psmt.executeUpdate(); // Execute the PreparedStatement

			// STEP 6 : Process the Result..
			if (rowsInserted > 0) {
				System.out.println("Admin details inserted successfully!");
			}

			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
		}
		
		catch(SQLException|ClassNotFoundException | NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	    
	// Method to encrypt the password using SHA-256
	private static String encryptPassword(String password)   throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(password.getBytes());
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}


    public static void adminLogin() {
        
        try {
            // STEP 1 : Load the Driver.. 
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // STEP 2 : Establish a Connection..
            String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
            Connection con = DriverManager.getConnection(dburl);
            
            System.out.println("\n********* ADMIN LOGIN **********\n");
            
            System.out.print("Enter username: ");
            String username = scn.next();
            
            System.out.print("Enter password: ");
            String password = scn.next();
            
            // Encrypt the provided password using SHA-256
            String encryptedPassword = encryptPassword(password);
            
            // STEP 3 : Issue the Query..
            String query = "SELECT * FROM admin ;";

            // STEP 4 : Create Statement..
            PreparedStatement psmt = con.prepareStatement(query); 
            //psmt.setString(1, username); // Set the username
            
            // STEP 5 : Execute Query..
            ResultSet result = psmt.executeQuery(); // Execute the PreparedStatement
            
            // STEP 6 : Process the Result..
            if(!result.next()) {
            	 Admin.InsertAdminDetails();
            }
            else {
            	
            	do {
            		String storedUsername = result.getString("username");
            		String storedPassword = result.getString("password");
                
            		if(!storedUsername.equals(username) || !storedPassword.equals(encryptedPassword)){
            			System.out.println("\nIncorrect username or password !!");
            			Admin.retry();
            		}
            		else { //( storedPassword.equals(encryptedPassword)) {
            			System.out.println("\n--- Login Successful ! ---");
            			Admin.admin(); 
            		}  
            	}while (result.next());
            
            }
                        
            // STEP 7 : Close the Connection..
            psmt.close();
            con.close();
        }  
         catch(SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    private static void retry() {
    	
    	System.out.print("\n Do you want to Retry (Y/N) : ");
    	char s = scn.next().charAt(0);
    	if(s=='Y' || s=='y' )
    	{
    		Admin.adminLogin();
    	}else {
    		System.out.println("\n\n\t\t************ THANKYOU !! ************");
    	}
    }
    
    
    
    public static void adminLogout() {
    	
    	System.out.println("Confirm you want to logout (Y/N) :");
    	char ch = scn.next().charAt(0);
    	if(ch=='Y' || ch =='y') {
    		System.out.println("\n -- Logout Successful --\n\n\n\t\t\t************ THANKYOU !! ************");
    	}
    	else {
    		Admin.admin();
    	}
    	
    }
    
    
    public static void admin() {
    	
    	System.out.println("1.GOTO APP \n2.ADD ADMIN \n3.VIEW ADMIN\n4.DELETE ADMIN\n5.LOGOUT\n\nEnter your choice :");
    	int ch = scn.nextInt();
    	switch(ch) {
    	case 1 : Access.adminAccess(); break;
    	case 2 : Admin.InsertAdminDetails(); break;
    	case 3 : Admin.viewAllAdminDetails(); break;
    	case 4 : Admin.deleteAdminDetails(); break;
    	case 5 : Admin.adminLogout(); break;
    	default : System.out.println(" Invalid Choice !"); Admin.admin();
    	
    	}
    }
    
    
    public static void viewAllAdminDetails() {
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			// STEP 3 : Issue the Query..
			String query = "SELECT * FROM admin ;";

			// STEP 4 : Create Statement..
			Statement stmt = con.createStatement(); // Used for Simpler and one-time queries..
													// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			
			// STEP 5 : Execute Query..
			ResultSet result = stmt.executeQuery(query); // here want to pass query.. 
			
			System.out.println("\t----------------- DISPLAYING ADMIN DETAILS ------------------\n");
			
			// STEP 6 : Process the Result..
			System.out.println("\n  Admin_Id  |\t Username \t |    Password ");
			
			// Display.. 
			while(result.next()) // Check if new row is there and value present or not..  
			{     
				int adminId= result.getInt(1);  				// Column 1
				String name = result.getString(2);		// Column 2
				String pwd = result.getString(3);				// Column 7
						
				System.out.println("   "+adminId+"\t"+name+"\t"+pwd);
			}
						
			// STEP 7 : Close the Connection..
			stmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Admin.admin();
	}  
    
    public static void deleteAdminDetails(){
		
		int result = 0;
		
		try {
			// STEP 1 : Load the Driver.. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// STEP 2 : Establish a Connection..
			String dburl = "jdbc:mysql://localhost:3307/clinic_management_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			
			System.out.println("********** DELETING ADMIN DETAILS ***********\n");
			System.out.println("Enter the Admin Id you want to delete :");
			int adminId = scn.nextInt();
			
			// STEP 3 : Issue the Query..
			String query = "DELETE FROM admin WHERE admin_id = ? ;";

			// STEP 4 : Create Statement..
			PreparedStatement psmt = con.prepareStatement(query); // Used for Simpler and one-time queries..
			psmt.setInt(1, adminId);										// DOES NOT support placeholder (?)..
													// More Flexible, Higher risk (Not Secure)..
			System.out.print("\nAre you confirm to delete this Admin Details ? (Y/N) : ");
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
				System.out.println("\n Admin deleted Sucessfull !!");
			}
				
			// STEP 7 : Close the Connection..
			psmt.close();
			con.close();
			
		}
		catch(SQLException |ClassNotFoundException e) {
			e.getStackTrace();
		}
		Admin.admin();
	}// method ends
}



