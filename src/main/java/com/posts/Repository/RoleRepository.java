package com.posts.Repository;

import com.posts.Domain.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mumarm45 on 07/08/2017.
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role,Long> {
}
