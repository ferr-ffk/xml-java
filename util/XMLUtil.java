package util;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLUtil {
    private static String caminhoArquivo = "tmp.xml";

    public static void salvarObjetoEmArquivo(Object o) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = docBuilder.newDocument();

        String nomeElementoRaiz = o.getClass().getSimpleName().toLowerCase();

        Element elementoRaiz = doc.createElement(nomeElementoRaiz);

        doc.appendChild(elementoRaiz);

        HashMap<String, Object> camposValoresObjeto = obterListaDeCampos(o);

        // Para cada par de nome do campo e valor do campo, cria uma tag daquele nome
        // com o valor daquele campo e adiciona no xml
        camposValoresObjeto.forEach((nomeCampo, valorCampo) -> {
            if (valorCampo == null) {
                valorCampo = "NULL";
            }

            Element elemento = doc.createElement(nomeCampo);
            elemento.setTextContent(valorCampo.toString());

            elementoRaiz.appendChild(elemento);
        });

        salvarXML(doc, caminhoArquivo);
    }

    private static HashMap<String, Object> obterListaDeCampos(Object o) {
        HashMap<String, Object> m = new HashMap<>();

        for (Field f : o.getClass().getDeclaredFields()) {
            // Torna o valor do campo acessível para uso
            f.setAccessible(true);

            String nomeCampo = f.getName();
            Object valorCampo = null;

            try {
                valorCampo = f.get(o);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            m.put(nomeCampo, valorCampo);
        }

        return m;
    }

    /**
     * @param doc
     * @param output
     * @param indentar
     * @throws TransformerException
     * @author Luiz Quirino
     */
    private static void salvarXML(Document doc, String caminhoArquivo) {
        try {
            // Cria um Transformer para converter o documento para XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;

            transformer = transformerFactory.newTransformer();

            // Indenta as tags no estilo do html
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(caminhoArquivo);

            // Escreve o documento XML para o arquivo
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return String return the caminhoArquivo
     */
    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    /**
     * @param caminhoArquivo the caminhoArquivo to set
     */
    public void setCaminhoArquivo(String caminhoArquivo) {
        XMLUtil.caminhoArquivo = caminhoArquivo;
    }

}
