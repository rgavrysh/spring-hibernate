package hib.restEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookTime {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @JsonProperty("start_date_time")
    private Date startDateTime;
    @JsonProperty("end_date_time")
    private Date endDateTime;
    @JsonProperty("customer_id")
    private Integer customerId;

    public BookTime(@JsonProperty(value = "start_date_time", required = true) String startTime,
                    @JsonProperty(value = "end_date_time", required = true) String endTime,
                    @JsonProperty(value = "customer_id", required = true) Integer customerId) throws ParseException {
        this.startDateTime = dateFormat.parse(startTime);
        this.endDateTime = dateFormat.parse(endTime);
        this.customerId = customerId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }
}
