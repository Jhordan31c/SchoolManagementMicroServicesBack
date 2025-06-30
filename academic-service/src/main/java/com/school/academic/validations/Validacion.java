package com.school.academic.validations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class Validacion {
    
    public ResponseEntity<?> informe(BindingResult br){
        Map<String, String> errores = new HashMap<>();

        br.getFieldErrors().forEach(x->{
            errores.put(x.getField(), x.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
