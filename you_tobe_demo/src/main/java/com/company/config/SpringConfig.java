package com.company.config;

import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers("/auth", "/auth/**").permitAll()
                .antMatchers("/init", "/init/**").permitAll()
                .antMatchers("/channel/public", "/channel/public/*").permitAll()
                .antMatchers("/playlist/public", "/playlist/public/*").permitAll()
                .antMatchers("/attach/open", "/attach/download").permitAll()
                .antMatchers("/attach/upload").permitAll()
                .antMatchers("/attach/deleted", "/attach/pagination").hasAnyRole("USER")
                .antMatchers("/category/adm", "/category/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/playlist/adm", "/playlist/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/email/pagination").hasAnyRole("ADMIN")
                .antMatchers("/profile/adm", "/profile/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/tag/adm", "/tag/adm/*").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//                .httpBasic();
        http.csrf().disable();
        http.cors().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMd5(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };

    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
