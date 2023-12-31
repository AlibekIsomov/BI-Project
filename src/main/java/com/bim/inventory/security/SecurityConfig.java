package com.bim.inventory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    UserProvider userProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userProvider).passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();
        http
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

//                .antMatchers("/api/account/authenticate").permitAll()
//
//                .antMatchers("/api/category").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/category/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/category/search-name/{name}").hasAnyAuthority("ADMIN","MANAGER")
//
//                .antMatchers("/api/inputitem").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inputitem/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inputitem/find-by-date-range").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inputitem/search-name/{name}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inputitem/total-price").hasAnyAuthority("ADMIN","MANAGER")
//
//                .antMatchers("/api/outputitem").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/outputitem/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/outputitem/find-by-date-range").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/outputitem/search-name/{name}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/outputitem/total-price").hasAnyAuthority("ADMIN","MANAGER")
//
//                .antMatchers("/api/inventory").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inventory/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inventory/find-by-date-range").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/inventory/search-name/{name}").hasAnyAuthority("ADMIN","MANAGER")
//
//                .antMatchers("/api/worker").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/worker/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/worker/{workerId}/update-salary").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/worker/search-name/{name}").hasAnyAuthority("ADMIN","MANAGER")
//
//                .antMatchers("/api/fayl").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/fayl/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/fayl/download/{id}").hasAnyAuthority("ADMIN","MANAGER")
//                .antMatchers("/api/fayl/upload").hasAnyAuthority("ADMIN","MANAGER")
//
//
//                .antMatchers("/swagger-ui/**").permitAll()
//                .antMatchers("/swagger-ui.html").permitAll()
//
//                .antMatchers("/api/user").hasAnyAuthority("ADMIN")
//                .antMatchers("/api/user/{id}").hasAnyAuthority("ADMIN")
//                .antMatchers("/api/user/all").hasAnyAuthority("ADMIN")
//                .antMatchers("/api/user/get/{id}").hasAnyAuthority("ADMIN")
//                .antMatchers("/api/user/search/{key}").hasAnyAuthority("ADMIN")

                .antMatchers("/**").permitAll()

                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {


        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/h2-console/**")
                .antMatchers("/api/v1/auth/**")
//                .antMatchers("/v2/api-docs/**")
//                .antMatchers("/swagger-ui/**")
//                .antMatchers("/swagger-ui.html")
                .antMatchers("/test/**")
                .antMatchers("/*.*"); // #3
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://localhost:5500");
        config.addAllowedOrigin("http://localhost:5501");
        config.addAllowedOrigin("http://localhost:5502");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://192.168.43.144:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }

}

