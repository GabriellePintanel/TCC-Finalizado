import { faCheck, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { useParams } from "react-router-dom";

interface Pilcha {
    id: string;
    pilchaType: number;
    description: string;
    tag: string;
    notes: string;
    owner: string;
}

const pilchaDescription = (id) => {
    const pilcha = [ "", "Bombacha", "Bota", "Camisa", "Faixa", "Guaiaca", "Sapatilha", "Vestido" ];
    return pilcha[id];
}

export const PilchaEditPage = () => {
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
                <strong className="is-size-1 is-uppercase">Detalhes da Pilcha - {pilchaDescription(pilcha?.pilchaType)}</strong>
            </h1>
            <div className=" mx-6">
                <div className="field">
                    <label className="label">Nome</label>
                    <div className="control">
                        <input className="input" type="text" placeholder="Digite um nome" value={pilcha?.description} />
                    </div>
                </div>
                <div className="field">
                    <label className="label">Tipo</label>
                    <div className="control">
                        <div className="select">
                            <select id="pilchaType" name="pilchaType" defaultValue={pilcha?.pilchaType}>
                                <option value="1">Bombacha</option>
                                <option value="2">Bota</option>
                                <option value="3">Camisa</option>
                                <option value="4">Faixa</option>
                                <option value="5">Guaiaca</option>
                                <option value="6">Sapatilha</option>
                                <option value="7">Vestido</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Tag</label>
                    <div className="control">
                        <input className="input" type="text" placeholder="Digite uma tag" value={pilcha?.tag} />
                    </div>
                </div>
                <div className="field">
                    <label className="label">Usuário</label>
                    <div className="control">
                        <input className="input" type="text" placeholder="Digite o nome do usuário da pilcha" value={pilcha?.owner} />
                    </div>
                </div>
                <div className="field">
                    <label className="label">Observações</label>
                    <div className="control">
                        <textarea className="textarea" rows={10} cols={80} placeholder="Observações sobre a pilcha" value={pilcha?.notes}></textarea>
                    </div>
                </div>
                
                <div className="field">
                    <div className="control">
                        <button className="button is-primary"><FontAwesomeIcon icon={faCheck} />&nbsp;Salvar</button>
                    </div>
                </div>
                <div className="field">
                    <div className="control">
                        <button className="button is-danger"><FontAwesomeIcon icon={faXmark} />&nbsp;Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    )
}