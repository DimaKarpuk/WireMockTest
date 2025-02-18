package models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseRegistrationNewUserModel {
    private String userID;
    private String username;
    private List<String> books = new ArrayList<>();
}
