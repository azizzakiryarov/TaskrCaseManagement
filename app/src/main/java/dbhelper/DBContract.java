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

    public static class UsersEntry implements BaseColumns {

        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_FIRSTNAME = "firstName";
        public static final String COLUMN_NAME_LASTNAME = "lastName";
        public static final String COLUMN_NAME_USER_NUMBER = "userNumber";
        public static final String COLUMN_NAME_USER_NAME = "userName";
        public static final String COLUMN_NAME_USER_STATE = "state";
        public static final String COLUMN_NAME_TEAMID = "teamId";

    }

    public static class TeamsEntry implements BaseColumns {

        public static final String TABLE_NAME = "Team";
        public static final String COLUMN_NAME_TEAM_NAME = "teamName";
        public static final String COLUMN_NAME_TEAM_STATE = "state";
    }

    public static class IssueEntry implements BaseColumns {

        public static final String TABLE_NAME = "Issue";
        public static final String COLUMN_NAME_ISSUE_COMMENT = "comment";
    }


}
