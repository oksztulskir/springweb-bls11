package com.sda.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String USERS_QUERY = "select login, password, enabled from user where login = ?";
    private static final String ROLES_QUERY =
            "select u.login as login, r.name as name from user u " +
            "   join user_to_role as ur on u.id = ur.user_id " +
            "       join role r on ur.role_id = r.id " +
            "           where u.login = ?";

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    @Bean
//    public FilterRegistrationBean<AuthorizationFilter> registerAuthFilter() {
//        FilterRegistrationBean<AuthorizationFilter> filterRegistrationBean
//                = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new AuthorizationFilter());
//        filterRegistrationBean.addUrlPatterns("/users/*", "/rest/*");
//
//        return filterRegistrationBean;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/users", "/").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/users/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/rest/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
        .and()
        .logout()
//                .formLogin()
//                .and()
//                .httpBasic()
        ;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .rolePrefix("ROLE_")
//                .dataSource(dataSource)
//                .usersByUsernameQuery(USERS_QUERY)
//                .authoritiesByUsernameQuery(ROLES_QUERY);
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("admin")
//                .password(passwordEncoder().encode("password"))
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
