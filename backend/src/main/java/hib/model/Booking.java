package hib.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "booking")
@Table(name = "booking")
public class Booking implements Serializable {
    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final long serialVersionUID = -3965610784506338772L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "venue_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Venue venue;

    @JoinColumn(name = "customer_id")
    @ManyToOne
    @JsonBackReference
    private Customer customer;

    @Column(name = "start_date_time")
    private Date startDateTime;

    @Column(name = "end_date_time")
    private Date endDateTime;

    protected Booking() {
    }

    public Booking(Venue venue, Customer customer, Date startDateTime, Date endDateTime) {
        this.venue = venue;
        this.customer = customer;
        if (timeFormat.format(startDateTime).compareTo(timeFormat.format(venue.getStartWorkTime())) >= 0) {
            this.startDateTime = startDateTime;
        } else {
            throw new IllegalArgumentException("Opening time is later than booking time.");
        }
        if (timeFormat.format(endDateTime).compareTo(timeFormat.format(venue.getEndWorkTime())) < 0) {
            this.endDateTime = endDateTime;
        } else {
            throw new IllegalArgumentException("Closing time is earlier than booking time.");
        }
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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Booking:{\"id\": " + this.getId() +
                "\", \"venue\": " + this.getVenue().toString() +
                "\", \"customer\": " + this.getCustomer().toString() +
                "\", \"start_date_time\": " + this.getStartDateTime() +
                "\", \"end_date_time\": " + this.getEndDateTime() + "\"}";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(startDateTime)
                .append(endDateTime)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Booking) {
            Booking that = (Booking) obj;
            return new EqualsBuilder()
                    .append(this.id, that.id)
                    .append(this.startDateTime, that.startDateTime)
                    .append(this.endDateTime, that.endDateTime)
                    .append(this.customer, that.customer)
                    .append(this.venue, that.venue)
                    .isEquals();
        }
        return false;
    }
}
