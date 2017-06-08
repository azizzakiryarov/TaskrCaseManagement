package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("state")
    @Expose
    private String state;

    public Team(){}

    public Team(Long id, String teamName, String state) {
        this.id = id;
        this.teamName = teamName;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public Team setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public String getState() {
        return state;
    }

    public Team setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (getId() != null ? !getId().equals(team.getId()) : team.getId() != null) return false;
        if (getTeamName() != null ? !getTeamName().equals(team.getTeamName()) : team.getTeamName() != null)
            return false;
        return getState() != null ? getState().equals(team.getState()) : team.getState() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTeamName() != null ? getTeamName().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
