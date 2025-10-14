<template>
  <div class="card register-card" aria-labelledby="registerTitle">
    <h2 id="registerTitle">Create account</h2>
    <p class="subtitle">Start tracking your expenses.</p>

    <form @submit.prevent="submit" novalidate>
      <label class="field">
        <span class="label">Username</span>
        <input v-model.trim="username" type="text" placeholder="choose a username" @input="usernameError = ''" />
        <small v-if="usernameError" class="error">{{ usernameError }}</small>
      </label>

      <label class="field">
        <span class="label">Password</span>
        <input v-model="password" type="password" placeholder="at least 6 characters" @input="passwordError = ''" />
        <small v-if="passwordError" class="error">{{ passwordError }}</small>
      </label>

      <label class="field">
        <span class="label">Confirm Password</span>
        <input v-model="confirm" type="password" placeholder="repeat password" @input="confirmError = ''" />
        <small v-if="confirmError" class="error">{{ confirmError }}</small>
      </label>

      <div class="actions">
        <button class="btn primary" :disabled="loading">
          <span v-if="!loading">Create account</span>
          <span v-else>Creating…</span>
        </button>
        <button type="button" class="btn outline" @click="clear">Clear</button>
      </div>

      <div class="switch-link">
        <span>Already have an account?</span>
        <a href="#" class="btn link" @click.prevent="$emit('switch-login')">Login</a>
      </div>

      <p v-if="serverMsg" :class="{'server-success': serverSuccess, 'server-error': !serverSuccess}">
        {{ serverMsg }}
      </p>
    </form>

    <div class="hint">Passwords are hashed on the server.</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "RegisterForm",
  data() {
    return {
      username: "",
      password: "",
      confirm: "",
      loading: false,
      usernameError: "",
      passwordError: "",
      confirmError: "",
      serverMsg: "",
      serverSuccess: false
    };
  },
  methods: {
    validate() {
      this.usernameError = "";
      this.passwordError = "";
      this.confirmError = "";
      if (!this.username) this.usernameError = "Please choose a username";
      if (!this.password || this.password.length < 6) this.passwordError = "Password must be at least 6 characters";
      if (this.password !== this.confirm) this.confirmError = "Passwords do not match";
      return !(this.usernameError || this.passwordError || this.confirmError);
    },
    async submit() {
      if (!this.validate()) return;
      this.loading = true;
      this.serverMsg = "";
      try {
        await axios.post("http://localhost:8185/api/v1/register", {
          username: this.username,
          password: this.password
        });
        this.serverMsg = "Account created. You can now log in.";
        this.serverSuccess = true;
        this.clear(false);
      } catch (err) {
        this.serverSuccess = false;
        if (err.response && err.response.data) {
          this.serverMsg = err.response.data.message || JSON.stringify(err.response.data);
        } else {
          this.serverMsg = err.message || "Registration failed";
        }
      } finally {
        this.loading = false;
      }
    },
    clear(resetAll = true) {
      if (resetAll) {
        this.username = "";
        this.password = "";
        this.confirm = "";
      }
      this.usernameError = "";
      this.passwordError = "";
      this.confirmError = "";
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

.register-card {
  padding: 28px;
  border-left: 1px solid rgba(15,23,42,0.03);
}

.subtitle {
  margin: 6px 0 18px;
  color: var(--muted);
  font-size: 13px;
}

.field { margin-bottom: 12px; }
.label { display:block; font-size: 13px; color: var(--muted); margin-bottom: 6px; }

input {
  width:100%;
  padding: 10px 12px;
  border-radius: 8px;
  border: 1px solid #e6e9ef;
  box-sizing: border-box;
  font-size: 14px;
  outline: none;
  background: linear-gradient(180deg,#fff,#fcfdff);
}
input:focus { border-color: var(--accent); box-shadow: 0 4px 16px rgba(37,99,235,0.06); }
input.invalid { border-color: var(--danger); }

.error { color: var(--danger); font-size: 12px; margin-top: 6px; display:block; }

.actions { display:flex; gap:12px; align-items:center; margin-top:10px; }

.btn { padding:10px 14px; border-radius:8px; border:none; cursor:pointer; font-weight:600; font-size:14px; }
.btn.primary { background: linear-gradient(180deg,var(--accent),var(--accent-600)); color:white; }
.btn.outline { background: transparent; border: 1px solid rgba(15,23,42,0.06); color: #111827; }

.hint { margin-top: 14px; color: var(--muted); font-size: 12px; }

/* server messages */
.server-success { color: var(--success); margin-top: 12px; font-weight: 600; }
.server-error { color: var(--danger); margin-top: 12px; font-weight: 600; }

.switch-link {
  margin-top: 16px;
  font-size: 13px;
  color: var(--muted);
  display: flex;
  gap: 6px;
  align-items: center;
}
</style>
