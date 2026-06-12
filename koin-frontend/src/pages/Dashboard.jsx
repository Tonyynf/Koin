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
    <div style={{ maxWidth: 800, margin: "40px auto", padding: 24 }}>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <h2>Dashboard</h2>
        <button onClick={handleLogout}>Sair</button>
      </div>

      <h3>Nova Transação</h3>
      <div style={{ display: "flex", gap: 8, flexWrap: "wrap", marginBottom: 24 }}>
        <input name="descricao" placeholder="Descrição" value={form.descricao} onChange={handleChange} style={{ padding: 8, flex: 1 }} />
        <input name="valor" placeholder="Valor" type="number" value={form.valor} onChange={handleChange} style={{ padding: 8, width: 120 }} />
        <select name="tipo" value={form.tipo} onChange={handleChange} style={{ padding: 8 }}>
          <option value="DESPESA">Despesa</option>
          <option value="RECEITA">Receita</option>
        </select>
        <select name="contaId" value={form.contaId} onChange={handleChange} style={{ padding: 8 }}>
          <option value="">Conta</option>
          {contas.map((c) => <option key={c.id} value={c.id}>{c.nome}</option>)}
        </select>
        <select name="categoriaId" value={form.categoriaId} onChange={handleChange} style={{ padding: 8 }}>
          <option value="">Categoria</option>
          {categorias.map((c) => <option key={c.id} value={c.id}>{c.nome}</option>)}
        </select>
        <button onClick={handleSubmit} style={{ padding: 8 }}>Adicionar</button>
      </div>

      <h3>Transações</h3>
      <table style={{ width: "100%", borderCollapse: "collapse", textAlign: "center" }}>
        <thead>
          <tr>
            <th style={{ textAlign: "center", padding: 8, borderBottom: "1px solid #ccc" }}>Descrição</th>
            <th style={{ textAlign: "center",   padding: 8, borderBottom: "1px solid #ccc" }}>Valor</th>
            <th style={{ textAlign: "center", padding: 8, borderBottom: "1px solid #ccc" }}>Tipo</th>
            <th style={{ textAlign: "center", padding: 8, borderBottom: "1px solid #ccc" }}>Data</th>
            <th style={{ textAlign: "center", padding: 8, borderBottom: "1px solid #ccc" }}>Ação</th>
          </tr>
        </thead>
        <tbody>
          {transacoes.map((t) => (
            <tr key={t.id}>
              <td style={{ padding: 8 }}>{t.descricao}</td>
              <td style={{ padding: 8, color: t.tipo === "RECEITA" ? "green" : "red" }}>R$ {t.valor}</td>
              <td style={{ padding: 8 }}>{t.tipo}</td>
              <td style={{ padding: 8 }}>{new Date(t.data).toLocaleDateString("pt-BR")}</td>
              <td style={{ padding: 8 }}>
                  <button onClick={() => handleDelete(t.id)} style={{ padding: 8 }}>
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