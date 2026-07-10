import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../api/api";

function Network() {
  const userId = Number(localStorage.getItem("userId"));

  const [keyword, setKeyword] = useState("");
  const [users, setUsers] = useState([]);
  const [pending, setPending] = useState([]);
  const [connections, setConnections] = useState([]);

  const searchUsers = async () => {
    if (!keyword.trim()) {
      alert("Enter name or email to search.");
      return;
    }

    const response = await api.get(
      `/users/search?keyword=${encodeURIComponent(
        keyword
      )}&page=0&size=10&sortBy=firstName`
    );

    setUsers(
      (response.data.data.content || []).filter(
        (user) => user.id !== userId
      )
    );
  };

  const sendRequest = async (receiverId) => {
    try {
      await api.post(
        `/connections/send?senderId=${userId}&receiverId=${receiverId}`
      );

      alert("Connection request sent");
    } catch (error) {
      alert(
        error.response?.data?.message ||
          "Unable to send connection request"
      );
    }
  };

  const loadPending = async () => {
    const response = await api.get(`/connections/pending/${userId}`);
    setPending(response.data.data || []);
  };

  const loadConnections = async () => {
    const response = await api.get(`/connections/my/${userId}`);
    setConnections(response.data.data || []);
  };

  const acceptRequest = async (connectionId) => {
    await api.put(`/connections/${connectionId}/accept`);
    loadPending();
    loadConnections();
  };

  const rejectRequest = async (connectionId) => {
    await api.put(`/connections/${connectionId}/reject`);
    loadPending();
  };

  useEffect(() => {
    loadPending();
    loadConnections();
  }, []);

  return (
    <MainLayout>
      <section className="content-card">
        <h1>Grow Your Network</h1>

        <div className="search-row">
          <input
            value={keyword}
            placeholder="Search by name or email..."
            onChange={(event) => setKeyword(event.target.value)}
          />

          <button onClick={searchUsers}>Search</button>
        </div>

        <div className="user-list">
          {users.map((user) => (
            <div className="user-item" key={user.id}>
              <div>
                <h3>
                  {user.firstName} {user.lastName}
                </h3>
                <p className="muted">{user.email}</p>
                <p>{user.headline || "ConnectPro Member"}</p>
              </div>

              <button onClick={() => sendRequest(user.id)}>
                Connect
              </button>
            </div>
          ))}
        </div>
      </section>

      <section className="content-card">
        <div className="section-heading">
          <h2>Pending Requests</h2>
          <button className="secondary-button" onClick={loadPending}>
            Refresh
          </button>
        </div>

        {pending.length === 0 && (
          <p className="muted">No pending requests.</p>
        )}

        {pending.map((request) => (
          <div className="user-item" key={request.id}>
            <div>
              <h3>{request.senderName}</h3>
              <p className="muted">Wants to connect with you</p>
            </div>

            <div className="button-group">
              <button onClick={() => acceptRequest(request.id)}>
                Accept
              </button>

              <button
                className="danger-button"
                onClick={() => rejectRequest(request.id)}
              >
                Reject
              </button>
            </div>
          </div>
        ))}
      </section>

      <section className="content-card">
        <div className="section-heading">
          <h2>My Connections</h2>
          <button className="secondary-button" onClick={loadConnections}>
            Refresh
          </button>
        </div>

        {connections.length === 0 && (
          <p className="muted">No accepted connections yet.</p>
        )}

        {connections.map((connection) => (
          <div className="user-item" key={connection.id}>
            <div>
              <h3>{connection.receiverName}</h3>
              <p className="success-text">Connected</p>
            </div>
          </div>
        ))}
      </section>
    </MainLayout>
  );
}

export default Network;