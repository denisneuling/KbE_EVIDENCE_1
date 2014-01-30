package de.htw_berlin.aStudent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw_berlin.aStudent.dao.PUserDao;
import de.htw_berlin.aStudent.model.PUser;
import de.htw_berlin.aStudent.service.PUserService;

@Service
public class PUserServiceImpl implements PUserService{

	@Autowired
	private PUserDao pUserDao;
	
	@Override
	public PUser createUser(String userName, String city) {
		if(userName == null){
			throw new IllegalArgumentException("Username already taken");
		}
		
		PUser exists = pUserDao.findByName(userName);
		if(exists != null){
			throw new IllegalArgumentException("Username already taken");
		}
		
		PUser puser = new PUser();
		puser.setName(userName);
		puser.setCity(city);
		
		pUserDao.save(puser);
		
		return puser;
	}

	@Override
	public void deleteUser(String userName) {
		if(userName == null){
			throw new IllegalArgumentException("User does not exist");
		}
		
		PUser puser = pUserDao.findByName(userName);
		if(puser==null){
			throw new IllegalArgumentException("User does not exist");
		}
		pUserDao.delete(puser);
	}

	@Override
	public List<PUser> all() {
		return pUserDao.all();
	}

	@Override
	public PUser findByUsername(String userName) {
		return pUserDao.findByName(userName);
	}
}