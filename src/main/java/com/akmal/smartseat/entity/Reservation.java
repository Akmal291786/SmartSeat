package com.akmal.smartseat.entity;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="reservations")
public class Reservation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Seat seat;
 @Column(nullable=false) private String customerName;
 @Column(nullable=false) private String customerEmail;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private ReservationStatus status;
 @Column(nullable=false) private LocalDateTime createdAt;
 private String paymentReference;
 public Reservation(){}
 public Reservation(Seat s,String n,String e){seat=s;customerName=n;customerEmail=e;status=ReservationStatus.PENDING_PAYMENT;createdAt=LocalDateTime.now();}
 public Long getId(){return id;} public Seat getSeat(){return seat;} public String getCustomerName(){return customerName;} public String getCustomerEmail(){return customerEmail;}
 public ReservationStatus getStatus(){return status;} public void setStatus(ReservationStatus s){status=s;} public LocalDateTime getCreatedAt(){return createdAt;} public String getPaymentReference(){return paymentReference;} public void setPaymentReference(String p){paymentReference=p;}
 public enum ReservationStatus{PENDING_PAYMENT,CONFIRMED,CANCELLED}
}
