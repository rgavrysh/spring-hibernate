package hib.controllers;

import hib.model.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public GlobalControllerExceptionHandler() {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Some problem in a server happened!") //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public
    @ResponseBody
    Response handleConflict() {
        return new Response("Conflict", String.valueOf(HttpStatus.CONFLICT),
                "Conflict in a server happened!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public
    @ResponseBody
    Response handleJsonParseException(Exception e) {
        return new Response("Bad request body", String.valueOf(HttpStatus.BAD_REQUEST),
                "Can not parse request body data. \n" + e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(MissingPathVariableException.class)
    public
    @ResponseBody
    Response handlePathVariableException(Exception e) {
        return new Response("Invalid Path Variable", String.valueOf(HttpStatus.NOT_ACCEPTABLE),
                e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(IllegalArgumentException.class)
    public
    @ResponseBody
    Response handleArgumentException(Exception e) {
        return new Response("Illegal argument", String.valueOf(HttpStatus.NOT_ACCEPTABLE),
                e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, HttpMediaTypeNotSupportedException.class})
    public
    @ResponseBody
    Response dataNotFound(Exception e) {
        return new Response("Bad request", String.valueOf(HttpStatus.BAD_REQUEST),
                "Constraint violation of request data. \n" + e.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    Response defaultErrorHandler(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return new Response("Internal server error", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                "General exception.\n" + e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public
    @ResponseBody
    Response noHandlerFound(Exception e, HttpServletRequest request) {
        return new Response("Not Found", String.valueOf(HttpStatus.NOT_FOUND), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoResultException.class, EmptyResultDataAccessException.class})
    public
    @ResponseBody
    Response noResultException(Exception e) {
        return new Response("No result found", String.valueOf(HttpStatus.NOT_FOUND),
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    public
    @ResponseBody
    Response missingParameterHandler(Exception e, HttpServletRequest request) {
        return new Response("Conflict", String.valueOf(HttpStatus.CONFLICT), e.getMessage());
    }
}
