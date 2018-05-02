package spp.lab.models;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription extends SafeDeleteEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long visitCount;

    private Long price;

    private Long duration;


    public Subscription()
    {

    }

    public Subscription(String name, Long visitCount, Long price, Long duration)
    {
        this.name = name;
        this.visitCount = visitCount;
        this.price = price;
        this.duration = duration;
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

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
