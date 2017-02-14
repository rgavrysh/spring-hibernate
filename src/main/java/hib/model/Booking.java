package hib.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = -3965610784506338772L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "venue_id")
    @ManyToOne(fetch = FetchType.EAGER)
    Venue venue;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    Customer customer;

    @Column(name = "start_date_time")
    private Date startDateTime;

    @Column(name = "end_date_time")
    private Date endDateTime;

    protected Booking(){}
    public Booking(Venue venue, Customer customer, Date startDateTime, Date endDateTime) {
        this.venue = venue;
        this.customer = customer;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStartDateTime() {
        return startDateTime.toString();
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime.toString();
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
}
