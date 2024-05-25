package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

//bean loads security user list and overrides security default user.
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//
//        List<UserDetails> userList = new ArrayList<>();
//
//        //add multiple users
//        userList.add(new User( "mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        userList.add(new User( "ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));
//
//
//        return new InMemoryUserDetailsManager(userList);
//    }




    //configure application access based on user role
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests()
//                .antMatchers("/user/**").hasRole("Admin")
                .antMatchers("/user/**").hasAuthority("Admin") //hasRole caused a problem since it was not adding ROLE_
                .antMatchers("/project/**").hasRole("Manager")
                .antMatchers("/task/**").hasRole("Manager")
                .antMatchers("/task/employee/**").hasRole("Employee")
//                .antMatchers("/task/employee/**").hasAnyRole("Employee", "Admin")
//                .antMatchers("task/**").hasAuthority("ROLE_Employee")
                .antMatchers("/", //list path impacted by security rules
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**")
                .permitAll() // grant access permit to above http locations
                .anyRequest().authenticated()
                .and()
//                .httpBasic()//type of login screen
                .formLogin() // use application login screen instead of default login
                    .loginPage("/login")
                    .defaultSuccessUrl("/welcome")
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and().build();

    }
}
