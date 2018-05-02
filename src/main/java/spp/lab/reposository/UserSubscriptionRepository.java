package spp.lab.reposository;

import org.springframework.stereotype.Repository;
import spp.lab.models.User;
import spp.lab.models.UserSubscription;

import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends BaseRepository<UserSubscription, Long> {

    Optional<UserSubscription> findFirstByUser(User user);

}
