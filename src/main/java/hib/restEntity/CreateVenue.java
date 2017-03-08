package hib.restEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateVenue {
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private String name;
    private CreateAddress createAddress;
    private Integer phone;
    private Time startWork;
    private Time endWork;

    public CreateVenue(@JsonProperty(value = "name") String name,
                       @JsonProperty(value = "address_id") CreateAddress createAddress,
                       @JsonProperty(value = "phone") Integer phone,
                       @JsonProperty(value = "start_work") String startWork,
                       @JsonProperty(value = "end_work") String endWork) throws ParseException {
        this.name = name;
        this.createAddress = createAddress;
        this.phone = phone;
        this.startWork = new Time(timeFormat.parse(startWork).getTime());
        this.endWork = new Time(timeFormat.parse(endWork).getTime());
    }

    public String getName() {
        return name;
    }

    public CreateAddress getCreateAddress() {
        return createAddress;
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
