import Head from "next/head";
import { Header } from '../../components/Header'

import styles from '../saidas/styles.module.scss'
import { useState, ChangeEvent, FormEvent, useEffect } from 'react'
import { toast } from 'react-toastify'
import { setupAPIClient } from "@/services/api";
import { format } from "date-fns";
import Datepicker from "react-datepicker";
import Pagamento from "../../components/ui/Enum/Pagamento";
import "react-datepicker/dist/react-datepicker.css";

export type Calcados = {
    id: number;
    nome: string;
    material: string;
    marca: string;
    cor: string
    precoGrade: number;
    frete: number;
    data: Date
    nomeImagem: string;
    arquivo: number[];
    fornecedor: Fornecedor;
    grade: { [key: number]: number };
};

export type Saida = {
    id: number;
    nome: string;
    material: string;
    cor: string
    fornecedor: Fornecedor;
    calcados: Calcados;
};

export type Fornecedor = {
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
}


export default function Saidas() {
    const [calcados, setCalcados] = useState<Calcados[]>([]);
    const [calcadoSelecionado, setCalcadoSelecionado] = useState<Calcados | null>(null);
    const [tamanhoSelecionado, setTamanhoSelecionado] = useState<number | null>(null);
    const [quantidadeSelecionada, setQuantidadeSelecionada] = useState<number | null>(null);
    const [quantidadesDisponiveis, setQuantidadesDisponiveis] = useState<number[]>([]);

    const [embalagem, setEmbalagem] = useState('');
    const [precoVenda, setPrecoVenda] = useState('');
    const [datas, setDatas] = useState<Date | null>(null);
    const [freteVenda, setFrete] = useState('');
    const [metodoPagamento, setMetodoPagamento] = useState('');

    const apiClient = setupAPIClient();

    const fetchCalcados = async () => {
        try {
            const response = await apiClient.get("/calcados");
            if (response) {
                const calcados: Calcados[] = response.data;
                setCalcados(calcados);
            }
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchCalcados();
    }, []);

    const handleCalcadoChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedIndex = parseInt(event.target.value);
        const selectedCalcado = calcados[selectedIndex];
        setCalcadoSelecionado(selectedCalcado);
    };

    const handleTamanhoChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const tamanho = parseInt(event.target.value);
        setTamanhoSelecionado(tamanho);

        if (calcadoSelecionado) {
            const grade = calcadoSelecionado.grade;
            const quantidades = Object.values(grade).find((value, index) => {
                return parseInt(Object.keys(grade)[index]) === tamanho;
            });
            if (quantidades) {
                const arr = Array.from({ length: quantidades }, (_, i) => i + 1);
                setQuantidadesDisponiveis(arr);
            }
        }
    };
    const handleQuantidadeChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const quantidade = parseInt(event.target.value);
        setQuantidadeSelecionada(quantidade);
    };

    const handleRegister = async (event: FormEvent) => {
        event.preventDefault();

        try {
            if (!calcadoSelecionado || tamanhoSelecionado === null || quantidadeSelecionada === null || embalagem === '' ||
                precoVenda === '' || freteVenda === '' || metodoPagamento === '' || datas === null) {
                toast.error("Preencha todos os campos");
                return;
            }

            const dataFormatada = format(datas, "dd/MM/yyyy");

            const data = {
                data: dataFormatada,
                calcados: {
                    id: calcadoSelecionado.id,
                },
                tamanho: tamanhoSelecionado,
                quantidade: quantidadeSelecionada,
                embalagem: embalagem,
                freteVenda: freteVenda,
                precoVenda: precoVenda,
                pagamento: metodoPagamento
            };

            await apiClient.post("/saidas", data);

            toast.success("Cadastrado com sucesso!");
            console.log(data);
        } catch (error) {
            console.log(error);
            toast.error("Opss, erro ao cadastrar!");
        }

        setEmbalagem('');
        setPrecoVenda('');
        setDatas(null);
        setFrete('');
        setMetodoPagamento('');
        setCalcadoSelecionado(null);
        setTamanhoSelecionado(null);
        setQuantidadeSelecionada(null);
    };
    const handleFreteChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value.replace(/\D/g, '');
        setFrete(value);
      };

      const handleEmbalagemChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value.replace(/\D/g, '');
        setEmbalagem(value);
      };
    return (
        <>
            <Head>
                <title>Calcados - Um Par</title>
            </Head>

            <Header />
            <div>
                <main className={styles.container}>
                    <h1>Saida de Produto</h1>
                    <form className={styles.form} onSubmit={handleRegister}>

                        <select className={styles.select} value={calcadoSelecionado ? calcados.indexOf(calcadoSelecionado) : ''} onChange={handleCalcadoChange}>
                            <option value="" disabled>
                                Selecione um Calçado
                            </option>
                            {calcados.map((item, index) => (
                                <option key={item.id} value={index}>
                                    {item.nome}
                                </option>
                            ))}
                        </select>

                        {calcadoSelecionado && (
                            <>
                                <select className={styles.select} value={tamanhoSelecionado || ''} onChange={handleTamanhoChange}>
                                    <option value="" disabled>
                                        Selecione o Tamanho
                                    </option>
                                    {Object.keys(calcadoSelecionado.grade).map((tamanho, index) => (
                                        <option key={index} value={parseInt(tamanho)}>
                                            Tamanho: {tamanho}
                                        </option>
                                    ))}
                                </select>
                                <select className={styles.select} value={quantidadeSelecionada || ''} onChange={handleQuantidadeChange}>
                                    <option value="" disabled>
                                        Selecione a Quantidade
                                    </option>
                                    {quantidadesDisponiveis.map((quantidade, index) => (
                                        <option key={index} value={quantidade}>
                                            Quantidade:   {quantidade}
                                        </option>
                                    ))}
                                </select>


                            </>
                        )}

                        <input
                            type="text"
                            placeholder="Valor da venda"
                            className={styles.input}
                            value={precoVenda}
                            onChange={(e) => setPrecoVenda(e.target.value)}
                        />

                        <input
                            type="text"
                            placeholder="Embalagem"
                            className={styles.input}
                            value={embalagem}
                            onChange={handleEmbalagemChange}
                        />

                        <input
                            type="text"
                            placeholder="Frete"
                            className={styles.input}
                            value={freteVenda}
                            onChange={handleFreteChange}
                        />

                        <select
                            className={styles.select}
                            value={metodoPagamento}
                            onChange={(e) => setMetodoPagamento(e.target.value)}
                        >
                            <option value="" disabled>
                                Selecione o metodo de Pagamento
                            </option>
                            {Object.values(Pagamento).map((PagamentoOption, index) => (
                                <option key={index} value={PagamentoOption}>
                                    {PagamentoOption}
                                </option>
                            ))}
                        </select>

                        <Datepicker
                            className={styles.input}
                            placeholderText="Selecione a Data"
                            selected={datas}
                            onChange={(date) => setDatas(date)}
                            dateFormat="dd/MM/yyyy"
                        />
                        <button className={styles.buttonAdd} type="submit">
                            Saída do Produto
                        </button>
                    </form>
                </main>

            </div>
        </>
    );
}
