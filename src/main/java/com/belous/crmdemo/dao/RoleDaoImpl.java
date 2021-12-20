package com.belous.crmdemo.dao;

import com.belous.crmdemo.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    // need to inject the security session factory
    @Autowired
    private SessionFactory securitySessionFactory;

    @Override
    public Role findRoleByName(String roleName) {

        // get the current hibernate session
        Session currentSession = securitySessionFactory.getCurrentSession();

        Query<Role> theQuery = currentSession.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", roleName);
        Role theRole;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }
}
