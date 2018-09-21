package com.example.hp.mydiary.Database;

public class DiaryDbSchema {
    public static final class DiaryTable {
        public static final String NAME = "Diary";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String WEATHER = "weather";
            public static final String DIARYTEXT ="diaryText";
//            public static final String SORT_NAME = "sortName";
            public static final String SORT_ID = "sort_id";
        }
    }


    public static final class DiarySortTable {
        public static final String NAME = "DiarySort";

        public static final class Cols {
            public static final String SORT_ID = "sort_id";
            public static final String SORT_NAME = "sort_name";
            public static final String SORT_DATE = "sort_date";
        }
    }

}
