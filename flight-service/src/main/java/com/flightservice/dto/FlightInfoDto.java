package com.flightservice.dto;

import com.flightservice.model.AbstractFlightInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class FlightInfoDto extends AbstractFlightInfo {
    private List<FlightSeatDto> seats; 
}