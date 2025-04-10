import { faPen, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { Link, useParams } from "react-router-dom";

interface Pilcha {
    id: string;
    pilchaType: string;
    description: string;
    tag: string;
    notes: string;
    owner: string;
}

export const PilchaDetailPage = () => {
    const params = useParams();
    const auth = useAuth();
    const [pilcha, setPilcha] = useState<Pilcha>();

    useEffect(() => {
        (async () => {
            try {
                const token = auth.user?.access_token;
                const response = await fetch("http://localhost:8080/api/pilchas/" + params.id, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setPilcha(await response.json());
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
                <strong className="is-size-1 is-uppercase">Detalhes da Pilcha - {pilcha?.pilchaType}</strong>
                <br />
                <Link to={"/pilcha/edit/" + params.id} className="button is-primary mx-4"  ><FontAwesomeIcon icon={faPen} /></Link>
                <Link to={"/pilcha/remove/" + params.id} className="button is-danger mx-4"><FontAwesomeIcon icon={faTrash} /></Link>
            </h1>
            <div className=" mx-6">
                <div className="field">
                    <label className="label">Nome</label>
                    <div className="control">
                        <p>{pilcha?.description}</p>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Tag</label>
                    <div className="control">
                        <p>{pilcha?.tag}</p>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Usuário</label>
                    <div className="control">
                        <p>{pilcha?.owner}</p>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Observações</label>
                    <div className="control">
                        <p>{pilcha?.notes}</p>
                    </div>
                </div>
            </div>
        </div>
    )
}