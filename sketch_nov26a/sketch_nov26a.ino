#include <SPI.h>
#include <MFRC522.h>
#include <Adafruit_GFX.h>
#include <Adafruit_ST7735.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

//Variaveiis para usar o WIFI
const char* ssid = "CARLOS_UP_2G";
const char* password = "15975346";

const char* serverName = "http://192.168.50.245:8080/api/reader/69F496B9";
char json[128] = { 0 };

StaticJsonDocument<256> doc;

// Pinos do Display TFT SPI
#define TFT_CS 5   // Chip Select (CS) do display
#define TFT_RST 4  // Reset (RST) do display
#define TFT_DC 2   // Data/Command (DC) do display

//Pinos do RFID
#define SS_PIN 15       // Pino SS do MFRC522
#define RST_PIN 22      // Pino RST do MFRC522
#define PINO_BOTAO2 19  // Pino do botão para alternar para ler o uid
#define PINO_BOTAO1 27  // Pino do botão para alternar para ler o uid
#define PINO_BOTAO3 26  // Pino do botão para alternar para ler o uid

MFRC522 mfrc522(SS_PIN, RST_PIN);
MFRC522::MIFARE_Key key;

// Instância SPI para o Display

Adafruit_ST7735 display(TFT_CS, TFT_DC, 23, 18, TFT_RST);

bool modoLeituraUID = false;       // Variável para controlar o modo (leitura = true, escrita = false)
bool botaoPressionadoUID = false;  // Estado do botão
unsigned long debounceTime = 0;    // Variável para controle de debounce do botão

bool modoLeituraLER = false;       // Variável para controlar o modo (leitura = true, escrita = false)
bool botaoPressionadoLER = false;  // Estado do botão

bool modoLeituraESCREVER = false;       // Variável para controlar o modo (leitura = true, escrita = false)
bool botaoPressionadoESCREVER = false;  // Estado do botão

String dadosPilchas;
int idPilchas;
String nomePilchas;
String usuarioPilchas;


unsigned long previousMillis = 0;
const long interval = 5000;

void setup() {
  Serial.begin(9600);
  SPI.begin(14, 12, 13);  // Define SCK, MISO e MOSI para o SPI
  mfrc522.PCD_Init();

  pinMode(PINO_BOTAO2, INPUT_PULLUP);  // Configura o botão com pull-up interno
  pinMode(PINO_BOTAO1, INPUT_PULLUP);  // Configura o botão com pull-up interno
  pinMode(PINO_BOTAO3, INPUT_PULLUP);  // Configura o botão com pull-up interno


  // Define chave padrão para autenticação
  for (byte i = 0; i < 6; i++) key.keyByte[i] = 0xFF;

  // Inicialização do Display
  display.initR(INITR_BLACKTAB);  // Ajuste para o modelo do display
  display.setRotation(1);
  display.fillScreen(ST77XX_BLACK);
  display.setTextColor(ST77XX_WHITE);
  display.setTextSize(1);
  display.setCursor(10, 10);
  display.println("Iniciando...");
  display.fillScreen(ST77XX_BLACK);
  display.setCursor(10, 10);
  display.println("Escolha o que quer fazer:");
  display.setCursor(10, 20);
  display.println("Botao 1: Leitura da Tag");
  display.setCursor(10, 30);
  display.println("Botao 2: Leitura do UID ");
  display.setCursor(10, 40);
  display.println("da Tag");
  display.setCursor(10, 50);
  display.println("Botao 3: Escrita na Tag");

  //Inicializa wifi
  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  Serial.println("Escolha oque quer fazer: ");

  const size_t capacity = JSON_OBJECT_SIZE(1) + JSON_ARRAY_SIZE(8) + 146;
  DynamicJsonDocument doc(capacity);
}


void loop() {
  // Verifica se o botão foi pressionado para alternar o modo
  if (digitalRead(PINO_BOTAO2) == LOW) {
    if (!botaoPressionadoUID && (millis() - debounceTime > 500)) {
      modoLeituraUID = true;
      modoLeituraLER = false;
      modoLeituraESCREVER = false;

      botaoPressionadoLER = false;       // Redefine o estado do botão quando liberado
      botaoPressionadoESCREVER = false;  // Redefine o estado do botão quando liberado
      botaoPressionadoUID = true;        // Marca o botão como pressionado
      debounceTime = millis();           // Atualiza o tempo do debounce

      // Exibe o modo no display
      display.fillScreen(ST77XX_BLACK);
      display.setCursor(10, 10);
      display.println("Modo: Leitura do UID da ");
      display.setCursor(10, 20);
      display.println("Tag");
    }
  } else if (digitalRead(PINO_BOTAO1) == LOW) {
    if (!botaoPressionadoLER && (millis() - debounceTime > 500)) {
      modoLeituraUID = false;
      modoLeituraLER = true;
      modoLeituraESCREVER = false;

      botaoPressionadoLER = true;        // Redefine o estado do botão quando liberado
      botaoPressionadoESCREVER = false;  // Redefine o estado do botão quando liberado
      botaoPressionadoUID = false;       // Marca o botão como pressionado
      debounceTime = millis();           // Atualiza o tempo do debounce

      // Exibe o modo no display
      display.fillScreen(ST77XX_BLACK);
      display.setCursor(10, 10);
      display.println("Modo: Leitura da Tag");
    }
  } else if (digitalRead(PINO_BOTAO3) == LOW) {
    if (!botaoPressionadoESCREVER && (millis() - debounceTime > 500)) {
      modoLeituraUID = false;
      modoLeituraLER = false;
      modoLeituraESCREVER = true;

      botaoPressionadoLER = false;      // Redefine o estado do botão quando liberado
      botaoPressionadoESCREVER = true;  // Redefine o estado do botão quando liberado
      botaoPressionadoUID = false;      // Marca o botão como pressionado

      debounceTime = millis();  // Atualiza o tempo do debounce

      // Exibe o modo no display
      display.fillScreen(ST77XX_BLACK);
      display.setCursor(10, 10);
      display.println("Modo: Escrever na Tag");
    }
  }
  if (modoLeituraUID) {
    lerUIDDoCartao();
  } else if (modoLeituraLER) {
    lerDadosDoCartao();
  } else if (modoLeituraESCREVER) {
    gravarDadosNoCartao();
  }
  delay(2000);  // Espera um segundo antes de tentar nova leitura/gravação
}

// Função para gravação de dados no cartão
void gravarDadosNoCartao() {
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    Serial.print("UID do cartão: ");
    display.setCursor(10, 20);
    display.print("UID: ");
    for (byte i = 0; i < mfrc522.uid.size; i++) {
      Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
      Serial.print(mfrc522.uid.uidByte[i], HEX);
      display.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
      display.print(mfrc522.uid.uidByte[i], HEX);
    }
    Serial.println();
    Serial.println("fazendo requisisao");

    dadosPilchas = httpGETRequest(serverName);
    if (dadosPilchas == "--") {
      return;
    }

    byte buffer[18] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    byte block = 16;
    int bi = -1;
    bool sus = true;
    for (int i = 0; i < dadosPilchas.length(); i++) {
      bi = i % 16;
      buffer[bi] = dadosPilchas.charAt(i);
      if (bi == 15) {
        sus = gravacao(block++, buffer);
        for (byte i = 0; i < 18; i++) buffer[i] = 0;
        bi = -1;
        if (sus == false) {
          break;
        }
      }
    }
    if (sus == true) {
      if (bi > -1) {
        sus = gravacao(block, buffer);
        if (sus == true) {
          Serial.println("Sucesso total");
        } else {
          Serial.println("Insucesso no final");
        }
      }
    } else {
      Serial.println("Insucesso");
    }
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
  }
}

// Função para leitura do UID do cartão
void lerUIDDoCartao() {
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    Serial.print("UID do cartão: ");
    display.setCursor(10, 30);
    display.print("UID: ");

    for (byte i = 0; i < mfrc522.uid.size; i++) {
      Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
      Serial.print(mfrc522.uid.uidByte[i], HEX);
      display.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
      display.print(mfrc522.uid.uidByte[i], HEX);
    }
    Serial.println();

    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
  }
}
// Função para leitura de dados do cartão
void lerDadosDoCartao() {
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    Serial.println();
    int block = 16;
    String json = "";
    while (true) {
      String cartao = leitura(block++);
      json.concat(cartao);
      
//      display.setCursor(10, (block * 10) - 130);
//      display.print(cartao);
      if (cartao.length() < 16) {
        break;
      }
    }
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    Serial.println(json);
    manipularJSON(json);
  }
}

void manipularJSON(String pilchas) {
  memset(json, 0, sizeof(json));
  pilchas.toCharArray(json, 400);
  deserializeJson(doc, json);

  const int doc_id = doc["id"];                //pega o id da pilcha do json
  const char* doc_pilchaName = doc["pilcha"];  //pega o nome da pilcha do json
  const char* doc_userName = doc["user"];      //pega o nome do usuario da pilcha do json

  idPilchas = doc_id;
  nomePilchas = doc_pilchaName;
  usuarioPilchas = doc_userName;

  display.setCursor(10, 40);
  display.print("Id: ");
  display.print(idPilchas);
  display.setCursor(10, 50);
  display.print("Pilcha: " + nomePilchas);
  display.setCursor(10, 60);
  display.print("Nome: " + usuarioPilchas);
}

String httpGETRequest(const char* serverName) {
  WiFiClient client;
  HTTPClient http;

  // Your Domain name with URL path or IP address with path
  http.begin(client, "192.168.50.39", 8080, "/api/reader/69F496B9", false);
  http.setReuse(true);

  // Send HTTP POST request
  int httpResponseCode = http.GET();

  String payload = "--";

  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    payload = http.getString();
  } else {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }
  // Free resources
  http.end();
  client.stop();
  Serial.println(payload);
  return payload;
}

bool gravacao(int block, byte* buffer) {
  // Autenticando
  MFRC522::StatusCode status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, block, &key, &(mfrc522.uid));
  if (status != MFRC522::STATUS_OK) {
    Serial.print("Falha na autenticação: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
    display.setCursor(10, 30);
    display.print("Falha na autenticação: ");
    display.setCursor(10, 40);
    display.print(mfrc522.GetStatusCodeName(status));
    return false;
  }
  status = mfrc522.MIFARE_Write(block, buffer, 16);
  if (status != MFRC522::STATUS_OK) {
    Serial.print("Falha na gravação: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
    display.setCursor(10, 30);
    display.print("Falha na gravação: ");
    display.setCursor(10, 40);
    display.print(mfrc522.GetStatusCodeName(status));
    return false;
  }
  Serial.println("Dados gravados com sucesso!");
  display.setCursor(10, 30);
  display.print("Dados gravados com ");
  display.setCursor(10, 40);
  display.print("sucesso!");
  return true;
}

String leitura(int block) {
  byte buffer[18];
  byte bufferSize = sizeof(buffer);
  for (byte i = 0; i < bufferSize; i++) buffer[i] = 0;
  buffer[16] = 0;
  // Autenticando
  MFRC522::StatusCode status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, block, &key, &(mfrc522.uid));
  if (status != MFRC522::STATUS_OK) {
    Serial.print("Falha na autenticação: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
    display.setCursor(10, 30);
    display.print("Falha na autenticação: ");
    display.setCursor(10, 40);
    display.print(mfrc522.GetStatusCodeName(status));
    return "--";
  }
  status = mfrc522.MIFARE_Read(block, buffer, &bufferSize);
  if (status != MFRC522::STATUS_OK) {
    Serial.print("Falha na leitura: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
    display.setCursor(10, 30);
    display.print("Falha na leitura: ");
    display.setCursor(10, 40);
    display.print(mfrc522.GetStatusCodeName(status));
    return "--";
  }
  buffer[16] = 0;
  Serial.println("Dados lidos com sucesso!");
  return String((char*)buffer);
}