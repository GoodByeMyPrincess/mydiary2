package com.example.hp.mydiary.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.hp.mydiary.Database.DiaryDbSchema.DiaryTable.Cols;
import com.example.hp.mydiary.Diary;

import java.util.Date;
import java.util.UUID;

public class DiaryCursorWrapper extends CursorWrapper {
    public DiaryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Diary getDiary() {
        String uuid = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        long date = getLong(getColumnIndex(Cols.DATE));
        String weather = getString(getColumnIndex(Cols.WEATHER));
        String diaryText = getString(getColumnIndex(Cols.DIARYTEXT));
        int diarySortId = getInt(getColumnIndex(Cols.SORT_ID));

        Diary diary = new Diary(UUID.fromString(uuid));
        diary.setTitle(title);
        diary.setDate(new Date(date));
        diary.setWeather(weather);
        diary.setDiaryText(diaryText);
        diary.setDiarySortId(diarySortId);

        return diary;
    }
}
