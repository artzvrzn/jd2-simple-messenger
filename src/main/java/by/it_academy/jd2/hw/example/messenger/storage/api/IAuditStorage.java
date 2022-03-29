package by.it_academy.jd2.hw.example.messenger.storage.api;

import by.it_academy.jd2.hw.example.messenger.model.audit.AuditUser;

import java.util.List;

public interface IAuditStorage {

    void create(AuditUser user);

    List<AuditUser> read();

}
