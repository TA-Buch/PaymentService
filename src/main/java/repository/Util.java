package repository;

import model.Payment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Util {

    private Util(){
    }
    public static final List<Payment> PAYMENTS = Arrays.asList(
            new Payment( 30, 10, BigDecimal.valueOf(100)),
            new Payment(2000, 12, BigDecimal.valueOf(3000)),
            new Payment(3000, 1992, BigDecimal.valueOf(300.9)),
            new Payment(20, 2012, BigDecimal.valueOf(800.2)),
            new Payment(30, 4012, BigDecimal.valueOf(300))
    );

//    public static JdbcTemplate getJdbcTemplate(Integer id, JdbcTemplate jdbcTemplate1, JdbcTemplate jdbcTemplate2, JdbcTemplate jdbcTemplate3) {
//        if (id < 1000) {
//            return jdbcTemplate1;
//        } else if (id < 2000) {
//            return jdbcTemplate2;
//        } else
//            return jdbcTemplate3;
//    }


    public static <T> T getSource(Integer id, T source1, T source2, T source3) {
        if (id < 1000) {
            return source1;
        } else if (id < 2000) {
            return source2;
        } else
            return source3;
    }
}
