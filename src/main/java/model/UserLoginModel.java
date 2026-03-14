package model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginModel {
    private String email;
    private String password;
    public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

