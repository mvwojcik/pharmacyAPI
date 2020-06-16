package pl.zzpj.pharmacy.api.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.zzpj.pharmacy.api.exception.EmployeeException;

@ControllerAdvice
public class EmployeeExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmployeeException.class)
    public String returnResponse(EmployeeException e) {
        return e.getMessage();
    }
}
