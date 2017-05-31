package repository;

public interface WorkItemsRepository {

    boolean addWorkItem(String title, String description, String state, Long userId, Long issueId);

    void updateTitle(Long id, String title);

    void updateDescriptoin(Long id, String description);

    void updateState(Long id, String state);

    void addWorkItemToUser(Long workItemId, Long userId);

}
