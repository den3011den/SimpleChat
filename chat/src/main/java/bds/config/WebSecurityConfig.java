package bds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                    .antMatchers("/chat").access("hasRole('CLIENT')")
                    .antMatchers( "/", "/registration").permitAll()
                    .antMatchers("/resources/**","/css", "/css/**","/img", "/img/**").permitAll()
                    .antMatchers("/error").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();
        }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .jdbcAuthentication().dataSource(dataSource)
/*                .usersByUsernameQuery(
                        "select login as username, password, 'true' as enabled from users where login = ?")
                .authoritiesByUsernameQuery(
                    "select login as username, password, 'true' as enabled from users where login = ?");*/
            .usersByUsernameQuery(
                    "select login as principal, password as credentials, true from users where login = ?")
            .authoritiesByUsernameQuery("select users.login as principal, roles.name as role from users, roles, userrole where users.id=userrole.user_id and" +
                    " userrole.role_id=roles.id and users.login = ?");
                /*.rolePrefix("ROLE_");*/
        System.out.println("");

        }

    }


