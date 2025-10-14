package com.techthrivecatalyst.expensafe.expense.expensecategory;

import com.techthrivecatalyst.expensafe.expense.expensecategorymembership.ExpenseCategoryMembershipEntity;
import com.techthrivecatalyst.expensafe.expense.expensecategorymembership.ExpenseCategoryMembershipRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository repository;
    private final ExpenseCategoryMembershipRepository membershipRepository;

    public ExpenseCategoryServiceImpl(ExpenseCategoryRepository repository,
            ExpenseCategoryMembershipRepository membershipRepository) {
        this.repository = repository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Page<ExpenseCategory> list(Pageable pageable, UUID userId) {
        Page<ExpenseCategoryMembershipEntity> memberships = membershipRepository.findAllByUserId(userId.toString(),
                pageable);
        return memberships
                .map(membership -> toDto(membership.getExpenseCategoryEntity(), membership.isFavorite()));
    }

    @Override
    public ExpenseCategory save(ExpenseCategory category, UUID userId) {
        var entity = ExpenseCategoryEntity.create(category.name(), category.budget());
        entity = repository.save(entity);
        createMembership(entity, userId.toString(), category.isFavorite());
        return toDto(entity, category.isFavorite());
    }

    private void createMembership(ExpenseCategoryEntity entity, String userId, boolean isFavorite) {
        var expenseCategoryMembership = ExpenseCategoryMembershipEntity.create(userId, entity, isFavorite);
        membershipRepository.save(expenseCategoryMembership);
    }

    private ExpenseCategory toDto(ExpenseCategoryEntity entity, boolean isFavorite) {
        return new ExpenseCategory(UUID.fromString(entity.getId()), entity.getName(),
                entity.getBudget(), isFavorite);
    }
}
