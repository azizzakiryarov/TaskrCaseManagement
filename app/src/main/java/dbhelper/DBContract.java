package dbhelper;

import android.provider.BaseColumns;

public class DBContract implements BaseColumns {

    private DBContract() {
    }

    public static class WorkItemsEntry implements BaseColumns {

        public static final String TABLE_NAME = "WorkItem";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_ISSUE_ID = "issueId";


    }


}
