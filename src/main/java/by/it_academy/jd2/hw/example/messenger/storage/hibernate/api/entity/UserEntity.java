package by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(schema = "messenger", name = "users")
public class UserEntity {

    private String login;
    private String password;
    private String fio;
    private LocalDate birthday;
    private LocalDateTime registration;

    @Id
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Column(name = "dt_reg")
    public LocalDateTime getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return login;
    }

}
