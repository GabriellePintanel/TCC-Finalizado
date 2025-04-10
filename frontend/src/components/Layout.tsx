import { Link, Outlet } from "react-router-dom";
import { useAuth } from "react-oidc-context";

import logoImg from "../assets/img/gaucho-dancing.png";

export const Layout = () => {
  return (
    <>
      <Navbar />
      <Outlet />
      <Footer />
    </>
  );
};

const Navbar = () => {
  return (
    <nav className="navbar" role="navigation" aria-label="main navigation">
      <div className="navbar-brand">
        <a className="navbar-item" href="http://localhost:5173">
          <img src={logoImg} />
          <span>TCC</span>
        </a>

        <a
          role="button"
          className="navbar-burger"
          aria-label="menu"
          aria-expanded="false"
          data-target="navbarBasicExample"
        >
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>

      <div id="navbarBasicExample" className="navbar-menu">
        <div className="navbar-end">
          <OidcMenu />
        </div>
      </div>
    </nav>
  );
};

const Footer = () => {
  return (
    <>
      <hr />
      <footer className="container">
        <h1 className="ml-6 is-size-3">
          <strong>CONTATO</strong>
        </h1>
        <div className="columns">
          <div className="column mx-6">
            <p>Sou um parágrafo. Clique aqui para adicionar e editar o texto.</p>
            <p>Org. registrada: 12345-67</p>
            <p>info@meusite.com</p>
          </div>
          <div className="column mx-6">
            <p>Rua Prates, 194 - Bom Retiro</p>
            <p>São Paulo - SP, 01121-000</p>
            <p>Tel: 0800-000-0000</p>
          </div>
        </div>
      </footer>
    </>
  );
};

const OidcMenu = () => {
  const auth = useAuth();

  if (auth.isAuthenticated) {
    return (
      <>
        <Link className="navbar-item" to="/">
          Início
        </Link>

        <Link className="navbar-item" to="/about">
          Sobre o CTG
        </Link>

        <Link className="navbar-item" to="/calendar">
          Calendário
        </Link>

        <Link className="navbar-item" to="/rodeio">
          Notas e Gráficos do Rodeio
        </Link>

        <div className="navbar-item has-dropdown is-hoverable">
          <a className="navbar-link">Mais</a>

          <div className="navbar-dropdown">
            <Link className="navbar-item" to="/group">
              O Grupo
            </Link>
            <Link className="navbar-item" to="/pilcha">
              Pilcha
            </Link>
            <Link className="navbar-item" to="/profile">
              Meu Perfil
            </Link>
          </div>
        </div>

        <div className="navbar-item">
          <div className="buttons">
            {auth.user?.profile.given_name}{" "}
            <button className="button is-primary"
              onClick={
                () => {
                  auth.removeUser();
                  auth.signoutRedirect();
                }
              }
            >
              <strong>Sair</strong>
            </button>
          </div>
        </div>
      </>
    );
  } else {
    return (
      <>
        <Link className="navbar-item" to="/">
          Início
        </Link>

        <Link className="navbar-item" to="/about">
          Sobre o CTG
        </Link>

        <Link className="navbar-item" to="/calendar">
          Calendário
        </Link>

        <div className="navbar-item">
          <div className="buttons">
            <button className="button is-primary"
              onClick={
                () => void auth.signinRedirect()
              }
            >
              <strong>Entrar</strong>
            </button>
          </div>
        </div>
      </>
    );
  };
};