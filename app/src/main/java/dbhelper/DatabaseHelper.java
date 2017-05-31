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

    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "TaskrCaseManagement";
    private static final String GET_ALL_CONTACTS = "SELECT * FROM " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_TITLE + " TEXT NOT NULL, " + COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_NAME_STATE + " TEXT NOT NULL, " + COLUMN_NAME_USER_ID + " INTEGER DEFAULT NULL, " +
                COLUMN_NAME_ISSUE_ID + " INTEGER DEFAULT NULL);";

        Log.d(TAG, "onCreate: " + CREATE_TABLE);

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    @Override
    public boolean addWorkItem(String title, String description, String state, Long userId, Long issueId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_TITLE, title);
        contentValues.put(COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(COLUMN_NAME_STATE, state);
        contentValues.put(COLUMN_NAME_USER_ID, state);
        contentValues.put(COLUMN_NAME_ISSUE_ID, state);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "onClick: added workItem  " + contentValues.toString());

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
