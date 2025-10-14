<template>
  <UILibCard class="category-card">
    <template #title>
      <div class="card-header">
        <div class="category-name">{{ category.name }}</div>
        <UILibButton
            icon="pi pi-ellipsis-v"
            class="p-button-text p-button-rounded p-button-sm context-menu-btn"
            @click="$emit('open-menu', $event)"
        />
      </div>
    </template>

    <template #content>
      <div class="category-amount">${{ category.amountSpent.toLocaleString() }}</div>
    </template>

    <template #footer>
      <div class="card-footer">
        <div class="footer-icons-left">
          <i v-if="category.isFavorite" class="pi pi-heart-fill favorite-icon-active"></i>
          <i v-if="category.isShared" class="pi pi-users shared-icon"></i>
        </div>

        <router-link :to="`/expense/${category.id}`" class="details-arrow-link">
          <i class="pi pi-arrow-right"></i>
        </router-link>
      </div>
    </template>
  </UILibCard>
</template>

<script>

export default {
  name: 'CategoryCard',
  props: {
    // This defines the 'category' prop that the parent will pass down
    category: {
      type: Object,
      required: true
    }
  },
  emits: ['open-menu'], // Declare the events this component can emit
};
</script>

<style scoped>
/* All the .category-card specific styles are now moved here */
.category-card {
  display: flex;
  flex-direction: column;
  transition: all 0.2s ease-in-out;
}

.category-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

.card-header {
  display: flex;
  justify-content: center; /* Center the name */
  align-items: center;
  position: relative; /* For positioning the menu button */
  width: 100%;
}

.category-name {
  text-align: center;
  font-weight: 600;
  color: #495057;
}

.context-menu-btn {
  position: absolute;
  top: -8px; /* Adjust as needed */
  right: -8px; /* Adjust as needed */
}

.category-amount {
  font-size: 2rem;
  font-weight: bold;
  color: #343a40;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.footer-icons-left {
  display: flex;
  align-items: center;
  gap: 0.5rem; /* Space between heart and user icon */
}

.shared-icon {
  font-size: 1.5rem;
  color: #6c757d; /* A neutral color */
}

.favorite-icon-active {
  font-size: 1.5rem;
  padding: 0.5rem;
  border-radius: 50%;
}

.favorite-icon {
  color: #adb5bd;
}

.favorite-icon-active {
  color: #e5194a;
}

.details-arrow-link {
  font-size: 1.2rem;
  color: #6c757d;
  padding: 0.5rem;
  border-radius: 50%;
  transition: all 0.2s;
  text-decoration: none;
}

.details-arrow-link:hover {
  background-color: #f8f9fa;
  color: #343a40;
}

/* PrimeVue Overrides */
:deep(.p-card-content) {
  flex-grow: 1;
  padding: 0.5rem 0;
  overflow: hidden;
}

:deep(.p-card-title) {
  text-align: center;
}

:deep(.p-card-footer) {
  padding: 0 0.25rem;
}
</style>