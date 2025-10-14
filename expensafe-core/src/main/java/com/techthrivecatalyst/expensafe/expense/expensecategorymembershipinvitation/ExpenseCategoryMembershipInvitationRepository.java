package com.techthrivecatalyst.expensafe.expense.expensecategorymembershipinvitation;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryMembershipInvitationRepository extends
        JpaRepository<ExpenseCategoryMembershipInvitationEntity, String> {
    ExpenseCategoryMembershipInvitationEntity findByInviteeUserIdAndInvitationIdAndExpirationDateLessThanEqual(
            String userId, String invitationId, LocalDateTime now);
}
