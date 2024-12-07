package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        // Configuration and SessionFactory
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        // Insert records
        insertRecords(factory);

        // Apply Criteria Queries
        applyCriteriaQueries(factory);

        factory.close();
    }

    private static void insertRecords(SessionFactory factory) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Customer c1 = new Customer();
        c1.setName("Alice");
        c1.setEmail("alice@example.com");
        c1.setAge(25);
        c1.setLocation("New York");

        Customer c2 = new Customer();
        c2.setName("Bob");
        c2.setEmail("bob@example.com");
        c2.setAge(30);
        c2.setLocation("California");

        session.save(c1);
        session.save(c2);

        tx.commit();
        session.close();
        System.out.println("Records Inserted Successfully!");
    }

    private static void applyCriteriaQueries(SessionFactory factory) {
        Session session = factory.openSession();

        System.out.println("Customers with age greater than 25:");
        Criteria criteria1 = session.createCriteria(Customer.class);
        criteria1.add(Restrictions.gt("age", 25));
        List<Customer> list1 = criteria1.list();
        list1.forEach(c -> System.out.println(c.getName()));

        System.out.println("Customers with location like 'New':");
        Criteria criteria2 = session.createCriteria(Customer.class);
        criteria2.add(Restrictions.like("location", "New%"));
        List<Customer> list2 = criteria2.list();
        list2.forEach(c -> System.out.println(c.getName()));

        System.out.println("Customers between ages 20 and 30:");
        Criteria criteria3 = session.createCriteria(Customer.class);
        criteria3.add(Restrictions.between("age", 20, 30));
        List<Customer> list3 = criteria3.list();
        list3.forEach(c -> System.out.println(c.getName()));

        session.close();
    }
}
