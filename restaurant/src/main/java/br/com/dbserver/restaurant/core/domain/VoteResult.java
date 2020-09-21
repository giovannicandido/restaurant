package br.com.dbserver.restaurant.core.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class VoteResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant winRestaurant;

    @Column(name = "day_of_result")
    private LocalDate dayOfResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getWinRestaurant() {
        return winRestaurant;
    }

    public void setWinRestaurant(Restaurant restaurant) {
        this.winRestaurant = restaurant;
    }

    public LocalDate getDayOfResult() {
        return dayOfResult;
    }

    public void setDayOfResult(LocalDate dayOfResult) {
        this.dayOfResult = dayOfResult;
    }
}
