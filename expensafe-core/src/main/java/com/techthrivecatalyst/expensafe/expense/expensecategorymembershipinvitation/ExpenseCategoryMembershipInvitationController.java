package com.techthrivecatalyst.expensafe.expense.expensecategorymembershipinvitation;

import com.techthrivecatalyst.expensafe.security.api.UserExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/expense-category-membership-invitations")
public class ExpenseCategoryMembershipInvitationController {

    private final ExpenseCategoryMembershipInvitationService service;
    private final UserExtractor userExtractor;

    public ExpenseCategoryMembershipInvitationController(ExpenseCategoryMembershipInvitationService service,
            UserExtractor userExtractor) {
        this.service = service;
        this.userExtractor = userExtractor;
    }


    @PostMapping("/join")
    public ResponseEntity<Void> joinExpenseCategory(@RequestParam("id") String invitationId) {
        service.joinExpenseCategory(invitationId, userExtractor.getCurrentUserId().toString());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/invite")
    public ResponseEntity<Void> inviteMember(@RequestParam("username") String username,
            @RequestParam("categoryId") String categoryId) {
        String invitationUrl = service.inviteMember(username, categoryId);
        return ResponseEntity.ok().build();
    }
}
