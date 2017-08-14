package hib.util;

import java.io.Serializable;

public class Response implements Serializable {
    private static final Long serialVersionUid = 8540905263219837767L;

    private String status;
    private String statusCode;
    private String message;

    public Response(String status, String statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
