package controle;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.ItemMidia;

@ManagedBean
@SessionScoped
public class CatalogoBean implements Serializable {

    private ItemMidia item = new ItemMidia();
    private List<ItemMidia> lista = new ArrayList<>();
    private List<ItemMidia> listaFiltrada;
    private String termoBusca = "";
    private int proximoId = 1;
    private String NOME_ARQUIVO = "C:/Users/meu/Documents/NetBeansProjects/CatalogoWebSimples5/catalogo.txt";

    @PostConstruct
    public void init() {
        carregarDoArquivo();
    }

    public void salvar() {
        if (item.getId() == 0) {
            item.setId(proximoId++);
            lista.add(item);
        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == item.getId()) {
                    lista.set(i, item);
                    break;
                }
            }
        }
        salvarEmArquivo();
        item = new ItemMidia();
        termoBusca = "";
        listaFiltrada = null;
    }

    public String prepararEdicao(ItemMidia i) {
        this.item = i;
        return "formulario";
    }

    public void excluir(ItemMidia i) {
        lista.remove(i);
        salvarEmArquivo();
        if (listaFiltrada != null) {
            listaFiltrada.remove(i);
        }
    }

    public void buscar() {
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            listaFiltrada = null;
        } else {
            listaFiltrada = new ArrayList<>();
            String busca = termoBusca.toLowerCase();
            for (ItemMidia i : lista) {
                String titulo = i.getTitulo() != null ? i.getTitulo().toLowerCase() : "";
                String autor = i.getAutor() != null ? i.getAutor().toLowerCase() : "";
                String genero = i.getGenero() != null ? i.getGenero().toLowerCase() : "";
                if (titulo.contains(busca) || autor.contains(busca) || genero.contains(busca)) {
                    listaFiltrada.add(i);
                }
            }
        }
    }

    public void limparBusca() {
        termoBusca = "";
        listaFiltrada = null;
    }

    private void salvarEmArquivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO))) {
            for (ItemMidia i : lista) {
                String titulo = i.getTitulo() != null ? i.getTitulo() : "";
                String tipo = i.getTipo() != null ? i.getTipo() : "";
                String genero = i.getGenero() != null ? i.getGenero() : "";
                String autor = i.getAutor() != null ? i.getAutor() : "";
                String ano = i.getAno() != null ? i.getAno().toString() : "";
                String sinopse = i.getSinopse() != null ? i.getSinopse().replace("\n", " ") : "";
                writer.println(i.getId() + ";" + titulo + ";" + tipo + ";" + 
                               genero + ";" + autor + ";" + ano + ";" + sinopse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarDoArquivo() {
        File arquivo = new File(NOME_ARQUIVO);
        if (arquivo.exists()) {
            try (Scanner scanner = new Scanner(arquivo)) {
                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    if (linha.trim().isEmpty()) continue;
                    String[] dados = linha.split(";", -1);
                    if (dados.length >= 7) {
                        ItemMidia i = new ItemMidia();
                        i.setId(Integer.parseInt(dados[0]));
                        i.setTitulo(dados[1]);
                        i.setTipo(dados[2]);
                        i.setGenero(dados[3]);
                        i.setAutor(dados[4]);
                        i.setAno(dados[5].isEmpty() ? null : Integer.parseInt(dados[5]));
                        i.setSinopse(dados[6]);
                        lista.add(i);
                        if (i.getId() >= proximoId) {
                            proximoId = i.getId() + 1;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ItemMidia getItem() { return item; }
    public void setItem(ItemMidia item) { this.item = item; }
    public List<ItemMidia> getLista() { return listaFiltrada != null ? listaFiltrada : lista; }
    public String getTermoBusca() { return termoBusca; }
    public void setTermoBusca(String termoBusca) { this.termoBusca = termoBusca; }
}