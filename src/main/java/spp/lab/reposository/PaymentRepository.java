package spp.lab.reposository;

import org.springframework.stereotype.Repository;
import spp.lab.models.Payment;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
}
