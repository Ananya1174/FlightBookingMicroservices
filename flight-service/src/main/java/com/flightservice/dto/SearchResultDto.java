package com.flightservice.dto;

import java.time.LocalDateTime;

public class SearchResultDto {
    private Long flightId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airlineName;
    private String airlineLogoUrl;
    private Double price;
    private String tripType;
    private Integer seatsAvailable;
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getAirlineLogoUrl() {
		return airlineLogoUrl;
	}
	public void setAirlineLogoUrl(String airlineLogoUrl) {
		this.airlineLogoUrl = airlineLogoUrl;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public Integer getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(Integer seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

}