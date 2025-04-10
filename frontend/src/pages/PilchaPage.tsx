import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { Link } from "react-router-dom";

interface Pilcha {
  id: string;
  pilchaType: number;
  description: string;
  owner: string;
}

const PilchaEmpty = () => {
  return <p>Vazio</p>
}

export const PilchaPage = () => {
  const auth = useAuth();
  const [pilchaList, setPilchaList] = useState<Pilcha[]>();

  useEffect(() => {
    (async () => {
      try {
        const token = auth.user?.access_token;
        const response = await fetch("http://localhost:8080/api/pilchas", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setPilchaList(await response.json());
      } catch (e) {
        console.error(e);
      }
    })();
  }, [auth]);

  if (auth.isLoading) {
    return <div>Signing you in/out...</div>;
  }

  if (!auth.isAuthenticated) {
    return <div>Unable to log in</div>;
  }

  return (
    <div className="container">
      <h1 className="has-text-centered mb-6">
        <strong className="is-size-1 is-uppercase">Pilchas</strong>
        <a className="button is-primary mt-4 ml-4"><FontAwesomeIcon icon={faPlus} /></a>
      </h1>
      <div className='columns is-centered'>
        <div className='column is-10'>
          {
            pilchaList ? (
              <table className="table is-striped has-text-left" style={{width: "100%"}}>
                <thead>
                  <tr>
                    <th  style={{width: "60%"}}><strong>Descrição</strong></th>
                    <th  style={{width: "20%"}}><strong>Tipo</strong></th>
                    <th  style={{width: "20%"}}><strong>Usuário</strong></th>
                  </tr>
                </thead>
                <tbody>
                  {
                    pilchaList.map(pilcha => (
                      <tr key={pilcha.id}>
                        <td><Link to={"/pilcha/" + pilcha.id}>{pilcha.description}</Link></td>
                        <td>{pilcha.pilchaType}</td>
                        <td>{pilcha.owner}</td>
                      </tr>
                    ))
                  }
                </tbody>
              </table>
            ) : (
              <PilchaEmpty />
            )
          }
        </div>
      </div>
    </div>
  );
};
