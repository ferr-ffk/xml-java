import model.Pessoa;
import util.XMLUtil;

public class Main {
    private static final Pessoa pessoa = new Pessoa("Steffeson", "Sousa", "steffe@gmail.com", "Avenida do Cursino", "1294");

    public static void main(String[] args) {
        XMLUtil.salvarObjetoEmArquivo(pessoa);

        Object novaPessoa = XMLUtil.obterObjetoEmArquivo("tmp.xml", Pessoa.class);

        System.out.println(novaPessoa);
    }
}