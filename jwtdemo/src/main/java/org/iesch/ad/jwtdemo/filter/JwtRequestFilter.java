package org.iesch.ad.jwtdemo.filter;

import org.iesch.ad.jwtdemo.services.JwtService;
import org.iesch.ad.jwtdemo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtService.extractUsername(jwt);

        }
        if (authorizationHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Rellenar la clase con el token
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // sirve para la pr??ctica
            }
        }

        // Indicarle que ser?? uin filtro y pasarle petici??n y respuesta
        filterChain.doFilter(request, response);
    }
}
