package com.zenika.zencontact.persistence.datastore;


import com.google.appengine.api.datastore.*;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserDao;

import java.util.List;

public class UserDaoDataStore implements UserDao {
    private static UserDaoDataStore INSTANCE = new UserDaoDataStore();
    private final String USER_KEY = "User";
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String email = "email";
    private final String birthdate = "birthdate";
    private final String notes = "notes";

    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static UserDaoDataStore getInstance() {
        return INSTANCE;
    }

    @Override
    public long save(User contact) {
        Entity entity = new Entity(USER_KEY);
        if (contact.id != null) {
            Key k = KeyFactory.createKey(USER_KEY, contact.id);
            try {
                entity = datastore.get(k);
            } catch (EntityNotFoundException ignored) {
                return 0L;
            }
        }

        entity.setProperty(firstName, contact.firstName);
        entity.setProperty(lastName, contact.lastName);
        entity.setProperty(email, contact.email);

        if (null != contact.birthdate) {
            entity.setProperty(birthdate, contact.birthdate);
        }

        entity.setProperty(notes, contact.notes);
        Key key = datastore.put(entity);

        return key.getId();
    }

    @Override
    public void delete(Long id) {
        Key key = KeyFactory.createKey(USER_KEY, id);
        datastore.delete(key);
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
