package com.picsart.java;

import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Scanner;
import static com.picsart.java.BooksDAO.displayBooks;
import static com.picsart.java.SalesDAO.displaySales;



public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        DataInitializer.initializeData(sessionFactory);

        Scanner scanner = new Scanner(System.in);
        BooksDAO booksDAO = new BooksDAO(sessionFactory);
        CustomersDAO customersDAO = new CustomersDAO(sessionFactory);
        SalesDAO salesDAO = new SalesDAO(sessionFactory);
        boolean exit = false;
        while(!exit){
            System.out.println("1. Display Books by Genre");
            System.out.println("2. Display Books by Author");
            System.out.println("3. Update book by author");
            System.out.println("4. Update customer by name");
            System.out.println("5. Show customer purchase history");
            System.out.println("6. Process new sales");
            System.out.println("7. Calculate total revenue by genre");
            System.out.println("8. Sold books report");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("Enter genre: ");
                    String genre = scanner.nextLine();
                    List<Books> booksByGenre = booksDAO.getBooksByGenre(genre);
                    displayBooks(booksByGenre);
                    break;
                case 2:
                    System.out.println("Enter author: ");
                    String author = scanner.nextLine();
                    List<Books> booksByAuthor = booksDAO.getBooksByAuthor(author);
                    displayBooks(booksByAuthor);
                    break;
                case 3:
                    System.out.println("Enter author to update: ");
                    String authorToUpdate = scanner.nextLine();
                    System.out.println("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.println("Enter new genre: ");
                    String newGenre = scanner.nextLine();
                    System.out.println("Enter new price: ");
                    Double newPrice = scanner.nextDouble();

                    boolean updateResult = booksDAO.updateBookDetails(authorToUpdate,newTitle,newGenre,newPrice);
                    if(updateResult){
                        System.out.println("Book details updated successfully.");
                    }else {
                        System.out.println("Failed to update book details.");
                    }
                    break;
                case 4:
                    System.out.println("Enter the name of the customer to update: ");
                    String nameToUpdate = scanner.nextLine();
                    System.out.println("Enter new email address");
                    String newEmail = scanner.nextLine();

                    boolean updateCustomer = customersDAO.updateCustomerDetails(nameToUpdate,newEmail);
                    if(updateCustomer){
                        System.out.println("Customer details updated successfully.");
                    }else {
                        System.out.println("Failed to update customer details.");
                    }
                    break;
                case 5:
                    System.out.println("Enter customer ID to see purchase history: ");
                    Long customerId = scanner.nextLong();
                    List<Sales> customerPurchases = salesDAO.customerPurchaseHistory(customerId);
                    displaySales(customerPurchases);
                    break;
                case 6:
                    System.out.println("Enter your customerId: ");
                    Long customerIdForSale = scanner.nextLong();
                    System.out.println("Enter the book ID you want to buy: ");
                    Long bookIdForSale = scanner.nextLong();
                    System.out.println("Enter the quantity you want to buy: ");
                    int quantityToBuy = scanner.nextInt();

                    salesDAO.processNewSale(customerIdForSale,bookIdForSale,quantityToBuy);
                    break;
                case 7:
                    salesDAO.calculateTotalRevenueByGenre();
                    salesDAO.displayTotalRevenueByGenre();
                    break;
                case 8:
                    salesDAO.soldBooksReport();
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");


            }
        }
        System.out.println("Exiting the application. Goodbye!");



        sessionFactory.close();
 
    }
}