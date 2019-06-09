package com.ivan.garcia.roomwordsample.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ivan.garcia.roomwordsample.Repository.WordRepository;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static String dbName = "word_database";
    private static volatile WordRoomDatabase INSTANCE;

    public abstract WordDao wordDao();

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                // Create database here
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, dbName).addCallback(sRoomDatabaseCallback).build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new WordRepository.PopulateDataBaseAsync(INSTANCE).execute();
                }
            };
}
