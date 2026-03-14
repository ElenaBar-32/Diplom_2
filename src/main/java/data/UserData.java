package data;


import com.github.javafaker.Faker;

public class UserData {
    public static final String BASE_URI= "https://stellarburgers.education-services.ru/";
    static Faker user = new Faker();
    public static final String EMAIL = user.internet().emailAddress();
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName();
    public static final String CREATE_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String ERROR_LOGIN_ALREADY_USED = "User already exists" ; // пользователь существует 403
    public static final String ERROR_INSUFFICIENT_DATA = "Email, password and name are required fields";// нет 1 из полей  403
    public static final String ERROR_ACCOUNT_NOT_FOUND = "email or password are incorrect"; // 401 если логин или пароль не верные
}
