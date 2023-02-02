package com.example.antifraud.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN =  "ADMINISTRATOR";
    private static final String SUPPORT =  "SUPPORT";
    private static final String MERCHANT =  "MERCHANT";
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    @Lazy
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic(c -> c.authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(
                                HttpStatus.UNAUTHORIZED.value(), authException.getMessage())))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(c -> c
                        .frameOptions().disable())
                .authorizeRequests(a -> a
                        //GENERAL AND ADMIN ENDPOINTS
                        .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(ADMIN,SUPPORT)
                        .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/*").hasRole(ADMIN)
                        .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasRole(MERCHANT)
                        .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                        .mvcMatchers(HttpMethod.PUT, "/api/auth/role").hasRole(ADMIN)
                        //IP ENDPOINTS
                        .mvcMatchers(HttpMethod.POST, "/api/antifraud/suspicious-ip").hasAnyRole(SUPPORT)
                        .mvcMatchers(HttpMethod.GET, "/api/antifraud/suspicious-ip").hasAnyRole(SUPPORT)
                        .mvcMatchers(HttpMethod.DELETE, "/api/antifraud/suspicious-ip/*").hasAnyRole(SUPPORT)
                        //CREDIT CARD ENDPOINTS
                        .mvcMatchers(HttpMethod.POST, "/api/antifraud/stolencard").hasAnyRole(SUPPORT)
                        .mvcMatchers(HttpMethod.GET, "/api/antifraud/stolencard").hasAnyRole(SUPPORT)
                        .mvcMatchers(HttpMethod.DELETE, "/api/antifraud/stolencard/*").hasAnyRole(SUPPORT)

                        .mvcMatchers(HttpMethod.PUT, "/api/auth/access").hasAnyRole(ADMIN)
                        .mvcMatchers("/actuator/shutdown").permitAll()

                        .anyRequest().denyAll()
                )
                .sessionManagement(a -> a.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }
}