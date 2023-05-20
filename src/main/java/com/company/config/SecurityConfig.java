package com.company.config;

import com.company.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;


    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    /*
     * · Spring always accepts encoded password
     * · antMatchers() can be based on a directory or controller
     *
     */

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//
//        List<UserDetails> userList = new ArrayList<>();
//
//        userList.add(new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        userList.add(new User("anna", encoder.encode("abc"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));
//        // saving the list of the users in the memory
//        return new InMemoryUserDetailsManager(userList); // saving the users in the memory not in DB
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests() // everything need to be authorized
//                .antMatchers("/user/**").hasRole("Admin")
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE")
                .antMatchers( // Means everyone can access below controller and directories, there is no restriction
                        "/",
                        "/login", // controller
                        "/fragments/**", // everything in the fragment directory
                        "/assets/**",
                        "/images/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
//                .httpBasic() // basic pop up
                .formLogin()
                   .loginPage("/login") // use this end point as a login page
//                   .defaultSuccessUrl("/welcome") // landing page
                     .successHandler(authSuccessHandler)
                   .failureUrl("/login?error=true")
                   .permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(120)
                    .key("company")
                    .userDetailsService(securityService)
                .and().build();


    }


}
