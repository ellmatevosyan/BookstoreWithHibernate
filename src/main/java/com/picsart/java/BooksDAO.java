package com.picsart.java;

import org.hibernate.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class BooksDAO {
    private final SessionFactory sessionFactory;

    public BooksDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }



    public List<Books> getBooksByGenre(String genre){
        try (Session session = sessionFactory.openSession()){
            //Read-only transaction
            return session.createQuery("FROM Books WHERE genre = :genre",Books.class)
                    .setParameter("genre",genre)
                    .list();
        }catch (HibernateException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Books> getBooksByAuthor(String author) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Books WHERE author = :author", Books.class)
                    .setParameter("author", author)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean updateBookDetails(String author,String newTitle, String newGenre, Double newPrice){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();

            //Retrieve the book based on author
            Books book = session.createQuery("FROM Books WHERE author = :author", Books.class)
                    .setParameter("author", author)
                    .uniqueResult();

            if(book != null){
                //Update the specified fields
                book.setTitle(newTitle);
                book.setGenre(newGenre);
                book.setPrice(newPrice);
                transaction.commit();
                return true;
            }else {
                System.out.println("Book not found for author: " + author);
                return false;
            }
        }catch (HibernateException e){
            e.printStackTrace();
            return false;
        }

    }
    public static void displayBooks(List<Books> books){
        if(books.isEmpty()){
            System.out.println("No books found");
        }else {
            for(Books book : books){
                System.out.println(book.toString());
            }
        }
    }


}
