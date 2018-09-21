package com.example.hp.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UScript;
import android.support.annotation.NonNull;

import com.example.hp.mydiary.Database.DiaryBaseHelper;
import com.example.hp.mydiary.Database.DiaryCursorWrapper;
import com.example.hp.mydiary.Database.DiaryDbSchema;
import com.example.hp.mydiary.Database.DiaryDbSchema.DiarySortTable;
import com.example.hp.mydiary.Database.DiaryDbSchema.DiaryTable;
import com.example.hp.mydiary.Database.DiarySortCursorWrapper;
import com.example.hp.mydiary.DiarySort.DiarySort;
import com.example.hp.mydiary.UserInformation.User;
import com.example.hp.mydiary.UserInformation.UserCurSorWrapper;
import com.example.hp.mydiary.UserInformation.UserDbSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DiaryLab {
    private static DiaryLab sDiaryLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DiaryLab get(Context context) {
        if (sDiaryLab == null) {
            sDiaryLab = new DiaryLab(context);
        }
        return sDiaryLab;
    }

    private DiaryLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DiaryBaseHelper(mContext)
                .getReadableDatabase();
    }

    public void addDiary(Diary diary) {
        ContentValues values = getContentValues(diary);
        mDatabase.insert(DiaryTable.NAME, null, values);
    }

    public void delete(Diary diary) {
        String uuidString = diary.getId().toString();
        mDatabase.delete(DiaryTable.NAME, DiaryTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public List<Diary> getDiaries() {
        List<Diary> diaries = new ArrayList<>();
        DiaryCursorWrapper diaryCursorWrapper = queryDiaries(null, null);
        try {
            diaryCursorWrapper.moveToFirst();
            while (!diaryCursorWrapper.isAfterLast()) {
                diaries.add(diaryCursorWrapper.getDiary());
                diaryCursorWrapper.moveToNext();
            }
        } finally {
            diaryCursorWrapper.close();
        }

        return diaries;
    }

    public List<Diary> getDiaries(int sortId) {
        List<Diary> diaries = new ArrayList<>();
        DiaryCursorWrapper diaryCursorWrapper = queryDiaries(DiaryTable.Cols.SORT_ID + " = ?",
                new String[]{"" + sortId});
        try {
            if (diaryCursorWrapper.getCount() == 0) {
                return null;
            }
            diaryCursorWrapper.moveToFirst();
            while (!diaryCursorWrapper.isAfterLast()) {
                diaries.add(diaryCursorWrapper.getDiary());
                diaryCursorWrapper.moveToNext();
            }
        } finally {
            diaryCursorWrapper.close();
        }

        return diaries;
    }

    public List<Diary> searchDiaries(String title) {
        List<Diary> diaries = new ArrayList<>();
        DiaryCursorWrapper diaryCursorWrapper = queryDiaries(DiaryTable.Cols.TITLE + " LIKE ?",
                new String[]{"%" + title + "%"});
        try {
            if (diaryCursorWrapper.getCount() == 0) {
                return null;
            }
            diaryCursorWrapper.moveToFirst();
            while (!diaryCursorWrapper.isAfterLast()) {
                diaries.add(diaryCursorWrapper.getDiary());
                diaryCursorWrapper.moveToNext();
            }
        } finally {
            diaryCursorWrapper.close();
        }

        return diaries;
    }


    public Diary getDiary(UUID uuid) {
        DiaryCursorWrapper cursorWrapper = queryDiaries(DiaryTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()});
        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }

            cursorWrapper.moveToFirst();
            return cursorWrapper.getDiary();
        } finally {
            cursorWrapper.close();
        }
    }

    public void updateDiaries(Diary diary) {
        String uuidString = diary.getId().toString();
        ContentValues values = getContentValues(diary);
        mDatabase.update(DiaryTable.NAME, values, DiaryTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }


    private DiaryCursorWrapper queryDiaries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DiaryTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                DiaryTable.Cols.DATE + " desc"
        );
        return new DiaryCursorWrapper(cursor);
    }


    private static ContentValues getContentValues(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(DiaryTable.Cols.UUID, diary.getId().toString());
        values.put(DiaryTable.Cols.TITLE, diary.getTitle());
        values.put(DiaryTable.Cols.DATE, diary.getDate().getTime());
        values.put(DiaryTable.Cols.WEATHER, diary.getWeather());
        values.put(DiaryTable.Cols.DIARYTEXT, diary.getDiaryText());
        values.put(DiaryTable.Cols.SORT_ID, diary.getDiarySortId());

        return values;
    }

    public DiarySort getDiarySort(String name) {
        DiarySortCursorWrapper wrapper = queryDiarySorts(DiarySortTable.Cols.SORT_NAME + " = ?",
                new String[]{name});
        try {
            if (wrapper.getCount() == 0) {
                return null;
            }

            wrapper.moveToFirst();
            return wrapper.getDiarySort();
        } finally {
            wrapper.close();
        }
    }

    public DiarySort getDiarySort(int id) {
        DiarySortCursorWrapper wrapper = queryDiarySorts(DiarySortTable.Cols.SORT_ID + " = ?",
                new String[]{"" + id});
        try {
            if (wrapper.getCount() == 0) {
                return null;
            }

            wrapper.moveToFirst();
            return wrapper.getDiarySort();
        } finally {
            wrapper.close();
        }
    }

    public List<DiarySort> getDiarySorts() {
        List<DiarySort> diarySorts = new ArrayList<>();
        DiarySortCursorWrapper cursorWrapper = queryDiarySorts(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                diarySorts.add(cursorWrapper.getDiarySort());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return diarySorts;
    }

    public void deleteDiarySort(DiarySort diarySort) {
        mDatabase.delete(DiarySortTable.NAME, DiarySortTable.Cols.SORT_NAME + "=?",
                new String[]{diarySort.getDiarySortName()});
    }

    public long addDiarySort(DiarySort diarySort) {
        ContentValues values = getContentValues(diarySort);
        long a = mDatabase.insert(DiarySortTable.NAME, null, values);
        return a;
    }

    public void updateDiarySort(DiarySort diarySort) {
        ContentValues values = getContentValues(diarySort);
        mDatabase.update(DiarySortTable.NAME, values, DiarySortTable.Cols.SORT_ID + "=?",
                new String[]{"" + diarySort.getDiarySortId()});


    }

    public int getRowId(String diarySort) {
        DiarySortCursorWrapper wrapper = queryDiarySorts(DiarySortTable.Cols.SORT_NAME + " = ?",
                new String[]{diarySort});
        try {
            if (wrapper.getCount() == 0) {
                return -1;
            }
            wrapper.moveToFirst();
            return wrapper.getDiarySort().getDiarySortId();
        } finally {
            wrapper.close();
        }
    }

    private static ContentValues getContentValues(DiarySort diarySort) {
        ContentValues values = new ContentValues();
        values.put(DiarySortTable.Cols.SORT_NAME, diarySort.getDiarySortName());
        values.put(DiarySortTable.Cols.SORT_DATE, diarySort.getDate().getTime());

        return values;
    }

    private DiarySortCursorWrapper queryDiarySorts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DiarySortTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                DiarySortTable.Cols.SORT_DATE
        );
        return new DiarySortCursorWrapper(cursor);
    }

    public User getUserInformation() {
        Cursor cursor = mDatabase.query(UserDbSchema.USER, null, "_id = ?", new String[]{"" + 1},
                null, null, null);

        UserCurSorWrapper wrapper = new UserCurSorWrapper(cursor);
        try {
            if (wrapper.getCount() == 0) {
                return null;
            }
            wrapper.moveToFirst();
            return wrapper.getUser();
        } finally {
            wrapper.close();
        }
    }

    public void updateUserInformation(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDbSchema.IS_SET_PASSWORD, user.getSetPassword() ? 1 : 0);
        values.put(UserDbSchema.PASSWORD, user.getPassword());
        values.put(UserDbSchema.QUESTION, user.getQuestion());
        values.put(UserDbSchema.ANSWER, user.getAnswer());

        mDatabase.update(UserDbSchema.USER, values, null, null);
    }


}
