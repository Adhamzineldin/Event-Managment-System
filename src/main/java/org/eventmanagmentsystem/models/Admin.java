package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.AdminService;
import java.util.List;

public class Admin extends User {

    private AdminService adminService;

    public Admin(int id, String userName, String password,  String email, String role) {
        super(id, userName, password, email, role);
        this.adminService = new AdminService();
    }

    public boolean addManager(ProjectManager manager) {
        return adminService.addUser(manager);
    }

    public boolean addServiceProvider(ServiceProvider serviceProvider) {
        return adminService.addUser(serviceProvider);
    }

    public boolean addCustomer(Customer customer) {
        return adminService.addUser(customer);
    }

    public boolean removeManager(int managerId) {
        return adminService.removeUser(managerId, "manager");
    }

    public boolean removeServiceProvider(int serviceProviderId) {
        return adminService.removeUser(serviceProviderId, "provider");
    }

    public boolean removeCustomer(int customerId) {
        return adminService.removeUser(customerId, "customer");
    }

    public List<ProjectManager> getManagers() {
        List<ProjectManager> managers = adminService.viewUsersByRole(ProjectManager.class);
        return managers;
    }

    public List<ServiceProvider> getServiceProviders() {
        List<ServiceProvider> serviceProviders = adminService.viewUsersByRole(ServiceProvider.class);
        return serviceProviders;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = adminService.viewUsersByRole(Customer.class);
        return customers;
    }

    public List<User> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return users;
    }

    public boolean addUser(User user) {
        return adminService.addUser(user);
    }

    public boolean removeUser(int id, String role) {
        return adminService.removeUser(id, role);
    }
    
    public boolean updateUser(User user) {
        return adminService.updateUser(user);
    }
}
