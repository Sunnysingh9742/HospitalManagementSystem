package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient 
{
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner)
	{
		this.connection=connection;
		this.scanner=scanner;
		
	}
	
	public void addPatient()
	{
		System.out.println("Enter Patient Name:");
		scanner.nextLine();
		String name=scanner.nextLine();
		
		System.out.println("Enter Patient Age:");
		int age=scanner.nextInt();
		
		System.out.println("Enter Patient Gender:");
		String gender=scanner.next();
		
		try
		{
			String query="INSERT INTO patients(name, age, gender) values(?,?,?)";
			
			
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setInt(2, age);
			statement.setString(3, gender);
			
			int affectedRows = statement.executeUpdate();
			
			if(affectedRows>0)
			{
				System.out.println("Patient Added Successfully");
			}
			else
			{
				System.out.println("Failed to Add Patient");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		
		
		
	}
	
	public void viewPatients()
	{
		String query="SELECT * FROM patients";
		
		try
		{
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			System.out.println("Patients: ");
			System.out.println("+------------+--------------------+----------+------------");
			
			System.out.println("|Patient Id  | Name               | Age      | Gender     |");
			
			System.out.println("+------------+--------------------+----------+------------");
			
			
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				
				System.out.printf("|%-12s|%-20s|%-10s|%-12s|\n",id,name,age,gender);
			System.out.println("+------------+--------------------+----------+------------");
				
				
				
			}
		
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	public boolean checkPatientById(int id)
	{
		String query="SELECT * FROM patients where id=?";
		
		try
		{
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		
		ResultSet resultSet= statement.executeQuery();
		
		if(resultSet.next())
		{
			return true;
		}
		else
		{
			return false;
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		return false;
		
	}
	

}
