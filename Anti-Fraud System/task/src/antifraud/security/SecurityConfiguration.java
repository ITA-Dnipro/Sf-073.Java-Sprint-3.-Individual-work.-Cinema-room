package antifraud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    final
    UserDetailsService userDetailsService;
    RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers(HttpMethod.POST, "/**/api/antifraud/transaction").hasRole("MERCHANT")// needs to run test
                .antMatchers(HttpMethod.GET, "/**/api/auth/list").hasAnyRole("ADMINISTRATOR", "SUPPORT")
                .antMatchers(HttpMethod.PUT, "/**/api/auth/access").hasRole("ADMINISTRATOR")
                .antMatchers("/**/api/auth/user/**").hasRole("ADMINISTRATOR")
                .antMatchers("/**/api/auth/role/**").hasRole("ADMINISTRATOR")
                .antMatchers("/**/api/antifraud/suspicious-ip/**").hasRole("SUPPORT")
                .antMatchers("/**/api/antifraud/stolencard/**").hasRole("SUPPORT")
                .antMatchers("/**/api/antifraud/history/**").hasRole("SUPPORT")
                .antMatchers("/**/api/antifraud/transaction/**").hasRole("SUPPORT")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
