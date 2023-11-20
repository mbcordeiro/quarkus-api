package org.matheuscordeiro.socialapi.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.matheuscordeiro.socialapi.domain.model.Follower;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
}
