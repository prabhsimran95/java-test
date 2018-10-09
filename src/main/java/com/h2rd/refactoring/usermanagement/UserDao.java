package com.h2rd.refactoring.usermanagement;

import java.util.ArrayList;

public class UserDao {

	public static ArrayList<User> users;

	public static UserDao userDao;

	public static UserDao getUserDao() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
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
		
		if(found == 0) {
			users.add(userToSave);
		}
	}

	public ArrayList<User> getUsers() {
		try {
			return users;
		} catch (Throwable e) {
			System.out.println("error");
			return null;
		}
	}

	public void deleteUser(User userToDelete) {
		try {
			for (User user : users) {
				if (user.getEmail().equals(userToDelete.getEmail())) {
					users.remove(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUser(User userToUpdate) {
		try {
			int index = 0;
			for (User user : users) {
				if (user.getEmail().equals(userToUpdate.getEmail())) {
					user.setName(userToUpdate.getName());
					user.setRoles(userToUpdate.getRoles());
					users.set(index, user);
				}
				index++;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public User findUser(String email) {
		try {
			for (User user : users) {
				if (user.getEmail().equals(email)) {
					return user;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}
}
