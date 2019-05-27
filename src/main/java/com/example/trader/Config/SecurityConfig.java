package com.example.trader.Config;

import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.Impl.TraderSideUserServiceImpl;
import com.example.trader.Service.TraderSideUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TraderSideUserService traderSideUserService;

    @Bean
    UserDetailsService customUserDetailService(){
        return (name) -> traderSideUserService.findByUsername(name);
    }

    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService());
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/v1/**").permitAll()
                .and()
                .formLogin().loginPage("/page/login")
                .successHandler((HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse,
                                 Authentication authentication ) -> {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        String username = authentication.getName();
                        logger.info("[SpringSecurity.login.success] " + username);
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(ResponseWrapperFactory.createResponseString(ResponseWrapper.SUCCESS, traderSideUserService.findByUsername(username)));
                        out.flush();
                        out.close();
                })
                .failureHandler((HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                AuthenticationException e) -> {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        logger.info("[SpringSecurity.login.failure] "
                                + httpServletRequest.getParameter("username") + " "
                                + httpServletRequest.getParameter("password"));
                        logger.info(e.getMessage());
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(ResponseWrapperFactory.createResponseString(ResponseWrapper.ERROR, e.getMessage()));
                        out.flush();
                        out.close();
                })
                .loginProcessingUrl("/api/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and()
                .logout().logoutUrl("/api/logout")
                .logoutSuccessHandler((HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse,
                                       Authentication authentication) ->{
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        String username = authentication.getName();
                        logger.debug("[SpringSecurity.logout.success]: " + username);

                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(ResponseWrapperFactory.createResponseString(ResponseWrapper.SUCCESS, "Logout Success"));
                        out.flush();
                        out.close();
                })
                .permitAll()
                .and().csrf().disable().cors()
                .and()
                .httpBasic();
    }
}
