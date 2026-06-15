import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Dashboard() {
  const [transacoes, setTransacoes] = useState([]);
  const [contas, setContas] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [form, setForm] = useState({
    descricao: "",
    valor: "",
    tipo: "DESPESA",
    contaId: "",
    categoriaId: "",
  });
  const navigate = useNavigate();

  const carregarDados = () => {
    api.get("/api/transacoes").then((res) => setTransacoes(res.data)).catch(() => navigate("/login"));
    api.get("/api/contas").then((res) => setContas(res.data));
    api.get("/api/categorias").then((res) => setCategorias(res.data));
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      await api.post("/api/transacoes", {
        ...form,
        valor: parseFloat(form.valor),
        contaId: parseInt(form.contaId),
        categoriaId: parseInt(form.categoriaId),
      });
      setForm({ descricao: "", valor: "", tipo: "DESPESA", contaId: "", categoriaId: "" });
      carregarDados();
    } catch (e) {
      alert("Erro ao adicionar transação");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Deseja excluir esta transação?")) return;
    await api.delete(`/api/transacoes/${id}`);
    carregarDados();
  };

  return (
    <div
      style={{
        maxWidth: 900,
        margin: "40px auto",
        padding: 24,
        background: "#111184",
        borderRadius: 16,
        color: "#fff",
        boxShadow: "0 10px 30px rgba(0,0,0,0.3)",
      }}
    >
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: 24,
        }}
      >
        <h2 style={{ margin: 0, color: "#7373FF" }}>Dashboard</h2>

        <button
          onClick={handleLogout}
          style={{
            background: "#3C3CE8",
            color: "#fff",
            border: "none",
            padding: "10px 18px",
            borderRadius: 8,
            cursor: "pointer",
          }}
        >
          Sair
        </button>
      </div>

      <h3 style={{ color: "#7373FF" }}>Nova Transação</h3>

      <div
        style={{
          display: "flex",
          gap: 10,
          flexWrap: "wrap",
          marginBottom: 30,
          background: "#070738",
          padding: 16,
          borderRadius: 12,
        }}
      >
        <input
          name="descricao"
          placeholder="Descrição"
          value={form.descricao}
          onChange={handleChange}
          style={{
            padding: 10,
            flex: 1,
            borderRadius: 8,
            border: "1px solid #3C3CE8",
            background: "#111184",
            color: "#fff",
          }}
        />

        <input
          name="valor"
          placeholder="Valor"
          type="number"
          value={form.valor}
          onChange={handleChange}
          style={{
            padding: 10,
            width: 120,
            borderRadius: 8,
            border: "1px solid #3C3CE8",
            background: "#111184",
            color: "#fff",
          }}
        />

        <select
          name="tipo"
          value={form.tipo}
          onChange={handleChange}
          style={{
            padding: 10,
            borderRadius: 8,
            border: "1px solid #3C3CE8",
            background: "#111184",
            color: "#fff",
          }}
        >
          <option value="DESPESA">Despesa</option>
          <option value="RECEITA">Receita</option>
        </select>

        <select
          name="contaId"
          value={form.contaId}
          onChange={handleChange}
          style={{
            padding: 10,
            borderRadius: 8,
            border: "1px solid #3C3CE8",
            background: "#111184",
            color: "#fff",
          }}
        >
          <option value="">Conta</option>
          {contas.map((c) => (
            <option key={c.id} value={c.id}>
              {c.nome}
            </option>
          ))}
        </select>

        <select
          name="categoriaId"
          value={form.categoriaId}
          onChange={handleChange}
          style={{
            padding: 10,
            borderRadius: 8,
            border: "1px solid #3C3CE8",
            background: "#111184",
            color: "#fff",
          }}
        >
          <option value="">Categoria</option>
          {categorias.map((c) => (
            <option key={c.id} value={c.id}>
              {c.nome}
            </option>
          ))}
        </select>

        <button
          onClick={handleSubmit}
          style={{
            background: "#3C3CE8",
            color: "#fff",
            border: "none",
            padding: "10px 20px",
            borderRadius: 8,
            cursor: "pointer",
            fontWeight: "bold",
          }}
        >
          Adicionar
        </button>
      </div>

      <h3 style={{ color: "#7373FF" }}>Transações</h3>

      <table
        style={{
          width: "100%",
          borderCollapse: "collapse",
          overflow: "hidden",
          borderRadius: 12,
          background: "#070738",
        }}
      >
        <thead>
          <tr style={{ background: "#3C3CE8" }}>
            <th style={{ padding: 12 }}>Descrição</th>
            <th style={{ padding: 12 }}>Valor</th>
            <th style={{ padding: 12 }}>Tipo</th>
            <th style={{ padding: 12 }}>Data</th>
            <th style={{ padding: 12 }}>Ação</th>
          </tr>
        </thead>

        <tbody>
          {transacoes.map((t) => (
            <tr
              key={t.id}
              style={{
                borderBottom: "1px solid rgba(115,115,255,0.2)",
              }}
            >
              <td style={{ padding: 12 }}>{t.descricao}</td>

              <td
                style={{
                  padding: 12,
                  color: t.tipo === "RECEITA" ? "#4ADE80" : "#F87171",
                  fontWeight: "bold",
                }}
              >
                R$ {t.valor}
              </td>

              <td style={{ padding: 12 }}>{t.tipo}</td>

              <td style={{ padding: 12 }}>
                {new Date(t.data).toLocaleDateString("pt-BR")}
              </td>

              <td style={{ padding: 12 }}>
                <button
                  onClick={() => handleDelete(t.id)}
                  style={{
                    background: "#7373FF",
                    color: "#fff",
                    border: "none",
                    padding: "8px 14px",
                    borderRadius: 8,
                    cursor: "pointer",
                  }}
                >
                  Excluir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}