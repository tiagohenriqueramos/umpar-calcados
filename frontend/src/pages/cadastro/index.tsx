import Head from "next/head";
import { Header } from '../../components/Header'
import Datepicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import styles from '../cadastro/styles.module.scss'
import { FiUpload } from "react-icons/fi";
import { useState, ChangeEvent, FormEvent, useEffect } from 'react'
import { toast } from 'react-toastify'
import { format } from "date-fns";

import { setupAPIClient } from "@/services/api";

export type Calcados = {
    id: number;
    nome: string;
    material: string;
    marca: string;
    cor: string
    fornecedor: Fornecedor;
    nomeImagem: string;
    arquivo: number[];
    grade: { [key: number]: number };
};
type Fornecedor = {
    id: string;
    razaoSocial: string;
    cnpj: string;
    ruaAvenida: string;
    bairro: string;
    cidade: string;
    estado: string;
    telefone: string;
    email: string;
    site: string;
};
export default function Cadastro() {

    const [calcados, setCalcados] = useState<Calcados[]>([]);
    const [nome, setNome] = useState('');
    const [material, setMaterial] = useState('');
    const [marca, setMarca] = useState('');
    const [cor, setCor] = useState('');
    const [avatarUrl, setAvatarUrl] = useState('');
    const [file, setFile] = useState<File | null>(null);
    const [fornecedor, setFornecedor] = useState<Fornecedor[]>([]);
    const [fornecedorSelecionado, setFornecedorSelecionado] = useState<number | string>("");
    const [precoGrade, setPrecoGrade] = useState('');
    const [datas, setDatas] = useState<Date | null>(null);
    const [frete, setFrete] = useState('');
    const [tipoGrade, setTipoGrade] = useState('');


    const apiClient = setupAPIClient();

    const fetchCalcados = async () => {
        const response = await apiClient
            .get("/calcados")
            .catch((err) => console.log(err));

        if (response) {
            const calcados: Calcados[] = response.data;
            setCalcados(calcados);
        }
    };

    useEffect(() => {
        fetchCalcados();
    }, []);

    const fetchFornecedor = async () => {
        const response = await apiClient
            .get("/fornecedor")
            .catch((err) => console.log(err));

        if (response) {
            const fornecedor: Fornecedor[] = response.data;
            setFornecedor(fornecedor);
        }
    };

    useEffect(() => {
        fetchFornecedor();
    }, []);

 
    async function handleChangeFornecedor(event) {
        setFornecedorSelecionado(event.target.value);
        console.log(fornecedor[event.target.value]);

    }

    function handleFile(e: ChangeEvent<HTMLInputElement>) {
        if (!e.target.files) {
            return;
        }

        const image = e.target.files[0];

        if (!image) {
            return;
        }

        if (image.type === "image/jpeg" || image.type === "image/png") {
            setFile(image);
            setAvatarUrl(URL.createObjectURL(e.target.files[0]));
        }
        console.log(e.target.files[0]);
    }

    const handleRegister = async (event: FormEvent) => {
        event.preventDefault();

        try {
            if (!file || nome === '' || material === '' || marca === '' || cor === '' || precoGrade === '' || frete === '' || datas === null) {
                toast.error("Preencha todos os campos");
                return;
            }
            let gradeSelecionada = {};

            if (tipoGrade === 'baixa') {
                gradeSelecionada = { 33: 1, 34: 1, 35: 2, 36: 3 };
            } if(tipoGrade === 'alta') {
                gradeSelecionada = { 37: 3, 38: 2, 39: 1, 40: 1 }
            }

            const dataFormatada = format(datas, "dd/MM/yyyy");

            const formData = new FormData();

            formData.append("file", file);
            formData.append("nome", nome);
            formData.append("material", material);
            formData.append("marca", marca);
            formData.append("cor", cor);
            formData.append("precoGrade", precoGrade);
            formData.append("frete", frete);
            formData.append("data", dataFormatada)
            formData.append("grade", JSON.stringify(gradeSelecionada));

            formData.append("fornecedor_id", fornecedor[fornecedorSelecionado].id);
            console.log("FormData:", Object.fromEntries(formData.entries()));

           await apiClient.post("/calcados/cadastro", formData, {
                headers: {
                    "Content-Type": "multipart/form-data"
                },
            });

            toast.success("Cadastrado com sucesso!");
        } catch (error) {
            console.log(error);
            toast.error("Ops, erro ao cadastrar!");
        }

        setNome("");
        setMaterial("");
        setMarca("");
        setCor("");
        setDatas(null);
        setPrecoGrade("");
        setFrete("");
        setTipoGrade("");
        setFile(null);
        setAvatarUrl("");
    };
    const handleFreteChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value.replace(/\D/g, '');
        setFrete(value);
      };
    
      const handlePrecoGradeChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value.replace(/\D/g, '');
        setPrecoGrade(value);
      };
    

    return (
        <>
            <Head>
                <title>Calcados - Um Par</title>
            </Head>

            <Header />

            <div>

                <main className={styles.container}>
                    <h1>Novo Produto</h1>
                    <form className={styles.form} onSubmit={handleRegister}>

                        <label className={styles.labelAvatar}>
                            <span>
                                <FiUpload size={25} color="#FFF" />
                            </span>

                            <input type="file" accept="image/png, image/jpeg" onChange={handleFile} />

                            {avatarUrl && (
                                <img
                                    className={styles.preview}
                                    src={avatarUrl}
                                    alt="Foto do produto"
                                    width={250}
                                    height={250}
                                />
                            )}
                        </label>

                        <input
                            type="text"
                            placeholder="Nome do Produto"
                            className={styles.input}
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Material"
                            className={styles.input}
                            value={material}
                            onChange={(e) => setMaterial(e.target.value)}
                        />

                        <input
                            type="text"
                            placeholder="Marca"
                            className={styles.input}
                            value={marca}
                            onChange={(e) => setMarca(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Cor"
                            className={styles.input}
                            value={cor}
                            onChange={(e) => setCor(e.target.value)}
                        />

                        <select
                            className={styles.select}
                            value={tipoGrade}
                            onChange={(e) => setTipoGrade(e.target.value)}
                        >
                            <option value="" disabled defaultValue="">
                                Selecione o Tipo de Grade
                            </option>
                            <option value="alta">Grade Alta</option>
                            <option value="baixa">Grade Baixa</option>
                        </select>

                        <input
                            type="text"
                            placeholder="Preço da Grade R$: "
                            className={styles.input}
                            value={precoGrade}
                            onChange={handlePrecoGradeChange}
                            />

                        <input
                            type="text"
                            placeholder="Frete R$: "
                            className={styles.input}
                            value={frete}
                            onChange={handleFreteChange}
                            />

                   


                        <select className={styles.select} value={fornecedorSelecionado} onChange={handleChangeFornecedor}>
                            <option value="" disabled defaultValue="" >
                                Selecione um Fornecedor
                            </option>
                            {fornecedor.map((item, index) => {
                                return (
                                    <option key={item.id} value={index}>
                                        Fornecedor:  {item.razaoSocial} CNPJ: {item.cnpj}
                                    </option>
                                );
                            })}
                        </select>

                        <Datepicker
                            className={styles.inputTamanhoQuantidade}
                            placeholderText="Selecione a Data"
                            selected={datas}
                            onChange={(date) => setDatas(date)}
                            dateFormat="dd/MM/yyyy"
                        />

                        <button className={styles.buttonAdd} type="submit">
                            Cadastrar Produto
                        </button>

                    </form>
                </main>
            </div>
        </>
    )
}