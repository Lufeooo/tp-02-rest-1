#include <stdlib.h>
#include <stdio.h>
#include <string.h>

//data
typedef struct{
    int ano, mes,dia
}Data;
Data parseData(char *s){
    Data data;
    char *p1 = strtok(s, "-");
    char *p2 = strtok(NULL, "-");
    char *p3 = strtok(NULL, "-");

    data.ano = atoi(p1);
    data.mes = atoi(p2);
    data.dia = atoi(p3);

    return data;
}
void formatarData(Data d, char *resp){
    printf("%02d/%02d/%04d", d.dia, d.mes, d.ano);
}
//hora
typedef struct
{
    int hora, minuto;
}Hora;
Hora parseHora(char *s){
    Hora hora;
    char *p1 = srtok(s, ":");
    char *p2 = srtok(NULL, ":");
    hora.hora =atoi(p1);
    hora.minuto = atoi(p2);

    return hora;
}
void formatarHora(Hora h, char *resp){
    printf("%02d:%02d", h.hora, h.minuto);
}

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos[10][50];
    int qtdTipos;
    int faixaPreco;
    Hora horarioAbertura;
    Hora horarioFechamento;
    Data dataAbertura;
    int aberto;
}Restaurante;

Restaurante parseRestaurante(char *linha){
    Restaurante r;
    char *partes[10];
    int i =0;

    partes[i] = strtok(linha, ",");
    while(partes[i] !=NULL){
        i++;
        partes[i] = strtok(NULL, ",");
    }
     r.id = atoi(partes[0]);
    strcpy(r.nome, partes[1]);
    strcpy(r.cidade, partes[2]);
    r.capacidade = atoi(partes[3]);
    r.avaliacao = atof(partes[4]);

    // tipos
    r.qtdTipos = 0;
    char *tipo = strtok(partes[5], ";");
    while (tipo != NULL) {
        strcpy(r.tipos[r.qtdTipos++], tipo);
        tipo = strtok(NULL, ";");
    }

    // faixa preço
    r.faixaPreco = strlen(partes[6]);

    // horario
    char *h1 = strtok(partes[7], "-");
    char *h2 = strtok(NULL, "-");

    r.horarioAbertura = parseHora(h1);
    r.horarioFechamento = parseHora(h2);
    // data
    r.dataAbertura = parseData(partes[8]);

    // aberto
    r.aberto = (strcmp(partes[9], "true") == 0);

    return r;
}