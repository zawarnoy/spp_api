package spp.lab.reposository;

import org.springframework.stereotype.Repository;
import spp.lab.models.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends BaseRepository<Subscription, Long>{

    Optional<Subscription> findFirstByName(String name);

    Optional<Subscription> findFirstByDuration(Long duration);

    Optional<Subscription> findFirstByPrice(Long price);

}
