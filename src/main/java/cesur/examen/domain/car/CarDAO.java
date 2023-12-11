package cesur.examen.domain.car;

import cesur.examen.common.DAO;
import cesur.examen.common.HibernateUtil;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Alejandro Álvarez Mérida
 * Fecha: 11-12-2023
 */

@Log
public class CarDAO implements DAO<Car> {
    @Override
    public Car save(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
            log.info("Car with ID " + car.getId() + " was saved successfully");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.severe("Error in save(): " + e.getMessage());
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        return null;
    }

    @Override
    public boolean remove(Car car) {
        return false;
    }

    @Override
    public Car get(Long id) {
        return null;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    public List<Car> getAllByManufacturer(String manufacturer) {
        var out = new ArrayList<Car>();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<Car> query = session.createQuery("FROM Car WHERE manufacturer = :manufacturer", Car.class);
            query.setParameter("manufacturer", manufacturer);
            out = (ArrayList<Car>) query.list();

            transaction.commit();
            log.info("Cars retrieved by manufacturer successfully. Manufacturer: " + manufacturer);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.severe("Error in getAllByManufacturer(): " + e.getMessage());
            throw new RuntimeException(e);
        }

        return out;
    }
}
