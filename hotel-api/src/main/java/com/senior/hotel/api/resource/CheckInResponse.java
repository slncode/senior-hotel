package com.senior.hotel.api.resource;

import java.math.BigDecimal;

import com.senior.hotel.api.model.CheckIn;

public class CheckInResponse {
    private String message;
    private CheckIn checkIn;
    private BigDecimal valorTotalGasto;

    public CheckInResponse(String message) {
        this.message = message;
    }
    
    public CheckInResponse(String message, CheckIn checkIn, BigDecimal valorTotalGasto) {
        this.message = message;
        this.checkIn = checkIn;
        this.valorTotalGasto = valorTotalGasto;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CheckIn getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(CheckIn checkIn) {
		this.checkIn = checkIn;
	}

	public BigDecimal getValorTotalGasto() {
		return valorTotalGasto;
	}

	public void setValorTotalGasto(BigDecimal valorTotalGasto) {
		this.valorTotalGasto = valorTotalGasto;
	}
	
	

    
}
