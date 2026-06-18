import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../services/api";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await api.post("/auth/login", { email, senha });
      localStorage.setItem("token", response.data.token);
      navigate("/dashboard");
    } catch (e) {
      setErro("Email ou senha inválidos");
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
       <h2 style={{
           textAlign: "center",
           color: "#7373FF",
           marginBottom: 22,
           fontSize: "35px"
         }}
       >
         Login
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
         onClick={handleLogin}
         style={{
            width: "100%",
                 boxSizing: "border-box",
                 padding: 12,
                 background: "#3C3CE8",
                 color: "#fff",
                 border: "none",
                 borderRadius: 8,
                 cursor: "pointer",
                 fontWeight: "bold",
         }}
       >
         Entrar
       </button>

       <p
         style={{
           textAlign: "center",
           marginTop: 20,
         }}
       >
         Não tem conta?{" "}
         <Link
           to="/register"
           style={{
             color: "#7373FF",
             textDecoration: "none",
             fontWeight: "bold",
           }}
         >
           Cadastre-se
         </Link>
       </p>
     </div>
   </div>
  );
}