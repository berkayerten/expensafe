<template>
  <div class="card login-card" aria-labelledby="loginTitle">
    <h2 id="loginTitle">Welcome back</h2>
    <p class="subtitle">Sign in to your account</p>

    <form @submit.prevent="submit" novalidate>
      <label class="field">
        <span class="label">Username</span>
        <input
            v-model.trim="username"
            type="text"
            autocomplete="username"
            required
            :class="{ invalid: usernameError }"
            @input="usernameError = ''"
            placeholder="e.g. alice"
        />
        <small v-if="usernameError" class="error">{{ usernameError }}</small>
      </label>

      <label class="field">
        <span class="label">Password</span>
        <input
            v-model="password"
            type="password"
            autocomplete="current-password"
            required
            :class="{ invalid: passwordError }"
            @input="passwordError = ''"
            placeholder="your password"
        />
        <small v-if="passwordError" class="error">{{ passwordError }}</small>
      </label>

      <div class="actions">
        <button class="btn primary" :disabled="loading">
          <span v-if="!loading">Sign in</span>
          <span v-else>Signing in…</span>
        </button>

        <button type="button" class="btn link" @click="fillDemo">Use demo</button>
      </div>

      <div class="switch-link">
        <span>Don't have an account?</span>
        <a href="#" class="btn link" @click.prevent="$emit('switch-register')">Register</a>
      </div>

      <p v-if="serverError" class="server-error">{{ serverError }}</p>
    </form>

    <div class="note">By signing in you accept our <a href="#">terms</a>.</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginForm",
  data() {
    return {
      username: "",
      password: "",
      loading: false,
      usernameError: "",
      passwordError: "",
      serverError: ""
    };
  },
  methods: {
    validate() {
      this.usernameError = "";
      this.passwordError = "";
      if (!this.username) this.usernameError = "Username is required";
      if (!this.password) this.passwordError = "Password is required";
      return !this.usernameError && !this.passwordError;
    },
    async submit() {
      if (!this.validate()) return;
      this.loading = true;
      this.serverError = "";
      try {
        const res = await axios.post("http://localhost:8185/api/v1/login", {
          username: this.username,
          password: this.password
        });
        const token = res.data.token;
        localStorage.setItem("token", token);
        // emit so parent can route to dashboard
        this.$emit("logged-in");
      } catch (err) {
        if (err.response && err.response.data) {
          // try to show helpful message if backend provides one
          this.serverError = err.response.data.message || JSON.stringify(err.response.data);
        } else {
          this.serverError = err.message || "Login failed";
        }
      } finally {
        this.loading = false;
      }
    },
    fillDemo() {
      this.username = "alice";
      this.password = "secret";
    }
  }
};
</script>

<style scoped>
.card {
  background: var(--card);
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(15, 23, 42, 0.06);
  padding: 20px;
  border: 1px solid rgba(15,23,42,0.04);
}

.login-card {
  padding: 28px;
}

h2 {
  margin: 0;
  font-size: 20px;
}
.subtitle {
  margin: 6px 0 18px;
  color: var(--muted);
  font-size: 13px;
}

.field {
  display: block;
  margin-bottom: 12px;
}
.label {
  display:block;
  font-size: 13px;
  color: var(--muted);
  margin-bottom: 6px;
}

input {
  width: 100%;
  padding: 10px 12px;
  border-radius: 8px;
  border: 1px solid #e6e9ef;
  box-sizing: border-box;
  font-size: 14px;
  outline: none;
  transition: box-shadow .12s, border-color .12s;
  background: linear-gradient(180deg, #fff, #fcfdff);
}
input:focus {
  border-color: var(--accent);
  box-shadow: 0 4px 16px rgba(37,99,235,0.08);
}
input.invalid {
  border-color: var(--danger);
}

.error {
  color: var(--danger);
  font-size: 12px;
  display: block;
  margin-top: 6px;
}

.actions {
  display:flex;
  gap: 12px;
  align-items: center;
  margin-top: 10px;
}

.btn {
  padding: 10px 14px;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  font-weight: 600;
  font-size: 14px;
}
.btn.primary {
  background: var(--primary-color);
  color: var(--primary-color-text);
}
.btn.primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.btn.link {
  background: transparent;
  color: var(--accent);
  font-weight: 600;
  border: 1px solid rgba(37,99,235,0.08);
}

/* server error */
.server-error {
  margin-top: 12px;
  color: var(--danger);
  font-weight: 500;
  font-size: 13px;
}

/* small note */
.note {
  margin-top: 16px;
  color: var(--muted);
  font-size: 12px;
}

.switch-link {
  margin-top: 16px;
  font-size: 13px;
  color: var(--muted);
  display: flex;
  gap: 6px;
  align-items: center;
}
</style>
