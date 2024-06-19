package com.example.taskapplication.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.taskapplication.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepositoryDatabaseImpl extends SQLiteOpenHelper implements TaskRepository {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    //table and columns
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_CREATION_DATE = "creation_date";
    public static final String COLUMN_IS_DONE = "is_done";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_CREATION_DATE + " INTEGER, " +
                    COLUMN_DUE_DATE + " INTEGER, " +
                    COLUMN_IS_DONE + " INTEGER" +
                    ");";


    private static TaskRepositoryDatabaseImpl instance;

    private List<Task> mTasks;


    // Synchronized method to get the singleton instance
    public static synchronized TaskRepositoryDatabaseImpl getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepositoryDatabaseImpl(context.getApplicationContext());
        }
        return instance;
    }
    private TaskRepositoryDatabaseImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    @Override
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_CREATION_DATE, COLUMN_DUE_DATE, COLUMN_IS_DONE},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                long creationDateMillis = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CREATION_DATE));
                long dueDateMillis = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE));
                boolean isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1;

                Task task = new Task(id, name, description, new Date(creationDateMillis), new Date(dueDateMillis), isDone);
                tasks.add(task);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return tasks;
    }

    @Override
    public void deleteFinishedTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_IS_DONE + " = ?", new String[]{"1"});
        db.close();
    }

    @Override
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = createContentValues(task);

        db.insert(TABLE_TASKS, null, values);


loadTasks();

        db.close();
    }

    @NonNull
    private static ContentValues createContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getShortName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_CREATION_DATE, task.getCreationDate().getTime());
        values.put(COLUMN_DUE_DATE, task.getDueDate().getTime());
        values.put(COLUMN_IS_DONE, task.isDone() ? 1 : 0); // Storing boolean as integer
        return values;
    }

    @Override
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = createContentValues(task);

        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }
}
