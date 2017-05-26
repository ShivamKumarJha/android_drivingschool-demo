package com.apptozee.drivingschool.ForgotPassword;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.apptozee.drivingschool.LoginActivity;
import com.apptozee.drivingschool.R;

public class viaSMS extends AppCompatActivity {

    // Declare UI references
    private EditText e;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_via_sms);

        e = (EditText)findViewById(R.id.mobile_number);
        b = (Button) findViewById(R.id.sumbit);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e.getText().toString().length() == 10)
                {
                    /*
                    Write code here to verify if number is true
                    if yes, message password.
                    */

                    //Hide Keyboard so Snackbar is visible
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Snackbar to let user know password is sent & to redirect to Login page
                    Snackbar snackbar = Snackbar
                            .make(v, "Password Sent.", Snackbar.LENGTH_INDEFINITE)
                            .setAction(getResources().getString(R.string.signin), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(viaSMS.this,LoginActivity.class);
                                    startActivity(i);

                                    viaSMS.this.finish(); // Don't come back to this activity on back press
                                }
                            });
                    snackbar.show();
                }
                else if(TextUtils.isEmpty(e.getText().toString()))
                {
                    e.setError(getString(R.string.error_field_required));
                }
                else
                {
                    e.setError(getString(R.string.error_invalid_mobile_number));
                }
            }
        });
    }
}
