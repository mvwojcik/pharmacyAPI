package pl.zzpj.pharmacy.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String password;
    private final PasswordEncoderImpl passwordEncoder;

    @Autowired
    public SecurityConfiguration(@Value("${application.admin.password}") String password, PasswordEncoderImpl passwordEncoder) {
        this.password = password;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(this.password).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()//TODO DO ZMIANY
                .antMatchers("/console/**").permitAll() //bez tego nie dziala consola h2
                .and()
                .httpBasic()
        .and().headers().frameOptions().disable(); //bez tego nie dziala consola h2
    }
}
