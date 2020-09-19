package br.com.dbserver.restaurant.core.application.dto;

import br.com.dbserver.restaurant.core.domain.Restaurant;

public class RestaurantListDto {
    private Long id;
    private String name;
    private Integer numberOfVotes;

    public RestaurantListDto(Long id, String name, Integer numberOfVotes) {
        this.id = id;
        this.name = name;
        this.numberOfVotes = numberOfVotes;
    }

    public RestaurantListDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Integer numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
