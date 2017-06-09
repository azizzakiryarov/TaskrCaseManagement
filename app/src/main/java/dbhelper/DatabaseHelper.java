package dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import http.HttpService;
import model.Team;
import model.User;
import model.WorkItem;
import repository.DBWorkItemsRepository;

public class DatabaseHelper extends SQLiteOpenHelper implements DBWorkItemsRepository {

    private HttpService httpService = new HttpService();
    private static final int DATABASE_VERSION = 20;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "TaskrCaseManagement";
    private static final String GET_ALL_WORKITEMS = "SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME;
    private static final String GET_ALL_USERS_BY_TEAM_ID = "SELECT * FROM " + DBContract.UsersEntry.TABLE_NAME + " WHERE teamId = 1;";

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_WORKITEMS = "CREATE TABLE " + DBContract.WorkItemsEntry.TABLE_NAME + " (" +
                DBContract.WorkItemsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.WorkItemsEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                DBContract.WorkItemsEntry.COLUMN_NAME_STATE + " TEXT NOT NULL, " +
                DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID + " INTEGER DEFAULT NULL, " +
                "FOREIGN KEY (" + DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID + ") REFERENCES " + DBContract.WorkItemsEntry.TABLE_NAME + "(" + DBContract.UsersEntry._ID + "));";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_WORKITEMS);
        db.execSQL(CREATE_TABLE_WORKITEMS);

        String CREATE_TABLE_USER = "CREATE TABLE " + DBContract.UsersEntry.TABLE_NAME + "( " +
                DBContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.UsersEntry.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_LASTNAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_NAME + " TEXT NOT NULL UNIQUE, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_NUMBER + " TEXT NOT NULL UNIQUE, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_STATE + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_TEAMID + " INTEGER DEFAULT NULL, " +
                "FOREIGN KEY (" + DBContract.UsersEntry.COLUMN_NAME_TEAMID + ") REFERENCES " + DBContract.UsersEntry.TABLE_NAME + "(" + DBContract.TeamsEntry._ID + "));";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_TEAM = "CREATE TABLE " + DBContract.TeamsEntry.TABLE_NAME + "( " +
                DBContract.TeamsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DBContract.TeamsEntry.COLUMN_NAME_TEAM_NAME + " TEXT NOT NULL);";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_TEAM);
        db.execSQL(CREATE_TABLE_TEAM);

        String CREATE_TABLE_ISSUE = "CREATE TABLE " + DBContract.IssueEntry.TABLE_NAME + "( " +
                DBContract.IssueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DBContract.IssueEntry.COLUMN_NAME_ISSUE_COMMENT + " TEXT NOT NULL);";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_ISSUE);
        db.execSQL(CREATE_TABLE_ISSUE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DBContract.WorkItemsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.UsersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.TeamsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.IssueEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public Cursor getAllOverView() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME, null);

    }

    @Override
    public Cursor getAllTeams() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + DBContract.TeamsEntry.TABLE_NAME, null);
    }

    @Override
    public Cursor getAllUsers() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + DBContract.UsersEntry.TABLE_NAME, null);
    }

    @Override
    public Cursor getAllWorkItemsTitle() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT title FROM " + DBContract.UsersEntry.TABLE_NAME, null);
    }

    @Override
    public void saveAllWorkItemsInSQLite() {

        ArrayList<WorkItem> list = (ArrayList<WorkItem>) httpService.getAllWorkItems();
        for (WorkItem workItems : list) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", workItems.getTitle());
            contentValues.put("description", workItems.getDescription());
            contentValues.put("state", workItems.getState());
            contentValues.put("userId", workItems.getUserId());
            db.insert(DBContract.WorkItemsEntry.TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void saveAllUsersByTeamIdInSQLite() {

        ArrayList<User> list = (ArrayList<User>) httpService.getAllUsersByTeamId(1L);
        for (User users : list) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("firstName", users.getFirstName());
            contentValues.put("lastName", users.getLastName());
            contentValues.put("userName", users.getUserName());
            contentValues.put("userNumber", users.getUserNumber());
            contentValues.put("state", users.getState());
            contentValues.put("teamId", users.getTeamId());
            db.insert(DBContract.UsersEntry.TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public List<User> getAllUsersByTeamId() {

        List<User> allUsersByTeamId = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_USERS_BY_TEAM_ID, null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.UsersEntry._ID);
            int firstNameIndex = cursor.getColumnIndex(DBContract.UsersEntry.COLUMN_NAME_FIRSTNAME);
            int lastNameIndex = cursor.getColumnIndex(DBContract.UsersEntry.COLUMN_NAME_LASTNAME);
            int userNameIndex = cursor.getColumnIndex(DBContract.UsersEntry.COLUMN_NAME_USER_NAME);
            int userNumberIndex = cursor.getColumnIndex(DBContract.UsersEntry.COLUMN_NAME_USER_NUMBER);
            int stateIndex = cursor.getColumnIndex(DBContract.UsersEntry.COLUMN_NAME_USER_STATE);
            int teamIdIndex = cursor.getColumnIndex(DBContract.UsersEntry.COLUMN_NAME_TEAMID);

            do {
                long id = cursor.getLong(longIndex);
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String userName = cursor.getString(userNameIndex);
                String userNumber = cursor.getString(userNumberIndex);
                String state = cursor.getString(stateIndex);
                long teamId = cursor.getLong(teamIdIndex);

                User user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUserName(userName);
                user.setUserNumber(userNumber);
                user.setState(state);
                user.setTeamId(teamId);

                allUsersByTeamId.add(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allUsersByTeamId;
    }


    @Override
    public List<WorkItem> getAllByTeamId() {

        List<WorkItem> allWorkItems = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME + " w, Team t WHERE t.id = 1;", null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry._ID);
            int titleIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION);
            int stateIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_STATE);
            int userIdIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID);

            do {
                long id = cursor.getLong(longIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String state = cursor.getString(stateIndex);
                long userId = cursor.getLong(userIdIndex);

                WorkItem workItem = new WorkItem();
                workItem.setId(id);
                workItem.setTitle(title);
                workItem.setDescription(description);
                workItem.setState(state);
                workItem.setUserId(userId);

                allWorkItems.add(workItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWorkItems;
    }

    @Override
    public List<WorkItem> getAllWorkItemsFromSQLite() {

        List<WorkItem> allWorkItems = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_WORKITEMS, null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry._ID);
            int titleIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION);
            int stateIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_STATE);
            int userIdIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID);

            do {
                long id = cursor.getLong(longIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String state = cursor.getString(stateIndex);
                long userId = cursor.getLong(userIdIndex);

                WorkItem workItem = new WorkItem();
                workItem.setId(id);
                workItem.setTitle(title);
                workItem.setDescription(description);
                workItem.setState(state);
                workItem.setUserId(userId);

                allWorkItems.add(workItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWorkItems;
    }


    @Override
    public List<WorkItem> getAllUnstarted() {

        List<WorkItem> allWorkItemsUnstarted = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME + " WHERE WorkItem.state = 'Unstarted';", null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry._ID);
            int titleIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION);
            int stateIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_STATE);
            int userIdIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID);

            do {
                long id = cursor.getLong(longIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String state = cursor.getString(stateIndex);
                long userId = cursor.getLong(userIdIndex);

                WorkItem workItem = new WorkItem();
                workItem.setId(id);
                workItem.setTitle(title);
                workItem.setDescription(description);
                workItem.setState(state);
                workItem.setUserId(userId);

                allWorkItemsUnstarted.add(workItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWorkItemsUnstarted;
    }

    @Override
    public List<WorkItem> getAllStarted() {

        List<WorkItem> allWorkItemsStarted = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME + " WHERE WorkItem.state = 'Started';", null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry._ID);
            int titleIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION);
            int stateIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_STATE);
            int userIdIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID);

            do {
                long id = cursor.getLong(longIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String state = cursor.getString(stateIndex);
                long userId = cursor.getLong(userIdIndex);

                WorkItem workItem = new WorkItem();
                workItem.setId(id);
                workItem.setTitle(title);
                workItem.setDescription(description);
                workItem.setState(state);
                workItem.setUserId(userId);

                allWorkItemsStarted.add(workItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWorkItemsStarted;
    }

    @Override
    public List<WorkItem> getAllDone() {

        List<WorkItem> allWorkItemsDone = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME + " WHERE WorkItem.state = 'Done';", null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry._ID);
            int titleIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION);
            int stateIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_STATE);
            int userIdIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID);

            do {
                long id = cursor.getLong(longIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String state = cursor.getString(stateIndex);
                long userId = cursor.getLong(userIdIndex);

                WorkItem workItem = new WorkItem();
                workItem.setId(id);
                workItem.setTitle(title);
                workItem.setDescription(description);
                workItem.setState(state);
                workItem.setUserId(userId);

                allWorkItemsDone.add(workItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWorkItemsDone;
    }

    @Override
    public List<WorkItem> getAllMyTask() {

        List<WorkItem> allWorkItemsMyTask = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME + " WHERE WorkItem.userId = '2';", null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int longIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry._ID);
            int titleIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION);
            int stateIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_STATE);
            int userIdIndex = cursor.getColumnIndex(DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID);

            do {
                long id = cursor.getLong(longIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String state = cursor.getString(stateIndex);
                long userId = cursor.getLong(userIdIndex);

                WorkItem workItem = new WorkItem();
                workItem.setId(id);
                workItem.setTitle(title);
                workItem.setDescription(description);
                workItem.setState(state);
                workItem.setUserId(userId);

                allWorkItemsMyTask.add(workItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWorkItemsMyTask;
    }

    @Override
    public String getTeamName() {

        Team team = new Team();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT teamName FROM " + DBContract.TeamsEntry.TABLE_NAME, null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                String teamName = cursor.getString(0);
                team.setTeamName(teamName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return team.getTeamName();

    }

}
