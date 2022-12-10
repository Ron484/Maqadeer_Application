package database;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DB {
    
    public static <T> T getObject(String queryCommand, Object... parameters) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        // create the query and set its parameters
        Query query = session.createQuery(queryCommand);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }                
        T obj = (T) query.uniqueResult();
        
        tx.commit();
        session.close();
        return obj;
    }
    
    // get list of object from database
    public static <T> List<T> getList(String queryCommand, Object... parameters) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        // create query and set its command
        Query query = session.createQuery(queryCommand);           
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);           
        }
        
        List<T> list = query.list();
       
        session.getTransaction().commit();
        session.close();        
        return list;  
    } 
    
    public static boolean isExist(String queryCommand, Object... parameters) {
        Object obj = getObject(queryCommand, parameters);
        return obj != null;
    }
    
    public static <T extends Serializable> void update(T obj) {        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();        
        session.update(obj);        
        tx.commit();
        session.close();
    }   
    
    public static <T extends Serializable> void delete(T obj) {        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();        
        session.delete(obj);        
        tx.commit();
        session.close();
    } 
    
    public static <T extends Serializable> void save(T obj) {        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();        
        session.save(obj);        
        tx.commit();
        session.close();
    } 
}
