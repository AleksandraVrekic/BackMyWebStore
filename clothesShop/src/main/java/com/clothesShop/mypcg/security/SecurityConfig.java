package com.clothesShop.mypcg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.clothesShop.mypcg.auth.AuthenticationService;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        	.cors() // Omogućite CORS
        	.and()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
            .authorizeRequests()
                .antMatchers("/auth/login", "/auth/register", "/addresses/**","/customers/**", "/accounts/**", "/auth/logout").permitAll() // Otvoreni endpointi
                .antMatchers("/products/**").permitAll() // Allow everyone to view products
                .antMatchers(HttpMethod.POST, "/products/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only admins can add products
                .antMatchers(HttpMethod.PUT, "/products/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only admins can update products
                .antMatchers(HttpMethod.DELETE, "/products/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only admins can delete products
                .antMatchers("/categories/**").permitAll() // Allow everyone to view products
                .antMatchers(HttpMethod.POST, "/categories/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only admins can add products
                .antMatchers(HttpMethod.PUT, "/categories/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only admins can update products
                .antMatchers(HttpMethod.DELETE, "/categories/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only admins can delete products
                .antMatchers(HttpMethod.POST, "/orders/**").hasRole("CUSTOMER") // Only users can create orders
                .antMatchers(HttpMethod.POST, "/orders/payment-intent").hasRole("CUSTOMER")
               // .antMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole("ADMIN", "CUSTOMER") // Admins and users can view specific orders
                .antMatchers(HttpMethod.PUT, "/orders/**").permitAll() 
                .antMatchers(HttpMethod.GET, "/orders/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/orders/**").hasAnyRole("ADMIN", "SUPER_ADMIN") // Only users can delete their orders
                .antMatchers(HttpMethod.GET, "/orderItems/**").permitAll()
                .antMatchers(HttpMethod.POST, "/orderItems/**").hasRole("CUSTOMER") // Only users can create order items
                .antMatchers(HttpMethod.PUT, "/orderItems/**").hasRole("CUSTOMER") // Only users can update their order items
                .antMatchers(HttpMethod.DELETE, "/orderItems/**").hasRole("CUSTOMER") // Only users
                .antMatchers(HttpMethod.GET, "/admin/transactions").permitAll()
                .antMatchers("/auth/register/customer").permitAll()
                .antMatchers("/uploads/**").permitAll()
                .antMatchers("/auth/register/staff").hasRole("SUPER_ADMIN")
                .antMatchers(HttpMethod.GET, "/staff/**").hasAnyRole("SUPER_ADMIN", "ADMIN") // Svi zaposleni mogu da vide listu svih zaposlenih
                .antMatchers(HttpMethod.PUT, "/staff/**").hasRole("SUPER_ADMIN") // Samo SUPER_ADMIN može uređivati zaposlene
                .antMatchers(HttpMethod.DELETE, "/staff/**").hasRole("SUPER_ADMIN") // Samo SUPER_ADMIN može brisati zaposlene
                .antMatchers("/webhook").permitAll() 
                
                .anyRequest().authenticated() // Svi ostali zahtevi moraju biti autentifikovani
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


