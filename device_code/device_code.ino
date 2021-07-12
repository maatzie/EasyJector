#include <ESP8266WiFi.h>
#include "ArduinoJson.h"

#define STASSID "IM-TEST"
#define STAPSK "abcd1234"

#define BAUD 115200

#define MAX_SRV_CLIENTS 2
#define PORT 1234

#define MAX_MESSAGE_LENGTH 100
#define BATTERY_SAMPLES 10
#define MAX_READ_VOLTAGE 4.2
#define MIN_READ_VOLTAGE 3.3
#define MESSAGE_SIZE_LENGTH 4

const IPAddress LOCAL_IP(192, 168, 1, 1);
const IPAddress GATEWAY(192, 168, 1, 1);
const IPAddress SUBNET(255, 255, 255, 0);

const char *KEY = "secret";
const int KEY_LENGTH = strlen(KEY);

WiFiServer server(PORT);
WiFiClient serverClients[MAX_SRV_CLIENTS];

void setup() {
  // put your setup code here, to run once:
  Serial.begin(BAUD);
  pinMode(D4, OUTPUT);
  pinMode(A0, INPUT);

  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);

  WiFi.softAPConfig(LOCAL_IP, GATEWAY, SUBNET);
  WiFi.softAP(STASSID, STAPSK);

  server.begin();
  server.setNoDelay(true);
}

float get_battery_state() {
  int sampleCount = 0;
  int sum = 0.0;

  while (sampleCount < BATTERY_SAMPLES) {
    sum += analogRead(A0);
    sampleCount++;
    delay(10);
  }

  float voltage = ((float) sum / (float) BATTERY_SAMPLES * MAX_READ_VOLTAGE) / 1024.0;
  float batteryState = (voltage - MIN_READ_VOLTAGE) / ((float) (MAX_READ_VOLTAGE - MIN_READ_VOLTAGE)) * 100.0;
  batteryState = (batteryState >= 0) ? batteryState : 0;

  Serial.print("battery state: ");
  Serial.print(batteryState);
  Serial.println("%");
  return batteryState;
}

void xor_crypt(char *message, int messageLength) {
  int keyIndex = 0;
  for (int messageIndex = 0; messageIndex < messageLength; messageIndex++) {
    message[messageIndex] ^= KEY[keyIndex++];
    if (keyIndex >= KEY_LENGTH) keyIndex = 0;
  }
}

void loop() {
  // put your main code here, to run repeatedly:
  if (server.hasClient()) {
    for (int i = 0; i < MAX_SRV_CLIENTS; i++) {
      if (!serverClients[i]) {
        serverClients[i] = server.available();
        Serial.print("new client index: ");
        Serial.println(i);
        break;
      }
    }
  }

  for (int i = 0; i < MAX_SRV_CLIENTS; i++) {
    if (serverClients[i].available()) {
      char messageBuffer[MAX_MESSAGE_LENGTH] = "";
      int bufferPosition = 0;
      while (serverClients[i].available()) {
        messageBuffer[bufferPosition++] = serverClients[i].read();
      }
      messageBuffer[bufferPosition] = '\0';

      xor_crypt(messageBuffer, bufferPosition);
      
      Serial.print("received message: ");
      Serial.println(messageBuffer);

      StaticJsonDocument<200> jsonBuffer;
      auto error = deserializeJson(jsonBuffer, messageBuffer);
      if (error) {
        Serial.print("Error while deserializing JSON");
        continue;
      }
      const char *cmd = jsonBuffer["cmd"];

      StaticJsonDocument<200> replyBuffer;
      char replyJson[MAX_MESSAGE_LENGTH] = {'\0'};

      if (!strcmp(cmd, "on")) {
        digitalWrite(D4, HIGH);
        replyBuffer["type"] = "confirmation";
        replyBuffer["result"] = "device on";
      } else if (!strcmp(cmd, "off")) {
        digitalWrite(D4, LOW);
        replyBuffer["type"] = "confirmation";
        replyBuffer["result"] = "device off";
      } else if(!strcmp(cmd, "battery")) {        
        float batteryState = get_battery_state();
        
        replyBuffer["type"] = "batteryInfo";
        replyBuffer["result"] = batteryState;
      } else {
        replyBuffer["type"] = "unknown";
      }

      serializeJson(replyBuffer, replyJson);
      
      Serial.print("replied with message: ");
      Serial.println(replyJson);
      
      int replySize = strlen(replyJson);
      xor_crypt(replyJson, replySize);

      char replyMessage[MAX_MESSAGE_LENGTH + MESSAGE_SIZE_LENGTH];
      snprintf(replyMessage, MESSAGE_SIZE_LENGTH, "%d", replySize);
      memcpy(replyMessage + MESSAGE_SIZE_LENGTH, replyJson, replySize);
      
      serverClients[i].write(replyMessage, replySize + MESSAGE_SIZE_LENGTH);
    }
  }
}
