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

    public String TAG = "TAG";
    private FirebaseAuth mAuth;
    private Button verifyEmailBtn;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextRetypeEmail;
    private EditText editTextRetypePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        verifyEmailBtn = (Button) findViewById(R.id.verify_email_button);
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
//        FirebaseUser user = mAuth.getCurrentUser();
//        updateUI(user);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }


    private void sendEmailVerification() {
        // Disable button
        // findViewById(R.id.registration_button).setEnabled(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Send verification email
        // [START send_email_verification]
        if (user != null) {

            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            // Re-enable button
                            //findViewById(R.id.registration_button).setEnabled(true);

                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterAccount.this,
                                        "Check your email for verification",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                            } else {
                                Log.e(TAG, "sendEmailVerification", task.getException());
                                Toast.makeText(RegisterAccount.this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // [END_EXCLUDE]
                        }
                    });
        }
    }

    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // user registered, start profile activity
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(RegisterAccount.this, "Verify your email", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            sendEmailVerification();

                            //intent takes user back to login activity

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterAccount.this, "Could not create account. Please try again", Toast.LENGTH_SHORT).show();
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


        if (TextUtils.isEmpty(name)) {
            //name field is empty
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;

        }

        if (TextUtils.isEmpty(age)) {
            //age field is empty
            Toast.makeText(this, "Please enter age", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;
        }

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmEmail)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;
        }

        if (!email.equals(confirmEmail)) {
            Toast.makeText(this, "Your emails do not match", Toast.LENGTH_SHORT).show();
            //stop further execution
            valid = false;
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            valid = false;
        }

        if (!password.equals(confirmPass)) {
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            //stop further execution
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View view) {

        if (view == buttonRegister) {
            createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());

        }
    }
}
