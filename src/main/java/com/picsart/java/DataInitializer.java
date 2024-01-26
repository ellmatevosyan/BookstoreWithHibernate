package com.picsart.java;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DataInitializer {
    public static void initializeData(SessionFactory sessionFactory){


        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Books book1 = new Books("Murder on the Orient Express", "Agatha Christie", "Mystery", 10.99, 50);
            Books book2 = new Books("To Kill a Mockingbird", "Harper Lee", "Fiction",12.50, 30);
            Customers customer1 = new Customers("John Doe", "john@example.com", "+123456789");
            Customers customer2 = new Customers("Alice Smith", "alice@example.com", "+987654321");

            Sales sale1 = new Sales(book1, customer1,LocalDate.of(2023,11,25),5,54.95);
            Sales sale2 = new Sales(book2,customer2,LocalDate.of(2024,1,3),3,37.5);

            session.persist(book1);
            session.persist(book2);
            session.persist(customer1);
            session.persist(customer2);
            session.persist(sale1);
            session.persist(sale2);

            session.flush();
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
