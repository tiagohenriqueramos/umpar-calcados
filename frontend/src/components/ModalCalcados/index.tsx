import Modal from 'react-modal';
import styles from './style.module.scss';
import { FiX } from 'react-icons/fi';
import { setupAPIClient } from "@/services/api";
import { useState, useEffect, FormEvent } from "react";
import { toast } from 'react-toastify';
import { CalcadosItemProps } from '@/pages/dashboard';

interface ModalCalcadosProps {
    isOpen: boolean;
    onRequestClose: () => void;
    listaCalcados: CalcadosItemProps[];
    handleDeleteModal: (id: string) => void;
}

export function ModalCalcados({ isOpen, onRequestClose, listaCalcados, handleDeleteModal }: ModalCalcadosProps) {
    const [calcados, setCalcados] = useState(listaCalcados || []);
    const [images, setImages] = useState<string[]>([]);
    const [showEditForm, setShowEditForm] = useState(false);
    const [nome, setNome] = useState('');
    const [material, setMaterial] = useState('');
    const [marca, setMarca] = useState('');
    const [cor, setCor] = useState('');
    const [precoGrade, setPrecoGrade] = useState('');
    const [frete, setFrete] = useState('');

    useEffect(() => {
        if (listaCalcados.length > 0) {
            const calcado = listaCalcados[0];
            setNome(calcado.nome);
            setMaterial(calcado.material);
            setMarca(calcado.marca);
            setCor(calcado.cor);
            setPrecoGrade(calcado.precoGrade);
            setFrete(calcado.frete);
        }
    }, [listaCalcados]);

    const customStyles = {
        content: {
            top: '50%',
            bottom: 'auto',
            left: '50%',
            right: 'auto',
            padding: '30px',
            transform: 'translate(-50%, -50% )',
            backgroundColor: "#f5e4d4",
            maxWidth: '90%',
            maxHeight: '90%'
        }
    };

    const api = setupAPIClient();

    const fetchImagem = async (nomeImagem: string) => {
        try {
            const resposta = await api.get(`/calcados/imagem/${nomeImagem}`, {
                responseType: 'arraybuffer',
            });
            const imagemData = `data:image/jpeg;base64,${Buffer.from(resposta.data).toString('base64')}`;
            return imagemData;
        } catch (erro) {
            console.error('Erro ao obter imagem:', erro);
            return '';
        }
    };

    useEffect(() => {
        const imagePromises = listaCalcados.map(async (calcado) => {
            const imagemData = await fetchImagem(calcado.nomeImagem);
            return imagemData;
        });

        Promise.all(imagePromises)
            .then((imageUrls) => setImages(imageUrls))
            .catch((err) => console.error('Erro ao carregar imagens:', err));
    }, [listaCalcados]);

    const handleEdit = async (event: FormEvent) => {
        event.preventDefault();

        try {
            if (!nome || !material || !marca || !cor) {
                toast.error("Preencha todos os campos");
                return;
            }

            const apiClient = setupAPIClient();
            await apiClient.put(`/calcados/${listaCalcados[0].id}`, {
                id: listaCalcados[0].id,
                nome,
                material,
                marca,
                cor,
                precoGrade,
                frete
            });
            toast.success("Editado com sucesso!");
           

        } catch (error) {
            console.error("Erro ao editar:", error);
            toast.error("Ops, erro ao editar!");
        }
    };

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={onRequestClose}
            style={customStyles}
        >
            <button
                type="button"
                onClick={onRequestClose}
                className="react-modal-close"
                style={{ background: 'transparent', border: 0 }}
            >
                <FiX size={45} color="#ee3c44" />
            </button>

            <div className={styles.container}>
                <h2>Detalhes do Produto:</h2>
                <span className={styles.produto}>
                    <strong>Calçado:</strong> {nome} -
                    <strong> Marca:</strong> {marca} -
                    <strong> Material:</strong> {material} -
                    <strong> Cor:</strong> {cor}
                    {calcados.map((calcado, index) => (
                        <section key={index} className={styles.calcadosItem}>
                            <div className={styles.tag}></div>
                            <strong>Disponibilidade:</strong>
                            <div>
                                {Object.entries(calcado.grade).map(([numero, quantidade], index) => (
                                    <div key={index}>
                                        {` Numero: ${numero} - Quantidade ${quantidade}`}
                                    </div>
                                ))}
                            </div>
                            <img
                                className={styles.card}
                                src={images[index]}
                                alt={`Imagem do calçado ${calcado.nome}`}
                            />
                        </section>
                    ))}

                    {showEditForm ? (
                        <>
                            <h2>Formulário de Edição</h2>
                            <form className={styles.form} onSubmit={handleEdit}>
                                <input
                                    type="text"
                                    className={styles.input}
                                    placeholder="Nome do Produto"
                                    value={nome}
                                    onChange={(e) => setNome(e.target.value)}
                                />
                                <input
                                    type="text"
                                    className={styles.input}
                                    placeholder="Material"
                                    value={material}
                                    onChange={(e) => setMaterial(e.target.value)}
                                />
                                <input
                                    type="text"
                                    className={styles.input}
                                    placeholder="Marca"
                                    value={marca}
                                    onChange={(e) => setMarca(e.target.value)}
                                />
                                <input
                                    type="text"
                                    className={styles.input}
                                    placeholder="Cor"
                                    value={cor}
                                    onChange={(e) => setCor(e.target.value)}
                                />
                                <button className={styles.buttonAdd} type="submit">Editar Produto</button>
                            </form>
                        </>
                    ) : (
                        <>
                            <button className={styles.buttonEdit} onClick={() => setShowEditForm(true)}>
                                Editar Calcados
                            </button>
                            <button className={styles.buttonDelete} onClick={() => handleDeleteModal(listaCalcados[0].id)}>
                                Deletar Calçados
                            </button>
                        </>
                    )}
                </span>
            </div>
        </Modal>
    );
}

