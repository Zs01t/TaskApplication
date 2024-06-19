package com.example.taskapplication.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.taskapplication.Task;
import com.example.taskapplication.helpers.Converters;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Task.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TaskRoomDatabase extends RoomDatabase
{
    public abstract TaskDao TaskDao();
    //my source of this: https://dr-shradhasrathod.medium.com/android-room-database-with-java-beginners-dabfbbcd46a5
    private static volatile TaskRoomDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized TaskRoomDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (TaskRoomDatabase.class)
            {
                if(instance == null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "task_database")
                            .build();
                }
            }
        }
        return instance;
    }

    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}
