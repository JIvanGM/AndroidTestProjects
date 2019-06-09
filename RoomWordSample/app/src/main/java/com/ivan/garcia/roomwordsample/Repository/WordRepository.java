package com.ivan.garcia.roomwordsample.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ivan.garcia.roomwordsample.Room.Word;
import com.ivan.garcia.roomwordsample.Room.WordDao;
import com.ivan.garcia.roomwordsample.Room.WordRoomDatabase;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getDatabase(application);
        mWordDao = wordRoomDatabase.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao wordDao) {
            mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }

    public static class PopulateDataBaseAsync extends AsyncTask<Void, Void, Void> {
        private WordDao mDao;

        public PopulateDataBaseAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();

            Word word = new Word("Hello");
            mDao.insert(word);

            Word word2 = new Word("world");
            mDao.insert(word2);

            Word word3 = new Word("everybody");
            mDao.insert(word3);

            return null;
        }
    }
}
