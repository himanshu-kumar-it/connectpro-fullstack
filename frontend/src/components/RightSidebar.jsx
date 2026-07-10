import { FaArrowRight, FaBriefcase, FaCode, FaUsers } from "react-icons/fa";
import { Link } from "react-router-dom";

function RightSidebar() {
  return (
    <aside className="right-sidebar">
      <section className="right-card">
        <div className="right-card-heading">
          <h3>ConnectPro Insights</h3>
          <span>Today</span>
        </div>

        <div className="insight-item">
          <div className="insight-icon">
            <FaCode />
          </div>
          <div>
            <strong>Java Developers</strong>
            <p>Grow your backend development network</p>
          </div>
        </div>

        <div className="insight-item">
          <div className="insight-icon">
            <FaBriefcase />
          </div>
          <div>
            <strong>Career Updates</strong>
            <p>Share projects and professional milestones</p>
          </div>
        </div>

        <div className="insight-item">
          <div className="insight-icon">
            <FaUsers />
          </div>
          <div>
            <strong>Developer Community</strong>
            <p>Connect with other technology professionals</p>
          </div>
        </div>
      </section>

      <section className="right-card network-promotion">
        <div className="network-promotion-icon">
          <FaUsers />
        </div>

        <h3>Grow your network</h3>

        <p>
          Search professionals, send connection requests and build your
          community.
        </p>

        <Link to="/network">
          Explore Network <FaArrowRight />
        </Link>
      </section>

      <div className="footer-links">
        <span>About</span>
        <span>Community</span>
        <span>Privacy</span>
        <span>ConnectPro © 2026</span>
      </div>
    </aside>
  );
}

export default RightSidebar;