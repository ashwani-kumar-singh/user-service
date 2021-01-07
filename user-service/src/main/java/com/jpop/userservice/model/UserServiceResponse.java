package com.jpop.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jpop.userservice.constant.UserStatusCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString(doNotUseGetters = true)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"status"}, allowGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserServiceResponse<T> {

    private UserStatusCode status;
    private T responseObject;

    @JsonIgnore
    private HttpStatus statusCode;

    public UserServiceResponse() {
      this.statusCode = HttpStatus.OK;
    }

    public UserServiceResponse(T responseObject) {
      this.responseObject = responseObject;
      this.statusCode = HttpStatus.OK;
    }

    public UserServiceResponse(T responseObject, UserStatusCode status) {
      this.responseObject = responseObject;
      this.status = status;
      this.statusCode = HttpStatus.OK;
    }

    public UserServiceResponse(UserStatusCode status) {
      this.status = status;
      this.statusCode = HttpStatus.OK;
    }

}