package com.lenguajes.recetas_bombur.login;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lenguajes.recetas_bombur.DBConnection.DBConnection;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.home.view.HomeActivity;
import com.lenguajes.recetas_bombur.users.view.CreateUserActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.login_LoginButton);
        EditText usernameET,passwordET;
        usernameET = findViewById(R.id.login_UsernameTextInput);
        passwordET = findViewById(R.id.login_PasswordTextInput);
        loginBtn.setOnClickListener(v -> {

            String usernameGot,passwordGot;
            usernameGot = usernameET.getText().toString();
            passwordGot = passwordET.getText().toString();
            if(usernameGot.equals("") | passwordGot.equals("")){
                Toast.makeText(getApplicationContext(),"Fill empty spaces first buddy.",Toast.LENGTH_LONG).show();
            }else{
                try{
                    /*Reference: https://stackoverflow.com/questions/54503286/connection-failed-with-psql-on-android-app-studio
                    to solve a problem where I could connect database through NetBeans but couldn't in Android Studio
                    without changing code.
                */

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    DBConnection con = new DBConnection();
                    int response = con.logIn(usernameGot,passwordGot);
                    con.close();
                    Log.d("DBConnection.logIn",String.valueOf(response));
                    if(response==1){
                        openHome(getCurrentFocus());
                        Toast.makeText(getApplicationContext(),"Welcome back "+usernameGot+"!",Toast.LENGTH_SHORT);
                    }else if(response==0){
                        Toast.makeText(getApplicationContext(),"Wrong credentials",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Connection problem",Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"No connection to internet.",Toast.LENGTH_SHORT);
                }
            }
        });

    }

    public void openHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openCreateUser(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
}
