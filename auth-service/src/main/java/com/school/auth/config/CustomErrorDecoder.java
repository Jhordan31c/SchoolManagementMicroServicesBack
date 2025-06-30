package com.school.auth.config;

import org.springframework.stereotype.Component;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    
    @Override
    public Exception decode(String methodKey, Response response) {
        System.err.println("Error en comunicación con microservicio: " + methodKey + " - Status: " + response.status());
        
        switch (response.status()) {
            case 404:
                return new EntityNotFoundException("Recurso no encontrado en " + methodKey);
            case 500:
                return new ServiceUnavailableException("Servicio no disponible: " + methodKey);
            case 400:
                return new BadRequestException("Petición inválida: " + methodKey);
            case 503:
                return new ServiceUnavailableException("Academic service no disponible");
            default:
                return new Exception("Error de comunicación: " + methodKey + " - Status: " + response.status());
        }
    }
}

class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}

class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}