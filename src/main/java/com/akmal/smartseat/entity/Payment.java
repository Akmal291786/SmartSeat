package com.akmal.smartseat.entity;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="payments")
public class Payment { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @OneToOne(optional=false) private Reservation reservation; @Column(nullable=false) private String reference; @Enumerated(EnumType.STRING) private PaymentStatus status; private LocalDateTime paidAt;
 public Payment(){} public Payment(Reservation r,String ref){reservation=r;reference=ref;status=PaymentStatus.SUCCESS;paidAt=LocalDateTime.now();} public Long getId(){return id;} public Reservation getReservation(){return reservation;} public String getReference(){return reference;} public PaymentStatus getStatus(){return status;} public LocalDateTime getPaidAt(){return paidAt;} public enum PaymentStatus{SUCCESS,FAILED}
}
