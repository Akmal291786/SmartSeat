package com.akmal.smartseat.entity;
import jakarta.persistence.*;
@Entity @Table(name="seats", uniqueConstraints=@UniqueConstraint(columnNames={"seat_number"}))
public class Seat {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="seat_number", nullable=false) private String seatNumber;
 @Enumerated(EnumType.STRING) private SeatStatus status=SeatStatus.AVAILABLE;
 public Seat(){} public Seat(String n){seatNumber=n;}
 public Long getId(){return id;} public String getSeatNumber(){return seatNumber;} public void setSeatNumber(String s){seatNumber=s;}
 public SeatStatus getStatus(){return status;} public void setStatus(SeatStatus s){status=s;}
 public enum SeatStatus{AVAILABLE,HELD,BOOKED}
}
