<template>
  <div class="dashboard-root">
    <!-- Section 1: Receipts List -->
    <UILibCard class="receipts">
      <template #title>
        <div class="receipts-header">
          <h2>Your Purchases This Month</h2>
          <UILibButton
              icon="pi pi-plus"
              rounded
              severity="primary"
              @click="showModal = true"
              aria-label="Add receipt"
          />
        </div>
      </template>
      <template #content>
        <ul>
          <li v-for="r in receipts" :key="r.id">
            <span>{{ r.date }}</span> —
            <span>{{ r.store }}</span> —
            <strong>{{ currencify(r.amount) }}</strong>
          </li>
        </ul>
      </template>
    </UILibCard>

    <!-- Section 2: Pie Chart -->
    <UILibCard class="chart">
      <template #title>
        <h2>Spending by Store</h2>
      </template>
      <template #content>
        <UILibChart type="doughnut" :data="chartData" :options="chartOptions" style="max-width: 400px"/>
      </template>
    </UILibCard>

    <!-- Section 3: Budget -->
    <UILibCard class="budget">
      <template #content>
        <div class="budget-header">
          <span>Monthly Budget</span>
          <br>
          <span class="budget-left">{{ currencify(budgetLeft) }} left</span>
        </div>
        <div class="budget-amount">{{ currencify(budget) }}</div>
      </template>
    </UILibCard>

    <UILibDialog v-model:visible="showModal" modal header="Add Receipt" :style="{ width: '25rem' }">
      <div class="dialog-form">
        <div class="card flex justify-center">
          <label for="expense-date">Date</label>
          <UILibDatePicker v-model="newReceipt.date" showIcon fluid :showOnFocus="false"/>
        </div>
        <div class="card flex justify-center">
          <UILibSelect v-model="newReceipt.store" :options=stores placeholder="Select a Store"
                       class="w-full md:w-56"/>
        </div>
        <div>
          <IftaLabel>
            <UILibInputNumber
                v-model="newReceipt.amount"
                inputId="total_input"
                mode="currency"
                currency="EUR"
                locale="en-US"
                variant="filled"
                :min="0"
                :step="0.01"/>
            <label for="total_input">Total</label>
          </IftaLabel>
        </div>
      </div>
      <template #footer>
        <UILibButton label="Cancel" text severity="secondary" @click="showModal = false"/>
        <UILibButton label="Save" severity="primary" @click="saveReceipt"/>
      </template>
    </UILibDialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ExpenseOverview",
  data() {
    return {
      receipts: [
        {id: 1, date: "2025-10-01", store: "REWE", amount: 45.2},
        {id: 2, date: "2024-10-02", store: "ALDI", amount: 18.99}
      ],
      budget: 400.0,
      spent: 64.19,
      showModal: false,
      newReceipt: {date: "", store: "", amount: null, expenseCategoryId: this.$route.params.id},
      stores: ["Lidl", "ALDI", "REWE", "Edeka", "Penny", "Rossmann", "Budnikowsky","MARKANT", "Other"],
      chartData: {
        labels: [],
        datasets: [
          {
            data: [],
            backgroundColor: [
              "#2563eb",
              "#16a34a",
              "#f59e42",
              "#e11d48",
              "#a21caf",
              "#0ea5e9",
              "#fbbf24",
              "#64748b"
            ]
          }
        ]
      },
      chartOptions: {
        plugins: {legend: {position: "bottom"}}
      }
    };
  },
  methods: {
    currencify(val) {
      return "€" + Number(val).toFixed(2);
    },
    closeModal() {
      this.showModal = false;
      this.newReceipt = {date: "", store: "", amount: null};
    },
    async saveReceipt() {
      try {
        const res = await axios.post("http://localhost:8185/api/v1/expenses", this.newReceipt, {
          headers: {Authorization: `Bearer ${localStorage.getItem("token")}`}
        });
        this.receipts.push({
          ...this.newReceipt,
          id: res.data.id || Date.now()
        });
        this.spent += Number(this.newReceipt.amount);
        this.closeModal();
      } catch (e) {
        alert("Failed to save receipt: " + (e.response?.data?.message || e.message));
      }
    },
    async fetchExpensesByCategory(categoryId) {
      try {
        const res = await axios.get(`http://localhost:8185/api/v1/expenses?categoryId=${categoryId}`, {
          headers: {Authorization: `Bearer ${localStorage.getItem("token")}`}
        });
        this.receipts = (res.data.content || []).map(r => ({
          id: r.id,
          date: r.date,
          store: r.store,
          amount: r.amount ?? r.price
        }));
        this.spent = this.receipts.reduce((sum, r) => sum + Number(r.amount), 0);
        this.updateChart();
      } catch (e) {
        alert("Failed to load receipts: " + (e.response?.data?.message || e.message));
      }
    },
    updateChart() {
      const storeTotals = {};
      this.receipts.forEach(r => {
        storeTotals[r.store] = (storeTotals[r.store] || 0) + Number(r.amount);
      });
      this.chartData.labels = Object.keys(storeTotals);
      this.chartData.datasets[0].data = Object.values(storeTotals);
    }
  },
  computed: {
    budgetLeft() {
      return this.budget - this.spent;
    }
  },
  mounted() {
    const categoryId = this.$route.params.id;
    this.fetchExpensesByCategory(categoryId);
    this.updateChart();
  }
};
</script>

<style scoped>
.dashboard-root {
  display: grid;
  grid-template-columns: 2fr 1.2fr 1fr;
  gap: 24px;
  padding: 40px 24px;
  background: var(--surface-ground);
  min-height: 100vh;
}

.receipts ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.receipts li {
  padding: 10px 0;
  border-bottom: 1px solid var(--surface-border);
  font-size: 15px;
  color: var(--text-color);
}

.receipts li:last-child {
  border-bottom: none;
}

.chart {
  align-items: center;
  justify-content: center;
}

.budget {
  align-items: flex-end;
  justify-content: flex-start;
}

.budget-header {
  display: flex;
  justify-content: space-between;
  width: 100%;
  font-size: 15px;
  color: var(--text-color-secondary);
  margin-bottom: 10px;
}

.budget-amount {
  font-size: 32px;
  font-weight: 700;
  color: var(--primary-color);
}

.budget-left {
  color: var(--green-400);
  font-weight: 600;
}
</style>
