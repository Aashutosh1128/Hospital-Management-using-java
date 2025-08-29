package HospitalManagemntSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static final  String username="root";
    private static final String password="Aashu@1128";

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner sc=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection, sc);
            Doctor doctor=new Doctor(connection);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1 Add Patient");
                System.out.println("2  View Patient");
                System.out.println("3  View Doctors");
                System.out.println("4 Book Appointment");
                System.out.println("5 Exit");

                System.out.println("Enter your Choice");
                int choice =sc.nextInt();
                switch(choice){
                    case 1:
                        //add Patient
                      patient.addPatient();
                        System.out.println();
                      break;
                    case 2:
                       patient.viewPatients();
                        System.out.println();
                       break;
                    case 3:
                    doctor.viewDoctors();
                        System.out.println();
                    break;
                    case 4:
bookAppointment(patient,doctor,connection,sc);
                        System.out.println();
break;

                    case 5:
                      return;

                    default:
                        System.out.println("Enter valid choice ;");
break;



                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctor doctor, Connection connection,Scanner sc){
        System.out.println("Enter the Patient Id");
        int patientId=sc.nextInt();
        System.out.println("Enter the Doctor Id");
        int doctorId=sc.nextInt();
        System.out.println("Enter the data(YYYY-MM-DD) :");
        String appointmentDate=sc.next();
        if(patient.getpatientId(patientId) && doctor.getDoctorById(doctorId)){
          if(checkDoctorAvailability(doctorId,appointmentDate,connection)){
              String appointmentQuery = "Insert into appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
            try{
                PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                preparedStatement.setInt(1,patientId);
                preparedStatement.setInt(2,doctorId);
                preparedStatement.setString(3,appointmentDate);

                int rowAffected=preparedStatement.executeUpdate();
                if(rowAffected>0){
                    System.out.println("Appointment Booked :");
                }else{
                    System.out.println("Failed Book Appointment :");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
          }else{
              System.out.println("Doctor is not Avilable on this Date:");
          }

        }else{
            System.out.println("Either Doctor or Patient does not exit: ");
        }

    }
    public static boolean checkDoctorAvailability(int doctorId,String appointment,Connection connection){
        String query="select count(*) from appointments where doctor_id=? AND appointment_data = ?";

        try{
         PreparedStatement preparedStatement=connection.prepareStatement(query);
         preparedStatement.setInt(1,doctorId);
         preparedStatement.setString(2,appointment);
         ResultSet resultSet=preparedStatement.executeQuery();
         if(resultSet.next()) {
             int count = resultSet.getInt(1);
             if (count == 0) {
                 return true;
             } else {
                 return false;
             }
         }
     }catch (SQLException e){
         e.printStackTrace();
     }
        return false;
    }
}










