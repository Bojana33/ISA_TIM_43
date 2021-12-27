package isa2.demo.Service;

import isa2.demo.Model.User;
import isa2.demo.Model.UserDeleteRequest;

import java.util.List;

public interface UserDeleteRequestService {

    UserDeleteRequest save(UserDeleteRequest userDeleteRequest);

    void approveRequest(UserDeleteRequest userDeleteRequest);

    void rejectRequest(UserDeleteRequest userDeleteRequest);

    UserDeleteRequest update(UserDeleteRequest userDeleteRequest);

    UserDeleteRequest findByUser(User user) throws Exception;

    List<UserDeleteRequest> findAll();

    UserDeleteRequest getRequestById(Integer id);
}
