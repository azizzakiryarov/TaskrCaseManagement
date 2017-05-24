package repository;

import java.util.List;

import http.HttpService;
import model.WorkItem;


public class WorkItemsRepository {

    private HttpService httpService;

    public WorkItemsRepository(HttpService httpService) {
        this.httpService = httpService;
    }

    public List<WorkItem> getAllUnstarted() {
        return httpService.getAllUnstarted();
    }

}
