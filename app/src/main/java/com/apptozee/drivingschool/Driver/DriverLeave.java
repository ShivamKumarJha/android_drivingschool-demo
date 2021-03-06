package com.apptozee.drivingschool.Driver;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apptozee.drivingschool.R;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

public class DriverLeave extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //UI references
    private EditText e1,e2;
    private Button b;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_leave);

        //Calendar code
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                DriverLeave.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        // link UI components
        e1 = (EditText) findViewById(R.id.lperiod);
        e2 = (EditText) findViewById(R.id.lreason);
        b = (Button) findViewById(R.id.apply);

        //date listener
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setError(null);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        // apply button listener
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(e1.getText().toString())){
                    e1.setError(getString(R.string.error_field_required));
                }
                else if (TextUtils.isEmpty(e2.getText().toString())){
                    e2.setError(getString(R.string.error_field_required));
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        //String datestart = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        //String dateend = dayOfMonthEnd+"/"+(monthOfYearEnd+1)+"/"+yearEnd;
        e1.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year+" to "+dayOfMonthEnd+"/"+(monthOfYearEnd+1)+"/"+yearEnd);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DriverLeave.this,DriverActivity.class);
        startActivity(i);
        DriverLeave.this.finish();
    }
}
