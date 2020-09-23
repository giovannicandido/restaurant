# Voto em restaurants

Olá, bem vindo ao sistema de escolha de restaurants da dbserver.

## Requisitos para rodar o software

* Java JDK 11
* NodeJS >= 10

## Como iniciar o software

A partir daqui refere-se a commando **gradlew** como sendo o executavel na pasta 
**restaurant/gradlew** no linux e **restaurant/gradlew.bat** no windows

**Servidor:** Usando um terminal na pasta _restaurant_ (cd restaurant) 
Rode o comando `./gradlew bootRun --args ' --restaurant.vote.startHour=8 --restaurant.vote.finishHour=23'`

Os parametros passados são para validar em qual janela de horários pode-se votar. Por padrão esse
valor inicia em 8 horas e termina as 12 horas.
Experimente trocar os horários para que veja o bloqueio ou não do voto dependendo do horario
atual da máquina.

Nota: repare no espaço adicionado ao primeiro parametro, isso é necessário no windows e no macosx por
algum motivo desconhecido.


**Interface:** Entre na pasta ui (cd ui), rode o commandos:

    npm install
    npm run start

### Utilização da aplicação

Para ver os restaurantes e votar neles você deve estar logado. Click em __login__ no
barra de menu à direita.

A aplicação cria os seguintes usuários ao iniciar:

* Usuario: user1 Senha: 123456
* Usuario: user2 Senha: 123456

A aplicação utiliza um banco de dados em memória

### Testando a aplicação

Rode o comando `gradlew check` 

Relatórios de teste podem ser acessados em **build/reports/tests/index.html**, e para cobertura em 
**build/reports/jacoco/test/html/index.html**

Todos os testes de API são testes de integração, os testes de repository incluem a camada de acesso
a dados, e os testes de servico foram feitos como testes unitário mocando-se dependencias.

### Arquitetura

O sistema é dividido em interface e servidor sendo a comunicação via protocolo rest.
Um banco de dados em memória H2 é utilizado.
O servidor utiliza o framework [Spring boot](https://spring.io/projects/spring-boot) e a interface
utiliza o framework [Angular](https://angular.io)

#### Autenticação

O mecanismo de autenticação e segurança do spring foi customizado para utilizar Tokens 
[JWT](https://jwt.io/) (Json Web Token) enviado para o cliente no momento do login que devem ser 
adicionado ao cabecalho **Authorization** de todas as requisições. Essa é a forma mais usual de se 
trabalhar autenticação em uma _single page application_.

Os usuário se encontram no banco de dados na tabela **users** tem seus grupos de acesso na tabela
**roles**. 

Existe duas roles cadastradas no sistema, **ROLE_ADMIN** e **ROLE_USER** mas apenas role  **ROLE_USER** é utilizada.

Um enum **ERole** mantem o mapeamento de todas as roles possíveis e deve ser atualizado caso
uma nova role seja criada.

#### Estrutura de pastas

Seguiu-se a [arquitetura hexagonal](https://dzone.com/articles/hexagonal-architecture-what-is-it-and-how-does-it)

* application - possui os casos de uso e as logicas de orquestramento dos serviços existentes
* domain - core de um módulo, possui as entidades e interfaces para repositorios, além de serviços
de domínio, compartilhados pelos cados de uso como por exemplo regras de negócio.
* framework - Portas de entrada para aplicação, no caso existem portas de api rest
* infrastructure - Configurações, implementação de repositório, de saidas como envio de email, etc. 

Importante notar que a dependencia diminui a medida que se entra no **dominio**, ou seja os pacotes
ao redor como application e framework podem depender do dominio mas o contrário não é verdade.
O ideal segundo a arquitetura é que o dominio seja livre também de dependencias externas como frameworks,
mas um baixo nível de dependencia foi optado aqui para simplificar o código. Um exemplo disso são as
entidades que foram mapeadas usando JPA dentro do domínio. Em locais onde não se pretende modificar 
os frameworks utilizados isso não representa um problema.

### Regras de negócio e como foram implementadas

* **Estoria 1** Essa Estória foi implementada na classe _VoteUseCase_ e sua regra de negócio está
na mesma. Também foi implementa um regra para permitir o voto em um time frame específico que por
padrão é das 8:00 as 12:00, isso é importante para computarmos os votos e escolher o restaurante.
* **Estoria 2** Essa história é implementada na classe _ListRestaurantUseCase_ e usa a regra aplicada
na classe _RestaurantService_ para listar apenas restaurantes não escolhidos na semana, ou seja o cliente não
irá ver e votar nesses restaurantes.
* **Estoria 3** Essa história está implementada na classe _ListVoteResultUseCase_ que apresenta todos os
resultados computados nos dias, com o restaurante vencedor e o numero de votos. 
É complementada por um serviço agendado na classe 
**VoteResultServiceImpl** que computa os resultados para o dia corrente. Por padrão roda de segunda
a sexta ao 12:00:30s, ou seja, 30 segundos depois de finalizado o período de votação padrão. 
Isso pode ser customizado pelo parametro **restaurant.vote.processCron**. Para virtude de testes
também é permitido rodar esse serviço manualmente, isso pode ser acesso no menu **Results** da
interface com usuário.
Caso um restaurante empate no numero de votos, o ultimo a ser percorrido é selecionado.

### Parametros da aplicação

Os seguintes parametros, com seus valores padrão, podem ser customizados:

* **restaurant.app.jwtSecret=theSecretKey**: Secret key usada para assinar os token JWT.
Deve ser uma senha forte
* **restaurant.app.jwtExpirationMs=86400000**: Tempo de expiração do token JWT em milisegundos. Por
padrão 1440 minutos
* **restaurant.developmentUrl=http://localhost:4200**: Url de desenvolvimento para configuração do 
[CORS](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS), liberando a aplicação cliente a fazer
requisições a url do servidor.
* **restaurant.productionUrl=http://localhost:4200**: Mesma coisa da url acima só que para produção
* **restaurant.vote.startHour=8**: Hora de inicio do voto
* **restaurant.vote.finishHour=12**: Hora de terminino dos votos
* **restaurant.vote.processCron=30 0 12 * * MON-FRI**: Expressão cron para rodar o processo de apuração
dos resultados
* **v1Api=/api**: Caminho raiz para os controladores da aplicação.

Para customizar um parametro desse há algumas formas.

* Utilizando o gradlew --args ' --parametro1.algumacoisa=valor --parametro2.algumacoisa=valor'
* Criando se variaveis de ambiente com o camel case e . em maiúsculo. Exemplo: 
`export RESTAURANT_VOTE_START_HOUR=12`
* Fazendo o build da aplicação com o comando `./gradlew build` e rodando o jar resultante 
`java -jar build/libs/restaurant-0.0.1-SNAPSHOT.jar --restaurant.vote.startHour=12`

### Trocando o banco de dados

O banco de dados pode ser trocado com o seguinte parametros (exemplo postgres):

* spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
* spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
* spring.datasource.username=user
* spring.datasource.password=passwd

#### Acessando o banco de dados em memória

Um console para acessar e manipular o banco de dados em memória pode se encontrado na url 
**http://localhost:8080/h2-console**, usuário **sa** senha **sa**

## Melhorias

* Tratar exceções de segurança na interface com usuário.
* Redirecionar usuário para login caso não esteje autenticado
* Não recarregar a lista de restaurants inteira ao computar um voto.
* Melhorar a performance da listagem. Hoje para cada resturante é feito uma nova
query no banco contando os votos, isso se deve ao fato de não se poder fazer em uma
única query e incluir os restaurantes não selecionados na semana e também os que não tiveram votos.
Mas posso estar enganado. No entanto esse problema só teria impacto para muitos restaurantes cadastrados.
* Enviar notificação por email aos usuários sobre o restaurante escolhido seria uma boa feature a se adicionar.
* Aumentar a cobertura dos testes. A aplicação tem uma média de 73% de cobertura, mas os itens principais, 
que são os casos de uso do modulo core e os servicos desse modulo possuem 95% e 87% respectivamente.
* Bloquear o voto ao restaurantes selecionados na semana. Atualmente o restaurante só é escondido da lista.

