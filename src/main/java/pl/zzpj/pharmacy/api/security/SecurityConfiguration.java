package pl.zzpj.pharmacy.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoderImpl passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final Jwt jwt;
    private final String password;

    @Autowired
    public SecurityConfiguration(@Value("${application.admin.password}") String password,
                                 PasswordEncoderImpl passwordEncoder,
                                 @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                 Jwt jwt) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwt = jwt;
        this.password = password;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
                .and()
                .inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN");
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwt, this.userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/console/**").permitAll() //bez tego nie dziala consola h2
                .antMatchers("/employees/login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/medicines").permitAll()
                .antMatchers(HttpMethod.GET, "/medicines/**").permitAll()
                .antMatchers("/medicines/**").authenticated()
                .and()
                .httpBasic()
        .and().headers().frameOptions().disable() //bez tego nie dziala consola h2
        .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
