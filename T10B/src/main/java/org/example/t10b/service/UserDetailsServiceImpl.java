package org.example.t10b.service;

import org.example.t10b.entity.User;
import org.example.t10b.repository.UserRepository;
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
        User domainUser = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found"));
        var authorities = domainUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                domainUser.getLogin(),
                domainUser.getPassword(),
                authorities
        );
    }
}