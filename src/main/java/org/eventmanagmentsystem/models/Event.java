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
    private double serviceProviderCost;
    private String status;
    private double tax;

    public Event(int eventId, int customerId, int managerId, int serviceProviderId, Date eventDate,
                 int eventDuration, int seats, double cost, String status, double serviceProviderCost, double tax) {
        this.eventId = eventId;
        this.customerId = customerId;
        this.managerId = managerId;
        this.serviceProviderId = serviceProviderId;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.seats = seats;
        this.cost = cost;
        this.status = status;
        this.serviceProviderCost = serviceProviderCost;
        this.tax = tax;
    }

    public Event(int customerId, Date eventDate, int eventDuration, int seats, double cost, String status, double serviceProviderCost) {
        this.customerId = customerId;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.seats = seats;
        this.cost = cost;
        this.status = status;
        this.serviceProviderCost = serviceProviderCost;
        this.tax = 10;  // Default tax rate
    }

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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double calculateTotalCost() {
        return this.cost + this.serviceProviderCost + (this.cost * this.tax / 100);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return eventId + "," + customerId + "," + managerId + "," + serviceProviderId + "," +
                dateFormat.format(eventDate) + "," + eventDuration + "," + seats + "," + cost + "," + status + "," + serviceProviderCost;
    }
}
