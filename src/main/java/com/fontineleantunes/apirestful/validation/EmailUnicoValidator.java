package com.fontineleantunes.apirestful.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.fontineleantunes.apirestful.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, String> {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public void initialize(EmailUnico annotation) {
        ConstraintValidator.super.initialize(annotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Se vazio, deixar para @NotBlank validar
        if (email == null || email.isEmpty()) {
            return true;
        }

        // Verifica se existe outro aluno com este email
        return !alunoRepository.existsByEmail(email);
    }
}
