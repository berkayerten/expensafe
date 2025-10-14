<template>
  <div class="page-container">
    <div class="left-section">
      <h2>Hi, {{ username }} ! 👋</h2>

      <UILibCard class="chart-card">
        <template #title>
          Your Month in Review
        </template>
        <template #content>
          <UILibChart type="doughnut" :data="chartData" :options="chartOptions" :plugins="[centerTextPlugin]"/>
        </template>
      </UILibCard>
    </div>


    <div class="right-section">
      <div class="expense-categories-box">
        <div class="expense-categories-header">
          <h3 class="expense-categories-header-title">Your Expense Categories</h3>
          <UILibButton label="New +" severity="contrast" variant="text" raised @click="openNewCategoryDialog"/>
        </div>

        <CategoryCardActions ref="cardActions"/>

        <div class="card-grid">
          <CategoryCard
              v-for="category in expenseCategories"
              :key="category.id"
              :category="category"
              @open-menu="handleOpenMenu"
          />
        </div>
      </div>
    </div>

    <UILibDialog v-model:visible="isDialogVisible" modal header="Add New Expense Category" :style="{ width: '25rem' }">
      <div class="dialog-form">
        <div class="form-field">
          <label for="category-name">Name</label>
          <UILibInputText id="category-name" v-model="newCategory.name"/>
          <UILibMessage size="small" severity="secondary" variant="simple">ie; Groceries, Utilities, Entertainment,
            Health & Wellness, etc.
          </UILibMessage>
        </div>
        <div class="form-field">
          <label for="category-budget">Budget</label>
          <UILibInputNumber id="category-budget" v-model="newCategory.budget" mode="currency" currency="USD"
                            locale="en-US"/>
        </div>
        <div class="form-field-checkbox">
          <UILibCheckbox v-model="newCategory.favorite" inputId="category-favorite" :binary="true"/>
          <label for="category-favorite">Add to Favorites</label>
        </div>
      </div>
      <template #footer>
        <UILibButton label="Cancel" text severity="secondary" @click="isDialogVisible = false"/>
        <UILibButton label="Save" severity="primary" @click="saveNewCategory"/>
      </template>
    </UILibDialog>

  </div>
</template>

<script>
import CategoryCard from '../components/CategoryCard.vue';
import CategoryCardActions from '../components/CategoryCardActions.vue';
import axios from "axios";

export default {
  name: 'UserDashboard',
  components: {
    CategoryCard,
    CategoryCardActions
  },
  data() {
    return {
      username: 'Berkay',
      chartData: {
        labels: ['Groceries', 'Utilities', 'Transport'],
        datasets: [
          {
            data: [540, 325, 175],
            backgroundColor: [
              '#FF6384',
              '#36A2EB',
              '#FFCE56'
            ]
          }
        ]
      },
      chartOptions: {
        plugins: {
          legend: {
            labels: {
              color: '#495057' // Color for the legend text
            }
          }
        },
        cutout: '70%' // Makes the doughnut hole smaller/larger
      },
      centerTextPlugin: {
        id: 'centerText',
        afterDraw(chart) {
          const {ctx, chartArea: {width, height}} = chart;
          ctx.save();

          const centerX = width / 2;
          const centerY = height / 2;

          // First line
          ctx.font = 'bold 25px sans-serif';
          ctx.textAlign = 'center';
          ctx.textBaseline = 'middle';
          ctx.fillStyle = '#333';
          ctx.fillText('October', centerX, centerY - 10); // move up a bit for first line

          // Second line
          ctx.font = 'bold 25px sans-serif';
          ctx.fillStyle = '#333';
          ctx.fillText('2025', centerX, centerY + 15); // move down for second line

          ctx.restore();
        }
      },
      expenseCategories: [],
      selectedCategory: null,
      lastMenuEvent: null,
      isDialogVisible: false,
      newCategory: {
        name: '',
        budget: null,
        favorite: false
      }
    };
  },
  methods: {
    async saveNewCategory() {
      try {
        const response = await axios.post('http://localhost:8185/api/v1/expense-categories', this.newCategory, {
          headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
        });
        response.data.amountSpent = 0;
        console.log(response);
        this.expenseCategories.push(response.data);
        this.isDialogVisible = false;
      } catch (error) {
        console.error('Failed to save category:', error);
      }
    },
    async fetchExpenseCategories() {
      try {
        const response = await axios.get('http://localhost:8185/api/v1/expense-categories', {
          headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
        });
        this.expenseCategories = response.data.content;

        this.chartData.labels = this.expenseCategories.map(cat => cat.name);
        this.chartData.datasets[0].data = this.expenseCategories.map(cat => cat.amountSpent || 0);
      } catch (error) {
        console.error('Failed to fetch categories:', error);
      }
    },
    openNewCategoryDialog() {
      this.newCategory = {
        name: '',
        budget: null,
        favorite: false
      };
      this.isDialogVisible = true;
    },
    handleOpenMenu(event) {
      this.$refs.cardActions.open(event);
    }
  },
  mounted() {
    this.fetchExpenseCategories();
  }
  };
</script>

<style scoped>

.page-container {
  display: flex;
  padding: 1.5rem;
  gap: 1.5rem;
  min-height: 100vh;
  background-color: #f8f9fa;
}

.left-section {
  flex: 1;
  background-color: #ffffff;
  padding: 1rem;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.right-section {
  flex: 2;
  background-color: #ffffff;
  padding: 1rem;
  border-radius: 8px;
}

.expense-categories-box {
  border: 1px solid #dee2e6;
  border-radius: 6px;
  padding: 1.5rem;
  height: 100%;
}

.expense-categories-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.expense-categories-header-title {
  flex-grow: 1;
  text-align: center;
  margin: 0;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
  margin-top: 1.5rem;
}

h2, h3 {
  margin: 0;
}

.chart-card {
  box-shadow: none;
}

.dialog-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  padding-top: 1rem;
}

.form-field {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-field-checkbox {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

:deep(.p-inputtext),
:deep(.p-inputnumber) {
  width: 100%;
}

</style>