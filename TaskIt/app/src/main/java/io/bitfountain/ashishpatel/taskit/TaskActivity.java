package io.bitfountain.ashishpatel.taskit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskActivity extends Activity {

    public static final String EXTRA = "TaskExtra";
    private static final String TAG = "TaskActivity";
    private Calendar mCal;
    private Task mTask;
    private Button mDateButton;
    private EditText taskNameInput;
    private CheckBox doneBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTask = (Task) getIntent().getSerializableExtra(EXTRA); //receive the Intent

        //If no task create a task
        if (mTask == null) {
            mTask = new Task();
        }

        mCal = Calendar.getInstance();


        taskNameInput = (EditText) findViewById(R.id.task_name);
        mDateButton = (Button) findViewById(R.id.task_date);
        doneBox = (CheckBox) findViewById(R.id.task_done);
        Button saveButton = (Button) findViewById(R.id.save);

        taskNameInput.setText(mTask.getName());
        if (mTask.getDueDate() == null) {
            mCal.setTime(new Date());
            mDateButton.setText(getResources().getString(R.string.no_date));
        } else {
            mCal.setTime(mTask.getDueDate()); //put the mTask in the calendar object
            updateButton();

        }

        doneBox.setChecked(mTask.isDone());


        //Date button onClickListener function
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This shows the Date picker for the user to pick a date
                DatePickerDialog dpd = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mCal.set(Calendar.YEAR, year);
                        mCal.set(Calendar.MONTH, monthOfYear);
                        mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        Intent i = new Intent(); //Don't need to put anything in parameter because we're not starting anything new
                        i.putExtra(EXTRA, mTask); // put an extra with the intent
                        setResult(RESULT_OK, i); //sets the result of the activity
                        finish(); //called when the function is done

                        updateButton();

                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));

                dpd.show(); //Show the DatePickerDialog

            }
        });


        /**
         * This is the Save button method
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setName(taskNameInput.getText().toString());
                mTask.setDone(true);
                mTask.setDueDate(mCal.getTime());

                //Send data back
                Intent i = new Intent();
                i.putExtra(EXTRA, mTask);
                setResult(RESULT_OK, i);
                finish();

            }
        });
    }

    //Update the button method
    private void updateButton() {
        DateFormat df = DateFormat.getDateInstance();
        mDateButton.setText(df.format(mCal.getTime()));

    }


}
