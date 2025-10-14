package com.techthrivecatalyst.expensafe.expense.expensecategorymembership;

import com.techthrivecatalyst.expensafe.expense.expensecategory.ExpenseCategoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "expense_category_membership")
public class ExpenseCategoryMembershipEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String userId;

    @OneToOne
    private ExpenseCategoryEntity expenseCategoryEntity;

    @Column
    private boolean favorite;

    public ExpenseCategoryMembershipEntity() {
    }

    private ExpenseCategoryMembershipEntity(String userId, ExpenseCategoryEntity expenseCategoryEntity,
            boolean isFavorite) {
        this.userId = userId;
        this.expenseCategoryEntity = expenseCategoryEntity;
        this.favorite = isFavorite;
    }

    public static ExpenseCategoryMembershipEntity create(String userId, ExpenseCategoryEntity expenseCategoryEntity,
            boolean isFavorite) {
        var membership = new ExpenseCategoryMembershipEntity(userId, expenseCategoryEntity, isFavorite);
        membership.id = randomUUID().toString();
        return membership;
    }

    public String getId() {
        return id;
    }

    public ExpenseCategoryEntity getExpenseCategoryEntity() {
        return expenseCategoryEntity;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
