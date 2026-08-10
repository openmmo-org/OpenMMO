package de.fiereu.openmmo.launcher.xml

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document
import org.w3c.dom.Element

/**
 * XML with doctypes and external entities off, since every document here comes from the network.
 */
object SecureXml {

  fun parse(bytes: ByteArray): Document =
      documentBuilderFactory().newDocumentBuilder().parse(ByteArrayInputStream(bytes))

  fun root(bytes: ByteArray, name: String): Element {
    val document = parse(bytes)
    return document.getElementsByTagName(name).item(0) as? Element ?: error("not a $name document")
  }

  fun write(document: Document): ByteArray {
    val transformer =
        TransformerFactory.newInstance()
            .apply {
              setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
              setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "")
              setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "")
            }
            .newTransformer()
            .apply {
              setOutputProperty(OutputKeys.ENCODING, "UTF-8")
              setOutputProperty(OutputKeys.STANDALONE, "no")
            }
    val out = ByteArrayOutputStream()
    transformer.transform(DOMSource(document), StreamResult(out))
    return out.toByteArray()
  }

  fun elements(parent: Element, tag: String): List<Element> {
    val nodes = parent.getElementsByTagName(tag)
    return buildList {
      for (index in 0 until nodes.length) {
        add(nodes.item(index) as? Element ?: continue)
      }
    }
  }

  private fun documentBuilderFactory(): DocumentBuilderFactory =
      DocumentBuilderFactory.newInstance().apply {
        setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
        setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        setFeature("http://xml.org/sax/features/external-general-entities", false)
        setFeature("http://xml.org/sax/features/external-parameter-entities", false)
        setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        isXIncludeAware = false
        isExpandEntityReferences = false
      }
}
