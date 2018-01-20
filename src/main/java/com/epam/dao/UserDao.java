package com.epam.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.epam.domain.User;

public interface UserDao {
	
	public User create(String name, String email, DateTime birthday);
	
	public void remove(Long id);

	public User findById(Long id);

	public User findByEmail(String email);

	public List<User> findByName(String name);
}
