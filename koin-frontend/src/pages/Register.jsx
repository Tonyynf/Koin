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
    <div style={{ maxWidth: 400, margin: "100px auto", padding: 24 }}>
      <h2>Cadastro</h2>
      {erro && <p style={{ color: "red" }}>{erro}</p>}
      <input
        placeholder="Nome"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
        style={{ display: "block", width: "100%", marginBottom: 12, padding: 8 }}
      />
      <input
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        style={{ display: "block", width: "100%", marginBottom: 12, padding: 8 }}
      />
      <input
        type="password"
        placeholder="Senha"
        value={senha}
        onChange={(e) => setSenha(e.target.value)}
        style={{ display: "block", width: "100%", marginBottom: 12, padding: 8 }}
      />
      <button onClick={handleRegister} style={{ width: "100%", padding: 10 }}>
        Cadastrar
      </button>
      <p>Já tem conta? <Link to="/login">Entrar</Link></p>
    </div>
  );
}