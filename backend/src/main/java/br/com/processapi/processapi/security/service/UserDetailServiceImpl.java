package br.com.processapi.processapi.security.service;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.domain.user.UserType;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), true, true,
                true, true, mapToGrantedAuthorities(user.getUserType())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserType userType) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userType.toString()));
        return authorities;
    }

}
