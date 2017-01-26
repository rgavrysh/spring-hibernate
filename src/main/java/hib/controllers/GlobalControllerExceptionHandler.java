package hib.controllers;

import hib.model.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public GlobalControllerExceptionHandler() {}

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Some problem in a server happened!") //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response handleConflict(){
        return new Response("Conflict", String.valueOf(HttpStatus.CONFLICT),
                "Conflict in a server happened!");
    }

    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class,
            HttpMediaTypeNotSupportedException.class})
    public Response  dataNotFound(){
        return new Response("Bad request", String.valueOf(HttpStatus.BAD_REQUEST),
                "Constraint violation of request data.");
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody Response defaultErrorHandler(Exception e, HttpServletRequest request){
        e.printStackTrace();
        return new Response("Internal server error", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                "General exception");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public @ResponseBody Response noHandlerFound(Exception e, HttpServletRequest request){
        return new Response("Not Found", String.valueOf(HttpStatus.NOT_FOUND), e.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    public @ResponseBody Response missingParameterHandler(Exception e, HttpServletRequest request){
        return new Response("Conflict", String.valueOf(HttpStatus.CONFLICT), e.getMessage());
    }
}
