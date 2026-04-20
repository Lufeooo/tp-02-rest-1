
import java.io.*;
import java.util.*;

class Data {
    private int ano, mes, dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public static Data parseData(String s) {
        String[] p = s.split("-");
        return new Data(
            Integer.parseInt(p[0]),
            Integer.parseInt(p[1]),
            Integer.parseInt(p[2])
        );
    }

    public String formatar() {
        String d = (dia < 10 ? "0" : "") + dia;
        String m = (mes < 10 ? "0" : "") + mes;
        return d + "/" + m + "/" + ano;
    }
}

class Hora {
    private int hora, minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        String[] p = s.split(":");
        return new Hora(
            Integer.parseInt(p[0]),
            Integer.parseInt(p[1])
        );
    }

    public String formatar() {
        String h = (hora < 10 ? "0" : "") + hora;
        String m = (minuto < 10 ? "0" : "") + minuto;
        return h + ":" + m;
    }
}

class Restaurante {
    private int id, capacidade, faixaPreco;
    private String nome, cidade;
    private double avaliacao;
    private String[] tipos;
    private Hora abertura, fechamento;
    private Data data;
    private boolean aberto;

    public Restaurante(int id, String nome, String cidade, int capacidade,
                       double avaliacao, String[] tipos, int faixaPreco,
                       Hora abertura, Hora fechamento, Data data, boolean aberto) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.tipos = tipos;
        this.faixaPreco = faixaPreco;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.data = data;
        this.aberto = aberto;
    }

    public static Restaurante parseRestaurante(String linha) {
        String[] dividir = linha.split(",");

        int id = Integer.parseInt(dividir[0]);
        String nome = dividir[1];
        String cidade = dividir[2];
        int capacidade = Integer.parseInt(dividir[3]);
        double avaliacao = Double.parseDouble(dividir[4]);

        String[] tipos = dividir[5].split(";");

        int faixa = dividir[6].length();

        String[] h = dividir[7].split("-");
        Hora abertura = Hora.parseHora(h[0]);
        Hora fechamento = Hora.parseHora(h[1]);

        Data data = Data.parseData(dividir[8]);

        boolean aberto = Boolean.parseBoolean(dividir[9]);

        return new Restaurante(id, nome, cidade, capacidade,
                avaliacao, tipos, faixa, abertura, fechamento, data, aberto);
    }

public String formatar() {
    // tipos de cozinha
    String tiposStr = "";
    for (int i = 0; i < tipos.length; i++) {
        tiposStr += tipos[i];
        if (i < tipos.length - 1) {
            tiposStr += ",";
        }
    }

    // faixa de preco
    String preco = "";
    for (int i = 0; i < faixaPreco; i++) {
        preco += "$";
    }

    // montagem final
    String resp = "[";
    resp += id + " ## ";
    resp += nome + " ## ";
    resp += cidade + " ## ";
    resp += capacidade + " ## ";
    resp += avaliacao + " ## ";
    resp += "[" + tiposStr + "] ## ";
    resp += preco + " ## ";
    resp += abertura.formatar() + "-" + fechamento.formatar() + " ## ";
    resp += data.formatar() + " ## ";
    resp += aberto;
    resp += "]";

    return resp;
}

    public int getId() {
        return id;
    }
}

class ColecaoRestaurantes {
    private Restaurante[] restaurantes;
    private int tamanho;

    public Restaurante[] getRestaurantes() {
        return restaurantes;
    }
    public int getTamanho(){
        return tamanho;
    }

    public void lerCsv(String path) {
        restaurantes = new Restaurante[1000];
        tamanho =0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String linha;

            br.readLine(); 

            while ((linha = br.readLine()) != null) {
                restaurantes[tamanho++] = Restaurante.parseRestaurante(linha);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes c = new ColecaoRestaurantes();
        c.lerCsv("/tmp/restaurantes.csv");
        return c;
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ColecaoRestaurantes c = ColecaoRestaurantes.lerCsv();
        Restaurante[] lista = c.getRestaurantes();

        while (true) {
            int id = sc.nextInt();
            if (id == -1) break;

            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getId() == id) {
                    System.out.println(lista[i].formatar());
                    break;
                }
            }
        }

        sc.close();
    }
}
