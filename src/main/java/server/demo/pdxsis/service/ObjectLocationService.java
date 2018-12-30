package server.demo.pdxsis.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import server.demo.pdxsis.dao.ObjectLocationDao;
import server.demo.pdxsis.entity.ObjectMapDescription;

@Service
@Transactional
public class ObjectLocationService {

	@Autowired
	ObjectLocationDao objectLocationDao;
	
	public void save(ObjectMapDescription omd) {
		objectLocationDao.save(omd);
	}

	public Page<ObjectMapDescription> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return objectLocationDao.findAll(pageable);
	}
}
