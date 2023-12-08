package com.scopie.authservice.repository;

import com.scopie.authservice.entity.MovieTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTimeRepository extends JpaRepository<MovieTime, Long> {
}
