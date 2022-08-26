package default_crud_app.repository;

import default_crud_app.model.Customer;
import default_crud_app.utils.SessionUtil;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CustomerRepository extends SessionUtil {

    public Long add(Customer customer) {
        Session session = openTransactionSession();
        session.persist(customer);
        Long id = (Long) session.getIdentifier(customer);
        closeTransactionSession();
        return id;
    }

    public void edit(Long id, Customer updatedCustomer) {

        Session session = openTransactionSession();

        BeanUtils.copyProperties(updatedCustomer, session.get(Customer.class, id));

        closeTransactionSession();
    }

    public List<Customer> find(Customer customer) {
        Session session = openTransactionSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);

        List<Customer> customerList = session
                .createQuery(cq.select(root)
                        .where(cb.and(
                                cb.like(root.get("name").as(String.class), '%' + customer.getName() + '%'),
                                cb.like(root.get("surname").as(String.class), '%' + customer.getSurname() + '%')
                                )
                        )
                        .orderBy(cb.asc(root.get("id")))
                ).getResultList();

        closeTransactionSession();

        return customerList;

    }

    public Customer getById(Long id) {
        Session session = openTransactionSession();

        Customer customer = session.get(Customer.class, id);

        closeTransactionSession();

        return customer;
    }

    public List<Customer> getList() {
        Session session = openTransactionSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);

        List<Customer> customerList = session
                .createQuery(cq.select(root).orderBy(cb.asc(root.get("id"))))
                .getResultList();

        closeTransactionSession();

        return customerList;

    }

    public void remove(Long id) {
        Session session = openTransactionSession();

        Customer customer = session.get(Customer.class, id);
        session.delete(customer);

        closeTransactionSession();
    }
}


