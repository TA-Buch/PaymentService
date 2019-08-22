package web;

import model.Payment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import repository.Util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PaymentServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private PaymentRestController paymentController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        paymentController = springContext.getBean(PaymentRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (request.getParameter("idSender") != null) {
            action = "sum";
        }

        switch (action == null ? "all" : action) {
            case "add":
                for (Payment payment: Util.PAYMENTS) {
                    paymentController.add(payment);
                }
                request.getRequestDispatcher("/payments.jsp").forward(request, response);
            case "sum":
                request.setAttribute("senders",paymentController.getIdSenders());
                List<Payment> p = new ArrayList<>();
                p.add(new Payment(getId(request), 0,  paymentController.getSumBySender(getId(request))));
                request.setAttribute("payments", p);
                request.getRequestDispatcher("/payments.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("senders",paymentController.getIdSenders());
                request.setAttribute("payments", Util.PAYMENTS);
                request.getRequestDispatcher("/payments.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = request.getParameter("idSender");
        return Integer.parseInt(paramId);
    }
}
