package com.company.config;

import com.company.entity.ProfileEntity;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> entity = profileRepository.findByEmail(username);
        if (entity.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new CustomUserDetails(entity.get());
    }
}
