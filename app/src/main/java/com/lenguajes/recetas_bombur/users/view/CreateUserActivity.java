package com.lenguajes.recetas_bombur.users.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.RecetasBomburApplication;
import com.lenguajes.recetas_bombur.activitymanagement.DialogManager;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUserActivity extends AppCompatActivity {

    private AlertDialog exitDialog;
    private TextInputLayout mName;
    private TextInputLayout mUsername;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);
        setUpExitDialog();

        Button createBtn = findViewById(R.id.createUser_CreateButton);

        mName = findViewById(R.id.createUser_NameTextInputLayout);
        mEmail = findViewById(R.id.createUser_EmailTextInputLayout);
        mUsername = findViewById(R.id.createUser_UsernameTextInputLayout);
        mPassword = findViewById(R.id.createUser_PasswordTextInputLayout);

        createBtn.setOnClickListener(v -> {
            String nameGot,emailGot,usernameGot,passwordGot;
            nameGot = mName.getEditText().getText().toString();
            emailGot = mEmail.getEditText().getText().toString();
            usernameGot = mUsername.getEditText().getText().toString();
            passwordGot= mPassword.getEditText().getText().toString();

            if(nameGot.equals("")|emailGot.equals("")|usernameGot.equals("")|passwordGot.equals("")){
                Toast.makeText(getApplicationContext(),"Fill the fields buddy",Toast.LENGTH_LONG).show();
            }else{
                RequestQueue queue = Volley.newRequestQueue(this);
                //URL hacia la app de Heroku
                //String url = getApplicationContext().getResources().getString(R.string.url);
                String url = RecetasBomburApplication.getURL();
                //"Jsonify" del username y password
                JSONObject payload = new JSONObject();
                try {
                    payload.put("username",usernameGot);
                    payload.put("password",passwordGot);
                    payload.put("name",nameGot);
                    payload.put("email",emailGot);
                    Log.d("Payload",payload.toString(2));

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url.concat("/register"), payload, response -> {
                        try {
                            if(response.getBoolean("response")){//Registro exitoso
                                Toast.makeText(getApplicationContext(),usernameGot+" registered succesfully!",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{//Registro fallido
                                Toast.makeText(getApplicationContext(),usernameGot +" already exists.",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            Log.d("Error", "Error @RegisterActivity/onCreate/onResponse");
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

    private void setUpExitDialog(){
        exitDialog = DialogManager.createYesNoDialog(this,
                (DialogInterface dialog, int which)->finish(),
                (DialogInterface dialog, int which) -> dialog.dismiss(),
                getString(R.string.warning),
                getString(R.string.user_cancel_prompt));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                exitDialog.show();
                return true;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValidName(){
        String nameInput = mName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()){
            mName.setError(getString(R.string.field_empty_message));
            return false;
        }

        else if (nameInput.length() < 2){
            mName.setError(getString(R.string.name_too_short));
            return false;
        }

        else{
            mName.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(){
        String emailInput = mEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            mEmail.setError(getString(R.string.field_empty_message));
            return false;
        }

        else{
            mEmail.setError(null);
            return true;
        }
    }

    private boolean isValidUsername(){
        String usernameInput = mUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()){
            mUsername.setError(getString(R.string.field_empty_message));
            return false;
        }


        else if (usernameInput.length() < 5){
            mUsername.setError(getString(R.string.username_too_short));
            return false;
        }

        else{
            mUsername.setError(null);
            return true;
        }
    }

    private boolean isValidPassword(){
        String passwordInput = mPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            mPassword.setError(getString(R.string.field_empty_message));
            return false;
        }


        else{
            mPassword.setError(null);
            return true;
        }
    }

    private boolean validateInputs(){
        return !(!isValidName() | !isValidEmail() | !isValidUsername() | !isValidPassword());
    }


    @Override
    public void onBackPressed() {
        exitDialog.show();
    }

    public void cancelNewUser(View view) {
        exitDialog.show();
    }

    public void newUser(View view) {
        if(validateInputs()){
            //TODO Log in
        }
    }
}
