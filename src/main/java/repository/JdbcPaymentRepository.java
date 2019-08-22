package repository;

import model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {

    @Autowired
    @Qualifier("jdbcTemplate1")
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("jdbcTemplate2")
    private JdbcTemplate jdbcTemplate2;

    @Autowired
    @Qualifier("jdbcTemplate3")
    private JdbcTemplate jdbcTemplate3;

    @Override
    public Payment add(Payment payment) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(payment);

        if (payment.isNew()) {
            payment.setId(UUID.randomUUID());
        }
        JdbcTemplate jdbcTemplate = getSource(payment.getIdSender(), jdbcTemplate1, jdbcTemplate2, jdbcTemplate3);
        SimpleJdbcInsert insertPayment = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("payments")
                .usingGeneratedKeyColumns("id");
        insertPayment.execute(parameterSource);
        return payment;
    }

    @Override
    public BigDecimal getSumBySender(int id) {
        JdbcTemplate jdbcTemplate = getSource(id, jdbcTemplate1, jdbcTemplate2, jdbcTemplate3);
        BigDecimal sum = jdbcTemplate.queryForObject("SELECT sum(amount) FROM payments WHERE id_sender=? GROUP BY id_sender", BigDecimal.class, id);
        return sum;
    }

    @Override
    public List<Integer> getIdSenders() {
        List<Integer> senders = new ArrayList<>();
        senders.addAll(jdbcTemplate1.queryForList("SELECT distinct id_sender FROM payments", Integer.class));
        senders.addAll(jdbcTemplate2.queryForList("SELECT distinct id_sender FROM payments", Integer.class));
        senders.addAll(jdbcTemplate3.queryForList("SELECT distinct id_sender FROM payments", Integer.class));
        return senders;
    }

    static <T> T getSource(Integer id, T source1, T source2, T source3) {
        if (id < 10000) {
            return source1;
        } else if (id < 20000) {
            return source2;
        } else
            return source3;
    }

    public static final List<Payment> PAYMENTS = Arrays.asList(
            new Payment(1, 2, BigDecimal.valueOf(100)),
            new Payment(2, 20001, BigDecimal.valueOf(3000)),
            new Payment(3, 30002, BigDecimal.valueOf(300.9)),
            new Payment(20001, 1, BigDecimal.valueOf(800.2)),
            new Payment(20002, 20001, BigDecimal.valueOf(300)),
            new Payment(20003, 30003, BigDecimal.valueOf(900.2)),
            new Payment(30001, 3, BigDecimal.valueOf(70)),
            new Payment(30002, 20003, BigDecimal.valueOf(890.42)),
            new Payment(30003, 20002, BigDecimal.valueOf(170)),
            new Payment(1, 3, BigDecimal.valueOf(7)),
            new Payment(20002, 20003, BigDecimal.valueOf(8.42)),
            new Payment(30003, 30002, BigDecimal.valueOf(17))
    );
}
