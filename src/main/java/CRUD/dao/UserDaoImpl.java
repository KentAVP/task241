package CRUD.dao;

import CRUD.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDAO {


   @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        List<User> listOfUser = null;
        listOfUser = entityManager.createQuery("from User").getResultList();
        return listOfUser;
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void add(User user) {
        if (user.getId() == 0) {
            entityManager.persist(user);
            User us = findByUsername(user.getUsername());
            entityManager.createNativeQuery("INSERT INTO user_role(user_id,role_id) VALUES ('"+us.getId()+"','2');").executeUpdate();
        } else {
            entityManager.merge(user);
        }

    }

    @Override
    public void update(User user) {
        entityManager.merge(user);

    }

    @Override
    public User getbyID(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = entityManager.createQuery("FROM User where username = '"+username+"'").getResultList();
        return users.get(0);
    }


}
