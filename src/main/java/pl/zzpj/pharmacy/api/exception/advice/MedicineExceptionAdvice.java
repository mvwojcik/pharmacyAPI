package pl.zzpj.pharmacy.api.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.zzpj.pharmacy.api.exception.MedicineException;

@ControllerAdvice
public class MedicineExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MedicineException.class)
    public String returnResponse(MedicineException e) {
        return e.getMessage();
    }
}
