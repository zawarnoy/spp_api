package spp.lab.Service;

import org.springframework.stereotype.Service;
import spp.lab.models.User;
import spp.lab.reposository.UserRepository;

@Service
public class UserService extends BaseService<User, UserRepository> {
}
