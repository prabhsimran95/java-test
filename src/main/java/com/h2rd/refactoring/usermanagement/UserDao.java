package com.h2rd.refactoring.usermanagement;

import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

	public static ArrayList<User> users;

	public static UserDao userDao;

	public static UserDao getUserDao() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}

	public ArrayList<User> getUsers() {
		try {
			return users;
		} catch (Throwable e) {
			System.out.println("error");
			return null;
		}
	}

	public User findUserByEmail(String email) {

		User userfound = null;
		int i = 0;
		if (users.isEmpty() == false) {
			boolean found = false;
			for (User user : users) {
				if (user.getEmail().equals(email)) {
					found = true;
					userfound = users.get(i);
				}
				i++;
			}
			if (found == true) {
				return userfound;
			} else
				return null;
		} else {
			return userfound;
		}

	}

	public void saveUser(User userToSave) {
		if (null == users) {
			users = new ArrayList<User>();
		}

		int found = 0;

		for (User user : users) {
			if (user.getEmail().equals(userToSave.getEmail())) {
				found++;
			}
		}

		if (found == 0) {
			users.add(userToSave);
		}
	}

	public void deleteUserByEmail(String Email) {
		try {
			for (User user : users) {
				if (user.getEmail().equals(Email)) {
					users.remove(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   /*
	 Based on the user requirement email is our unique identifier so we will
	 compare email first before updating the
	 name and role
   */
	public void updateUser(User userToUpdate) {
		try {
			int index = 0;
			for (User user : users) {
				if (user.getEmail().equals(userToUpdate.getEmail())) {
					user.setName(userToUpdate.getName());
					user.setRoles(userToUpdate.getRoles());
					users.set(index, user);
				} else {

				}
				index++;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

}
