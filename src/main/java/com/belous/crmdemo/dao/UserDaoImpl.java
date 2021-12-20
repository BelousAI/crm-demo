package com.belous.crmdemo.dao;

import com.belous.crmdemo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    // need to inject the security session factory
    @Autowired
    private SessionFactory securitySessionFactory;

    @Override
    public User findByUserName(String userName) {

        // get the current hibernate session
        Session currentSession = securitySessionFactory.getCurrentSession();

        // now retrieve/read from database using username
        Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", userName);
        User theUser;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    public void save(User theUser) {

        // get the current hibernate session
        Session currentSession = securitySessionFactory.getCurrentSession();

        // create the user
        currentSession.saveOrUpdate(theUser);
    }
}
