💃 Sistema de Gerenciamento de Invernadas Artísticas com RFID

Este projeto propõe uma solução tecnológica para a modernização da gestão de invernadas artísticas nos Centros de Tradições Gaúchas (CTGs), integrando hardware, tags RFID e uma API web com uma interface moderna para facilitar o controle de pilchas, registro de notas de rodeios, calendário de eventos e geração de gráficos de desempenho das invernadas nos rodeios.

📌 Funcionalidades

📦 Controle de Pilchas: Registro individual das vestimentas por meio de tags RFID MIFARE.

📊 Notas de Rodeios: Registro e exibição de notas de cada apresentação artística nos rodeios com geração de gráficos.

🗓️ Calendário de Eventos: Visualização de eventos com opção para confirmação de presença.

🔄 Integração Completa: Comunicação entre o protótipo físico, backend e frontend.

🛠️ Tecnologias Utilizadas

💻 Backend

Spring Boot (Java)

API REST

Banco de Dados (MySQL ou H2)

🌐 Frontend

Vite + TypeScript

HTML5 + Bulma CSS

Axios para consumo da API

⚙️ Prototipagem

ESP32 / Arduino UNO

Módulo RFID RC522

Display TFT SPI 1.8"

Botão físico para alternar leitura e escrita

⚠️ Possíveis Problemas e Soluções

Erro -1 em requisições HTTP (ESP32): Identificada intermitência na conexão Wi-Fi e no uso do HttpClient. Reinicializar a conexão Wi-Fi temporariamente resolve. Possível causa: cache interno não identificado.

Instabilidade na leitura RFID: Verifique as conexões SPI e a alimentação do módulo.

Em andamento
