package bds.config;

import bds.model.MyAuthenticationSuccessHandler;
import bds.model.MyLogoutSuccessHandler;
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



     // Задаются права на доступ к контекстам
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/chat", "/sendmessage", "/getlatestmessages").access("hasRole('ROLE_CLIENT')")
                .antMatchers("/", "/registration", "/registrationpage", "/register", "/error").permitAll()
                .antMatchers("/resources/**", "/css", "/css/**", "/img", "/img/**").permitAll()
                .antMatchers("/error").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/chat", true)
                .successHandler(myAuthenticationSuccessHandler)
                .and()
                .logout().logoutSuccessUrl("/home")
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);
    }

    // БД
    @Autowired
    private DataSource dataSource;

    // для совершения нужных действий при логине пользователя
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    // для совершения нужных нам действий при разлогинивании пользователя
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;


    // поиск пользователя в БД с его ролью и расшифровкой пароля
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

    // для шифрования / расшифровки пароля пользователя
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    // вход пользователя в систему
    @Bean
    public MyAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        MyAuthenticationSuccessHandler myHandler = new MyAuthenticationSuccessHandler();
        return myHandler;
    }

    ;

    // разлогинивание пользователя в системе
    @Bean
    public MyLogoutSuccessHandler myLogoutSuccessHandler() {
        MyLogoutSuccessHandler myHandler = new MyLogoutSuccessHandler();
        return myHandler;
    }

    ;

}


