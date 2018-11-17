package bds.services;

import bds.dao.UsersRecord;
import bds.model.RegistrationForm;

public interface IUserService {
    UsersRecord registerNewUserAccount(RegistrationForm registrationForm) throws LoginExistsException;
}
