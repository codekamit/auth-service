package com.ecommerce.security.services;

import com.ecommerce.security.exceptions.UserNotFoundException;
import com.ecommerce.security.models.SecurityUser;
import com.ecommerce.security.models.User;
import com.ecommerce.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No such user exists"));
        return new SecurityUser(user);
    }
}
