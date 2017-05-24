package model;

public final class WorkItem {

    private Long id;
    private String title;
    private String description;
    private String state;
    private Long userId;
    private Long issueId;


    public Long getId() {
        return id;
    }

    public WorkItem setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public WorkItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getState() {
        return state;
    }

    public WorkItem setState(String state) {
        this.state = state;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WorkItem setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getIssueId() {
        return issueId;
    }

    public WorkItem setIssueId(Long issueId) {
        this.issueId = issueId;
        return this;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

