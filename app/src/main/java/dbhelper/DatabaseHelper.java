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
import model.WorkItem;
import repository.DBWorkItemsRepository;

public class DatabaseHelper extends SQLiteOpenHelper implements DBWorkItemsRepository {

    private HttpService httpService = new HttpService();
    private static final int DATABASE_VERSION = 14;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "TaskrCaseManagement";
    private static final String GET_ALL_WORKITEMS = "SELECT * FROM " + DBContract.WorkItemsEntry.TABLE_NAME;


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
                DBContract.TeamsEntry.COLUMN_NAME_TEAM_NAME + " TEXT NOT NULL, " +
                DBContract.TeamsEntry.COLUMN_NAME_TEAM_STATE + " TEXT NOT NULL);";

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
    public void addTeam(String teamName, String state) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("teamName", teamName);
        contentValues.put("state", state);
        db.insert(DBContract.TeamsEntry.TABLE_NAME, null, contentValues);

    }

    @Override
    public void addUser(String firstName, String lastName, String userName, String userNumber, String state, Long teamId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("userName", userName);
        contentValues.put("userNumber", userNumber);
        contentValues.put("state", state);
        contentValues.put("teamId", teamId);
        db.insert(DBContract.UsersEntry.TABLE_NAME, null, contentValues);

    }

    @Override
    public void addWorkItem(String title, String descrpition, String state, Long userId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", descrpition);
        contentValues.put("state", state);
        contentValues.put("userId", userId);
        db.insert(DBContract.WorkItemsEntry.TABLE_NAME, null, contentValues);

    }

    @Override
    public void saveAllWorkItemsInSQLite() {

        ArrayList<WorkItem> list = (ArrayList<WorkItem>) httpService.getAllMyTask();
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
        if (cursor != null) {
            cursor.close();
        }
        return allWorkItems;
    }

    @Override
    public List<WorkItem> getAllMyTask() {

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
        if (cursor != null) {
            cursor.close();
        }
        return allWorkItems;
    }


    @Override
    public List<WorkItem> getAllUnstarted() {
        return null;
    }

    @Override
    public List<WorkItem> getAllStarted() {
        return null;
    }

    @Override
    public List<WorkItem> getAllDone() {
        return null;
    }

}
