import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../api/api";

function Profile() {
  const userId = localStorage.getItem("userId");

  const [profile, setProfile] = useState(null);
  const [form, setForm] = useState({
    headline: "",
    about: "",
    location: "",
    profileImage: "",
    coverImage: "",
  });

  const loadProfile = async () => {
    const response = await api.get(`/users/${userId}`);
    setProfile(response.data.data);

    setForm({
      headline: response.data.data.headline || "",
      about: "",
      location: response.data.data.location || "",
      profileImage: response.data.data.profileImage || "",
      coverImage: "",
    });
  };

  useEffect(() => {
    loadProfile();
  }, []);

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value,
    });
  };

  const updateProfile = async () => {
    const response = await api.put(`/users/${userId}/profile`, form);
    setProfile(response.data.data);
    alert("Profile updated successfully");
  };

  if (!profile) {
    return (
      <MainLayout>
        <div className="content-card">Loading profile...</div>
      </MainLayout>
    );
  }

  return (
    <MainLayout>
      <section className="profile-card">
        <div className="profile-cover"></div>

        <div className="profile-avatar">
          {profile.firstName?.charAt(0)}
          {profile.lastName?.charAt(0)}
        </div>

        <h1>
          {profile.firstName} {profile.lastName}
        </h1>

        <p className="profile-headline">
          {profile.headline || "Java Full Stack Developer"}
        </p>

        <p className="muted">{profile.email}</p>
        <p className="muted">{profile.location || "Location not added"}</p>
      </section>

      <section className="content-card profile-form">
        <h2>Edit Profile</h2>

        <label>Professional Headline</label>
        <input
          name="headline"
          value={form.headline}
          placeholder="Java Full Stack Developer"
          onChange={handleChange}
        />

        <label>About</label>
        <textarea
          name="about"
          value={form.about}
          placeholder="Write something about yourself..."
          onChange={handleChange}
        />

        <label>Location</label>
        <input
          name="location"
          value={form.location}
          placeholder="Ghaziabad, Uttar Pradesh"
          onChange={handleChange}
        />

        <label>Profile Image URL</label>
        <input
          name="profileImage"
          value={form.profileImage}
          placeholder="Optional image URL"
          onChange={handleChange}
        />

        <button onClick={updateProfile}>Save Profile</button>
      </section>
    </MainLayout>
  );
}

export default Profile;