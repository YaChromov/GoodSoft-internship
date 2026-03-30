package org.example.t5sr.service;

import org.example.t5sr.dao.UserRepository;
import org.example.t5sr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userMapper) {
        this.userRepository = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User domainUser = userRepository.findUserByLogin(login);

        if (domainUser == null) {
            throw new UsernameNotFoundException("Пользователь с логином " + login + " не найден");
        }

        var authorities = domainUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                domainUser.getLogin(),
                domainUser.getPassword(),
                authorities
        );
    }
}