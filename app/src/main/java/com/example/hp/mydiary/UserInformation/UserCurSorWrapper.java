package com.example.hp.mydiary.UserInformation;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.icu.lang.UScript;

public class UserCurSorWrapper extends CursorWrapper {
    public UserCurSorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser () {
        int isSetPassword = getInt(getColumnIndex(UserDbSchema.IS_SET_PASSWORD));
        String password = getString(getColumnIndex(UserDbSchema.PASSWORD));
        String question = getString(getColumnIndex(UserDbSchema.QUESTION));
        String answer = getString(getColumnIndex(UserDbSchema.ANSWER));

        User user = new User();
        user.setSetPassword(isSetPassword == 1);
        user.setPassword(password);
        user.setQuestion(question);
        user.setAnswer(answer);
        return user;
    }
}
