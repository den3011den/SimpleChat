package bds.services;

// используется при попытке зарегистрировать нового пользователя с уже существующим в системе логином
public class LoginExistsException extends Throwable {
    public LoginExistsException(String s) {
    }
}
