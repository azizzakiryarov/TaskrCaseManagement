package repository;

public interface WorkItemsRepository {

    boolean addWorkItem(String title, String description, String state);


}
