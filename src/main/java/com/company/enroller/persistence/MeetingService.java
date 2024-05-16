package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("meetingService")
public class MeetingService {

	Session session;

	public MeetingService() {
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = this.session.createQuery(hql);
		return query.list();
	}

	public Collection<Meeting> getAll(String titleValue, String sortBy, String sortOrder) {
		String hql = "FROM Meeting WHERE title LIKE :title";
		if (sortBy.equals("title")) {
			hql+= " ORDER BY " + sortBy;
			if (sortOrder.equals("ASC") || sortOrder.equals("DESC")) {
				hql += " " + sortOrder;
			}
		}
		Query query = session.createQuery(hql);
		query.setParameter("title", "%" + titleValue + "%");
		return query.list();
	}

	public Meeting findById(long id) {
		return session.get(Meeting.class, id);
	}

	public Meeting findByTitle(String title) {
		String hql = "FROM Meeting WHERE title = :title";
		Query query = session.createQuery(hql);
		query.setParameter("title", title);
		return (Meeting) query.uniqueResult();
	}

	public Meeting add(Meeting meeting) {
		Transaction transaction = session.beginTransaction();
		session.save(meeting);
		transaction.commit();
		return meeting;
	}

	public void update(Meeting meeting) {
		Transaction transaction = session.beginTransaction();
		session.merge(meeting);
		transaction.commit();
	}

	public void delete(Meeting meeting) {
		Transaction transaction = session.beginTransaction();
		session.delete(meeting);
		transaction.commit();
	}
}
