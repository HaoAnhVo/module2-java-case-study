package case_study.model.account_manage;

import java.util.HashSet;
import java.util.Set;

public class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected String identification;
    protected final Set<Role> roles = new HashSet<>();

    private Employee employeeDetails;
    private Customer customerDetails;
    private User userDetails;

    public User(String username, String password, String name, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles.add(Role.ROLE_USER);
    }

    public User(String username, String password, String name, String email, String phoneNumber, String identification) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles.add(Role.ROLE_USER);
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public Employee getEmployeeDetails() {
        return employeeDetails;
    }




    public Customer getCustomerDetails() {
        return customerDetails;
    }


    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Override
    public String toString() {
        return  "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles;
    }
}
