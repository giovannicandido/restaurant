package br.com.dbserver.restaurant.core.domain.dto;

import java.util.Objects;

public class RestaurantVoteDto {
    private final Long id;
    private final String name;
    private final String description;
    private final String imgUrl;
    private final Long numberOfVotes;

    public RestaurantVoteDto(Long id, String name, String description, String imgUrl, Long numberOfVotes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.numberOfVotes = numberOfVotes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantVoteDto that = (RestaurantVoteDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(numberOfVotes, that.numberOfVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imgUrl, numberOfVotes);
    }
}
