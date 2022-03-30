package by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "messenger", name = "audit_users")
public class AuditUserEntity {

    private Long id;
    private UserEntity user;
    private UserEntity author;
    private LocalDateTime created;
    private String info;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "\"user\"", referencedColumnName = "login")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "login")
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
