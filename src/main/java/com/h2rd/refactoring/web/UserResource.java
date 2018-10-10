package com.h2rd.refactoring.web;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Repository

public class UserResource {

	@Autowired
	private UserDao userDao;

	/*
	 * API call for GET request
	 */
	@GET
	@Path("find/")
	public Response getUsers() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:/application-config.xml" });
		userDao = context.getBean(UserDao.class);
		List<User> users = userDao.getUsers();
		if (users == null) {
			users = new ArrayList<User>();
			return Response.status(Response.Status.NO_CONTENT).entity("NO USER ADDED YET").build();
		} else {
			GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(users) {
			};
			return Response.status(Response.Status.OK).entity(usersEntity).build();
		}

	}

	/*
	 * API call for GET request of a specific person by email
	 */

	@GET
	@Path("search/{email}")
	public Response findUserByEmail(@PathParam("email") String email) {

		if (userDao == null) {
			userDao = UserDao.getUserDao();
		}

		List<User> users = userDao.getUsers();
		if (users == null) {
			users = new ArrayList<User>();
			return Response.status(Response.Status.NO_CONTENT).entity("NO USER ADDED YET").build();
		} else {
			User user = userDao.findUserByEmail(email);

			if (user == null) {

				return Response.status(Response.Status.NOT_FOUND).entity("NO SUCH USER FOUND WITH THIS EMAIL :" + email)
						.type(MediaType.APPLICATION_XML).build();
			}

			else {

				return Response.ok().entity(user).build();
			}
		}
	}

	/*
	 * API call for POST request
	 */

	@POST
	@Path("add/")
	public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email,
			@QueryParam("role") List<String> roles) {

		if (roles.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("USER MUST HAVE ATLEAST ONE ROLE")
					.type(MediaType.APPLICATION_XML).build();
		}

		else {
			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setRoles(roles);

			if (userDao == null) {
				userDao = UserDao.getUserDao();

			}

			userDao.saveUser(user);
			return Response.status(Response.Status.CREATED).entity("NEW USER ADDED").type(MediaType.APPLICATION_XML)
					.build();
		}

	}
	/*
	 * API call for UPDATE data
	 */

	@PUT
	@Path("update/")
	public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email,
			@QueryParam("role") List<String> roles) {
		if (roles.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("USER MUST HAVE ATLEAST ONE ROLE")
					.type(MediaType.APPLICATION_XML).build();
		} else {
			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setRoles(roles);

			if (userDao == null) {
				userDao = UserDao.getUserDao();
			}

			userDao.updateUser(user);
			return Response.status(Response.Status.CREATED).entity(user).build();
		}
	}

	@DELETE
	@Path("delete/{email}")
	public Response deleteUser(@PathParam("email") String email) {
		if (userDao == null) {
			userDao = UserDao.getUserDao();
		}
		List<User> users = userDao.getUsers();
		if (users == null) {
			users = new ArrayList<User>();
			return Response.status(Response.Status.NO_CONTENT).entity("NO USER ADDED YET").build();
		} else {
			userDao.deleteUserByEmail(email);

			return Response.status(Response.Status.OK).entity("USER DELETED").build();
		}

	}

}
