package com.school.auth.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.auth.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Para llamar los atributos staticos de otra clase
import static com.school.auth.security.Parametros.*;


/*
 * Filtra las solicitudes entrantes para autenticar al usuario.
 * Lee las credenciales (nombre de usuario y contraseña) desde la solicitud.
 * Utiliza el AuthenticationManager para autenticar las credenciales.
 * Si la autenticación es exitosa, genera un token JWT y lo agrega al encabezado de la respuesta.
 */

 //FILTRO PARA CREAR EL TOKEN     PARA HACER LOGIN   CREA EL TOKEN Y DEVUELVE AL CLIENTE
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private AuthenticationManager am;

    

    //CONSTRUCTOR
    public JwtAuthenticationFilter(AuthenticationManager am) {
        this.am = am;
    }



    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // Paso 1: Inicialización de variables
        User u = null;
        String username = null;
        String password = null;

        // Paso 2: Leer los datos del usuario desde la solicitud (por ejemplo, JSON con nombre de usuario y contraseña)
        try {
            u = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = u.getUsername();
            password = u.getPassword();

        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Paso 3: Crear un token de autenticación con el nombre de usuario y la contraseña
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);

        // Paso 4: Autenticar las credenciales utilizando el AuthenticationManager
        return am.authenticate(upat);
    }





    // Método que se ejecuta cuando la autenticación es exitosa
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Obtener detalles del usuario autenticado (por ejemplo, nombre de usuario)
        org.springframework.security.core.userdetails.User   u = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String[] datos = u.getUsername().split("-");
        String username = datos[0];
        Long id = Long.parseLong(datos[1]);

        // Obtener las autoridades/roles del usuario autenticado
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
    
        // Crear un objeto Claims para incluir información en el token JWT
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username",username)
        .build();

        // Generar un token JWT con los datos del usuario
        String token    = Jwts.builder()
                .subject    (username)
                .claims     (claims)
                .expiration (new Date(System.currentTimeMillis()+3600000))
                .issuedAt   (new Date())
                .signWith   (SECRET_KEY)
        .compact    ();

        // Agregar el token al encabezado de la respuesta
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // Convertir las autoridades a una lista de cadenas
        List<String> rolesList = roles.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        // Crear un cuerpo de respuesta JSON con información adicional
        Map<String, Object> body = new HashMap<>();
        body.put("user_id", id);
        body.put("token"    , token);
        body.put("username" , username);
        body.put("roles", rolesList);
        body.put("message"  , String.format("Hola %s has iniciado sesion con exito", username));

        // Escribir el cuerpo de respuesta en la salida
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }






    // Método que se ejecuta cuando la autenticación falla
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                
        Map<String, String> body = new HashMap<>();
        body.put("message"  , "Error en la autenticacion, Username o Password incorrectos!");
        body.put("error"    , failed.getMessage());
        
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }




    

    
}    
