package case_study.model.account_manage;

public class Employee extends User {

    public Employee(String username, String password, String identification, String name, String email, String phoneNumber) {
        super(username, password, name, email, phoneNumber, identification);
        this.roles.add(Role.ROLE_EMPLOYEE);
    }

    @Override
    public String toString() {
        return  "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", identification='" + identification + '\'' +
                ", roles=" + roles;
    }
}
