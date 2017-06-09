package repository;

import android.database.Cursor;

import java.util.List;

import model.User;
import model.WorkItem;

public interface DBWorkItemsRepository {

    void saveAllWorkItemsInSQLite();

    void saveAllUsersByTeamIdInSQLite();

    List<User> getAllUsersByTeamId();

    List<WorkItem> getAllByTeamId();

    List<WorkItem> getAllWorkItemsFromSQLite();

    List<WorkItem> getAllUnstarted();

    List<WorkItem> getAllStarted();

    List<WorkItem> getAllDone();

    List<WorkItem> getAllMyTask();

    String getTeamName();

    Cursor getAllOverView();

    Cursor getAllTeams();

    Cursor getAllUsers();

    Cursor getAllWorkItemsTitle();
}
