package com.manifesters.alumni.types;

import com.manifesters.alumni.domain.TransactionType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@ToString
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TRANSACTION_ID")
    private long transactionId;
    @Column(name="TRANSACTION_TYPE")
    private String transactionType = TransactionType.Event.toString();
    @Column(name="ALUMNI_ID")
    private Long alumniId;
    @Column(name="EVENT_ID")
    private Long eventId;
    @Column(name="TRANSACTION_DATE")
    private Date transactionDate;
    @Column(name="NAME_ON_CARD")
    private String cardName;
    @Column(name="CARD_NUMBER")
    private long cardNumber;
    @Column(name="AMOUNT")
    private double amount;
    @Column(name="EXPIRY_DATE")
    private String expiryDate;
    @Column(name="CARD_TYPE")
    private String cardType;
    @Column(name="CREATE_TS")
    private Date createTs;
    @Column(name="CREATE_USER")
    private String createUser;
    @Column(name="UPDATE_TS")
    private Date updateTs;
    @Column(name="UPDATE_USER")
    private String updateUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && cardNumber == that.cardNumber && Double.compare(that.amount, amount) == 0 && Objects.equals(transactionType, that.transactionType) && Objects.equals(alumniId, that.alumniId) && Objects.equals(eventId, that.eventId) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(cardName, that.cardName) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(cardType, that.cardType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, transactionType, alumniId, eventId, transactionDate, cardName, cardNumber, amount, expiryDate, cardType);
    }
}
