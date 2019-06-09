package com.ivan.garcia.roomwordsample.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert()
    void insert(Word word);

    @Query("DELETE FROM WORD_TABLE")
    void deleteAll();

    @Query("SELECT * FROM WORD_TABLE ORDER BY id ASC")
    LiveData<List<Word>> getAllWords();

}
