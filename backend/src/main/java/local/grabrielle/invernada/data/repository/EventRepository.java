package local.grabrielle.invernada.data.repository;

import local.grabrielle.invernada.data.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByDateBetweenOrderByDateAsc(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
