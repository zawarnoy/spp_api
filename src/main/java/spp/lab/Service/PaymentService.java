package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spp.lab.models.Payment;
import spp.lab.reposository.PaymentRepository;

@Service
public class PaymentService extends BaseService<Payment, PaymentRepository> {

    @Autowired
    public PaymentService(PaymentRepository rep)
    {
        this.repository = rep;
    }
}

