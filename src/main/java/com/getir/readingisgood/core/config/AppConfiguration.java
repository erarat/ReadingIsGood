package com.getir.readingisgood.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfiguration extends WebSecurityConfigurerAdapter
{


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors().and().csrf().disable();
        http.authorizeRequests().antMatchers("/h2-console/**")
            .permitAll();
    }

    @Override
    public void configure(WebSecurity http) throws Exception
    {
        http.ignoring().antMatchers("/h2-console/**");
    }


    @Bean
    public SecurityFilter securityFilter()
    {
        return new SecurityFilter();
    }
}
