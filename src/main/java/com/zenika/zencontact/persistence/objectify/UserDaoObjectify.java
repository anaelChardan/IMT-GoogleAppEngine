package com.zenika.zencontact.persistence.objectify;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserDao;

import java.util.List;

public class UserDaoObjectify implements UserDao {
    private static UserDaoObjectify ourInstance = new UserDaoObjectify();

    public static UserDaoObjectify getInstance() {
        ObjectifyService.factory().register(User.class);

        return ourInstance;
    }

    private UserDaoObjectify() {
    }

    @Override
    public long save(User contact) {
        return ObjectifyService.ofy().save().entity(contact).now().getId();
    }

    @Override
    public void delete(Long id) {
        ObjectifyService.ofy().delete().key(Key.create(User.class, id)).now();
    }

    @Override
    public User get(Long id) {
        return ObjectifyService.ofy().load().key(Key.create(User.class, id)).now();
    }

    @Override
    public List<User> getAll() {
        return ObjectifyService.ofy().load().type(User.class).list();
    }
}
