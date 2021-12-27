package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.User;
import isa2.demo.Model.UserDeleteRequest;
import isa2.demo.Repository.UserDeleteRequestRepository;
import isa2.demo.Service.UserDeleteRequestService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class UserDeleteRequestServiceImpl implements UserDeleteRequestService {

    public final UserDeleteRequestRepository userDeleteRequestRepository;

    public final UserServiceImpl userService;

    public UserDeleteRequestServiceImpl(UserDeleteRequestRepository userDeleteRequestRepository, UserServiceImpl userService){
        this.userDeleteRequestRepository = userDeleteRequestRepository;
        this.userService = userService;
    }

    @Override
    public UserDeleteRequest save(UserDeleteRequest userDeleteRequest) {
        return this.userDeleteRequestRepository.save(userDeleteRequest);
    }

    @Override
    public void approveRequest(UserDeleteRequest userDeleteRequest) {
        User user = userDeleteRequest.getUser();
        String subject = "Delete Profile Request approved";
        String content = "Dear " + user.getFirstName() + " " + user.getSurname() + ",<br>" + userDeleteRequest.getResponse();
        try {
            this.userService.sendEmail(subject,content, userDeleteRequest.getUser().getEmail());
        } catch (MessagingException me){
            System.out.println("Message exception");
        }
        this.userDeleteRequestRepository.delete(userDeleteRequest);
        this.userService.delete(user);

    }

    @Override
    public void rejectRequest(UserDeleteRequest userDeleteRequest) {
        User user = userDeleteRequest.getUser();
        String subject = "Delete Profile Request rejected";
        String content = "Dear " + user.getFirstName() + " " + user.getSurname() + ",<br>" + userDeleteRequest.getResponse();
        try {
            this.userService.sendEmail(subject,content, userDeleteRequest.getUser().getEmail());
        } catch (MessagingException me){
            System.out.println("Message exception");
        }
        this.userDeleteRequestRepository.delete(userDeleteRequest);
    }

    @Override
    public UserDeleteRequest update(UserDeleteRequest userDeleteRequest){
        UserDeleteRequest requestToUpdate = this.userDeleteRequestRepository.findById(userDeleteRequest.getId()).get();
        requestToUpdate.setUser(userDeleteRequest.getUser());
        requestToUpdate.setDescription(userDeleteRequest.getDescription());
        requestToUpdate.setResponse(userDeleteRequest.getResponse());
        return this.userDeleteRequestRepository.save(requestToUpdate);
    }

    @Override
    public UserDeleteRequest findByUser(User user){
        UserDeleteRequest userDeleteRequest = this.userDeleteRequestRepository.findByUser(user);
        return userDeleteRequest;
    }

    @Override
    public List<UserDeleteRequest> findAll() {
        return this.userDeleteRequestRepository.findAll();
    }

    @Override
    public UserDeleteRequest getRequestById(Integer id) {
        return this.userDeleteRequestRepository.findById(id).get();
    }
}
