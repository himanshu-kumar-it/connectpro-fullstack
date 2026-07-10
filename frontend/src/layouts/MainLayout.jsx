import Navbar from "../components/Navbar";
import RightSidebar from "../components/RightSidebar";

function MainLayout({ children }) {
  const email = localStorage.getItem("email") || "ConnectPro Member";
  const role = localStorage.getItem("role") || "ROLE_USER";

  const initial = email.charAt(0).toUpperCase();

  return (
    <>
      <Navbar />

      <div className="application-shell">
        <aside className="left-sidebar">
          <div className="left-profile-cover"></div>

          <div className="left-profile-avatar">{initial}</div>

          <h3>ConnectPro Member</h3>
          <p className="sidebar-email">{email}</p>

          <div className="profile-divider"></div>

          <div className="sidebar-stat-row">
            <span>Account Role</span>
            <strong>{role.replace("ROLE_", "")}</strong>
          </div>

          <div className="sidebar-stat-row">
            <span>Profile Status</span>
            <strong className="active-status">Active</strong>
          </div>

          <div className="profile-divider"></div>

          <p className="sidebar-message">
            Keep your profile updated and share your latest professional
            achievements.
          </p>
        </aside>

        <main className="page-content">{children}</main>

        <RightSidebar />
      </div>
    </>
  );
}

export default MainLayout;