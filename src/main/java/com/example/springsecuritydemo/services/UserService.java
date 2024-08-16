package com.example.springsecuritydemo.services;

import com.example.springsecuritydemo.entities.Authority;
import com.example.springsecuritydemo.entities.Role;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is not found by username" + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapAuthoritiesTogether(user.getRoles(), user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> mapAuthoritiesTogether(Collection<Role> roles, Collection<Authority> authorities) {
        return Stream.concat(
                        mapRolesToAuthorities(roles).stream(), // Поток из roles
                        authorities.stream() // Поток из authorities
                ).map(authority -> (GrantedAuthority) authority) // Преобразуем в GrantedAuthority
                .collect(Collectors.toList()); // Собираем в List
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public boolean updateUserEmail(String email, Long id){
        try{
            userRepository.updateUserEmail(email, id);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public boolean deleteUser(Long id){
        try{
            userRepository.deleteUser(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
