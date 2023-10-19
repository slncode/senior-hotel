# Projeto Desafio Backend Senior - Sergio

## Necessário fazer a configuração do application.propeties para usuário e senha do banco.

Consultas pelo Postman:

POST: localhost:8080/hospedes/cadastrar-hospede (é possível cadastrar um hóspede no hotel, da seguinte maneira): 

{
    "nome": "Fulano",    
    "documento": "987654321",    
    "telefone": "219887766"    
}

POST: localhost:8080/hospedes/checkin (é possível fazer o checkin passando um JSON como o seguinte) :

{
  "hospede": {  
    "nome": "Fulano",
    "documento": "987654321",
    "telefone": "2188996655"
  },  
  "dataEntrada": "2023-10-15T08:00:00",  
  "dataSaida": "2023-10-21T10:17:00",  
  "adicionalVeiculo": false  
}

Para ver a situação do check in -> GET: localhost:8080/hospedes/checkin/{hospede-id} 

Para buscar a situação atual de determinado hóspede -> GET: localhost:8080/hospedes/buscar-situacao-hospede/documento_hospede 

Para buscar hospede pelo nome -> GET: localhost:8080/hospedes/buscar-hospede-nome/{nome} 

Para buscar hospede pelo documento -> GET: localhost:8080/hospedes/buscar-hospede-documento/{documento} 

Para buscar hóspede pelo telefone -> GET: localhost:8080/hospedes/buscar-hospede-telefone/{telefone} 

Para buscar situação do hóspede -> GET: localhost:8080/hospedes/buscar-situacao-hospede/{hospedeId} 
