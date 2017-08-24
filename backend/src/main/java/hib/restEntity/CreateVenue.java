package hib.restEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@JsonPropertyOrder(value = {"name", "address", "phone", "startWork", "endWork"})
public class CreateVenue implements Serializable {
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final long serialVersionUID = 1L;

    private String name;
    private CreateAddress address;
    private Integer phone;
    private Time startWork;
    private Time endWork;

    public CreateVenue() {
    }

    public CreateVenue(@JsonProperty(value = "name") String name,
                       @JsonProperty(value = "address") CreateAddress address,
                       @JsonProperty(value = "phone") Integer phone,
                       @JsonProperty(value = "startWork") String startWork,
                       @JsonProperty(value = "endWork") String endWork) throws ParseException {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.startWork = new Time(timeFormat.parse(startWork).getTime());
        this.endWork = new Time(timeFormat.parse(endWork).getTime());
    }

    public String getName() {
        return name;
    }

    public CreateAddress getAddress() {
        return address;
    }

    public Integer getPhone() {
        return phone;
    }

    public Time getStartWork() {
        return startWork;
    }

    public Time getEndWork() {
        return endWork;
    }
}
