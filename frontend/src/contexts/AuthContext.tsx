import { createContext, ReactNode, useState, useEffect } from "react";
import { api } from "../services/apiClient";
import { destroyCookie, setCookie, parseCookies } from "nookies";
import Router from "next/router";
import { toast } from "react-toastify";



type AuthContextData = {
  user?: UserProps;
  isAuthenticated: boolean;
  signIn: (credentials: SignInProps) => Promise<void>;
  signOut: () => void;
  signUp: (credentials: SignUpProps) => Promise<void>;
};

type UserProps = {
  email: string;
};

type SignInProps = {
  email: string;
  senha: string;
};

type SignUpProps = {
  nome: string;
  email: string;
  senha: string;
};

type AuthProviderProps = {
  children: ReactNode;
};

export const AuthContext = createContext({} as AuthContextData);

export function signOut() {
  try {
    destroyCookie(undefined, "@auth.token");
    Router.push("/");
  } catch {
    console.log("erro ao deslogar");
  }
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<UserProps | undefined>();
  const isAuthenticated = !!user;

  useEffect(() => {
    const cookies = parseCookies();
    const token = cookies['@auth.token'];

    if (token) {
      api.defaults.headers.authorization = `Bearer ${token}`;
    }
  }, []);

  async function signIn({ email, senha }: SignInProps) {
    try {
      const response = await api.post("/usuarios/autenticar", {
        email,
        senha,
      });

      const { token } = response.data;
      console.log(response.data);
      setCookie(undefined, "@auth.token", token, {
        maxAge: 60 * 60 * 2.5,
        path: '/',
      });

      setUser({
        email,
      });

      api.defaults.headers["Authorization"] = `Bearer ${token}`;
      toast.success("Logado com sucesso!");
      Router.push("/dashboard");
      console.log(response)
    } catch (err) {
      toast.error("Erro ao acessar!");
      console.log("Erro ao acessar", err);
    }
  }

  async function signUp({ nome, email, senha }: SignUpProps) {
    try {
      const response = await api.post("/usuarios", {
        nome,
        email,
        senha,
      });

      toast.success("Conta criada com sucesso!");

      Router.push("/");
    } catch (err) {
      toast.error("Erro ao cadastrar!");
    }
  }

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated, signIn, signOut, signUp }}
    >
      {children}
    </AuthContext.Provider>
  );
}