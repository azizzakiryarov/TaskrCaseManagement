package dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.Issue;
import repository.WorkItemsRepository;

import static android.provider.BaseColumns._ID;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_ISSUE_ID;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_STATE;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_TITLE;
import static dbhelper.DBContract.WorkItemsEntry.COLUMN_NAME_USER_ID;
import static dbhelper.DBContract.WorkItemsEntry.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper implements WorkItemsRepository {

    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "TaskrCaseManagement";
    private static final String GET_ALL_CONTACTS = "SELECT * FROM " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_WORKITEMS = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER NOT NULL, " +
                COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_NAME_STATE + " TEXT NOT NULL, " +
                COLUMN_NAME_USER_ID + " INTEGER DEFAULT NULL, " +
                COLUMN_NAME_ISSUE_ID + " INTEGER DEFAULT NULL, " +
                "PRIMARY KEY (" + _ID + "), " +
                "UNIQUE KEY (" + DBContract.IssueEntry._ID + "), " +
                "KEY (" + DBContract.UsersEntry._ID + ")" +
                "FOREIGN KEY (" + COLUMN_NAME_ISSUE_ID + ") REFERENCES (" + DBContract.IssueEntry._ID + "), " +
                "FOREIGN KEY (" + COLUMN_NAME_USER_ID + ") REFERENCES (" + DBContract.UsersEntry._ID + ");";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_WORKITEMS);
        db.execSQL(CREATE_TABLE_WORKITEMS);

        String CREATE_TABLE_USER = "CREATE TABLE " + DBContract.UsersEntry.TABLE_NAME + "( " +
                DBContract.UsersEntry._ID + " INTEGER NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_LASTNAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_NAME + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_NUMBER + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_USER_STATE + " TEXT NOT NULL, " +
                DBContract.UsersEntry.COLUMN_NAME_TEAMID + " INTEGER DEFAULT NULL, " +
                "PRIMARY KEY (" + DBContract.UsersEntry._ID + "), " +
                "UNIQUE KEY (" + DBContract.UsersEntry.COLUMN_NAME_USER_NAME + "), " +
                "UNIQUE KEY (" + DBContract.UsersEntry.COLUMN_NAME_USER_NUMBER + "), " +
                "KEY (" + DBContract.UsersEntry.COLUMN_NAME_TEAMID + ")" +
                "FOREIGN KEY (" + DBContract.UsersEntry.COLUMN_NAME_TEAMID + ") REFERENCES (" + DBContract.TeamsEntry._ID + ");";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_TEAM = "CREATE TABLE " + DBContract.TeamsEntry.TABLE_NAME + "( " +
                DBContract.TeamsEntry._ID + " INTEGER NOT NULL, " +
                DBContract.TeamsEntry.COLUMN_NAME_TEAM_NAME + " TEXT NOT NULL, " +
                DBContract.TeamsEntry.COLUMN_NAME_TEAM_STATE + " TEXT NOT NULL, " +
                "PRIMARY KEY (" + DBContract.TeamsEntry._ID + "), " +
                "UNIQUE KEY (" + DBContract.TeamsEntry.COLUMN_NAME_TEAM_NAME + ");";

        Log.d(TAG, "onCreate: " + CREATE_TABLE_TEAM);
        db.execSQL(CREATE_TABLE_TEAM);

        String CREATE_TABLE_ISSUE = "CREATE TABLE " + DBContract.IssueEntry.TABLE_NAME + "( " +
                DBContract.IssueEntry._ID + " INTEGER NOT NULL, " +
                DBContract.IssueEntry.COLUMN_NAME_ISSUE_COMMENT + " TEXT NOT NULL, " +
                "PRIMARY KEY (" + DBContract.IssueEntry._ID + ");";

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
    public boolean addWorkItem(String title, String description, String state) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_TITLE, title);
        contentValues.put(COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(COLUMN_NAME_STATE, state);
        //contentValues.put(COLUMN_NAME_USER_ID, state);
        //contentValues.put(COLUMN_NAME_ISSUE_ID, state);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "onClick: added workItem  " + contentValues.toString());

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*

    private static final String CREATE_TABLE_WORKITEMS =
            "CREATE TABLE " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + " (" +
                    ItemsDbContract.WorkItemsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS + " TEXT NOT NULL," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE + " TEXT NOT NULL," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TEAM_ID + " INTEGER NOT NULL,"+
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_USER_ID + " INTEGER NOT NULL,"+
                    "FOREIGN KEY(" + ItemsDbContract.UsersEntry._ID + ") REFERENCES " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + "(" + ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TEAM_ID + "), " +
                    "FOREIGN KEY(" + ItemsDbContract.TeamsEntry._ID + ") REFERENCES " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + "(" + ItemsDbContract.WorkItemsEntry.COLUMN_NAME_USER_ID + "), " +
                    "FOREIGN KEY(" + ItemsDbContract.UsersEntry.COLUMN_NAME_ASSIGNEE + ") REFERENCES " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + "(" + ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE + "));";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + ItemsDbContract.UsersEntry.TABLE_NAME + " (" +
                    ItemsDbContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_USER_NUMBER + " TEXT, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_LASTNAME + " TEXT NOT NULL, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_USER_STATE + " TEXT NOT NULL,"+
                    ItemsDbContract.UsersEntry.COLUMN_NAME_ASSIGNEE + "TEXT NOT NULL," +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_TEAMID + "INTEGER NOT NULL," +
                    "FOREIGN KEY(" + ItemsDbContract.TeamsEntry._ID + ") REFERENCES " + ItemsDbContract.UsersEntry.TABLE_NAME + "(" + ItemsDbContract.UsersEntry.COLUMN_NAME_TEAMID + "));";

    private static final String CREATE_TABLE_TEAMS =
            "CREATE TABLE " + ItemsDbContract.TeamsEntry.TABLE_NAME + " (" +
                    ItemsDbContract.TeamsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME + " TEXT, " +
                    ItemsDbContract.TeamsEntry.COLUMN_TEAM_STATUS + " TEXT NOT NULL);";


    private static final String DROP_TABLE_WORKITEMS =
            "DROP TABLE IF EXISTS " + ItemsDbContract.WorkItemsEntry.TABLE_NAME;

    private static final String DROP_TABLE_USERS =
            "DROP TABLE IF EXISTS " + ItemsDbContract.UsersEntry.TABLE_NAME;

    private static final String DROP_TABLE_TEAMS =
            "DROP TABLE IF EXISTS " + ItemsDbContract.TeamsEntry.TABLE_NAME;



    public static synchronized ItemsDbHelper getInstance(Context context) {
        if(instance == null) {
            instance = new ItemsDbHelper(context);
        }
        return instance;
    }

    private ItemsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKITEMS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TEAMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_WORKITEMS);
        db.execSQL(DROP_TABLE_USERS);
        db.execSQL(DROP_TABLE_TEAMS);

        onCreate(db);
    }
}
     */
}
