package CRUD.dao;

import CRUD.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

public interface UserDAO {

    List<User> getAll() ;

    void delete(User user) ;

    void add(User user) ;

    void update(User user) ;

    User getbyID(int id) ;

    User findByUsername(String username) ;
}
