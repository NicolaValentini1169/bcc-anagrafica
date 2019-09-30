package eu.winwinit.bcc.config;

import eu.winwinit.bcc.security.JWTAuthenticationEntryPoint;
import eu.winwinit.bcc.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JWTAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Autenticazione e autorizzazione con JWTs
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                    .antMatchers(
                            "/api/v1/login",
                            "/api/v1/checkToken",
                            "/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/swagger-resources/**",
                            "/configuration/**"
                    ).permitAll()
                    .anyRequest().fullyAuthenticated()
                    .and().logout().permitAll();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                    .withUser("user").password("{noop}password").roles("USER")
                .and()
                    .withUser("manager").password("{noop}password").authorities("WRITE_PRIVILEGES", "READ_PRIVILEGES").roles("MANAGER");
        //TODO: Autenticazione da DB

        //                ldapAuthentication().contextSource(contextSource()).userSearchFilter("sAMAccountName={0}").ldapAuthoritiesPopulator(customLdapAuthoritiesPopulator);
    }

}
