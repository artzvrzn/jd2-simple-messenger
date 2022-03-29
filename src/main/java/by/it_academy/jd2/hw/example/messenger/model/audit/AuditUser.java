package by.it_academy.jd2.hw.example.messenger.model.audit;

import by.it_academy.jd2.hw.example.messenger.model.User;

import java.time.LocalDateTime;

public class AuditUser {

    private Long id;
    private User user;
    private User author;
    private LocalDateTime created;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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

    @Override
    public String toString() {
        return "AuditUser{" +
                "id=" + id +
                ", user=" + (user != null ? user.getLogin() : null) +
                ", author=" + (author != null ? author.getLogin() : null) +
                ", dt_create=" + created +
                ", info='" + info + '\'' +
                '}';
    }
}
