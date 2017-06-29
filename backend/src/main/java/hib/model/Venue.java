package hib.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "venue")
public class Venue implements Serializable {

    private static final long serialVersionUID = -3965610784506338772L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "phone")
    @NotNull
    private Integer phone;
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Address address;
    @Column(name = "start_work")
    private Time startWorkTime;
    @Column(name = "end_work")
    private Time endWorkTime;

    protected Venue() {
    }

    public Venue(String name, Integer phone, Address address, Time startWorkTime, Time endWorkTime) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Time getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(Time startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public Time getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(Time endWorkTime) {
        this.endWorkTime = endWorkTime;
    }
}
