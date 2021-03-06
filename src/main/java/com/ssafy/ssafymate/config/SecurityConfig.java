package com.ssafy.ssafymate.config;

import com.ssafy.ssafymate.JWT.JwtAccessDeniedHandler;
import com.ssafy.ssafymate.JWT.JwtAuthenticationEntryPoint;
import com.ssafy.ssafymate.JWT.JwtFilter;
import com.ssafy.ssafymate.JWT.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {

        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                    .httpBasic().disable() // httpBasic : ID, PW??? ????????? ?????????????????? ?????? (????????? ??????) vs. Bearer (?????? ??????)
                    .cors().configurationSource(corsConfigurationSource())
                .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session??? ???????????? ??????. STATELESS ????????? ??????.
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                    .formLogin().disable()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**").access("hasRole('USER')")
                    .antMatchers("/api/chats/rooms/**").access("hasRole('USER')")
                    .antMatchers("/api/chats/logs/**").access("hasRole('USER')")
                    .anyRequest().permitAll()    // ??? ??? ????????? ?????? ??? ??????
                .and()
                    .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class); // JwtFilter??? UsernamePasswordAuthenticationFilter ?????? ?????????

    }

    // h2 ????????? ??? ?????? ??????
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/h2-console/**", "/favicon.ico");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager??? Bean ?????????.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

    // CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://youthful-kirch-818851.netlify.app/", "https://ssafymate.site", "https://www.ssafymate.site", "https://i6a402.p.ssafy.io"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);

        return source;

    }

}
