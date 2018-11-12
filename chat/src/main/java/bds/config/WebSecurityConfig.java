package bds.config;

import bds.RegistrationForm;
import bds.dao.UsersRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers("/chat").access("hasRole('ROLE_CLIENT')")
                    .antMatchers( "/", "/registration", "/registrationpage","/register","/test").permitAll()
                    .antMatchers("/resources/**","/css", "/css/**","/img", "/img/**").permitAll()
                    .antMatchers("/error").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/chat", true)
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();

        }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication().dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery(
                    "select TRIM(login) as username, password as password, 'true' as enabled "
                            + "from users where login = ?")
            .authoritiesByUsernameQuery("select TRIM(users.login) as username, UPPER(TRIM(roles.name)) as role from users, roles, userrole where users.login = ? and " +
                    "userrole.user_id = users.id and" +
                    " userrole.role_id=roles.id")
            .rolePrefix("ROLE_");
        }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }







//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user 1").password("111").roles("CLIENT");
//    }

    }


