package bds;

import bds.dao.UsersRecord;
import bds.services.LoginExistsException;

public interface IUserService {
        UsersRecord registerNewUserAccount(RegistrationForm registrationForm) throws LoginExistsException;
}
