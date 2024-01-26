package com.picsart.java;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class CustomersDAO {
    private final SessionFactory sessionFactory;

    public CustomersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean updateCustomerDetails(String name, String newEmail){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();

            //Retrieve the customer based on the name
            Customers customer = session.createQuery("FROM Customers WHERE name =: name",Customers.class)
                    .setParameter("name",name)
                    .uniqueResult();

            if(customer != null){
                customer.setEmail(newEmail);
                transaction.commit();
                return true;
            }else{
                System.out.println("No customer found with that name!");
                return false;
            }

        }catch (HibernateException e){
            e.printStackTrace();
            return false;
        }
    }


}
