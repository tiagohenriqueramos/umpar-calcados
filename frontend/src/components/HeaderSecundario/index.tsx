import Link from 'next/link'
import styles from './styles.module.scss'
import { FiLogOut } from 'react-icons/fi'
export function HeaderSecundario() {

    return (

        <header className={styles.headerContainer}>
            <div className={styles.headerContent}>
                <Link href="/dashboard">
                    <img src="/LOGOTIPO VARIACAO 04.png" width={300} height={100} />
                </Link>
                <Link href="/dashboard">
                    <img src="/LOGOTIPO VARIACAO 04.png" width={300} height={100} />
                </Link> 
                <Link href="/dashboard">
                    <img src="/LOGOTIPO VARIACAO 04.png" width={300} height={100} />
                </Link> 
                  <Link href="/dashboard">
                    <img src="/LOGOTIPO VARIACAO 04.png" width={300} height={100} />
                </Link>


            </div>
        </header>

    )
}