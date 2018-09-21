package com.example.hp.mydiary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hp.mydiary.Database.DiaryDbSchema.DiaryTable;
import com.example.hp.mydiary.DiarySort.DiarySort;
import com.example.hp.mydiary.UserInformation.User;
import com.example.hp.mydiary.UserInformation.UserDbSchema;

import java.util.Date;

import static com.example.hp.mydiary.Database.DiaryDbSchema.*;


public class DiaryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAE = "DiaryBase.db";

    public DiaryBaseHelper(Context context) {
        super(context, DATABASE_NAE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 创建User表
         */
        db.execSQL("create table " + UserDbSchema.USER +
                "(_id integer primary key ," +
                UserDbSchema.IS_SET_PASSWORD + "," +
                UserDbSchema.PASSWORD + "," +
                UserDbSchema.QUESTION + "," +
                UserDbSchema.ANSWER +
                ")"
        );

        /**
         * 创建日记分类表
         */

        db.execSQL("insert into user values (1, 0, null, null, null)");


        db.execSQL("create table " + DiarySortTable.NAME +
                "(" +
                DiarySortTable.Cols.SORT_ID + " integer primary key autoincrement," +
                DiarySortTable.Cols.SORT_NAME + " unique ," +
                DiarySortTable.Cols.SORT_DATE +
                " )"
        );

        /**
         * 创建日记表
         */

        db.execSQL("create table " + DiaryTable.NAME +
                "(diary_id integer primary key autoincrement," +
                DiaryTable.Cols.UUID + ", " +
                DiaryTable.Cols.TITLE + ", " +
                DiaryTable.Cols.DATE + ", " +
                DiaryTable.Cols.WEATHER + ", " +
                DiaryTable.Cols.DIARYTEXT + ", " +
                DiaryTable.Cols.SORT_ID + " integer, " +
                "foreign key (" +  DiaryTable.Cols.SORT_ID + ")" +
                "references " + DiarySortTable.NAME + "(" + DiarySortTable.Cols.SORT_ID + ")" + "on delete cascade" +
                " on update cascade" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = on;");
            //打开SQLite数据库外键支持
        }
    }
}
