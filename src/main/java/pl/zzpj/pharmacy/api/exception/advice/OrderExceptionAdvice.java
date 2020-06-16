package pl.zzpj.pharmacy.api.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.zzpj.pharmacy.api.exception.ClientException;
import pl.zzpj.pharmacy.api.exception.OrderNotValidException;

@ControllerAdvice
public class OrderExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientException.class)
    public String returnResponse(ClientException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderNotValidException.class)
    public String returnResponse(OrderNotValidException e) {
        return e.getMessage();
    }
}
