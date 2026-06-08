package com.example.buscard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TapilmadiException.class)
    public ResponseEntity<XetaMesaji> tapilmadiExceptionHandler(TapilmadiException ex) {
        XetaMesaji xeta = new XetaMesaji(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(xeta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArtiqMovcuddurException.class)
    public ResponseEntity<XetaMesaji> artiqMovcuddurExceptionHandler(ArtiqMovcuddurException ex) {
        XetaMesaji xeta = new XetaMesaji(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(xeta, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(YersizIstekException.class)
    public ResponseEntity<XetaMesaji> yersizIstekExceptionHandler(YersizIstekException ex) {
        XetaMesaji xeta = new XetaMesaji(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(xeta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validationExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> xetalar = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(xeta -> {
            String field = ((FieldError) xeta).getField();
            String mesaj = xeta.getDefaultMessage();
            xetalar.put(field, mesaj);
        });

        Map<String, Object> cavab = new HashMap<>();
        cavab.put("status", HttpStatus.BAD_REQUEST.value());
        cavab.put("xetalar", xetalar);
        cavab.put("zaman", LocalDateTime.now());

        return new ResponseEntity<>(cavab, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<XetaMesaji> umumiExceptionHandler(Exception ex) {
        XetaMesaji xeta = new XetaMesaji(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Daxili server xətası: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(xeta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
