package com.techthrivecatalyst.expensafe.expense.expensecategorymembershipinvitation;

public interface ExpenseCategoryMembershipInvitationService {

    void joinExpenseCategory(String invitationId, String userId);

    String inviteMember(String username, String categoryId);
}
