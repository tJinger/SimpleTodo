package com.walmart.simpletodo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.walmart.simpletodo.model.Task;

/**
 * Created by tjing on 9/28/15.
 */
public class TaskDatabase extends SQLiteOpenHelper {
    public static final String TAG = "simpleTodo";

    // Database Info
    private static final String DATABASE_NAME = "tasksDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TASKS = "tasks";

    // Post Table Columns
    private static final String KEY_ROW_ID = "rowid";
    private static final String KEY_TASK_ID = "_id";
    private static final String KEY_TASK_TITLE = "title";
    private static final String KEY_TASK_DUE_DATE = "due_date";
    private static final String KEY_TASK_NOTE = "note";
    private static final String KEY_TASK_PRIORITY = "priority";
    private static final String KEY_TASK_STATUS = "status";

    private static TaskDatabase sInstance;

    public static synchronized TaskDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TaskDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    // Must override implicit constructor
    public TaskDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS +
                "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TASK_TITLE + " text not null, "
                + KEY_TASK_DUE_DATE + " integer not null, "
                + KEY_TASK_NOTE + " text,"
                + KEY_TASK_PRIORITY + " integer not null, "
                + KEY_TASK_STATUS + " integer not null "
                + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop Task table if exists
        db.execSQL("Drop table if exists " + TABLE_TASKS);
        onCreate(db);
    }

    // Insert a task into the database
    public void addTask(Task task, SQLiteDatabase db) {
        // Create and/or open the database for writing
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TITLE, task.getTitle());
        values.put(KEY_TASK_DUE_DATE, task.getDueDate().getTimeInMillis());
        values.put(KEY_TASK_NOTE, task.getNote());
        values.put(KEY_TASK_PRIORITY, task.getPriorityLevel());
        values.put(KEY_TASK_STATUS, task.getStatus());

        // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
        db.insert(TABLE_TASKS, null, values);
    }

    public void addorUpdateTask(Task task, SQLiteDatabase db) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_TASK_TITLE, task.getTitle());
        updateValues.put(KEY_TASK_DUE_DATE, task.getDueDate().getTimeInMillis());
        updateValues.put(KEY_TASK_NOTE, task.getNote());
        updateValues.put(KEY_TASK_PRIORITY, task.getPriorityLevel());
        updateValues.put(KEY_TASK_STATUS, task.getStatus());

        db.update(TABLE_TASKS, updateValues, " rowid = '" + task.getId() + "'", null);
    }

    // Get all tasks in the database
    public Cursor getAllTasks(SQLiteDatabase db) {
        return db.rawQuery("Select rowid _id, * from " + TABLE_TASKS, null);
    }

    public Cursor getTaskById(SQLiteDatabase db, String taskId) {
        return db.rawQuery("Select * from " + TABLE_TASKS + " where KEY_TASK_ID = " + taskId, null);
    }

    // delete the selected Task
    public void deleteTask(SQLiteDatabase db, String taskId){
        db.delete(TABLE_TASKS, KEY_ROW_ID + " = '" + taskId + "'", null);
    }
}
