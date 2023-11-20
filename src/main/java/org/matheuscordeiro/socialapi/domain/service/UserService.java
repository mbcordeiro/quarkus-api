package org.matheuscordeiro.socialapi.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.model.User;
import org.matheuscordeiro.socialapi.domain.repository.UserRepository;
import org.matheuscordeiro.socialapi.rest.dto.CreateUserRequest;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void create(CreateUserRequest createUserRequest) {
        final var user = new User();
        user.setAge(createUserRequest.age());
        user.setName(createUserRequest.name());
        userRepository.persist(user);
    }

    public List<User> list() {
        return userRepository.listAll();
    }

    @Transactional
    public User update(Long id, CreateUserRequest userRequest) {
        final var user = userRepository.findById(id);

        if(user != null){
            user.setName(userRequest.name());
            user.setAge(userRequest.age());
        }

        userRepository.persist(user);
        return user;
    }

    public boolean delete(Long id) {
        return userRepository.deleteById(id);
    }
}
