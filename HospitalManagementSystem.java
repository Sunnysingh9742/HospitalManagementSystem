package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem
{
	
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	
	private static final String userName="root";
	
	private static final String password="W7301@jqir#";
	

	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}
		catch(ClassNotFoundException e)
		{
			
			e.printStackTrace();
			
		}
		
		Scanner scanner = new Scanner(System.in);
		
		
		try
		{
			Connection connection = DriverManager.getConnection(url,userName,password);
			
	
			Patient patient = new Patient(connection, scanner);
			
			Doctor doctor = new Doctor(connection);
			
			while(true)
			{
			System.out.println(" HOSPITAL MANAGEMENT SYSTEM ");
			System.out.println("1. Add Patient");
			System.out.println("2. View Patients");
			System.out.println("3. View Doctors");
			System.out.println("4. Book Appointment");
			System.out.println("5. Exit");
			
			System.out.println("Enter Your Choice ");
			int choice=scanner.nextInt();
			
			switch(choice)
			{
			
			case 1:
				//Add Patient
				patient.addPatient();
				System.out.println();
				break;
			case 2:
				//Add Patient
				patient.viewPatients();
				System.out.println();
				break;
			case 3:
				//Add Patient
				doctor.viewDoctors();
				System.out.println();
				break;
			case 4:
				//Add Patient
				bookAppointment(patient, doctor, connection, scanner);
				System.out.println();
				break;
				
			case 5:
				//Add Patient
				return;
			default:
				System.out.println("Enter Valid Choice !!! ");
				break;
				
			
			}
			
		
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}

	}
	
	public static void bookAppointment(Patient patient,Doctor doctor, Connection connection,Scanner scanner)
	{
		System.out.println("Enter Patient Id: ");
		int patientId=scanner.nextInt();
		
		System.out.println("Enter Doctor Id: ");
		int doctorId=scanner.nextInt();
		
		System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
		
		String appointMentDate = scanner.next();
		
		if(patient.checkPatientById(patientId) && doctor.checkDoctorById(doctorId))
		{
			if(checkDoctorAvailability(doctorId,appointMentDate,connection))
			{
				String Query="INSERT INTO appointments(patient_id, doctor_id,appointment_date) VALUES(?,?,?)";
				
				try
				{
					PreparedStatement statement = connection.prepareStatement(Query);
					statement.setInt(1, patientId);
					statement.setInt(2, doctorId);
					statement.setString(3, appointMentDate);
					
					int affectedRows = statement.executeUpdate();
					
					if(affectedRows>0)
					{
						System.out.println("Appointment Booked");
					}
					else
					{
						System.out.println("Failed To book Appointment");
					}
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}
			else
			{
				System.out.println("Doctor not available on this date !!");
			}
			
		}
		else
		{
			System.out.println("Either Doctor or Patient doesn't exist !!!");
		}
		
	}
	
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection)
	{
		String Query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		
		try
		{
		PreparedStatement statement = connection.prepareStatement(Query);
		statement.setInt(1, doctorId);
		statement.setString(2, appointmentDate);
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next())
		{
			int count = resultSet.getInt(1);
			if(count==0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		
		
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
		
	}

}
