package com.school.payment.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.payment.models.ParametroPaga;
import com.school.payment.repositories.ParametroPagaRepository;

@Service
public class ParametroService {

    @Autowired
    private ParametroPagaRepository parametroRepository;

    @Transactional(readOnly = true)
    public Optional<ParametroPaga> findByNivel(int nivel) {
        return parametroRepository.findByNivel(nivel);
    }
}