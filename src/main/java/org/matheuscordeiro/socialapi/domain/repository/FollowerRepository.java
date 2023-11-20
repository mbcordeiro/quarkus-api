package org.matheuscordeiro.socialapi.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.matheuscordeiro.socialapi.domain.model.Follower;
import org.matheuscordeiro.socialapi.domain.model.User;

import java.util.List;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    public boolean follows(User follower, User user){
        var params = Parameters.with("follower", follower)
                .and("user", user).map();
        final var  query = find("follower = :follower and user = :user ", params);
       return query.firstResultOptional().isPresent();
    }

    public List<Follower> findByUser(Long userId){
        final var  query = find("user.id", userId);
        return query.list();
    }
}
