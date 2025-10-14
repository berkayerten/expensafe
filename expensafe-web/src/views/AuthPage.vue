<script>
import LoginForm from "../components/LoginForm.vue";
import RegisterForm from "../components/RegisterForm.vue";

export default {
  name: "AuthPage",
  components: { LoginForm, RegisterForm },
  data() {
    return { showLogin: true };
  },
  methods: {
    onLoggedIn() {
      this.$router.push("/overview");
    },
    switchToRegister() {
      this.showLogin = false;
    },
    switchToLogin() {
      this.showLogin = true;
    }
  }
};
</script>

<template>
  <div class="auth-root">
    <div class="brand">
      <h1>Expensafe</h1>
      <p class="tag">Sign in or create an account to continue.</p>
    </div>
    <div class="cards">
      <LoginForm
          v-if="showLogin"
          @logged-in="onLoggedIn"
          @switch-register="switchToRegister"
      />
      <RegisterForm
          v-else
          @switch-login="switchToLogin"
      />
    </div>
    <footer class="attribution">
      <small>Built with ♥ • ValBerk</small>
    </footer>
  </div>
</template>

<style scoped>

.auth-root {
  min-height: 100vh;
  padding: 48px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: var(--surface-ground);
  color: var(--text-color);
  font-family: var(--font-family);
}

/* cards container */
.cards {
  display: flex;
  justify-content: center;
  width: min(980px, 96%);
  align-items: start;
}

/* footer */
.brand .tag,
.attribution {
  color: var(--text-color-secondary);
}

</style>
