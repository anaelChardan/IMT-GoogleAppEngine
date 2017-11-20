package com.zenika.zencontact.persistence.datastore;


import com.google.appengine.api.datastore.*;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserDao;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        Entity entity = fromUser(contact);

        if (null == entity) {
            return 0L;
        }

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
        Entity entity;
        Key k = KeyFactory.createKey(USER_KEY, id);
        try {
            entity = datastore.get(k);
        } catch (EntityNotFoundException ignored) {
            return null;
        }

        return fromEntity(entity);
    }

    @Override
    public List<User> getAll() {
        Query q = new Query(USER_KEY)
                .addProjection(new PropertyProjection(firstName, String.class))
                .addProjection(new PropertyProjection(lastName, String.class))
                .addProjection(new PropertyProjection(email, String.class))
                .addProjection(new PropertyProjection(notes, String.class));

        PreparedQuery pq = datastore.prepare(q);

        return StreamSupport.stream(pq.asIterable().spliterator(), false).map(this::fromEntity).collect(Collectors.toList());
    }

    private Entity fromUser(User user) {
        Entity entity = new Entity(USER_KEY);

        if (user.id != null) {
            Key k = KeyFactory.createKey(USER_KEY, user.id);
            try {
                entity = datastore.get(k);
            } catch (EntityNotFoundException ignored) {
                return null;
            }
        }

        entity.setProperty(firstName, user.firstName);
        entity.setProperty(lastName, user.lastName);
        entity.setProperty(email, user.email);

        if (null != user.birthdate) {
            entity.setProperty(birthdate, user.birthdate);
        }

        entity.setProperty(notes, user.notes);

        return entity;
    }

    private User fromEntity(Entity entity) {
        return User.create()
                .id(entity.getKey().getId())
                .firstName((String) entity.getProperty(firstName))
                .lastName((String) entity.getProperty(lastName))
                .email((String) entity.getProperty(email))
                .birthdate((Date) entity.getProperty(birthdate))
                .notes((String) entity.getProperty(notes));
    }
}
