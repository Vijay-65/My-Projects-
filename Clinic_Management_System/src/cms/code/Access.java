package cms.code;

import java.util.Scanner;

public class Access {
	
	static Scanner scn = new Scanner(System.in);
	
	public static void adminAccess() {
		
		System.out.println("\n************ MENU **********");
		System.out.print("\n1.Doctor\n2.Patient\n3.Appointment_Details\n4.Feedback\n5.Back\n\nEnter your Choice : ");
        int ch = scn.nextInt();
		
        switch(ch) {
        
        case 1 : Doctor.doctorDetail(); break;
        case 2 : Patient.patientDetail(); break;
        case 3 : Appointment.appointmentDetail(); break;
        case 4 : Feedback.FeedbackDetail(); break;
        case 5 : Admin.admin(); break;
        default : System.out.println("Please Check your choice !! ");
        
		}
	}
}
