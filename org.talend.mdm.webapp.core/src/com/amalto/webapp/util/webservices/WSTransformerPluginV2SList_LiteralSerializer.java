// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class WSTransformerPluginV2SList_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_Item_QNAME = new QName("", "Item");
    private static final QName ns2_WSTransformerPluginV2sList$2d$Item_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSTransformerPluginV2sList-Item");
    private CombinedSerializer ns2_myWSTransformerPluginV2SListItem_LiteralSerializer;
    
    public WSTransformerPluginV2SList_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSTransformerPluginV2SList_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSTransformerPluginV2SListItem_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.webapp.util.webservices.WSTransformerPluginV2SListItem.class, ns2_WSTransformerPluginV2sList$2d$Item_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSTransformerPluginV2SList instance = new com.amalto.webapp.util.webservices.WSTransformerPluginV2SList();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_Item_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_Item_QNAME))) {
                    value = ns2_myWSTransformerPluginV2SListItem_LiteralSerializer.deserialize(ns1_Item_QNAME, reader, context);
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull");
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.amalto.webapp.util.webservices.WSTransformerPluginV2SListItem[values.size()];
            member = values.toArray((Object[]) member);
            instance.setItem((com.amalto.webapp.util.webservices.WSTransformerPluginV2SListItem[])member);
        }
        else {
            instance.setItem(new com.amalto.webapp.util.webservices.WSTransformerPluginV2SListItem[0]);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSTransformerPluginV2SList instance = (com.amalto.webapp.util.webservices.WSTransformerPluginV2SList)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSTransformerPluginV2SList instance = (com.amalto.webapp.util.webservices.WSTransformerPluginV2SList)obj;
        
        if (instance.getItem() != null) {
            for (int i = 0; i < instance.getItem().length; ++i) {
                ns2_myWSTransformerPluginV2SListItem_LiteralSerializer.serialize(instance.getItem()[i], ns1_Item_QNAME, null, writer, context);
            }
        }
    }
}
