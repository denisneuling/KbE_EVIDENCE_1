package de.htw_berlin.aStudent.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.htw_berlin.aStudent.model.PUser;

@Transactional
public interface PUserService {

	public PUser createUser(String userName, String city);

	public void deleteUser(String userName);

	public PUser findByUsername(String userName);

	public List<PUser> all();
}