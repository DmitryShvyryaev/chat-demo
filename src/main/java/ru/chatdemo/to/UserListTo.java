package ru.chatdemo.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.chatdemo.model.User;

import java.util.List;

@Data
@AllArgsConstructor
public class UserListTo {
    private Long versionID;
    private List<User> users;

    @Override
    public String toString() {
        return "UserListTo{" +
                "versionID=" + versionID +
                ", users=" + users +
                '}';
    }
}
