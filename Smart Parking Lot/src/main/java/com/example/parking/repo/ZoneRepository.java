package main.java.com.example.parking.repo;

import main.java.com.example.parking.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> { }
