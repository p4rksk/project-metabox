package org.example.metabox.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SessionGuest {

    private Integer id;

    private String birth;
    private String phone;

    public SessionGuest(Integer id, String birth, String phone) {
        this.id = id;
        this.birth = birth;
        this.phone = phone;
    }
}