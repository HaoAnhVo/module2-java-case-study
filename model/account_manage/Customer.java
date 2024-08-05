package case_study.model.account_manage;

public class Customer extends User {

    public Customer(String username, String password, String name, String email, String phoneNumber) {
        super(username, password, name, email, phoneNumber);
        this.roles.add(Role.ROLE_CUSTOMER);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
