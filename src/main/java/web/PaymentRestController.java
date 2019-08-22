package web;

import model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.PaymentService;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PaymentRestController {

    @Autowired
    private PaymentService service;

    public BigDecimal getSumBySender(int id) {
        return service.getSum(id);
    }

    public Payment add(Payment payment) {
        return service.add(payment);
    }

    public List<Integer> getIdSenders() {
        return service.getIdSenders();
    }
}