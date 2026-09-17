package com.akmal.smartseat.service;
import com.akmal.smartseat.dto.*; import com.akmal.smartseat.entity.*; import com.akmal.smartseat.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service public class SeatService {
 private final SeatRepository seats; private final ReservationRepository reservations; private final PaymentRepository payments;
 public SeatService(SeatRepository s,ReservationRepository r,PaymentRepository p){seats=s;reservations=r;payments=p;}
 public List<Seat> all(){return seats.findAll();}
 @Transactional public Reservation reserve(ReserveRequest req){Seat seat=seats.findBySeatNumber(req.seatNumber()).orElseThrow(()->new IllegalArgumentException("Seat not found")); if(seat.getStatus()!=Seat.SeatStatus.AVAILABLE) throw new IllegalStateException("Seat is not available"); seat.setStatus(Seat.SeatStatus.HELD); reservations.save(new Reservation(seat,req.customerName(),req.customerEmail())); return reservations.findAll().get(reservations.findAll().size()-1);}
 @Transactional public Reservation confirm(Long id, PaymentRequest req){Reservation r=reservations.findById(id).orElseThrow(()->new IllegalArgumentException("Reservation not found")); if(r.getStatus()!=Reservation.ReservationStatus.PENDING_PAYMENT) throw new IllegalStateException("Reservation is not awaiting payment"); Payment pay=new Payment(r,req.paymentReference()); payments.save(pay); r.setPaymentReference(req.paymentReference()); r.setStatus(Reservation.ReservationStatus.CONFIRMED); r.getSeat().setStatus(Seat.SeatStatus.BOOKED); return reservations.save(r);}
 @Transactional public Reservation cancel(Long id){Reservation r=reservations.findById(id).orElseThrow(()->new IllegalArgumentException("Reservation not found")); if(r.getStatus()==Reservation.ReservationStatus.CONFIRMED) throw new IllegalStateException("Confirmed reservation requires cancellation policy"); r.setStatus(Reservation.ReservationStatus.CANCELLED); r.getSeat().setStatus(Seat.SeatStatus.AVAILABLE); return reservations.save(r);}
 public List<Reservation> reservations(){return reservations.findAll();}
}
