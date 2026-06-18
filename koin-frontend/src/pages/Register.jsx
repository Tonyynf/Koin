import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../services/api";

export default function Register() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const response = await api.post("/auth/register", { nome, email, senha });
      localStorage.setItem("token", response.data.token);
      navigate("/dashboard");
    } catch (e) {
      setErro("Erro ao cadastrar. Tente outro email.");
    }
  };

  return (
      <div
      style={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        style={{
          width: "100%",
          maxWidth: 400,
          background: "#111184",
          padding: 32,
          borderRadius: 16,
          boxShadow: "0 10px 30px rgba(0,0,0,0.4)",
          color: "#fff",
        }}
      >
        <h2
          style={{
            textAlign: "center",
            color: "#7373FF",
            marginBottom: 24,
            fontSize: "35px"
          }}
        >
          Cadastro
        </h2>

        {erro && (
          <p
            style={{
              background: "#dc2626",
              color: "#fff",
              padding: 10,
              borderRadius: 8,
              marginBottom: 16,
            }}
          >
            {erro}
          </p>
        )}

        <input
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          style={{
             marginBottom: 14,
             width: "100%",
             boxSizing: "border-box",
             padding: 12,
             borderRadius: 8,
             border: "1px solid #3C3CE8",
             background: "#070738",
             color: "#fff",
          }}
        />

        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={{
             marginBottom: 14,
             width: "100%",
             boxSizing: "border-box",
             padding: 12,
             borderRadius: 8,
             border: "1px solid #3C3CE8",
             background: "#070738",
             color: "#fff",
          }}
        />

        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          style={{
             marginBottom: 20,
             width: "100%",
             boxSizing: "border-box",
             padding: 12,
             borderRadius: 8,
             border: "1px solid #3C3CE8",
             background: "#070738",
             color: "#fff",
          }}
        />

        <button
          onClick={handleRegister}
          style={{
            width: "100%",
            padding: 12,
            background: "#3C3CE8",
            color: "#fff",
            border: "none",
            borderRadius: 8,
            cursor: "pointer",
            fontWeight: "bold",
          }}
        >
          Cadastrar
        </button>

        <p
          style={{
            textAlign: "center",
            marginTop: 20,
          }}
        >
          Já tem conta?{" "}
          <Link
            to="/login"
            style={{
              color: "#7373FF",
              textDecoration: "none",
              fontWeight: "bold",
            }}
          >
            Entrar
          </Link>
        </p>
      </div>
    </div>
  );
}