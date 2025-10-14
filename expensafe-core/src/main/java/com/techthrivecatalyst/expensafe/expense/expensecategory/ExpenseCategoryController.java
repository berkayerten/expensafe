package com.techthrivecatalyst.expensafe.expense.expensecategory;

import com.techthrivecatalyst.expensafe.expense.ExpenseService;
import com.techthrivecatalyst.expensafe.security.api.UserExtractor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/expense-categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService service;
    private final ExpenseService expenseService;
    private final UserExtractor userExtractor;

    public ExpenseCategoryController(ExpenseCategoryService service, ExpenseService expenseService,
            UserExtractor userExtractor) {
        this.service = service;
        this.expenseService = expenseService;
        this.userExtractor = userExtractor;
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseCategoryResponse>> list(@PageableDefault Pageable pageable) {
        Page<ExpenseCategoryResponse> response = service.list(pageable, userExtractor.getCurrentUserId())
                .map(expenseCategory -> toResponse(expenseCategory,
                expenseService.getTotalAmountSpentInCategory(expenseCategory.id()).doubleValue()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ExpenseCategory> save(@RequestBody ExpenseCategoryRequest request) {
        ExpenseCategory expenseCategory = new ExpenseCategory(null, request.getName(),
                request.getBudget(), request.isFavorite());
        ExpenseCategory savedExpenseCategory = service.save(expenseCategory, userExtractor.getCurrentUserId());
        return ResponseEntity.ok(savedExpenseCategory);
    }

    private ExpenseCategoryResponse toResponse(ExpenseCategory expenseCategory, double amountSpent) {
        return new ExpenseCategoryResponse(expenseCategory.id().toString(), expenseCategory.name(),
                expenseCategory.budget().doubleValue(), expenseCategory.isFavorite(), amountSpent);
    }
}
