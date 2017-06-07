package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userNumber")
    @Expose
    private String userNumber;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("teamId")
    @Expose
    private Long teamId;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public User setUserNumber(String userNumber) {
        this.userNumber = userNumber;
        return this;
    }

    public String getState() {
        return state;
    }

    public User setState(String state) {
        this.state = state;
        return this;
    }

    public Long getTeamId() {
        return teamId;
    }

    public User setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getUserName() != null ? !getUserName().equals(user.getUserName()) : user.getUserName() != null)
            return false;
        if (getUserNumber() != null ? !getUserNumber().equals(user.getUserNumber()) : user.getUserNumber() != null)
            return false;
        if (getState() != null ? !getState().equals(user.getState()) : user.getState() != null)
            return false;
        return getTeamId() != null ? getTeamId().equals(user.getTeamId()) : user.getTeamId() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getUserNumber() != null ? getUserNumber().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getTeamId() != null ? getTeamId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", userNumber='" + userNumber + '\'' +
                ", state='" + state + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
