package com.epam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.dao.UserDao;
import com.epam.domain.Ticket;
import com.epam.domain.User;
import com.epam.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    UserDao userDao;
	
	
	public User register(String name, String email, DateTime birthday) {
		return userDao.create(name,email,birthday);
	}

	public void remove(Long id) {
		userDao.remove(id);
		
	}

	public User getById(Long id) {
		return userDao.findById(id);
	}

	public User getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public List<User> getUsersByName(String name) {
		return userDao.findByName(name);
	}

	public Map<Long,Ticket> getBookedTickets(Long id) {
		List<Ticket> tickets = this.getById(id).getTickets();
		
		Map<Long, Ticket> result = new HashMap<Long, Ticket>();		
		for (Ticket ticket : tickets) {
			result.put(ticket.getSeat(), ticket);
		}		
		return result;
	}

}
