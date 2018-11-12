package bds;

import bds.dao.UsersRecord;

public interface IUserService {
        UsersRecord registerNewUserAccount(RegistrationForm registrationForm)
               /* throws LoginExistsException  */;
}
