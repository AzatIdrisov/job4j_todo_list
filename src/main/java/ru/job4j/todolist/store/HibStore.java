package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todolist.model.Task;

import java.util.List;

public class HibStore {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Holder {
        private static final HibStore INSTANCE = new HibStore();
    }

    public static HibStore instOf() {
        return Holder.INSTANCE;
    }

    public Task add(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        }
        return task;
    }

    public List<Task> findAll() {
        List<Task> result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from Task").list();
            session.getTransaction().commit();
        }
        return result;
    }

    public List<Task> findNotDone() {
        List<Task> result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "from Task where isDone = :paramDone");
            query.setParameter("paramDone", false);
            result = query.list();
            session.getTransaction().commit();
        }
        return result;
    }

    public Task findById(int id) {
        Task result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.get(Task.class, id);
            session.getTransaction().commit();
        }
        return result;
    }

    public void changeStatusToDone(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            String hql = "update Task "
                    + "SET isDone = true "
                    +  " where id = :paramId";
            Query query = session.createQuery(hql);
            query.setParameter("paramId", id);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
        List<Task> tasks = HibStore.instOf().findAll();
        tasks.forEach(System.out::println);
    }
}
