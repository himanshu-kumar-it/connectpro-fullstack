import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";

function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
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

  const handleRegister = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await api.post("/users/register", form);
      alert("Registration successful. Please login.");
      navigate("/");
    } catch (error) {
      setMessage(
        error.response?.data?.message || "Registration failed"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-info">
        <h1>ConnectPro</h1>
        <h2>Start building your career network</h2>
        <p>Create your account and connect with other professionals.</p>
      </div>

      <form className="auth-card" onSubmit={handleRegister}>
        <h2>Create Account</h2>

        {message && <div className="error-message">{message}</div>}

        <div className="two-column">
          <input
            name="firstName"
            value={form.firstName}
            placeholder="First name"
            onChange={handleChange}
            required
          />

          <input
            name="lastName"
            value={form.lastName}
            placeholder="Last name"
            onChange={handleChange}
            required
          />
        </div>

        <input
          type="email"
          name="email"
          value={form.email}
          placeholder="Email"
          onChange={handleChange}
          required
        />

        <input
          name="phone"
          value={form.phone}
          placeholder="10-digit phone number"
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          value={form.password}
          placeholder="Password"
          onChange={handleChange}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? "Creating account..." : "Register"}
        </button>

        <p className="auth-link">
          Already registered? <Link to="/">Login</Link>
        </p>
      </form>
    </div>
  );
}

export default Register;