package web;

import model.Payment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.JdbcPaymentRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
                for (Payment payment: JdbcPaymentRepository.PAYMENTS) {
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
                request.setAttribute("payments", JdbcPaymentRepository.PAYMENTS);
                request.getRequestDispatcher("/payments.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = request.getParameter("idSender");
        return Integer.parseInt(paramId);
    }
}
