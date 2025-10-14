package com.techthrivecatalyst.expensafe.expense.expensecategorymembershipinvitation;

import com.techthrivecatalyst.expensafe.expense.expensecategory.ExpenseCategoryRepository;
import com.techthrivecatalyst.expensafe.expense.expensecategorymembership.ExpenseCategoryMembershipEntity;
import com.techthrivecatalyst.expensafe.expense.expensecategorymembership.ExpenseCategoryMembershipRepository;
import com.techthrivecatalyst.expensafe.security.api.AppUser;
import com.techthrivecatalyst.expensafe.security.api.UserService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ExpenseCategoryMembershipInvitationServiceImpl implements ExpenseCategoryMembershipInvitationService {

    private final ExpenseCategoryMembershipInvitationRepository repository;
    private final ExpenseCategoryMembershipRepository membershipRepository;
    private final ExpenseCategoryRepository categoryRepository;
    private final UserService userService;

    public ExpenseCategoryMembershipInvitationServiceImpl(
            ExpenseCategoryMembershipInvitationRepository repository,
            ExpenseCategoryMembershipRepository membershipRepository,
            ExpenseCategoryRepository categoryRepository,
            UserService userService) {
        this.repository = repository;
        this.membershipRepository = membershipRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public void joinExpenseCategory(String invitationId, String userId) {
        ExpenseCategoryMembershipInvitationEntity invitation =
                repository.findByInviteeUserIdAndInvitationIdAndExpirationDateLessThanEqual(userId, invitationId,
                        LocalDateTime.now());
        categoryRepository.findById(invitation.getExpenseCategoryId())
                .ifPresent(category ->
                        membershipRepository.save(ExpenseCategoryMembershipEntity.create(userId, category, false))
                );
    }

    @Override
    public String inviteMember(String username, String categoryId) {
        AppUser user = userService.getByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return createInvitationUrl(user.userId().toString(), categoryId);
    }

    private String createInvitationUrl(String userId, String categoryId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
