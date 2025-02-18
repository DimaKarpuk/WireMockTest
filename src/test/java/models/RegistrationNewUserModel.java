package models;

import lombok.Data;

@Data
public class RegistrationNewUserModel {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
