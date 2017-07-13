package com.apptozee.drivingschool.Driver;

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

import com.apptozee.drivingschool.R;

public class RegisterCustomer extends AppCompatActivity {

    //UI references
    private EditText e1,e2,e3,e4;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        e1 = (EditText) findViewById(R.id.name);
        e2 = (EditText) findViewById(R.id.mobile);
        e3 = (EditText) findViewById(R.id.days);
        e4 = (EditText) findViewById(R.id.slot);
        b = (Button) findViewById(R.id.register);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(e1.getText().toString())){
                    e1.setError(getString(R.string.error_field_required));
                } else if (TextUtils.isEmpty(e2.getText().toString())){
                    e2.setError(getString(R.string.error_field_required));
                } else if (TextUtils.isEmpty(e3.getText().toString())){
                    e3.setError(getString(R.string.error_field_required));
                } else if (TextUtils.isEmpty(e4.getText().toString())){
                    e4.setError(getString(R.string.error_field_required));
                }else {

                    DBHome dbh = new DBHome(RegisterCustomer.this);
                    dbh.insertData(e2.getText().toString(),
                            e1.getText().toString(),
                            e3.getText().toString(),e4.getText().toString());
                    dbh.close();

                    //Hide Keyboard so Snackbar is visible
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Snackbar to let driver know customer is registered
                    Snackbar snackbar = Snackbar
                            .make(v, "Registered.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("FINISH!", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onBackPressed(); // Don't come back to this activity on back press
                                }
                            });
                    snackbar.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegisterCustomer.this,DriverActivity.class);
        startActivity(i);
    }
}
