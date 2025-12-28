package main.java.com.example.parking.repo;

import main.java.com.example.parking.domain.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorRepository extends JpaRepository<Floor, Long> { }
