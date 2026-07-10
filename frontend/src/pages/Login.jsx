import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";

function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value,
    });
  };

  const handleLogin = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const response = await api.post("/auth/login", form);
      const loginData = response.data.data;

      localStorage.setItem("token", loginData.token);
      localStorage.setItem("userId", loginData.userId);
      localStorage.setItem("email", loginData.email);
      localStorage.setItem("role", loginData.role);

      navigate("/home");
    } catch (error) {
      setMessage(
        error.response?.data?.message || "Invalid email or password"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-info">
        <h1>ConnectPro</h1>
        <h2>Build your professional network</h2>
        <p>
          Share professional updates, connect with developers and grow your
          career.
        </p>
      </div>

      <form className="auth-card" onSubmit={handleLogin}>
        <h2>Welcome Back</h2>
        <p className="muted">Login to continue to ConnectPro</p>

        {message && <div className="error-message">{message}</div>}

        <label>Email</label>
        <input
          type="email"
          name="email"
          value={form.email}
          placeholder="Enter your email"
          onChange={handleChange}
          required
        />

        <label>Password</label>
        <input
          type="password"
          name="password"
          value={form.password}
          placeholder="Enter your password"
          onChange={handleChange}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

        <p className="auth-link">
          New to ConnectPro? <Link to="/register">Create account</Link>
        </p>
      </form>
    </div>
  );
}

export default Login;