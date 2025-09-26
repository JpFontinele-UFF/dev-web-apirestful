package com.fontineleantunes.apirestful.service;

import com.fontineleantunes.apirestful.model.Inscricao;
import com.fontineleantunes.apirestful.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscricaoService {
    @Autowired
    private InscricaoRepository inscricaoRepository;

    public Inscricao save(Inscricao inscricao) {
        return inscricaoRepository.save(inscricao);
    }

    public void deleteById(Long id) {
        inscricaoRepository.deleteById(id);
    }
}
