import {
  FaHome,
  FaUserFriends,
  FaUser,
  FaSignOutAlt,
  FaSearch,
} from "react-icons/fa";
import { NavLink, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <header className="top-navbar">
      <div className="navbar-content">
        <NavLink to="/home" className="navbar-logo">
          <span className="logo-symbol">C</span>
          <span>ConnectPro</span>
        </NavLink>

        <div className="navbar-search">
          <FaSearch />
          <input
            type="text"
            placeholder="Search professionals, posts and skills"
          />
        </div>

        <nav className="navbar-menu">
          <NavLink to="/home" className="navbar-link">
            <FaHome />
            <span>Home</span>
          </NavLink>

          <NavLink to="/network" className="navbar-link">
            <FaUserFriends />
            <span>Network</span>
          </NavLink>

          <NavLink to="/profile" className="navbar-link">
            <FaUser />
            <span>Profile</span>
          </NavLink>

          <button className="navbar-logout" onClick={logout}>
            <FaSignOutAlt />
            <span>Logout</span>
          </button>
        </nav>
      </div>
    </header>
  );
}

export default Navbar;