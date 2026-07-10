import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../api/api";

function Home() {
  const userId = localStorage.getItem("userId");

  const [posts, setPosts] = useState([]);
  const [content, setContent] = useState("");
  const [commentValues, setCommentValues] = useState({});
  const [loading, setLoading] = useState(true);

  const loadPosts = async () => {
    try {
      const response = await api.get("/posts?page=0&size=20");
      setPosts(response.data.data.content || []);
    } catch (error) {
      alert(error.response?.data?.message || "Unable to load posts");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPosts();
  }, []);

  const createPost = async () => {
    if (!content.trim()) {
      alert("Please write something first.");
      return;
    }

    await api.post(`/posts/user/${userId}`, {
      content,
      imageUrl: "",
    });

    setContent("");
    loadPosts();
  };

  const likePost = async (postId) => {
    try {
      await api.post(`/posts/${postId}/like/user/${userId}`);
      alert("Post liked successfully");
    } catch (error) {
      alert(error.response?.data?.message || "Unable to like post");
    }
  };

  const addComment = async (postId) => {
    const comment = commentValues[postId];

    if (!comment?.trim()) {
      alert("Please enter a comment.");
      return;
    }

    await api.post(`/posts/${postId}/comments/user/${userId}`, {
      content: comment,
    });

    setCommentValues({
      ...commentValues,
      [postId]: "",
    });

    alert("Comment added successfully");
  };

  return (
    <MainLayout>
      <section className="hero-card">
        <div>
          <span className="badge">Professional Community</span>
          <h1>Share ideas. Build connections. Grow together.</h1>
          <p>
            ConnectPro helps developers and professionals share career updates
            and build meaningful connections.
          </p>
        </div>
      </section>

      <section className="content-card">
        <h2>Create a post</h2>

        <textarea
          value={content}
          placeholder="Share your professional update..."
          onChange={(event) => setContent(event.target.value)}
        />

        <button onClick={createPost}>Publish Post</button>
      </section>

      <section>
        <div className="section-heading">
          <h2>Latest Posts</h2>
          <button className="secondary-button" onClick={loadPosts}>
            Refresh
          </button>
        </div>

        {loading && <div className="content-card">Loading posts...</div>}

        {!loading && posts.length === 0 && (
          <div className="content-card">No posts available.</div>
        )}

        {posts.map((post) => (
          <article className="post-card" key={post.id}>
            <div className="post-author">
              <div className="post-avatar">
                {post.authorName?.charAt(0) || "U"}
              </div>

              <div>
                <h3>{post.authorName}</h3>
                <p className="muted">
                  {new Date(post.createdAt).toLocaleString()}
                </p>
              </div>
            </div>

            <p className="post-content">{post.content}</p>

            {post.imageUrl && !post.imageUrl.includes("example.com") && (
              <img
                className="post-image"
                src={post.imageUrl}
                alt="Post"
              />
            )}

            <div className="post-actions">
              <button onClick={() => likePost(post.id)}>Like</button>

              <input
                value={commentValues[post.id] || ""}
                placeholder="Write a comment..."
                onChange={(event) =>
                  setCommentValues({
                    ...commentValues,
                    [post.id]: event.target.value,
                  })
                }
              />

              <button onClick={() => addComment(post.id)}>
                Comment
              </button>
            </div>
          </article>
        ))}
      </section>
    </MainLayout>
  );
}

export default Home;