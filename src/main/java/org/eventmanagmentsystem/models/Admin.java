package org.eventmanagmentsystem.models;

public class Admin extends User {

    /* constructor */
    public Admin(String userName, String email, String role) {
        super(userName, email, role);
    }
    
    /* method for add customer */
    public void addCustomer(String userName, String email){
        /* create new customer account */
        Customer newCustomer = new Customer(userName, email, "Customer");
        
        /* add customer file */
        // add implementation
    }
    
    /* method for add Project Manage */
    public void addProjectManager(String userName, String email){
        /* create new Project Manager object */
        ProjectManager newProjectManager = new ProjectManager(userName, email, "Project Manager");
        
        /* add Project Manager file */
        // add implementation
    }
    
    /* method for add Project Manage */
    public void addServiceProvider(String userName, String email){
        /* create new Project Manager object */
        ServiceProvider newServiceProvider = new ServiceProvider(userName, email, "Service Provider");
        
        /* add Service Provider file */
        // add implementation
    }
    
    /* update Customer account */
    public void updateCustomer(int id){
        /* implement it */
        /* load customer file and search for id for replace data */
    }

    /* update Project Manager account */
    public void updateProjectManager(int id){
        /* implement it */
        /* load Project Manager file and search for id for replace data */
    }
    
    /* update Project Manager account */
    public void updateServiceProvider(int id){
        /* implement it */
        /* load Service Provider file and search for id for replace data */
    }
    
    /* delete customer */
    public void deleteCustomer(int id){
        /* delete customer from file and all event that he booked */
    }
    
    /* delete Project Manager */
    public void deleteProjectManager(int id){
        /* delete Project Manager from file */
    }
    
    /* delete Service Provider */
    public void deleteServiceProvider(int id){
        /* delete Service Provider from file */
    }
}
