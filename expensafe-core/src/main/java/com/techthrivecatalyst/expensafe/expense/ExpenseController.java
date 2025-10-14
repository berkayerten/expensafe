package com.techthrivecatalyst.expensafe.expense;

import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<Expense> save(@RequestBody ExpenseRequest expenseRequest) {
        Expense newExpense = map(expenseRequest);
        return ResponseEntity.ok(service.save(newExpense));
    }

    private static Expense map(ExpenseRequest expenseRequest) {
        ZoneId zone = ZoneId.of("Europe/Berlin");
        LocalDate localDate = expenseRequest.getDate().atZone(zone).toLocalDate();
        return new Expense(null, localDate, expenseRequest.getStore(), expenseRequest.getAmount(),
                expenseRequest.getExpenseCategoryId());
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<Page<Expense>> list(@RequestParam(name = "categoryId") String expenseCategoryId,
            @PageableDefault(value = 30) Pageable pageable) {
        return ResponseEntity.ok(service.list(expenseCategoryId, pageable));
    }
}
