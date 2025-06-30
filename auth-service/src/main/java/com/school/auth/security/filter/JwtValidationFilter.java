package com.school.auth.security.filter;

import static com.school.auth.security.Parametros.CONTENT_TYPE;
import static com.school.auth.security.Parametros.HEADER_AUTHORIZATION;
import static com.school.auth.security.Parametros.PREFIX_TOKEN;
import static com.school.auth.security.Parametros.SECRET_KEY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.auth.security.SimpleGrantedAuthorityJasonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*
 * Filtra las solicitudes entrantes para validar y verificar tokens JWT.
 * Extrae el token del encabezado de la solicitud.
 * Verifica la validez del token utilizando la clave secreta.
 * Si el token es válido, establece la autenticación en el contexto de seguridad.
 */

public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                // Obtener el encabezado "Authorization" de la solicitud
                String header = request.getHeader(HEADER_AUTHORIZATION);
                
                // Verificar si el encabezado existe y comienza con el prefijo correcto ("Bearer")
                if(header == null || !header.startsWith(PREFIX_TOKEN)){
                    chain.doFilter(request, response);// Si no hay token o no tiene el formato correcto, continuar con la cadena de filtros
                    return;
                }
                String token = header.replace(PREFIX_TOKEN, "");// Extraer el token de autorización (sin el prefijo "Bearer")


                try {
                    // Verificar la validez del token utilizando la clave secreta
                    Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
                    String username = claims.getSubject();
                    Object authoritiesClaims = claims.get("authorities");
            
                    // Extraer las autoridades/roles del token (si es necesario)
                    Collection<? extends GrantedAuthority> authorities =Arrays.asList(
                        new ObjectMapper()
                        .addMixIn(SimpleGrantedAuthority.class,SimpleGrantedAuthorityJasonCreator.class)
                        .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class)
                    );

                    // Crear un objeto UsernamePasswordAuthenticationToken con el nombre de usuario y las autoridades
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(upat);// Establecer la autenticación en el contexto de seguridad
                    chain.doFilter(request, response);// Continuar con la cadena de filtros

                
                } catch (JwtException e) {
                    // Manejar errores de autenticación (token inválido, expirado, etc.)
                    Map<String, String> body = new HashMap<>();
                    body.put("error", e.getMessage());
                    body.put("message", "El toke JWT es invalido!");
                    
                    // Escribir el cuerpo de respuesta JSON con los detalles del error
                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(CONTENT_TYPE);
                }
    }

    
    
}
