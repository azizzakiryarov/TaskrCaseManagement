package repository;


import java.util.List;

import model.User;
import model.WorkItem;

public interface DBWorkItemsRepository {

    void saveAllWorkItemsInSQLite();

    void saveAllUsersByTeamIdInSQLite();

    List<User> getAllUsersByTeamId();

    List<WorkItem> getAllWorkItemsFromSQLite();

    List<WorkItem> getAllUnstarted();

    List<WorkItem> getAllStarted();

    List<WorkItem> getAllDone();

    List<WorkItem> getAllMyTask();

    String getTeamName();
}
