package org.eventmanagmentsystem.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private int eventId;
    private int customerId;
    private int managerId;
    private int serviceProviderId;
    private Date eventDate;
    private int eventDuration;
    private int seats;
    private double cost;
    private double serviceProviderCost; // Added service provider cost
    private String status;
    int tax = 10;

    public Event(int eventId, int customerId, int managerId, int serviceProviderId, Date eventDate,
                 int eventDuration, int seats, double cost, String status, double serviceProviderCost) {
        this.eventId = eventId;
        this.customerId = customerId;
        this.managerId = managerId;
        this.serviceProviderId = serviceProviderId;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.seats = seats;
        this.cost = cost;
        this.status = status;
        this.serviceProviderCost = serviceProviderCost; // Initialize service provider cost
    }

    public Event( int customerId, Date eventDate,
                 int eventDuration, int seats, double cost, String status, double serviceProviderCost) {

        this.customerId = customerId;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.seats = seats;
        this.cost = cost;
        this.status = status;
        this.serviceProviderCost = serviceProviderCost;
    }

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(int serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(int eventDuration) {
        this.eventDuration = eventDuration;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getServiceProviderCost() {
        return serviceProviderCost;
    }

    public void setServiceProviderCost(double serviceProviderCost) {
        this.serviceProviderCost = serviceProviderCost;
    }

    // Method to update event details
    public void updateEventDetails(Date eventDate, int eventDuration, int seats, double cost, double serviceProviderCost) {
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.seats = seats;
        this.cost = cost;
        this.serviceProviderCost = serviceProviderCost; // Update service provider cost as well
    }

    // Method to update event status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public int calculateTotalCost() {
        return (int) (cost + serviceProviderCost + (cost * tax / 100));
    }


    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return eventId + "," +
                customerId + "," +
                managerId + "," +
                serviceProviderId + "," +
                dateFormat.format(eventDate) + "," +
                eventDuration + "," +
                seats + "," +
                cost + "," +
                status + "," +
                serviceProviderCost;
    }

}
