package com.school.auth.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.auth.models.User;
import com.school.auth.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository ur;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> ou = ur.findByUsername(username);
        if(ou.isEmpty()){
            throw new UsernameNotFoundException(username+"no se encuentra en la BD");
        }
        User u = ou.orElseThrow();

        List<GrantedAuthority> authorities = u.getRoles()
            .stream()
            .map(rol->new SimpleGrantedAuthority(rol.getNombre()))
            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            u.getUsername().concat("-"+u.getId()),
            u.getPassword(),
            u.isActivo(),
            true,true,true,
            authorities
        );
    }
    
}
