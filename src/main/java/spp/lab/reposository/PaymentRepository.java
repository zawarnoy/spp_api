package spp.lab.reposository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spp.lab.models.Payment;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
}
