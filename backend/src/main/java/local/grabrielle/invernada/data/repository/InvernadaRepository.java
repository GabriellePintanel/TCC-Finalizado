package local.grabrielle.invernada.data.repository;

import local.grabrielle.invernada.data.entity.InvernadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvernadaRepository extends JpaRepository<InvernadaEntity, Integer> {
}
