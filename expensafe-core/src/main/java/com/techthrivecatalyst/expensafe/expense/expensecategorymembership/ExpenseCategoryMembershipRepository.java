package com.techthrivecatalyst.expensafe.expense.expensecategorymembership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryMembershipRepository extends JpaRepository<ExpenseCategoryMembershipEntity, String> {
    Page<ExpenseCategoryMembershipEntity> findAllByUserId(String userId, Pageable pageable);

}
