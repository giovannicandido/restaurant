package br.com.dbserver.restaurant.core.application.dto;

public class RestaurantListDto {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Long numberOfVotes;

    public RestaurantListDto(Long id, String name, String description, String imgUrl, Long numberOfVotes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
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

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
