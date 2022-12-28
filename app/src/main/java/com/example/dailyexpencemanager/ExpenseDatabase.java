package com.example.dailyexpencemanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ExpenseTable.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {
    public abstract ExpenseDao getDao();
    public static volatile ExpenseDatabase INSTANSE;

    public static ExpenseDatabase getInstance(Context context){
        if (INSTANSE==null){
            INSTANSE= Room.databaseBuilder(context, ExpenseDatabase.class,"expense")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANSE;

    }
}
