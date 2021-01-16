package pl.solventive.LocalFarmer.LocalFarmerApi.util;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> messages = new ArrayList<>();
        result.getAllErrors().forEach(error -> messages.add(error.getDefaultMessage()));
        ValidationError error = new ValidationError(result.getErrorCount(),
                result.getObjectName() + " has validation issues.",
                messages);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected HttpStatus handleConstaintViolation(ConstraintViolationException ex) {
        StringBuilder message = new StringBuilder();
        ex.getConstraintViolations().iterator().forEachRemaining(violation -> message.append(violation.getMessage()).append(", "));
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, message.toString()
        );
    }
}