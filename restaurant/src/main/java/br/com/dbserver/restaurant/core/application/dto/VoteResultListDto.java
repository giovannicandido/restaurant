package br.com.dbserver.restaurant.core.application.dto;

import java.time.LocalDate;

public class VoteResultListDto {
    private String restaurantName;
    private LocalDate dayOfResult;
    private Long numberOfVotes;

    public VoteResultListDto(String restaurantName, LocalDate dayOfResult, Long numberOfVotes) {
        this.restaurantName = restaurantName;
        this.dayOfResult = dayOfResult;
        this.numberOfVotes = numberOfVotes;
    }

    public VoteResultListDto() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDate getDayOfResult() {
        return dayOfResult;
    }

    public void setDayOfResult(LocalDate dayOfResult) {
        this.dayOfResult = dayOfResult;
    }

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
