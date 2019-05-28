package com.lenguajes.recetas_bombur.DBConnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection{
    //Credentials to connect to the database
    public String host,database,user,password;
    public int port;
    public Connection con;
    public DBConnection(){

        try{
            host ="ec2-184-72-237-95.compute-1.amazonaws.com";
            database ="da15auk6s4029p";
            port = 5432;
            user ="iuiicrcrbattld";
            password="dc5d69030961f07ffb2acaeebb90653248dc166ae72cd8454ba4a3de4d978f44";

            Class.forName("org.postgresql.Driver");
            Log.d("Params","jdbc:postgresql://"+host+"/"+database);
            con = DriverManager.getConnection("jdbc:postgresql://"+host+":"+String.valueOf(port)+"/"+database,user,password);

            Log.d("CONNECTED!","Connected to "+con.getMetaData().getDatabaseProductName());

        }catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.out.println("Could not connect to DB");
        }
    }

    /**
     * Method to test connection
     */
    public void getAllUsers(){

        if (con != null){//Hay conexion
            try {

                System.out.println("Connection to database successful.");

                Statement stmt;
                stmt = con.createStatement();

                String query = "SELECT * FROM spGetAllUsers();";

                stmt.execute(query);

                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()){

                    int idUser= rs.getInt("ID");
                    String nameUser = rs.getString("Name");
                    String emailUser = rs.getString("Email");
                    String username = rs.getString("Username");

                    Log.d("Test","ID: "+String.valueOf(idUser)+"\nName: "+nameUser+"\nE-mail: "+emailUser+"\nUsername: "+username);
                }
                stmt.close();
            } catch (Exception ex) {
                Log.d("Error","@getAllUsers()");
            }
        }else {
            System.out.println("No connection found.");
        }
    }

    /**
     * Login method
     * @param username
     * @param password
     * @return
     */
    public int logIn(String username,String password){
        try {
            con = DriverManager.getConnection("jdbc:postgresql://"+host+"/"+database,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Connection error","@logIn");
        }
        if (con != null){//Hay conexion
            try {
                System.out.println("Connection to database successful.");

                Statement stmt;
                stmt = con.createStatement();
                String input = "'"+username+"','"+ password+"'";
                System.out.println("Params: "+input);
                String query = "SELECT * FROM spLogin("+input+");";
                System.out.println("QUERY: "+query);
                //stmt.execute(query);

                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    boolean result = rs.getBoolean("splogin");

                    stmt.close();
                    Log.d("Result",String.valueOf(result));
                    if(result==true){
                        return 1;
                    }else{
                        return 0;
                    }
                }else{
                    stmt.close();
                    return 0;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                Log.d("Error","@spLogin()");
                return -1;
            }
        }else {
            System.out.println("No connection found.");
        }
        return -1;
    }

    public void close(){
        try {
            con.close();
            Log.d("Status","Connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int register(String name,String email,String username,String password){
        try{
            System.out.println("Connection to database successful.");

            Statement stmt;
            stmt = con.createStatement();
            String input = "'"+name+"','"+email+"','"+username+"','"+ password+"'";
            System.out.println("Params: "+input);
            String query = "SELECT * FROM spNewUser("+input+");";
            System.out.println("QUERY: "+query);
            //stmt.execute(query);

            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()){
                boolean result = rs.getBoolean("spnewuser");

                stmt.close();
                Log.d("Result",String.valueOf(result));
                if(result==true){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                stmt.close();
                return 0;
            }

        }catch(SQLException sqe){
            sqe.printStackTrace();
            Log.d("SQLException caught","@register");
            return -1;
        }catch(Exception e){
            e.printStackTrace();
            Log.d("Exception caught","@register");
            return -1;
        }
    }

}
