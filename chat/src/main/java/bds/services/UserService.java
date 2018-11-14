package bds.services;

import bds.IUserService;
import bds.RegistrationForm;
import bds.dao.UserRoleRecord;
import bds.dao.UsersRecord;
import bds.dao.repo.UserRoleRepository;
import bds.dao.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    static private List<UsersRecord> allUsers;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public UsersRecord registerNewUserAccount(RegistrationForm registrationForm) throws LoginExistsException {
        if(loginExists(registrationForm.getUserName())) {
           throw new LoginExistsException("Уже есть логин: " + registrationForm.getUserName());
        }

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

    public boolean loginExists(String login) {

        allUsers = (List<UsersRecord>) usersRepository.findAll();

        if (allUsers.size() == 0) {
            return false;
        }

        boolean loginContains = allUsers.contains(new UsersRecord(login));
        return loginContains;
    }

}
