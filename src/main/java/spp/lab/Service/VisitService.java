package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.Visit;
import spp.lab.reposository.VisitRepository;

@Service
public class VisitService extends BaseService<Visit, VisitRepository> {

    @Autowired
    public VisitService(VisitRepository rep)
    {
        this.repository = rep;
    }

}
