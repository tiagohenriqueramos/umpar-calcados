import Link from 'next/link'
import styles from './styles.module.scss'
import {FiLogOut} from 'react-icons/fi'
export function Header() {

    return (

        <header className={styles.headerContainer}>
            <div className={styles.headerContent}>
                <Link href="/dashboard">
                    <img src="/LOGOTIPO VARIACAO 04.png" width={300} height={100} />
                    
                </Link>
                <nav className={styles.menuNav}>
                    <Link href="/cadastro" passHref legacyBehavior>
                        <a>Cadastro</a>
                    </Link>
                    
                    <Link href="/saidas" passHref legacyBehavior>
                        <a>Saídas</a>
                    </Link>
                    <Link href="/fornecedor" passHref legacyBehavior>
                        <a>Fornecedores</a>
                    </Link>
                    
                    <button>
                        <FiLogOut color = "#ee3c44" size={24} />
                    </button>
                </nav>



            </div>
        </header>

    )
}