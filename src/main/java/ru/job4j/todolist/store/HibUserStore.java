package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.User;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;

public class HibUserStore {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Holder {
        private static final HibUserStore INSTANCE = new HibUserStore();
    }

    public static HibUserStore instOf() {
        return HibUserStore.Holder.INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void addUser(User user) {
        this.tx(
                session -> session.save(user)
        );
    }

    public User findUserById(int id) {
        return this.tx(
                session -> session.get(User.class, id)
        );
    }

    public List<User> findUserByEmail(String email) {
        return this.tx(session -> {
            final Query query = session.createQuery("from User where email=:email");
            query.setParameter("email", email);
            return query.getResultList();
        });
    }
}
