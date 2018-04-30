package spp.lab.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_subscriptions")
public class UserSubscription extends SafeDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subscription subscription;

    private Long available_visits;

    private Date end_date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Long getAvailable_visits() {
        return available_visits;
    }

    public void setAvailable_visits(Long available_visits) {
        this.available_visits = available_visits;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Long getId() {
        return id;
    }
}
