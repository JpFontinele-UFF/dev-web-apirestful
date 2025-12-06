package com.fontineleantunes.apirestful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(false, e.getMessage(), null));
    }

    // Handler para CPF já utilizado — retorna ErrorResponse com status 400
    @ExceptionHandler(CpfJaUtilizadoException.class)
    public ResponseEntity<ErrorResponse> handleCpfJaUtilizado(CpfJaUtilizadoException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("cpf", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(false, "Validation failed", errors));
    }

    // Handler para Email já utilizado — retorna ErrorResponse com status 400
    @ExceptionHandler(EmailJaUtilizadoException.class)
    public ResponseEntity<ErrorResponse> handleEmailJaUtilizado(EmailJaUtilizadoException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(false, "Validation failed", errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        
        // Itera sobre todos os erros de campo e constrói o mapa campo -> mensagem
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            
            // Se o campo já existe no mapa, concatena com separador
            if (errors.containsKey(fieldName)) {
                errors.put(fieldName, errors.get(fieldName) + "; " + errorMessage);
            } else {
                errors.put(fieldName, errorMessage);
            }
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(false, "Validation failed", errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(false, "Você não tem permissão para acessar este recurso.", null));
    }
}

