package com.walmart.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.walmart.simpletodo.model.Task;

import java.util.Calendar;

public class ModifyTaskActivity extends AppCompatActivity {

    private Task task = null;
    private String action;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.task = new Task();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        action = getIntent().getStringExtra("action");
        if (action.equals("modify")) {
            setTitle("Modify Task");
            taskId = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String dueDate = getIntent().getStringExtra("dueDate");
            String note = getIntent().getStringExtra("note");
            String priority = getIntent().getStringExtra("priority");
            String status = getIntent().getStringExtra("status");
            ((EditText)findViewById(R.id.edittext_task_title)).setText(title);
            ((EditText)findViewById(R.id.editText_note)).setText(note);
            DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.datepicker_due_date);

            String[] dates = dueDate.split("/");
            int date = Integer.valueOf(dates[1]);
            int month = Integer.valueOf(dates[0]);
            int year = Integer.valueOf(dates[2]);
            taskDueDatePicker.updateDate(year, month, date);
            Spinner prioritySpinner = (Spinner)findViewById(R.id.spinner_priority_level);
            prioritySpinner.setSelection(getIndex(prioritySpinner, priority));
            Spinner statusSpinner = (Spinner)findViewById(R.id.spinner_completion_status);
            statusSpinner.setSelection(getIndex(statusSpinner, status));
        } else
            setTitle("Add Task");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        Bundle resultBundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.menu_actionbar_cancel:
            case R.id.menu_actionbar_save:
                if (action.equals("add")) {
                    updateTaskObject();
                    resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
                    resultBundle.putString("action", "add");
                } else {
                    updateTaskObject();
                    this.task.setId(Integer.valueOf(this.taskId));
                    resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
                    resultBundle.putString("action", "edit");
                }
                resultIntent.putExtras(resultBundle);
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTaskObject() {
        String taskTitle = ((EditText)findViewById(R.id.edittext_task_title)).getText().toString();
        this.task.setTitle(taskTitle);
        DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.datepicker_due_date);
        this.task.getDueDate().set(Calendar.DATE, taskDueDatePicker.getDayOfMonth());
        this.task.getDueDate().set(Calendar.MONTH, taskDueDatePicker.getMonth());
        this.task.getDueDate().set(Calendar.YEAR, taskDueDatePicker.getYear());
        // set note
        String taskNote = ((EditText)findViewById(R.id.editText_note)).getText().toString();
        this.task.setNote(taskNote);
        // set priority level
        int priorityLevel = ((Spinner)findViewById(R.id.spinner_priority_level)).getSelectedItemPosition();
        this.task.setPriorityLevel(priorityLevel);
        // set completion status
        int completionStatus = ((Spinner)findViewById(R.id.spinner_completion_status)).getSelectedItemPosition();
        this.task.setStatus(completionStatus);
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
