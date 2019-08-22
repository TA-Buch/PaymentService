package repository;

import model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {

    private static final BeanPropertyRowMapper<Payment> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Payment.class);

    @Autowired
    @Qualifier("jdbcTemplate1")
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("jdbcTemplate2")
    private JdbcTemplate jdbcTemplate2;

    @Autowired
    @Qualifier("jdbcTemplate3")
    private JdbcTemplate jdbcTemplate3;

    private JdbcTemplate jdbcTemplate;
//
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public Payment add(Payment payment) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(payment);

        if (payment.isNew()) {
            payment.setId(UUID.randomUUID());
        }
        jdbcTemplate = Util.getSource(payment.getIdSender(), jdbcTemplate1, jdbcTemplate2, jdbcTemplate3);
        SimpleJdbcInsert insertPayment = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("payments")
                .usingGeneratedKeyColumns("id");
        insertPayment.execute(parameterSource);
        return payment;
    }

    @Override
    public BigDecimal getSumBySender(int id) {
        jdbcTemplate = Util.getSource(id, jdbcTemplate1, jdbcTemplate2, jdbcTemplate3);
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
}
