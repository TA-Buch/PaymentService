package model;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment extends AbstractBaseEntity {

    private Integer idSender;

    private Integer idRecipient;

    private BigDecimal amount;

    public Payment(int idSender, int idRecipient, int i) {
    }

    public Payment(Integer idSender, Integer idRecipient, BigDecimal amount) {
        this(null, idSender, idRecipient, amount);
    }

    public Payment(Payment u) {
        this(u.getId(), u.getIdSender(), u.getIdRecipient(), u.getAmount());
    }

    public Payment(UUID id, Integer idSender, Integer idRecipient, BigDecimal amount) {
        super(id);
        this.idSender = idSender;
        this.idRecipient = idRecipient;
        this.amount = amount;
    }

    public void setIdSender(Integer idRSender) {
        this.idSender = idSender;
    }

    public Integer getIdSender() {
        return this.idSender;
    }

    public void setIdRecipient(Integer idRecipient) {
        this.idRecipient = idRecipient;
    }

    public Integer getIdRecipient() {
        return this.idRecipient;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", first name=" + idSender +
                ", last name=" + idRecipient +
                ", description=" + amount.toString() +
                '}';
    }
}