package dbhelper;

import android.provider.BaseColumns;

public class DBContract implements BaseColumns {

    private DBContract() {
    }

    static class WorkItemsEntry implements BaseColumns {
        public static final String TABLE_NAME = "WorkItem";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_DESCRIPTION = "description";
        static final String COLUMN_NAME_STATE = "state";
        static final String COLUMN_NAME_USER_ID = "userId";
    }

    static class UsersEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        static final String COLUMN_NAME_FIRSTNAME = "firstName";
        static final String COLUMN_NAME_LASTNAME = "lastName";
        static final String COLUMN_NAME_USER_NAME = "userName";
        static final String COLUMN_NAME_USER_NUMBER = "userNumber";
        static final String COLUMN_NAME_USER_STATE = "state";
        static final String COLUMN_NAME_TEAMID = "teamId";

    }

    public static class TeamsEntry implements BaseColumns {
        public static final String TABLE_NAME = "Team";
        static final String COLUMN_NAME_TEAM_NAME = "teamName";
    }

    static class IssueEntry implements BaseColumns {
        static final String TABLE_NAME = "Issue";
        static final String COLUMN_NAME_ISSUE_COMMENT = "comment";
    }


}
