package service;

import model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    @Autowired
    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment add(Payment payment) {
        Objects.requireNonNull(payment, "payment must not be null");
        return repository.add(payment);
    }

    public BigDecimal getSum(int id) {
        return repository.getSumBySender(id);
    }

    public List<Integer> getIdSenders() {
        return repository.getIdSenders();
    }
}