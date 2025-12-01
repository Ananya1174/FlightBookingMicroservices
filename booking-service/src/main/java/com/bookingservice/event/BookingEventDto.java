package com.bookingservice.event;

import java.util.List;
import java.time.Instant;

public class BookingEventDto {
    private String pnr;
    private Long flightId;
    private String userEmail;
    private Integer numSeats;
    private Instant createdAt;
    private String eventType; 
    private List<PassengerInfo> passengers;

    public static class PassengerInfo {
        private String name;
        private String gender;
        private Integer age;
        private String seatNumber;
        private String mealPreference;
      
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getSeatNumber() { return seatNumber; }
        public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
        public String getMealPreference() { return mealPreference; }
        public void setMealPreference(String mealPreference) { this.mealPreference = mealPreference; }
    }

    
    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public Integer getNumSeats() { return numSeats; }
    public void setNumSeats(Integer numSeats) { this.numSeats = numSeats; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public List<PassengerInfo> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerInfo> passengers) { this.passengers = passengers; }
}