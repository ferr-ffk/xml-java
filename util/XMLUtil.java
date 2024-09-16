package util;

import java.io.IOException;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtil<T> {
    private static String caminhoArquivo = "tmp.xml";

    /**
     * Salva um objeto o em um arquivo XML
     * 
     * @param o O objeto para ser salvo
     */
    public void salvarObjetoEmArquivo(Object o) {
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
                valorCampo = "";
            }

            Element elemento = doc.createElement(nomeCampo);
            elemento.setTextContent(valorCampo.toString());

            elementoRaiz.appendChild(elemento);
        });

        salvarXML(doc, caminhoArquivo);
    }

    /**
     * Salva um objeto o em um arquivo XML de caminho fornecido
     * 
     * @param o O Objeto para ser salvo
     * @param caminhoArquivo O caminho do arquivo para ser salvo
     */
    public static void salvarObjetoEmArquivo(Object o, String caminhoArquivo) {
        XMLUtil.caminhoArquivo = caminhoArquivo;

        salvarObjetoEmArquivo(o);
    }

    public T obterObjetoEmArquivo(String caminhoArquivo, Class<T> classeObjeto) {
        T obj = null;

        try {
            obj = classeObjeto.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(caminhoArquivo);

            document.getDocumentElement().normalize();

            Element elementoRaiz = document.getDocumentElement();

            NodeList elementosFilhoRaiz = elementoRaiz.getChildNodes();

            for (int i = 0; i < elementosFilhoRaiz.getLength(); i++) {
                Node iesimoElemento = elementosFilhoRaiz.item(i);

                String nomeIesimoElemento = iesimoElemento.getNodeName();
                String valorIesimoElemento = iesimoElemento.getTextContent();

                if (nomeIesimoElemento == "#text") {
                    continue;
                }

                // System.out.println(nomeIesimoElemento + ": " + valorIesimoElemento);

                Field f = classeObjeto.getDeclaredField(nomeIesimoElemento);
                f.setAccessible(true);

                if (valorIesimoElemento.toLowerCase().equals("")) {
                    valorIesimoElemento = null;
                }

                f.set(obj, valorIesimoElemento);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
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
