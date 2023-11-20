package org.matheuscordeiro.socialapi.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.matheuscordeiro.socialapi.domain.model.Follower;
import org.matheuscordeiro.socialapi.domain.model.User;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    public boolean follows(User follower, User user){
        var params = Parameters.with("follower", follower)
                .and("user", user).map();
        final var  query = find("follower = :follower and user = :user ", params);
       return query.firstResultOptional().isPresent();
    }
}
