package com.codeactuator.rocket.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/swagger/*")
                .authorizeRequests()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.EMPLOYEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails userShekhar = User.builder()
                .username("shekhar")
                .password(passwordEncoder.encode("password"))
                .roles("EMPLOYEE")
                .build();

        UserDetails userAnjali = User.builder()
                .username("anjali")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(
                    userShekhar,
                    userAnjali
        );

    }
}
