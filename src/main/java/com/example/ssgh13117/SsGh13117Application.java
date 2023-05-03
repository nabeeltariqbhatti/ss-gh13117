package com.example.ssgh13117;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SsGh13117Application {

    public static void main(String[] args) {
        SpringApplication.run(SsGh13117Application.class, args);
    }


    @Bean
    SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {

        return
                httpSecurity.authorizeHttpRequests(request ->
                                request.anyRequest().authenticated()
                        ).formLogin()
                        .and()
                        .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.invalidSessionUrl("/invalid")
                                                .sessionConcurrency(concurrencyControlConfigurer -> concurrencyControlConfigurer.maximumSessions(1).maxSessionsPreventsLogin(true))

                        ).build();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }


    @Bean
    UserDetailsService userDetailsService(){
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("bill")
                .roles("USER")
                .password("password")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

}

@RestController
class DemoController{
    @GetMapping("/demo")
    public String demo(){
        return "hello from demo";
    }

    @GetMapping("invalid")
    public  String invalid(){
        return "invalid";
    }
}
