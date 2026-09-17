package com.akmal.smartseat.config;
import com.akmal.smartseat.entity.Seat; import com.akmal.smartseat.repository.SeatRepository; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
@Configuration public class DataSeeder { @Bean CommandLineRunner seed(SeatRepository repo){return args->{if(repo.count()==0){for(char row='A';row<='F';row++)for(int n=1;n<=8;n++)repo.save(new Seat(row+""+n));}};}}
