package com.zenika.zencontact.persistence;

import java.util.List;

import com.zenika.zencontact.domain.User;

public interface UserDao {
	long save(User contact);
	void delete(Long id);
	User get(Long id);
	List<User> getAll();
}
