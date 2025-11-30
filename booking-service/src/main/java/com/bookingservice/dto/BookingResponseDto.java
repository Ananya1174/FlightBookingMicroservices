package com.bookingservice.dto;

import java.time.Instant;
import java.util.List;

public class BookingResponseDto {
    private String pnr;
    private Long flightId;
    private String userEmail;
    private Integer numSeats;
    private Double totalPrice;
    private String status;
    private Instant createdAt;
    private List<PassengerInfo> passengers;

    public static class PassengerInfo {
        private String name;
        public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getSeatNumber() {
			return seatNumber;
		}
		public void setSeatNumber(String seatNumber) {
			this.seatNumber = seatNumber;
		}
		public String getMealPreference() {
			return mealPreference;
		}
		public void setMealPreference(String mealPreference) {
			this.mealPreference = mealPreference;
		}
		private String gender;
        private Integer age;
        private String seatNumber;
        private String mealPreference;
        // getters/setters
    }
    // getters/setters

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public List<PassengerInfo> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerInfo> passengers) {
		this.passengers = passengers;
	}
}