package spp.lab.reposository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spp.lab.models.Subscription;

@Repository
public interface SubscriptionRepository extends BaseRepository<Subscription, Long>{
}
