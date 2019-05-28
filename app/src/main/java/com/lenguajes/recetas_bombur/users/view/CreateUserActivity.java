package com.lenguajes.recetas_bombur.users.view;

import android.content.DialogInterface;

import android.support.design.widget.TextInputLayout;

import android.os.StrictMode;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lenguajes.recetas_bombur.DBConnection.DBConnection;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.DialogManager;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

public class CreateUserActivity extends AppCompatActivity {

    private AlertDialog exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);
        setUpExitDialog();

        Button createBtn = findViewById(R.id.createUser_CreateButton);

        EditText nameET,emailET,usernameET,passwordET;
        nameET = findViewById(R.id.createUser_NameTextInput);
        emailET = findViewById(R.id.createUser_EmailTextInput);
        usernameET = findViewById(R.id.createUser_UsernameTextInput);
        passwordET = findViewById(R.id.createUser_PasswordTextInput);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameGot,emailGot,usernameGot,passwordGot;
                nameGot = nameET.getText().toString();
                emailGot = emailET.getText().toString();
                usernameGot = usernameET.getText().toString();
                passwordGot= passwordET.getText().toString();

                if(nameGot.equals("")|emailGot.equals("")|usernameGot.equals("")|passwordGot.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill the fields buddy",Toast.LENGTH_LONG);
                }else{
                    try{
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        DBConnection con = new DBConnection();
                        int response = con.register(nameGot,emailGot,usernameGot,passwordGot);
                        con.close();
                        Log.d("DBConnection.register",String.valueOf(response));
                        if(response==1){
                            Toast.makeText(getApplicationContext(),usernameGot+" successfully registered!",Toast.LENGTH_LONG).show();
                        }else if(response==0){
                            Toast.makeText(getApplicationContext(),"Username already exists",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Connection problem",Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"No connection to internet.",Toast.LENGTH_SHORT);
                    }
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
