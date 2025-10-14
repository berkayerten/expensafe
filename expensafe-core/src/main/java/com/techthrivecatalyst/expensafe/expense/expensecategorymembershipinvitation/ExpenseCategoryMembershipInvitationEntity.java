package com.techthrivecatalyst.expensafe.expense.expensecategorymembershipinvitation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense_category_membership_invitation")
public class ExpenseCategoryMembershipInvitationEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String inviteeUserId;

    @Column(nullable = false)
    private String inviterUserId;

    @Column(nullable = false)
    private String invitationId;

    @Column
    private String expenseCategoryId;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public ExpenseCategoryMembershipInvitationEntity() {}

    private ExpenseCategoryMembershipInvitationEntity(String inviteeUserId, String inviterUserId,
            String invitationId, String expenseCategoryId, LocalDateTime expirationDate) {
        this.inviteeUserId = inviteeUserId;
        this.inviterUserId = inviterUserId;
        this.invitationId = invitationId;
        this.expenseCategoryId = expenseCategoryId;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getInviteeUserId() {
        return inviteeUserId;
    }

    public String getInviterUserId() {
        return inviterUserId;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public String getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
