package bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountManager {
    private List<Account> users;
    private String [] adminNames;
    private Map<String, Integer> LikeTimes;

    public AccountManager(String[] adminNames) {
        this.users = new ArrayList<>();
        this.adminNames = adminNames;
        this.LikeTimes = new LinkedHashMap<>();
    }

    public List<Account> getUsers() {
        return users;
    }

    public void setUsers(List<Account> users) {
        this.users = users;
    }

    public String[] getAdminNames() {
        return adminNames;
    }

    public void setAdminNames(String[] adminNames) {
        this.adminNames = adminNames;
    }

    public Map<String, Integer> getLikeTimes() {
        return LikeTimes;
    }

    public void setLikeTimes(Map<String, Integer> likeTimes) {
        LikeTimes = likeTimes;
    }

    @Override
    public String toString() {
        return "AccountManager{" +
                "users=" + users +
                ", adminNames=" + Arrays.toString(adminNames) +
                ", LikeTimes=" + LikeTimes +
                '}';
    }
}
