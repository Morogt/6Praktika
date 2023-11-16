public class User {
    private String username;
    private int role;
    private String fullName;

    public User(String username, int role, String fullName) {
        this.username = username;
        this.role = role;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public int getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }
}
