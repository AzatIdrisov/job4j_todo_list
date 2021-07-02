package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todolist.model.Category;
import ru.job4j.todolist.model.Task;

import java.util.List;
import java.util.function.Function;

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

    public void add(Task task, String[] ids) {
        this.tx(
                session -> {
                    for (String id : ids) {
                        Category category = session.find(Category.class, Integer.parseInt(id));
                        task.addCategory(category);
                    }
                    return session.save(task);
                }
        );
    }

    public List<Task> findAll() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct t from Task t join fetch t.categories")
                        .list()
        );
    }

    public List<Task> findNotDone() {
        return this.tx(session -> session.createQuery(
                "select distinct t from Task t join fetch t.categories where t.isDone = false ").list()
        );
    }

    public Task findById(int id) {
        return this.tx(
                session -> session.get(Task.class, id)
        );
    }

    public void changeStatusToDone(int id) {
        this.tx(
                session -> session.createQuery("update Task "
                        + "SET isDone = true "
                        + " where id = " + id)
                        .executeUpdate()
        );
    }

    public List<Category> getAllCategories() {
        return tx(session ->
                session.createQuery("from ru.job4j.todolist.model.Category").list());
    }

    public static void main(String[] args) {
        List<Task> tasks = HibStore.instOf().findAll();
        tasks.forEach(System.out::println);
    }
}
