# 🌍 Aplicativo de Exibição de Usuários com API do Random User e Integração com Google Maps

Este projeto é um aplicativo Java que utiliza a API do [randomuser.me](https://randomuser.me/) para buscar e exibir dados de um usuário aleatório, aplicando filtros de gênero e nacionalidade. A segunda fase do aplicativo inclui a exibição de localização no Google Maps, mostrando a distância em linha reta entre o usuário e o dispositivo.

## ⚙️ Funcionalidades

### 🔹 Parte 1: Exibição de Dados do Usuário
- 🔍 Acessa a API do Random User para buscar dados de um usuário aleatório.
- 🎯 Filtra os resultados por gênero e nacionalidade.
- 📷 Exibe informações básicas do usuário, incluindo foto, obtidas via JSON usando a biblioteca Retrofit.

### 🔹 Parte 2: Remodelagem com Fragmentos e Integração com Google Maps
- 📱 O aplicativo é remodelado para utilizar dois fragmentos alternados:
  - **Fragmento 1**: Exibe a interface de seleção do usuário, incluindo a foto.
  - **Fragmento 2**: Exibe a localização do usuário no MapView (Google Maps).
- 📏 Calcula e exibe a distância em linha reta entre o usuário e o dispositivo.

## 📝 Requisitos

- 📦 **Biblioteca Retrofit**: Utilizada para buscar os dados em JSON da API do Random User.
- 🗺️ **Google Maps API**: Utilizada para exibir a localização do usuário selecionado.
- 📍 **Permissões de Localização**: Necessárias para calcular a distância entre o dispositivo e o usuário.
