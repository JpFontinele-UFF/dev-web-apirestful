package com.fontineleantunes.apirestful.exception;

// Estrutura padrão de resposta de erro usada pelo GlobalExceptionHandler
public class ErrorResponse {
    private boolean success;
    private String message;
    private Object data;

    public ErrorResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Object getData() { return data; }
}

