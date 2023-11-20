package org.matheuscordeiro.socialapi.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.matheuscordeiro.socialapi.domain.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
