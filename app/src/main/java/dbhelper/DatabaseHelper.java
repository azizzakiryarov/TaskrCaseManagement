package dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import repository.WorkItemsRepository;

import static android.provider.BaseColumns._ID;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_ISSUE_ID;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_STATE;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_TITLE;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID;
import static dbhelper.DBContract.WorkItemsEntry.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper implements WorkItemsRepository {

    private static final int DATABASE_VERSION = 4;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "TaskrCaseManagement";

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
                DBContract.WorkItemsEntry.COLUMN_NAME_ISSUE_ID + " INTEGER DEFAULT NULL, " +
                "FOREIGN KEY (" + DBContract.WorkItemsEntry.COLUMN_NAME_ISSUE_ID + ") REFERENCES " + DBContract.WorkItemsEntry.TABLE_NAME + "(" + DBContract.IssueEntry._ID + "), " +
                "FOREIGN KEY (" + DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID + ") REFERENCES " + DBContract.WorkItemsEntry.TABLE_NAME + "(" + DBContract.UsersEntry._ID + "));";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_WORKITEMS);
        db.execSQL(CREATE_TABLE_WORKITEMS);

        String CREATE_TABLE_USER = "CREATE TABLE " + DBContract.UsersEntry.TABLE_NAME + "( " +
                DBContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.UsersEntry.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_LASTNAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_NAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_NUMBER + " TEXT NOT NULL, " +
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
    public boolean addWorkItem(String title, String description, String state, Long userId, Long issueId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE, title);
        contentValues.put(COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(COLUMN_NAME_STATE, state);
        contentValues.put(COLUMN_NAME_USER_ID, userId);
        contentValues.put(COLUMN_NAME_ISSUE_ID, issueId);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "onClick: added workItem  " + contentValues.toString());

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void updateTitle(Long id, String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


    }

    @Override
    public void updateDescriptoin(Long id, String description) {

    }

    @Override
    public void updateState(Long id, String state) {

    }

    @Override
    public void addWorkItemToUser(Long workItemId, Long userId) {

    }
}
