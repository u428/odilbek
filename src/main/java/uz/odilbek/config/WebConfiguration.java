package uz.odilbek.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uz.odilbek.security.AuthorizationFilter;
import uz.odilbek.service.interf.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userServices;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebConfiguration(UserService userServices, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userServices = userServices;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        I resolved this issue by configuring the HSTS header as follows:
        http.headers().httpStrictTransportSecurity()
                .maxAgeInSeconds(0)
                .includeSubDomains(true);
        http
                .csrf().disable()

                .authorizeRequests()

                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers( "/auth")
                .permitAll()
                .antMatchers( "/auth/**")
                .permitAll()
                .antMatchers( "/auth/*")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/a23d_m23_i23n/add_image")
                .permitAll()
//               .antMatchers( "/users/**").hasRole(ApplicationUserRole.USER.name())

                .anyRequest()
                .authenticated()
                .and()

//                .cors()
//                .and()

                .cors().disable()

//                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServices).passwordEncoder(bCryptPasswordEncoder);
    }

//    public AuthenticationFilter getAuthenticationFilter() throws Exception{
//        final AuthenticationFilter filter=new AuthenticationFilter(authenticationManager(), authService);
//        filter.setFilterProcessesUrl("/auth/login");
////        filter.setPostOnly(true);
//        return filter;
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*")); // <-- you may change "*"
//        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Arrays.asList(
//                "Accept", "Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
//                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
