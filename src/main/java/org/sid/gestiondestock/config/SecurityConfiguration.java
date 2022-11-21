package org.sid.gestiondestock.config;

import org.sid.gestiondestock.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private ApplicationRequestFilter applicationRequestFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {

        // swagger
        web.ignoring().antMatchers(
                "/v2/api-docs",  "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger-ui/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(applicationUserDetailsService)
        .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().antMatchers("/**/authenticate",
                "/**/entreprises/create",
                "/swagger-resources/**").permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
