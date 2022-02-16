package com.java.assignment.dao;

import com.java.assignment.model.Counters;

public interface CountersDAO {
	
	int getNextSequenceValue(String sequenceName);

	int getCurrentSequenceValue(String counterId);

	void saveCounterObject(Counters counter);
}