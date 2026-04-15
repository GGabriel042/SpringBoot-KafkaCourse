package br.com.school.product.domain.exception;

import lombok.Getter;

import java.util.List;

public class DomainException extends RuntimeException {

    @Getter
    private List<Error> errors;

    public DomainException(String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }
}
