package br.com.dbserver.restaurant.core.domain;

import java.time.LocalDate;
import java.util.Objects;

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
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant winRestaurant;

    @Column(name = "day_of_result", nullable = false)
    private LocalDate dayOfResult;

    @Column(name = "number_of_votes", nullable = false)
    private Long numberOfVotes;

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

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteResult that = (VoteResult) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(winRestaurant, that.winRestaurant) &&
                Objects.equals(dayOfResult, that.dayOfResult) &&
                Objects.equals(numberOfVotes, that.numberOfVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, winRestaurant, dayOfResult, numberOfVotes);
    }

    @Override
    public String toString() {
        return "VoteResult{" +
                "id=" + id +
                ", winRestaurant=" + winRestaurant +
                ", dayOfResult=" + dayOfResult +
                ", numberOfVotes=" + numberOfVotes +
                '}';
    }
}
