package com.qorakol.ilm.ziyo.config;

import com.qorakol.ilm.ziyo.constant.SecurityConstants;
import com.qorakol.ilm.ziyo.security.AuthenticationFilter;
import com.qorakol.ilm.ziyo.security.AuthorizationFilter;
import com.qorakol.ilm.ziyo.service.interfaces.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class webConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    //    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public webConfig(AuthService authService) {
        this.authService = authService;
    }




    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("src/main/resources/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()

                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll()
                .antMatchers(HttpMethod.GET, "/auth/checkTelNomer")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/auth/image")
                .permitAll()
//               .antMatchers( "/users/**").hasRole(ApplicationUserRole.USER.name())

                .anyRequest()
                .authenticated()
                .and()

                .cors()
                .and()

                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter=new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/auth/login");
//        filter.setPostOnly(true);
        return filter;
    }

}
