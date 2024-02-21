import Head from "next/head";
import { Header } from '../../components/Header'
import styles from '../fornecedor/styles.module.scss'
import { useState, FormEvent } from 'react'
import { toast } from 'react-toastify'
import { setupAPIClient } from "@/services/api";


export default function Fornecedor() {


    const [razaoSocial, setRazaoSocial] = useState('');
    const [cnpj, setCnpj] = useState('');
    const [ruaAvenida, setRuaAvenida] = useState('');
    const [numero, setNumero] = useState('');
    const [bairro, setBairro] = useState('');
    const [cidade, setCidade] = useState('')
    const [estado, setEstado] = useState('')
    const [telefone, setTelefone] = useState('')
    const [email, setEmail] = useState('')
    const [site, setSite] = useState('')


    const handleRegister = async (event: FormEvent) => {
        event.preventDefault();

        try {
            if (razaoSocial === '' || cnpj === '' || ruaAvenida === '' || numero === '' || bairro === '' ||
                cidade === '' || estado === '' || telefone === '' || email === '' || site === '') {
                toast.error("Preencha todos os campos");
                return;
            }

            const apiClient = setupAPIClient();
            
            await apiClient.post("/fornecedor", {

                razaoSocial: razaoSocial,
                cnpj: cnpj,
                ruaAvenida: ruaAvenida,
                numero: numero,
                bairro: bairro,
                cidade: cidade,
                estado: estado,
                telefone: telefone,
                email: email,
                site: site

            });


            toast.success("Cadastrado com sucesso!");
        } catch (error) {
            console.log(error);
            toast.error("Ops, erro ao cadastrar!");
        }

        setRazaoSocial("");
        setCnpj("");
        setRuaAvenida("");
        setNumero("");
        setBairro("");
        setCidade("");
        setEstado("");
        setTelefone("");
        setEmail("");
        setSite("");



    };

    
    const handleCnpjChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const rawValue = e.target.value;
        const formattedValue = rawValue
            .replace(/\D/g, '') 
            .slice(0, 14); 
        setCnpj(formattedValue);
    };

    return (
        <>
            <Head>
                <title>Calcados - Um Par</title>
            </Head>



            <Header />
            <div>

                <main className={styles.container}>
                    <h1>Fornecedores </h1>
                    <form className={styles.form} onSubmit={handleRegister}>

                        <input
                            type="text"
                            placeholder="Razão Social"
                            className={styles.input}
                            value={razaoSocial}
                            onChange={(e) => setRazaoSocial(e.target.value)}
                        />


<input
                            type="text"
                            placeholder="CNPJ"
                            className={styles.input}
                            value={cnpj}
                            onChange={handleCnpjChange}
                        />


                        <input
                            type="text"
                            placeholder="Rua / Av"
                            className={styles.input}
                            value={ruaAvenida}
                            onChange={(e) => setRuaAvenida(e.target.value)}
                        />
                         <input
                            type="text"
                            placeholder="Numero"
                            className={styles.input}
                            value={numero}
                            onChange={(e) => setNumero(e.target.value)}
                        />   
                        <input
                            type="text"
                            placeholder="Bairro"
                            className={styles.input}
                            value={bairro}
                            onChange={(e) => setBairro(e.target.value)}
                        />       <input
                            type="text"
                            placeholder="Cidade"
                            className={styles.input}
                            value={cidade}
                            onChange={(e) => setCidade(e.target.value)}
                        />       <input
                            type="text"
                            placeholder="Estado"
                            className={styles.input}
                            value={estado}
                            onChange={(e) => setEstado(e.target.value)}
                        />       <input
                            type="text"
                            placeholder="Telefone"
                            className={styles.input}
                            value={telefone}
                            onChange={(e) => setTelefone(e.target.value)}
                        />       <input
                            type="text"
                            placeholder="Email"
                            className={styles.input}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Site"
                            className={styles.input}
                            value={site}
                            onChange={(e) => setSite(e.target.value)}
                        />


                        <button className={styles.buttonAdd} type="submit">
                            Cadastrar Fornecedores
                        </button>
                    </form>
                </main>

            </div>
        </>
    )
}
