package com.raveleen.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Святослав on 31.12.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getShaPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/image/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/profile-image/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/post-create").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/post-delete/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/like/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/unlike/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/number-of-likes/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/post/likes-number/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/post/comments-number/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/get-posts/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/comment-create/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/get-comments/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/comment-delete/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/post-likes/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/followers/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/following/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/user-list/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/dialogs/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/dialog/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/messages-get-unread/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/calendar/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/message-to/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/message-create/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/messages/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/settings/validation").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/settings").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/search").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/calendar/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/get-following-posts/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/calendar/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/calendar/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/news").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("j_login")
                .passwordParameter("j_password")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);
    }

    private ShaPasswordEncoder getShaPasswordEncoder() {
        return new ShaPasswordEncoder();
    }
}
