package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkItem {

    private static final long DEFAULT_ID = -1;

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("userId")
    @Expose
    private Long issueId;

    public WorkItem() {
    }

    public WorkItem(String title, String description, String state) {
        this.title = title;
        this.description = description;
        this.state = state;
    }

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
        return "WorkItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", userId=" + userId +
                '}';
    }
}