// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.encoding.SerializationException;
import com.sun.xml.rpc.encoding.literal.LiteralObjectSerializerBase;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;

public class WSRoleSpecification_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_objectType_QNAME = new QName("", "objectType");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final QName ns1_admin_QNAME = new QName("", "admin");
    private static final QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final QName ns1_instance_QNAME = new QName("", "instance");
    private static final QName ns2_WSRole$2d$specification$2d$instance_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSRole-specification-instance");
    private CombinedSerializer ns2_myWSRoleSpecificationInstance_LiteralSerializer;
    
    public WSRoleSpecification_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSRoleSpecification_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer("", boolean.class, ns3_boolean_TYPE_QNAME);
        ns2_myWSRoleSpecificationInstance_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSRoleSpecificationInstance.class, ns2_WSRole$2d$specification$2d$instance_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSRoleSpecification instance = new com.amalto.workbench.webservices.WSRoleSpecification();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_objectType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_objectType_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setObjectType((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_objectType_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_admin_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_admin_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setAdmin(((Boolean)member).booleanValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_admin_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_instance_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_instance_QNAME))) {
                    value = ns2_myWSRoleSpecificationInstance_LiteralSerializer.deserialize(ns1_instance_QNAME, reader, context);
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull");
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.amalto.workbench.webservices.WSRoleSpecificationInstance[values.size()];
            member = values.toArray((Object[]) member);
            instance.setInstance((com.amalto.workbench.webservices.WSRoleSpecificationInstance[])member);
        }
        else {
            instance.setInstance(new com.amalto.workbench.webservices.WSRoleSpecificationInstance[0]);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSRoleSpecification instance = (com.amalto.workbench.webservices.WSRoleSpecification)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSRoleSpecification instance = (com.amalto.workbench.webservices.WSRoleSpecification)obj;
        
        if (instance.getObjectType() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getObjectType(), ns1_objectType_QNAME, null, writer, context);
        if (new Boolean(instance.isAdmin()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isAdmin()), ns1_admin_QNAME, null, writer, context);
        if (instance.getInstance() != null) {
            for (int i = 0; i < instance.getInstance().length; ++i) {
                ns2_myWSRoleSpecificationInstance_LiteralSerializer.serialize(instance.getInstance()[i], ns1_instance_QNAME, null, writer, context);
            }
        }
    }
}
