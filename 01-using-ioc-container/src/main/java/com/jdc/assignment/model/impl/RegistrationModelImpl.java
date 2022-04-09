package com.jdc.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.RegistrationModel;

/**
 * @author Mya Thinzar
 */
public class RegistrationModelImpl implements RegistrationModel {

	private static final String INSERT = """
			insert into registration(open_class_id,student,phone,email)
			values(?,?,?,?)
			""";

	private static final String SELECT_SQL = """
			select r.id , r.student, r.phone,r.email,
			oc.id openClassId, oc.teacher, oc.start_date
			from registration r join open_class oc on oc.id = r.open_class_id
			where r.open_class_id = ?
			""";

	private DataSource dataSource;

	public RegistrationModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Registration> findAllByClassId(int classId) {
		var list = new ArrayList<Registration>();

		try (var conn = dataSource.getConnection(); var stmt = conn.prepareStatement(SELECT_SQL)) {

			stmt.setInt(1, classId);

			var rs = stmt.executeQuery();

			while (rs.next()) {
				var oc = new OpenClass();
				oc.setId(rs.getInt("openClassId"));
				oc.setStartDate(rs.getDate("start_date").toLocalDate());
				oc.setTeacher(rs.getString("teacher"));

				var r = new Registration();
				r.setId(rs.getInt("id"));
				r.setOpenClass(oc);
				r.setPhone(rs.getString("phone"));
				r.setEmail(rs.getString("email"));
				r.setStudent(rs.getString("student"));

				list.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void create(Registration registration) {
		try (var conn = dataSource.getConnection(); var stmt = conn.prepareStatement(INSERT)) {

			stmt.setInt(1, registration.getOpenClass().getId());
			stmt.setString(2, registration.getStudent());
			stmt.setString(3, registration.getPhone());
			stmt.setString(4, registration.getEmail());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
