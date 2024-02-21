import Head from "next/head";
import styles from './styles.module.scss'
import { Header } from '../../components/Header'
import { useState, useEffect } from "react";
import { setupAPIClient } from "@/services/api";
import Modal from 'react-modal'
import { ModalCalcados } from '@/components/ModalCalcados'
import { toast } from 'react-toastify'


type Calcados = {
  id: string;
  nome: string;
  material: string;
  marca: string;
  cor: string;
  precoGrade: string;
  data: Date;
  frete: string;
  fornecedor: Fornecedor;
  nomeImagem: string;
  arquivo: number[];
  grade: Map<number, number>;

};

interface HomeProps {
  listaCalcados: Calcados[];
}

export type Saidas = {
  id: string;
  data: Date;
  calcados: Calcados;
  quantidade: string;
  tamanho: string;
  precoVenda: string;
  freteVenda: string;
  embalagem: string;
  pagamento: string;
  liquido: string;
  unidade: string;


}
export type CalcadosItemProps = {
  id: string;
  nome: string;
  material: string;
  marca: string;
  cor: string;
  precoGrade: string;
  data: Date;
  frete: string;
  fornecedor: Fornecedor;
  nomeImagem: string;
  arquivo: number[];
  grade: Map<number, number>;

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


export default function Dashboard({ listaCalcados }: HomeProps) {
  const [calcados, setCalcados] = useState(listaCalcados || []);
  const [images, setImages] = useState<string[]>([]);

  const [modalItem, setModalItem] = useState<Calcados[]>()
  const [modalVisible, setModalVisible] = useState(false);


  const api = setupAPIClient();
  const fetchCalcados = async () => {
    try {
      const response = await api.get("/calcados");
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

  async function handleCloseModal() {
    setModalVisible(false);
        await fetchCalcados();

  }


  async function handleOpenModalView(id: string) {
    try {
      const response = await api.get(`/calcados/${id}`);

      if (response.data) {
        setModalItem([response.data]);
        setModalVisible(true);
        console.log(response.data);
      } else {
        console.error('Calcado não encontrado com o ID:', id);
      }
    } catch (error) {
      console.error('Erro ao abrir o modal:', error);
    }
  }



  async function handleDeleteModal(id: string) {
    try {
      const apiClient = setupAPIClient();

      await apiClient.delete(`/calcados/${id}`);


      const response = await api.get('/calcados');
      setCalcados(response.data);

      setModalVisible(false);
      toast.error("Deletado com sucesso!");
    } catch (error) {
      console.error("Ocorreu um erro ao excluir o item de calçado:", error);
    }
  }




  Modal.setAppElement('#__next')
  return (
    <>
      <Head>
        <title>Painel Um Par</title>
      </Head>
      <div>
        <Header />

        <main className={styles.container}>
          <div className={styles.containerHeader}>
            <h1>
              Lista de Produtos
            </h1>
          </div>

          <article className={styles.listCalcados}>
            {calcados.map((calcado, index) => (
              <section key={index} className={styles.calcadosItem}>

                <button onClick={() => handleOpenModalView(calcado.id)}>
                  <div className={styles.tag}></div>
                  <span>Calcados: {calcado.nome} -  {calcado.marca}</span>

                </button>
              </section>

            ))}
          </article>
        </main>
        {modalVisible && (
          <ModalCalcados
            isOpen={modalVisible}
            onRequestClose={handleCloseModal}
            listaCalcados={modalItem}
            handleDeleteModal={handleDeleteModal}
          />
        )}
      </div>

    </>
  );
}