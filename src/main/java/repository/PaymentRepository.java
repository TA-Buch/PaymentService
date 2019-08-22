package repository;

import model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository {

    Payment add(Payment payment);

    BigDecimal getSumBySender(int idSender);

    List<Integer> getIdSenders();
}