package case_study.service;

import case_study.model.account_manage.Role;
import case_study.model.account_manage.User;
import case_study.model.account_manage.Customer;
import case_study.model.account_manage.Employee;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UserService {
    private static final String USERS_FILE_PATH = "src/case_study/repository/users.csv";
    private static final String EMPLOYEES_FILE_PATH = "src/case_study/repository/employees.csv";
    private static final String CUSTOMERS_FILE_PATH = "src/case_study/repository/customers.csv";
    private Map<String, User> users = new HashMap<>();
    private Map<String, Employee> employees = new HashMap<>();
    private Map<String, Customer> customers = new HashMap<>();

    public UserService() {
        new MovieService();
        new ShowTimeService();
        new CinemaService();
        users = readUsersFromFile();
        customers = readCustomersFromFile();
        employees = readEmployeesFromFile();
    }

    public Map<String, Employee> getAllEmployees() {
        return employees;
    }

    public Map<String, Customer> getAllCustomers() {
        return customers;
    }


    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /******************************************* Admin Handle *************************************************/
    public void registerAdmin(String username, String password, String name, String email, String phoneNumber) {
        User user = new User(username, password, name, email, phoneNumber);
        users.put(username, user);
        writeUsersToFile(users);
    }

    /******************************************* User Handle *************************************************/

    public void registerUser(String username, String password, String name, String email, String phoneNumber) {
        User user = new User(username, password, name, email, phoneNumber);
        users.put(username, user);
        addRoleToUser(username, Role.ROLE_USER);
        writeUsersToFile(users);
    }

    public void addRoleToUser(String username, Role role) {
        User user = users.get(username);
        if (user != null) {
            user.addRole(role);
            writeUsersToFile(users);
        }
    }

    public void updateUserDetails(String username, String newPassword, String newName, String newPhoneNumber, String newEmail) {
        User user = users.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            user.setName(newName);
            user.setEmail(newEmail);
            user.setPhoneNumber(newPhoneNumber);
            writeUsersToFile(users);
        }
    }

    public boolean deleteUser(String username) {
        boolean removedFromUsers = users.remove(username) != null;
        boolean removedFromCustomers = customers.remove(username) != null;
        boolean removedFromEmployees = employees.remove(username) != null;
        boolean removed = removedFromUsers && (removedFromCustomers || removedFromEmployees);
        if (removed) {
            writeUsersToFile(users);
            writeEmployeesToFile(employees);
            writeCustomersToFile(customers);
        }
        return removed;
    }


    public Map<String, User> getAllUsers() {
        return users;
    }


    /******************************************* Employee Handle *************************************************/

    public void registerEmployee(String username, String password, String identification, String phoneNumber, String name, String email) {
        Employee employee = new Employee(username, password, identification, name, email, phoneNumber);
        employee.setIdentification(identification);
        User user = new User(username, password, name, email, phoneNumber, identification);
        users.put(username, user);
        employees.put(username, employee);
        addRoleToUser(username, Role.ROLE_EMPLOYEE);
        writeUsersToFile(users);
        writeEmployeesToFile(employees);
    }

    public void updateEmployeeDetails(String username, String newPassword, String newIdentification, String newPhoneNumber, String newName, String newEmail) {
        User user = users.get(username);
        Employee employee = employees.get(username);
        if (user != null && employee != null) {
            employee.setPassword(newPassword);
            employee.setIdentification(newIdentification);
            employee.setPhoneNumber(newPhoneNumber);
            employee.setName(newName);
            employee.setEmail(newEmail);
            user.setPassword(newPassword);
            user.setIdentification(newIdentification);
            user.setPhoneNumber(newPhoneNumber);
            user.setName(newName);
            user.setEmail(newEmail);
            writeUsersToFile(users);
            writeEmployeesToFile(employees);
        }
    }

    public boolean promoteEmployeeToManager(String username) {
        User user = users.get(username);
        if (user != null && user.getRoles().contains(Role.ROLE_EMPLOYEE)) {
            addRoleToUser(username, Role.ROLE_MANAGER);
            writeUsersToFile(users);
            writeEmployeesToFile(employees);
            return true;
        }
        return false;
    }

    /******************************************* Customer Handle *************************************************/

    public void registerCustomer(String username, String password, String name, String email, String phoneNumber) {
        Customer customer = new Customer(username, password, name, email, phoneNumber);
        User user = new User(username, password, name, email, phoneNumber);
        users.put(username, user);
        customers.put(username, customer);
        addRoleToUser(username, Role.ROLE_CUSTOMER);
        writeUsersToFile(users);
        writeCustomersToFile(customers);
    }

    public void updateCustomerDetails(String username, String newPassword, String newName, String newPhoneNumber, String newEmail) {
        User user = users.get(username);
        Customer customer = customers.get(username);
        if (customer != null && user != null) {
            user.setPassword(newPassword);
            user.setName(newName);
            user.setPhoneNumber(newPhoneNumber);
            user.setEmail(newEmail);
            customer.setPassword(newPassword);
            customer.setName(newName);
            customer.setPhoneNumber(newPhoneNumber);
            customer.setEmail(newEmail);
            writeUsersToFile(users);
            writeCustomersToFile(customers);
        }
    }

    /******************************************* Check Old PassWord To Update *************************************************/
    public boolean isNotOldPassword(String username, String oldPassword) {
        User user = users.get(username);
        return user == null || !oldPassword.equals(user.getPassword());
    }

    /******************************************* Check userName, email and phoneNumber *************************************************/
    public boolean usernameExists(String username) {
        return users.containsKey(username);
    }

    public boolean emailExists(String username, String newEmail) {
        return users.values().stream()
                .anyMatch(user -> !user.getUsername().equals(username) && user.getEmail().equals(newEmail));
    }

    public boolean phoneNumberExists(String username, String newPhoneNumber) {
        return users.values().stream()
                .anyMatch(user -> !user.getUsername().equals(username) && user.getPhoneNumber().equals(newPhoneNumber));
    }


    /******************************************* Handle Users with File *************************************************/
    private void writeUsersToFile(Map<String, User> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
            writer.write("UserName,Password,Name,Email,PhoneNumber,Role\n");
            for (User user : users.values()) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s%n",
                        user.getUsername(),
                        user.getPassword(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getRoles()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCustomersToFile(Map<String, Customer> customers) {
        try (FileWriter writer = new FileWriter(CUSTOMERS_FILE_PATH)) {
            writer.write("UserName,Name,Email,PhoneNumber,Role\n");
            for (Customer customer : customers.values()) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s%n",
                        customer.getUsername(),
                        customer.getPassword(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhoneNumber(),
                        customer.getRoles()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeEmployeesToFile(Map<String, Employee> employees) {
        try (FileWriter writer = new FileWriter(EMPLOYEES_FILE_PATH)) {
            writer.write("UserName,Password,Identification,Name,Email,PhoneNumber,Role\n");
            for (Employee employee : employees.values()) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s%n",
                        employee.getUsername(),
                        employee.getPassword(),
                        employee.getIdentification(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getPhoneNumber(),
                        employee.getRoles()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, User> readUsersFromFile() {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    String username = fields[0];
                    String password = fields[1];
                    String name = fields[2];
                    String email = fields[3];
                    String phoneNumber = fields[4];
                    String role = fields[5];
                    User user = new User(username, password, name, email, phoneNumber);
                    users.put(name, user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private Map<String, Customer> readCustomersFromFile() {
        Map<String, Customer> customers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 5) {
                    String username = fields[0];
                    String password = fields[1];
                    String name = fields[2];
                    String email = fields[3];
                    String phoneNumber = fields[4];
                    String role = fields[5];
                    Customer customer = new Customer(username, password, name, email, phoneNumber);
                    customers.put(name, customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private Map<String, Employee> readEmployeesFromFile() {
        Map<String, Employee> employees = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEES_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    String username = fields[0];
                    String password = fields[1];
                    String name = fields[2];
                    String email = fields[3];
                    String phoneNumber = fields[4];
                    String identification = fields[5];
                    String role = fields[6];
                    Employee employee = new Employee(username, password, name, email, phoneNumber, identification);
                    employees.put(name, employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
