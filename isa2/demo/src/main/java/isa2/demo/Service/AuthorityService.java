package isa2.demo.Service;

import isa2.demo.Model.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findById(Integer id);
    List<Authority> findByname(String name);
    Authority findByName(String name);
}
