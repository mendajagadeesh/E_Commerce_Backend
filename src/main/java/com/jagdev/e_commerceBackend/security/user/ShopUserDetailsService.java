package com.jagdev.e_commerceBackend.security.user;

import com.jagdev.e_commerceBackend.model.User;
import com.jagdev.e_commerceBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(
                ()->new UsernameNotFoundException("user not found"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
