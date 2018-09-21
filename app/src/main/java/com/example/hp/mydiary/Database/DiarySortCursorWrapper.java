package com.example.hp.mydiary.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.hp.mydiary.DiarySort.DiarySort;

import java.util.Date;

import static com.example.hp.mydiary.Database.DiaryDbSchema.*;

public class DiarySortCursorWrapper extends CursorWrapper{
    public DiarySortCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public DiarySort getDiarySort() {
        String diarySortName = getString(getColumnIndex(DiarySortTable.Cols.SORT_NAME));
        Long date = getLong(getColumnIndex(DiarySortTable.Cols.SORT_DATE));
        int id = getInt(getColumnIndex(DiarySortTable.Cols.SORT_ID));


        DiarySort diarySort = new DiarySort(diarySortName, new Date(date));
        diarySort.setDiarySortId(id);

        return diarySort;
    }
}
