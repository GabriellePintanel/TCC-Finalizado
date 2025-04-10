import ctgImage from "../assets/img/ctg.png";
import loveTradition from "../assets/img/casal-gaucho.png";
import improveTradition from "../assets/img/chimarrao.png";
import dancingGroup from "../assets/img/grupo-danca.png";

export const AboutPage = () => {
  return (
    <>
      <figure className="image container is-256x256" style={{ width: 600 }}>
        <img src={ctgImage} />
      </figure>
      <h1 className="is-size-1 has-text-centered is-uppercase is-overlay">
        <strong>Sobre o CTG</strong>
      </h1>
      <p className="is-size-4 has-text-centered m-6">
        Site para realizar o controle de grupos de danças tradicionais gauchas.
        Aqui você pode adicionar seu próprio texto. É fácil, é só clicar em
        "Editar texto" ou clicar duas vezes sobre mim para editar seu conteúdo e
        alterar a fonte. Você também pode me arrastar e soltar em qualquer lugar
        da página. É um ótimo espaço para você compartilhar a sua história.
      </p>
      <p className="is-size-4 has-text-centered m-6">
        Use este espaço para escrever um texto longo sobre sua empresa e sobre
        os serviços que você oferece. Conte aos visitantes a história de como
        você teve a ideia de criar essa empresa e o que a torna diferente de
        seus concorrentes. Você pode apresentar a sua equipe, seus projetos e
        metas. Faça com que a sua empresa se destaque e mostre aos visitantes
        quem você é. Clique em "Editar texto" para começar.
      </p>
      <h2 className="is-size-2 has-text-centered">
        <strong>Objetivos</strong>
      </h2>
      <div className="columns">
        <div className="column m-6">
          <figure className="image">
            <img className="is-rounded" src={loveTradition} />
          </figure>
          <p className="is-size-4 has-text-centered">
            Preservar a tradição gaucha através da educação
          </p>
        </div>
        <div className="column m-6">
          <figure className="image">
            <img className="is-rounded" src={improveTradition} />
          </figure>
          <p className="is-size-4 has-text-centered">
            Promover a cultura gaucha em todas as regiões de forma igualitária
          </p>
        </div>
        <div className="column m-6">
          <figure className="image">
            <img className="is-rounded" src={dancingGroup} />
          </figure>
          <p className="is-size-4 has-text-centered">
            Auxiliar mais grupos de danças tradicionais gauchas a se
            apresentarem em eventos
          </p>
        </div>
      </div>
    </>
  );
};
