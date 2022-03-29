package by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "messenger", name = "messages")
public class MessageEntity {

    private Long id;
    private String from;
    private String to;
    private LocalDateTime sendDate;
    private String text;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "\"from\"")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(name = "\"to\"")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(name = "send_date")
    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    @Column(columnDefinition = "varchar")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
