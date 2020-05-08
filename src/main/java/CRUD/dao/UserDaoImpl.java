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
    //пытался сделать через энтити

  /*  @PersistenceContext
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

*/

 @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<User> getAll() {
        Transaction transaction = null;
        List<User> listOfUser = null;
        try {
            // start a transaction
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            // get an user object

            listOfUser = session.createQuery("from User").list(); // забераем из наименование класса

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfUser;
    }

    @Transactional
    public void delete(User user) {
        Transaction transaction = null;

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (user != null) {
                session.delete(user);
                System.out.println("Вы удалили пользователя!");
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Transactional
    public void add(User user) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        User us = findByUsername(user.getUsername());
        NativeQuery query = sessionFactory.getCurrentSession().createNativeQuery("INSERT INTO user_role(user_id,role_id) VALUES ('"+us.getId()+"','2');");
        query.executeUpdate();
    }

    @Transactional
    public void update(User user) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Transactional
    public User getbyID(int id) {
        Transaction transaction = null;
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            user = (User) session.get(User.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }
    @Transactional
    public User findByUsername(String username) {
        List<User> users = sessionFactory.getCurrentSession().createQuery("FROM User where username = '"+username+"'").list();
        return users.get(0);
    }
}
