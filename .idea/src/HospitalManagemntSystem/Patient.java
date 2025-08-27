package HospitalManagemntSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

  private Connection connection;

  private Scanner scanner;


  public Patient(Connection connection , Scanner scanner){
      this.connection=connection;
      this.scanner=scanner;

  }

  public void addPatient(){
      System.out.println("Enter Patient Name : ");
      String name=scanner.next();
      System.out.println("Enter the Patient Age :");
      int age=scanner.nextInt();
      System.out.println("Enter the Patient Gender :");
      String gender=scanner.next();
      try{
String query="INSERT INTO patients1(name,age,gender) values(?,?,?)";
          PreparedStatement preparedStatement=connection.prepareStatement(query);
          preparedStatement.setString(1,name);
          preparedStatement.setInt(2,age);
          preparedStatement.setString(3,gender);
int affected=preparedStatement.executeUpdate();
if(affected>0){
    System.out.println("Patient  Added Successfully!");
}
else{
    System.out.println("Failed to add Patient!");
}
      }catch (SQLException e){
          e.printStackTrace();
      }


      //view Patient
  }

}
