package com.example.teamc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by shahkhan on 14/03/2018.
 */

public class RegisterAccount extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    public String TAG = "TAG";

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextRetypeEmail;
    private EditText editTextRetypePass;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);

        buttonRegister = (Button) findViewById(R.id.registration_button);
        editTextEmail = (EditText) findViewById(R.id.register_email);
        editTextPassword = (EditText) findViewById(R.id.register_password);
        editTextName = (EditText) findViewById(R.id.users_name);
        editTextRetypeEmail = (EditText) findViewById(R.id.retype_register_email);
        editTextRetypePass = (EditText) findViewById(R.id.retype_register_password);
        editTextAge = (EditText) findViewById(R.id.users_age);
        buttonRegister.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    currentUser.getEmail(), currentUser.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, currentUser.getUid()));
        }
    }
//            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
//            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
//        }
//        else
//            {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
//        }


    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // user registered, start profile activity
                            Toast.makeText(RegisterAccount.this,"Account Created",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            //intent takes user back to login activity

                        }
                        else{
                            Toast.makeText(RegisterAccount.this,"Could not create account. Please try again",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        //if valid, will register the user
        //since it is internet operation and will take time, will use progress bar
        //enter progress bar code

    }

    public boolean validateForm() {

        boolean valid = true;

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String confirmEmail = editTextRetypeEmail.getText().toString();
        String confirmPass = editTextRetypePass.getText().toString();



        if(TextUtils.isEmpty(name)) {
            //name field is empty
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;

        }

        if(TextUtils.isEmpty(age)) {
            //age field is empty
            Toast.makeText(this, "Please enter age", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;
        }

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmEmail)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;
        }

        if(!email.equals(confirmEmail)){
            Toast.makeText(this, "Your emails do not match", Toast.LENGTH_SHORT).show();
            //stop further execution
            valid = false;
        }

        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;
        }

        if(!password.equals(confirmPass)){
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            //stop further execution
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View view) {

        if(view == buttonRegister) {
            createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        }
    }
}
