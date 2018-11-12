package bds.services;

import bds.IUserService;
import bds.RegistrationForm;
import bds.dao.UserRoleRecord;
import bds.dao.UsersRecord;
import bds.dao.repo.UserRoleRepository;
import bds.dao.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public UsersRecord registerNewUserAccount(RegistrationForm registrationForm)
            /*throws LoginExistsException*/
    {

    /*    if(loginExist(accountDto.getLogin())){
            throw new LoginExistsException(
                    "There is an account with that login:" + accountDto.getLogin());
        }
    */
        UsersRecord usersRecord = new UsersRecord();

        usersRecord.setLogin(registrationForm.getUserName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usersRecord.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

        UsersRecord savedRecord = usersRepository.save(usersRecord);
        UserRoleRecord userRoleRecord = new UserRoleRecord();
        userRoleRecord.setUserId(savedRecord.getId());
        userRoleRecord.setRoleId(1);

        userRoleRepository.save(userRoleRecord);

        return savedRecord;
    }



//    private boolean loginExist(String login){
//        UserEntity userEntity = userRepo.getByLogin(login);
//        if(userEntity != null) {
//            return true;
//        }
//        return false;
//    }
}
