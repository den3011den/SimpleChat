package bds.services;

import bds.dao.UsersRecord;
import bds.model.RegistrationForm;

// интерфейс сервиса работы с таблицами БД
public interface IUserService {
    UsersRecord registerNewUserAccount(RegistrationForm registrationForm) throws LoginExistsException;
}
