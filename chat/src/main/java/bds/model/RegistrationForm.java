package bds.model;


// класс для встраивания объекта класса в форму регистрации /resourses/templates/registrationpage.html
// с помощью thymeleaf для передачи данных от сервера клиету и назад при регистрации в системе нового пользователя
public class RegistrationForm {

    private String userName;
    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
