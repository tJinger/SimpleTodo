package com.walmart.simpletodo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tjing on 9/28/15.
 */
public class TasksCursorAdapter extends CursorAdapter {
    public TasksCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvId = (TextView) view.findViewById(R.id.tkId);
        TextView tvName = (TextView) view.findViewById(R.id.tkName);
        TextView tvNote = (TextView) view.findViewById(R.id.tkNote);
        TextView tvDueDate = (TextView) view.findViewById(R.id.tkDueDate);
        TextView tvPriority = (TextView) view.findViewById(R.id.tkPriority);
        TextView tvStatus = (TextView) view.findViewById(R.id.tkStatus);

        // Extract properties from cursor
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));
        Long dueDate = cursor.getLong(cursor.getColumnIndexOrThrow("due_date"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

        tvId.setText(String.valueOf(id));
        tvName.setText(title);
        tvNote.setText(note);
        String[] priorityLevels = {"High", "Medium", "Low"};
        tvPriority.setText(String.valueOf(priorityLevels[priority]));
        tvDueDate.setText(getCalendarDate(dueDate));
        String[] statusLevel = {"ToDo", "Done"};
        tvStatus.setText(String.valueOf(statusLevel[status]));
    }

    private String getCalendarDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }
}
