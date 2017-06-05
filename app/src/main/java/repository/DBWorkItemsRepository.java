package repository;

import android.database.Cursor;

import java.util.List;

import model.WorkItem;

public interface DBWorkItemsRepository {


    void addTeam(String teamName, String state);

    void addUser(String firstName, String lastName, String userName, String userNumber, String state, Long teamId);

    void addWorkItem(String title, String descrpition, String state, Long userId);

    List<WorkItem> getAllByTeamId(Long id);

    List<WorkItem> getAllMyTask();

    List<WorkItem> getAllUnstarted();

    List<WorkItem> getAllStarted();

    List<WorkItem> getAllDone();

    Cursor getAllOverView();

    Cursor getAllTeams();

    Cursor getAllUsers();
}
