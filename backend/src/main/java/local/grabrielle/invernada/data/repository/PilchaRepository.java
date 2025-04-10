package local.grabrielle.invernada.data.repository;

import local.grabrielle.invernada.data.entity.PilchaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilchaRepository extends JpaRepository<PilchaEntity, Integer> {
    PilchaEntity findByTag(String id);
}
