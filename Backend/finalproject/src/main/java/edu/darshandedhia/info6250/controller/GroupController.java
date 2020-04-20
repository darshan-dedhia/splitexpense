package edu.darshandedhia.info6250.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {
	@Autowired
	@Qualifier(value="groupService")
	private GroupService groupService;
	
	@RequestMapping(value = "/addUpdate", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> addUpdateGroup(@RequestBody Group group){
		return groupService.addUpdateGroup(group);
	}
	
}
