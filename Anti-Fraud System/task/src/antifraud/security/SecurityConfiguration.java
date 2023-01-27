package antifraud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();
    final
    UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .antMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers(HttpMethod.POST, "/**/api/antifraud/transaction").hasRole("MERCHANT")// needs to run test
                .antMatchers(HttpMethod.GET, "/**/api/auth/list").hasAnyRole("ADMINISTRATOR", "SUPPORT")
                .antMatchers(HttpMethod.PUT,"/**/api/auth/access").hasRole("ADMINISTRATOR")
                .antMatchers( "/**/api/auth/user/**").hasRole("ADMINISTRATOR")
                .antMatchers("/**/api/auth/role/**").hasRole("ADMINISTRATOR")
                .antMatchers("/**/api/antifraud/suspicious-ip/**").hasRole("SUPPORT")
                .antMatchers("/**/api/antifraud/stolencard/**").hasRole("SUPPORT")
                // other matchers
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
