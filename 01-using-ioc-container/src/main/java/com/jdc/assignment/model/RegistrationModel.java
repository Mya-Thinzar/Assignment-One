package com.jdc.assignment.model;

import java.util.List;

import com.jdc.assignment.domain.Registration;

/**
 * @author Mya Thinzar
 */
public interface RegistrationModel {
	List<Registration> findAllByClassId(int classId);
	void create(Registration student);
}
