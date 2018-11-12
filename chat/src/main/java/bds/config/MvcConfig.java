package bds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registrationpage");
        registry.addViewController("/chat").setViewName("chat");
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        driverManagerDataSource.setUrl("jdbc:h2:tcp://localhost:9092/~/ChatDatabase;USER=sa;PASSWORD=12345");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("12345");
        return driverManagerDataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    /*@Override
//    public UsersRecord registerNewUserAccount(RegistrationForm registrationForm)/* throws EmailExistsException */{
//        if (emailExist(accountDto.getEmail())) {
//            throw new EmailExistsException(
//                    "There is an account with that email adress:" + accountDto.getEmail());
//        }*/
//        UsersRecord usersRecord = new UsersRecord;
//        usersRecord.setLogin(registrationForm.getUserName());
//
//        usersRecord.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
//
//        user.setRole(new Role(Integer.valueOf(1), user));
//
//        return repository.save(user);
//    }
}


