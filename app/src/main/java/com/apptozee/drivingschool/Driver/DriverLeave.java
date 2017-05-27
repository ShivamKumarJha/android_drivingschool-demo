package com.apptozee.drivingschool.Driver;

import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.apptozee.drivingschool.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DriverLeave extends AppCompatActivity {

    //UI references
    private TextView t1,t2;
    private EditText e1,e2,e3;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_leave);

        // link UI components
        t1 = (TextView) findViewById(R.id.ltaken);
        t2 = (TextView) findViewById(R.id.lleft);
        e1 = (EditText) findViewById(R.id.lstart);
        e2 = (EditText) findViewById(R.id.lend);
        e3 = (EditText) findViewById(R.id.lreason);
        b = (Button) findViewById(R.id.apply);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(e1.getText().toString())){
                    e1.setError(getString(R.string.error_field_required));
                }
                else if (TextUtils.isEmpty(e2.getText().toString())){
                    e2.setError(getString(R.string.error_field_required));
                }
                else if (TextUtils.isEmpty(e3.getText().toString())){
                    e3.setError(getString(R.string.error_field_required));
                }
                else {
                    //Snackbar to let user process is done
                    Snackbar snackbar = Snackbar
                            .make(v, "Applied for leave.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Finish.", new View.OnClickListener() {
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
}
