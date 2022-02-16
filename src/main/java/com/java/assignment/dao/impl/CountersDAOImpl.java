package com.java.assignment.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.java.assignment.dao.CountersDAO;
import com.java.assignment.model.Counters;

@Repository
public class CountersDAOImpl implements CountersDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public int getNextSequenceValue(String sequenceName) {

		Query query = new Query(Criteria.where("name").is(sequenceName));
		Update update = new Update().inc("sequence", 1);
        
        Counters counter = mongoTemplate.findAndModify(query, update, Counters.class); // return old Counter object
        return (int)counter.getSequence();
	}
	
	@Override
	public int getCurrentSequenceValue(String counterId) {
		Query query = new Query(Criteria.where("name").is(counterId));
        return (int)mongoTemplate.findOne(query, Counters.class).getSequence();
	}

	@Override
	public void saveCounterObject(Counters counter) {

		mongoTemplate.save(counter, "counters");
	}
}