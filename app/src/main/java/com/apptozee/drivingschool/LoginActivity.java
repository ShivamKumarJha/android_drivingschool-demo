package com.apptozee.drivingschool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apptozee.drivingschool.Driver.DriverActivity;
import com.apptozee.drivingschool.ForgotPassword.viaEmail;
import com.apptozee.drivingschool.ForgotPassword.viaSMS;


/**
 * A login screen that offers login via username & password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private EditText mPasswordView, mUsername;
    private Button mEmailSignInButton;
    private TextView mForgotPassword;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Decide AlertDialog design based on Android Version.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(LoginActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(LoginActivity.this);
        }

        // Set up the login form components.
        mUsername = (EditText)findViewById(R.id.userField);
        mPasswordView = (EditText) findViewById(R.id.passField);
        mEmailSignInButton = (Button) findViewById(R.id.loginButton);
        mForgotPassword = (TextView) findViewById(R.id.forgotpasswordfield);

        // Button LogIn Click Listener.
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        //Forgot Password listener
        mForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle(getResources().getString(R.string.forgot))
                        .setMessage("Retrieve password via")
                        .setPositiveButton("EMAIL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // call viaEmail activity
                                Intent i = new Intent (LoginActivity.this,viaEmail.class);
                                startActivity(i);
                                LoginActivity.this.finish();
                            }
                        })
                        .setNegativeButton("SMS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // call viaSMS activity
                                Intent i = new Intent (LoginActivity.this,viaSMS.class);
                                startActivity(i);
                                LoginActivity.this.finish();
                            }
                        })
                        .show();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsername.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String uname = mUsername.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false; //If encountered error during sign in, make it false.
        View focusView = null; // focus at field if error encountered.

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username
        if (TextUtils.isEmpty(uname)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (uname.length()!=10) {
            mUsername.setError(getString(R.string.error_invalid_mobile_number));
            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            /*Start new activiy*/
            Intent i = new Intent(LoginActivity.this, DriverActivity.class);
            startActivity(i);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onBackPressed() {
        builder.setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setIcon(android.R.drawable.stat_sys_warning)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close application
                        LoginActivity.this.finish();
                        System.exit(0);
                    }
                })
                .show();

    }
}
