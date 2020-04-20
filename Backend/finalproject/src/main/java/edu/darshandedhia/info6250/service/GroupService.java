package edu.darshandedhia.info6250.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.dao.GroupDao;
import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.response.Response;

public class GroupService {
	
	@Autowired
	@Qualifier(value="groupDao")
	private GroupDao groupDao;
	
	public ResponseEntity<Object> addUpdateGroup(Group group){
		if (group.getUserList().size() <= 0)
			return new ResponseEntity<Object>(new Response(StatusCode.inappropriateInputFormat, Status.failure, Message.noUserInGroup), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(groupDao.addUpdateGroup(group), HttpStatus.OK);
	}
}
