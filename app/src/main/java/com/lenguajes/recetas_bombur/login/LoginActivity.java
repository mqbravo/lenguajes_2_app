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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.RecetasBomburApplication;
import com.lenguajes.recetas_bombur.home.view.HomeActivity;
import com.lenguajes.recetas_bombur.users.view.CreateUserActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
                RequestQueue queue = Volley.newRequestQueue(this);
                //URL hacia la app de Heroku
                //"Jsonify" del username y password
                JSONObject payload = new JSONObject();
                try {
                    payload.put("username",usernameGot);
                    payload.put("password",passwordGot);
                    Log.d("Payload",payload.toString(2));

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RecetasBomburApplication.getURL().concat("/login"), payload, response -> {
                        try {
                            String token = response.get("token").toString();
                            if(token.equals("")){
                                Toast.makeText(getApplicationContext(), "Wrong credentials " + token, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Token: " + token, Toast.LENGTH_LONG).show();
                                RecetasBomburApplication.setSessionToken(token);
                                openHome(getCurrentFocus());
                            }

                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            Log.d("Error", "Error @LoginActivity/onCreate/onResponse");
                        }
                    }, error -> {
                        error.printStackTrace();
                        //Log.d("IP",this.get);
                        Log.d("Error", "Error @LoginActivity/onCreate/ErrorListener");
                    });
                    Log.d("URL",jsonObjectRequest.getUrl());
                    //Space for the request part
                    queue.add(jsonObjectRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error at serializing payload",Toast.LENGTH_LONG).show();
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
