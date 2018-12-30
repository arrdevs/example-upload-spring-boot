package server.demo.pdxsis.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import server.demo.pdxsis.entity.ObjectMapDescription;

@Repository
public interface ObjectLocationDao extends PagingAndSortingRepository<ObjectMapDescription, Long> {

}
