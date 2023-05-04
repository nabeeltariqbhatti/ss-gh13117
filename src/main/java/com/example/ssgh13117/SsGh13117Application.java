package com.example.ssgh13117;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
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


//    @Autowired
//    private JavaMailSender mailSender;
//    @EventListener
//    public void loginEventListener(AuthenticationSuccessEvent authenticationSuccessEvent){
//        UserDetails principal = (UserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
//        System.out.println(principal.getUsername());
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("nabeeltaariq@gmail.com");
//        message.setSubject("hello");
//        message.setText("here");
//
//        mailSender.send(message);
//    }
//
//    @EventListener
//    public void onFailure(AbstractAuthenticationFailureEvent failures) {
//        System.out.println(failures);
//    }

