package repository;

import android.database.Cursor;

import java.util.List;

import model.WorkItem;

public interface DBWorkItemsRepository {

    boolean addWorkItem(String title, String description, String state, Long userId, Long issueId);

    Cursor getAllOverView();

    void updateTitle(Long id, String title);

    void updateDescriptoin(Long id, String description);

    void updateState(Long id, String state);

    void addWorkItemToUser(Long workItemId, Long userId);

    List<WorkItem> getAllByTeamId(Long id);

    List<WorkItem> getAllMyTask();

    List<WorkItem> getAllUnstarted();

    List<WorkItem> getAllStarted();

    List<WorkItem> getAllDone();

}
