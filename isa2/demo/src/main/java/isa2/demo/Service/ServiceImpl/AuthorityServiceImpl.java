package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Authority;
import isa2.demo.Repository.AuthorityRepository;
import isa2.demo.Service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findById(Integer id) {
        Authority auth = this.authorityRepository.getById(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByname(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public Authority findByName(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        return auth;
    }
}
