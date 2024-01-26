package com.picsart.java;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesDAO {
    private final SessionFactory sessionFactory;

    public SalesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Sales> customerPurchaseHistory(Long customerId) {
        try (Session session = sessionFactory.openSession()) {
            Customers customer = session.get(Customers.class, customerId);

            if (customer == null) {
                System.out.println("Customer not found with ID: " + customerId);
                return Collections.emptyList();
            }

            List<Sales> salesList = session.createQuery("FROM Sales WHERE customer = :customer", Sales.class)
                    .setParameter("customer", customer)
                    .list();
            return salesList != null ? salesList : Collections.emptyList();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static void displaySales(List<Sales> sales){
        if(sales.isEmpty()){
            System.out.println("No books found");
        }else {
            for(Sales sale : sales){
                System.out.println(sale.toString());
            }
        }
    }

    public void processNewSale(Long customerId, Long bookId, int quantitySold){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();

            Customers customer = session.get(Customers.class, customerId);
            Books book = session.get(Books.class, bookId);

            if(customer == null || book == null){
                System.out.println("Invalid customer ID or book ID.");
                transaction.rollback();
                return;
            }

            //Check if there is enough stock
            int remainingStock = book.getQuantity_in_stock() - quantitySold;
            if(remainingStock < 0){
                System.out.println("Not enough books available for the sale.");
                transaction.rollback();
                return;
            }

            //Update stock
            book.setQuantity_in_stock(remainingStock);
            session.update(book);

            //Create and save new sale
            double totalPrice = book.getPrice() * quantitySold;
            Sales sale = new Sales(book, customer, LocalDate.now(), quantitySold, totalPrice);
            session.save(sale);

            transaction.commit();
            System.out.println("Sale processed successfully.");
        }
    }

    public Map<String,Double> calculateTotalRevenueByGenre(){
        Map<String,Double> totalRevenueByGenre = new HashMap<>();
        try(Session session = sessionFactory.openSession()){
            List<Sales> salesList = session.createQuery("FROM Sales ",Sales.class).list();

            for(Sales sale : salesList){
                String genre = sale.getBook().getGenre();
                Double totalRevenue = totalRevenueByGenre.getOrDefault(genre,0.0);
                totalRevenue += sale.getTotalPrice();
                totalRevenueByGenre.put(genre,totalRevenue);
            }
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return totalRevenueByGenre;
    }
    public  void displayTotalRevenueByGenre() {
        Map<String, Double> totalRevenueByGenre = calculateTotalRevenueByGenre();

        if (!totalRevenueByGenre.isEmpty()) {
            System.out.println("Total Revenue by Genre:");
            for (Map.Entry<String, Double> entry : totalRevenueByGenre.entrySet()) {
                System.out.println(entry.getKey() + ": $" + entry.getValue());
            }
        } else {
            System.out.println("No sales data available.");
        }
    }

    public void soldBooksReport(){
        try(Session session = sessionFactory.openSession()){
            List<Sales> sales = session.createQuery("FROM Sales",Sales.class).list();

            if(!sales.isEmpty()){
                System.out.println("Books sold report:");
                for(Sales sale : sales){
                    String title = sale.getBook().getTitle();
                    String genre = sale.getBook().getGenre();
                    String customerName = sale.getCustomer().getName();
                    LocalDate saleDate = sale.getDateOfSale();

                    System.out.println("Title: " + title + ", Genre: " + genre + ", Customer: " + customerName + ", Sale Date " + saleDate);
                }
            }else{
                System.out.println("No sales data available.");
            }

        }catch (HibernateException e){
            e.printStackTrace();
        }
    }

}
