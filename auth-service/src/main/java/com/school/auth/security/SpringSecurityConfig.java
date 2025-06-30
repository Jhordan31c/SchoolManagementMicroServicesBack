package com.school.auth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.school.auth.security.filter.JwtAuthenticationFilter;
import com.school.auth.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {
    
    @Bean
    PasswordEncoder encriptador(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //Reglas de acceso para rutas específicas        
        return http.authorizeHttpRequests( (a)-> a
            //En la base de datos "ROLE_ADMIN", omtie "ROLE_"
            .requestMatchers(HttpMethod.GET , "/users" ,"/users/{id}/{rol}" ).permitAll()
            .requestMatchers(HttpMethod.POST, "/users"                      ).permitAll()
            .requestMatchers(HttpMethod.PUT , "/users"                      ).permitAll()

            .requestMatchers(HttpMethod.POST    ,"/alumnos"         ,"/docentes"            ,"/citas"           ,"/pagos"           ,"/eventos"         ,"/materias"        ,"/aulas"                       ).permitAll()
            .requestMatchers(HttpMethod.PUT     ,"/alumnos/{id}"    ,"/docentes/{id}"       ,"/citas/{id}"      ,"/pagos/{id}"      ,"/eventos/{id}"    ,"/materias/{id}"   ,"/docentes/alumnos-materias"   ).permitAll()
            .requestMatchers(HttpMethod.DELETE  ,"/alumnos/{id}"    ,"/docentes/{id}"       ,"/citas/{id}"      ,"/pagos/{id}"      ,"/eventos/{id}"    ,"/materias/{id}"   ,"/pagos/parametro-paga"        ).permitAll()
            .requestMatchers(HttpMethod.PUT     ,"/alumnos/{id}/{x}","/docentes/{id}/{x}"   ,"/citas/{id}/{x}"  ,"/pagos/{id}/{x}"  ,"/aulas/{id}/{x}"                      ,"/pagos/parametro-paga"        ).permitAll()

            .requestMatchers(HttpMethod.GET ,"/alumnos" ,"/alumnos/{id}"    ,"/alumnos/estado/{x}"      ,"/alumnos/aula/{id}"           ,"/alumnos/disponible/{x}"  ,"/alumnos/materias/{id}","/alumnos/materias/{x1}/{x2}", "/alumnos/grados/{id}", "/alumnos/materias-todo/{id}").permitAll()
            .requestMatchers(HttpMethod.GET ,"/aulas"   ,"/aulas/{id}"      ,"/aulas/estado/{x}"        ).permitAll()
            .requestMatchers(HttpMethod.GET ,"/citas"   ,"/citas/{id}"      ,"/citas/estado/{x}"        ,"/citas/estado/{x}/alumno/{id}","/citas/estado/{x}/docente/{id}").permitAll()
            .requestMatchers(HttpMethod.GET ,"/materias","/materias/{id}"   ,"/materias/areas"          ).permitAll()
            .requestMatchers(HttpMethod.GET ,"/grados"  ,"/grados/{id}"     ,"/grados/nivel/{x}"        ,"/grados/niveles"              ).permitAll()
            .requestMatchers(HttpMethod.GET ,"/eventos" ,"/eventos/{id}"    ,"/eventos/filtro/{año}"    ,"/eventos/filtro/{año}/{mes}"  ).permitAll()
            .requestMatchers(HttpMethod.GET ,"/pagos"   ,"/pagos/alumnos"   ,"/pagos/alumnos/{id}"      ,"/pagos/alumnos/{id}/{año}"    ,"/pagos/parametro-paga").permitAll()
            .requestMatchers(HttpMethod.GET ,"/docentes","/docentes/{id}"   ,"/docentes/estado/{x}"     ,"/docentes/horario/{id}"       ,"/docentes/aulas-asignadas/{id}"    ,"/docentes/alumnos-materias/{x1}/{x2}/{x3}", "/docentes/alumnos-correspondientes/{x}").permitAll()

            .requestMatchers(HttpMethod.GET ,"/datos"   ,"/datos/eventos"   ,"/datos/eventos/{x}"       ,"/datos/recaudacion"           ,"/datos/recaudacion/{x}"   ,"/datos/alumnos-estado",   "/datos/alumnos-estado/{x}" ,"/datos/docentes-estado","/datos/docentes-estado/{x}", "/datos/citas-estado/{x}", "/datos/aulas-estado/{x}",  "/datos/aulas-estado","/datos/citas").permitAll()
            .anyRequest().authenticated()//lo demas, requiere autenticacion
        )                      
        //Agregar filtros personalizados
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtValidationFilter(authenticationManager()))
        
        //CSRF, evita vulnerabilidad, hack
        .csrf(config -> config.disable()) //desabilitado
        //Agregar cors
        .cors(cors-> cors.configurationSource(corsConfigurationSource()))
        // Configurar gestión de sesiones como STATELESS
        .sessionManagement( m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))    //La sesion HTTP no tendra estado, la autenticacion se maneja con el token
        .build();
    }

     //Autenticador de token
    @Autowired
    private AuthenticationConfiguration ac;
    
    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return ac.getAuthenticationManager();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }


}
