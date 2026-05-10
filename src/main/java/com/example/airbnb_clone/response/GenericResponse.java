package com.example.airbnb_clone.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GenericResponse<T> (
    StatusDescription statusDescription,
    T body
){
    public static <T> GenericResponse<T> success(T data){
        StatusDescription statusDescription=StatusDescription.builder().statusCode(HttpStatus.OK.value()).statusMessage("Success").build();
        return new GenericResponse<>(statusDescription, data);
    }

    public static <T> GenericResponse<T> error(Integer statusCode, String statusMessage){
        StatusDescription statusDescription=StatusDescription.builder().statusCode(statusCode).statusMessage(statusMessage).build();
        return new GenericResponse<>(statusDescription, null);
    }
}


