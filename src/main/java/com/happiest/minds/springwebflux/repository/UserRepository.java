package com.happiest.minds.springwebflux.repository;

import com.happiest.minds.springwebflux.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
