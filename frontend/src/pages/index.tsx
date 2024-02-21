import { useContext, FormEvent, useState } from 'react'

import Head from 'next/head'
import Image from 'next/image'
import logotipo from '../../public/LOGOTIPO MARCA D_ÁGUA.png'
import styles from '../styles/home.module.scss'
import { Input } from '@/components/ui/Input'
import { Button } from '@/components/ui/Button'
import { AuthContext } from '@/contexts/AuthContext'
import Link from 'next/link'
import { canSSRGuest } from '../utils/canSSRGuest'
export default function Home() {
  const { signIn } = useContext(AuthContext)

  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('');

  const [loading, setLoading] = useState(false);

  async function handleLogin(event: FormEvent){
    event.preventDefault();

    if(email === '' || senha === ''){
      alert("PREENCHA OS DADOS")
      return;
    }

    setLoading(true);

    let data = {
      email,
      senha
    }

    await signIn(data)

    setLoading(false);
  }

  return (
    <>
      <Head>
        <title>  Um Par - Faça seu Login   </title>
      </Head>
      <div className={styles.containerCenter}>
        <Image src={logotipo} alt="Logo Um Par Calçado" width={500} height={200} />

        <div className={styles.login}>
        <form onSubmit={handleLogin}>
        <Input
          placeholder="Digite seu email"
          type='text'
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
         <Input
          placeholder="Digite sua senha"
          type='password'
          value={senha}
          onChange={(e) => setSenha(e.target.value)}

        />
            <Button type="submit"
              loading={loading}>

              Acessar
            </Button>
          </form>

          <Link href="/cadastros" passHref>
            <div className={styles.text}> Não possui uma conta? Cadastre-se</div>
          </Link>

        </div>
      </div>


    </>

  )
}
export const getServerSideProps = canSSRGuest(async (context) => {

  return {
    props: {}
  }
})