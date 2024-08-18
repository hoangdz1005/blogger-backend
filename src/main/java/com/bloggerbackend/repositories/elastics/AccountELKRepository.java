package com.bloggerbackend.repositories.elastics;

import com.bloggerbackend.entities.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountELKRepository extends ElasticsearchRepository<Account, Long> {
}
