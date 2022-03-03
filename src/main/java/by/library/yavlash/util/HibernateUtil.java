package by.library.yavlash.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private HibernateUtil() {}

    private static class SessionFactoryHolder {
        public static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return SessionFactoryHolder.sessionFactory;
    }
}