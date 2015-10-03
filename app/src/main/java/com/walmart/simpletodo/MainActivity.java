package com.walmart.simpletodo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.walmart.simpletodo.db.TaskDatabase;
import com.walmart.simpletodo.model.Task;

public class MainActivity extends AppCompatActivity {
    ListView tasksListView;
    private TaskDatabase taskDatabase;
    private SQLiteDatabase db;
    private final int REQUEST_CODE = 20;
    private TasksCursorAdapter tasksAdapter;
    private String taskId;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasksListView = (ListView) findViewById(R.id.lvItems);

        taskDatabase = TaskDatabase.getInstance(this);
        db = taskDatabase.getWritableDatabase();
        Cursor tasksCursor = taskDatabase.getAllTasks(db);
        tasksAdapter = new TasksCursorAdapter(this, tasksCursor, 1);
        tasksListView.setAdapter(tasksAdapter);

        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent addNewTaskIntent = new Intent(this, ModifyTaskActivity.class);
                addNewTaskIntent.putExtra("action", "add");
                startActivityForResult(addNewTaskIntent, this.REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupListViewListener() {
        tasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id) {
                TextView idTextView = (TextView)view.findViewById(R.id.tkId);
                taskId = idTextView.getText().toString();
                DeleteConfirmationDialog deleteFragment = new DeleteConfirmationDialog();
                // Show Alert DialogFragment
                deleteFragment.show(fm, "Alert Dialog Fragment");
//                taskDatabase.deleteTask(db, taskId);
//                tasksAdapter.changeCursor(taskDatabase.getAllTasks(db));
//                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                TextView idTextView = (TextView)view.findViewById(R.id.tkId);
                TextView titleTextView = (TextView)view.findViewById(R.id.tkName);
                TextView noteTextView = (TextView)view.findViewById(R.id.tkNote);
                TextView dueDateTextView = (TextView)view.findViewById(R.id.tkDueDate);
                TextView priorityTextView = (TextView)view.findViewById(R.id.tkPriority);
                TextView statusTextView = (TextView)view.findViewById(R.id.tkStatus);

                Intent i = new Intent(MainActivity.this, ModifyTaskActivity.class);
                // put "extras" into the bundle for access in the second activity
                i.putExtra("id", idTextView.getText().toString());
                i.putExtra("title", titleTextView.getText().toString());
                i.putExtra("note", noteTextView.getText().toString());
                i.putExtra("dueDate", dueDateTextView.getText().toString());
                i.putExtra("priority", priorityTextView.getText().toString());
                i.putExtra("status", statusTextView.getText().toString());
                i.putExtra("action", "modify");
                startActivityForResult(i, REQUEST_CODE);
                tasksAdapter.changeCursor(taskDatabase.getAllTasks(db));
                tasksAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Bundle bundle = data.getExtras();
            Task t = (Task) bundle.getSerializable(Task.TASK_BUNDLE_KEY);
            String action = bundle.getString("action");
            if (action.equals("add")) {
                this.taskDatabase.addTask(t, db);
            } else if (action.equals("edit")) {
                this.taskDatabase.addorUpdateTask(t, db);
            }
            tasksAdapter.changeCursor(taskDatabase.getAllTasks(db));
        }
    }

    public void OnDeleteTaskSumbit() {
        taskDatabase.deleteTask(db, taskId);
        tasksAdapter.changeCursor(taskDatabase.getAllTasks(db));
        tasksAdapter.notifyDataSetChanged();
    }
}
