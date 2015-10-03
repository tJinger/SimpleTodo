package com.walmart.simpletodo.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by tjing on 9/28/15.
 */
public class Task implements Serializable {
    public static final String TASK_BUNDLE_KEY = "task_bundle_key";

    public static final int HIGH_PRIORITY = 0;
    public static final int MEDIUM_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;

    public static final int TASK_DONE = 0;
    public static final int TASK_TO_DO = 1;

    private int id;
    private String title;
    private Calendar dueDate;
    private String note;
    private int priorityLevel;
    private int status;

    public Task() {
        this.title = "";
        this.dueDate = Calendar.getInstance();
        this.note = "";
        this.priorityLevel = HIGH_PRIORITY;
        this.status = TASK_DONE;
    }

    public Task(String title, Calendar dueDate, String note,
                int priorityLevel, int status) {
        super();
        this.title = title;
        this.dueDate = dueDate;
        this.note = note;
        this.priorityLevel = priorityLevel;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {

        this.note = note;
    }

    public int getPriorityLevel() {

        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {

        this.priorityLevel = priorityLevel;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
