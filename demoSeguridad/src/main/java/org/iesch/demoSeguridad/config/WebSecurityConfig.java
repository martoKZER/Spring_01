package org.iesch.demoSeguridad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.core.userdetails.User.withUsername;

@Configuration
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("PepeBotella")
                .password("{noop}" + "0000")
                .roles("USER")
                .build());
        manager.createUser(User
                .withUsername("admin")
                .password("{noop}" + "1234")
                .roles("USER","ADMIN")
                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf()
                .disable()
                .authorizeRequests()
                // Que los Admin sean los que pueden borrar
                .antMatchers(HttpMethod.DELETE)
                .hasRole("ADMIN")
                // Cuando algo sea algo de la URL admin, solo pueden verla y entrar los administradores
                .antMatchers("/admin/**")
                .hasAnyRole("ADMIN")
                // Cuando algo sea algo de la URL public, puede verla y entrar los que esten logueados
                .antMatchers("/public/**")
                .hasAnyRole("USER","ADMIN")
                // para loguearse, puede entrar cualquiera
                .antMatchers("/login/**")
                .anonymous()
                .anyRequest()
                .authenticated()
                .and().httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }
}
