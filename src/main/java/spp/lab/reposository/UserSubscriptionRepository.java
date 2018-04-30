package spp.lab.reposository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spp.lab.models.UserSubscription;

@Repository
public interface UserSubscriptionRepository extends BaseRepository<UserSubscription, Long> {
}
