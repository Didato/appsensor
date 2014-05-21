package org.owasp.appsensor.storage.jpa2.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.owasp.appsensor.Event;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventRepository {

	@PersistenceContext
	private EntityManager em;
	
	public EventRepository() { }
	
	@Transactional
	public void save(Event event) {
		Event merged = em.merge(event);
		em.flush();
		event.setId(merged.getId());
	}
	
	@Transactional(readOnly = true)
	public Event find(Integer id) {
		return em.createQuery("FROM Event WHERE id = :id", Event.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	@Transactional(readOnly = true)
	public Collection<Event> findAll() {
		return em.createQuery("FROM Event", Event.class).getResultList();
	}
	
}
