package com.example.demo.config;

import com.example.demo.jwt.JwiFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwiFilter jwiFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    //Phải config sessionManagement này mới tránh lỗi lưu 1 cái authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/welcome","/authentication").permitAll()
                 .antMatchers("/admin").hasAuthority("Admin")
                 .and().sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

         http.addFilterBefore(jwiFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
