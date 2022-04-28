# Spock Demo
Um projeto para demonstrar testes em Spock Framework.
Para deixar mais interessante as coisas, ele utiliza a API da Giant Bomb 
(https://www.giantbomb.com/api/) para listar certas informações de jogos.

## Como rodar a aplicação?
### Com uma chave da API
Caso não possua uma chave da API siga as instruções desse link: https://www.giantbomb.com/api/.
Tendo uma chave em mãos apenas suba a aplicação com o perfil padrão do Spring
(não há necessidade de configurar algo aqui, apenas suba a aplicação pela classe **Application**)
e atribua sua chave à seguinte variável de ambiente:
```
GIANT_BOMB_API_KEY=SUA_CHAVE_BEM_AQUI
```
Em IDEs como Intellij e Eclipse você pode configurar variáveis de ambiente ao subir a aplicação.

### Sem uma chave da API
Caso você não queira criar uma chave, você pode utilizar o Wiremock para simular a API.
Apenas suba a instância do Wiremock e a aplicação com o perfil local.
Para subir a instância do Wiremock vá para `/config/wiremock` e execute o seguinte comando nesse diretório:
```
docker-compose up
```
É necessário ter instalado Docker e Docker Compose.

Para subir a aplicação com o perfil local use o seguinte comando como argumento:
```
--spring.profiles.active=local
```
Em IDEs como Intellij e Eclipse você pode configurar argumentos ao subir a aplicação.

Os mapeamentos do Wiremock só possuem dados para a busca de **starfox**.
Qualquer outro valor irá retornar vazio.