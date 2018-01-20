package com.epam.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.epam.dao.UserDao;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.epam.domain.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	private Map<Long,User> users = new ConcurrentHashMap<Long,User>();
	private AtomicLong index = new AtomicLong(0);
	
	public User create(String name, String email, DateTime birthday){
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setBirthday(birthday);
		user.setId(index.getAndIncrement());
		
		users.put(user.getId(), user);
		return user;
	}
	
	public void remove(Long id){
		users.remove(id);
	}

	public User findById(Long id) {
		return users.get(id);
	}

	public User findByEmail(String email) {
		for (User user : users.values()) {
			if (email.equals(user.getEmail())){
				return user;
			}
		}
		return null;
	}

	public List<User> findByName(String name) {
		List<User> result = new ArrayList<User>();
		for (User user : users.values()) {
			if (name.equals(user.getName())){
				result.add(user);
			}
		}
		return result;
	}
	
	

}
