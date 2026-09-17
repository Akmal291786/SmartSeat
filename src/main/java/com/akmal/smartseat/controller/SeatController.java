package com.akmal.smartseat.controller;
import com.akmal.smartseat.dto.*; import com.akmal.smartseat.entity.*; import com.akmal.smartseat.service.SeatService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin
public class SeatController { private final SeatService service; public SeatController(SeatService s){service=s;}
 @GetMapping("/seats") public List<Seat> seats(){return service.all();}
 @PostMapping("/reservations") public ResponseEntity<?> reserve(@Valid @RequestBody ReserveRequest r){try{return ResponseEntity.ok(service.reserve(r));}catch(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}}
 @PostMapping("/reservations/{id}/payment") public ResponseEntity<?> pay(@PathVariable Long id,@Valid @RequestBody PaymentRequest p){try{return ResponseEntity.ok(service.confirm(id,p));}catch(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}}
 @PostMapping("/reservations/{id}/cancel") public ResponseEntity<?> cancel(@PathVariable Long id){try{return ResponseEntity.ok(service.cancel(id));}catch(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}}
 @GetMapping("/reservations") public List<Reservation> reservations(){return service.reservations();}
}
