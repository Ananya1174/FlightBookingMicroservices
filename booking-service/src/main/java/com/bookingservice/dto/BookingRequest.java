package com.bookingservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class BookingRequest {

   
    private String userEmail; // also required in header - double-check at controller

    @NotNull
    private Long flightId;

    @Positive
    private Integer numSeats;

    @NotEmpty
    @Valid

    private List<PassengerDto> passengers;

    // getters/setters

    public static class PassengerDto {
        @NotBlank
        private String name;

        @Positive
        private Integer age;

        @NotBlank
        private String seatNumber;

        @NotBlank
        private String mealPreference; // VEG/NONVEG

        @NotBlank
        private String gender;

        // standard getters / setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }

        public String getSeatNumber() { return seatNumber; }
        public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

        public String getMealPreference() { return mealPreference; }
        public void setMealPreference(String mealPreference) { this.mealPreference = mealPreference; }

        public String getGender() { return gender; }        // <-- fixed
        public void setGender(String gender) { this.gender = gender; }
    }
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public Integer getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}

	public List<PassengerDto> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDto> passengers) {
		this.passengers = passengers;
	}
}