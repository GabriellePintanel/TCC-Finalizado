import desfileGaucho from "../assets/img/desfile-gaucho.png";

export const HomePage = () => {
  return (
    <>
        <figure className="image container is-256x256" style={{ width: 600 }}>
          <img src={desfileGaucho} />
        </figure>
      <h1 className="is-size-1 has-text-centered is-uppercase is-overlay">
        <strong>Controle de Grupos de Danças Tradicionais Gaúchas</strong>
      </h1>
      <p className="is-size-4 has-text-centered m-6">
        Bem-vindo ao site para realizar o controle de grupos de danças
        tradicionais gaúchas.
        <br />
        Aqui, você encontrará as ferramentas necessárias para gerenciar e
        fortalecer os grupos de danças tradicionais gaúchas em sua comunidade.
      </p>
    </>
  );
};
