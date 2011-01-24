package com.amalto.workbench.providers;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDAttributeGroupDefinition;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDAttributeUseCategory;
import org.eclipse.xsd.XSDComplexTypeContent;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFacet;
import org.eclipse.xsd.XSDIdentityConstraintCategory;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.XSDVariety;
import org.eclipse.xsd.XSDWildcard;
import org.eclipse.xsd.XSDXPathDefinition;
import org.eclipse.xsd.XSDXPathVariety;
import org.eclipse.xsd.util.XSDConstants;
import org.w3c.dom.Element;

import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.utils.Util;


public class XSDTreeLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		
//		System.out.println("getText   "+obj.getClass().getName());
		
		if (obj instanceof XSDElementDeclaration) {
			String name = ((XSDElementDeclaration)obj).getName();
			if (((XSDElementDeclaration)obj).isAbstract())
				name+= "   (abstract)";
			String tail = ((XSDElementDeclaration) obj).getTargetNamespace() != null ? " : " + ((XSDElementDeclaration) obj)
					.getTargetNamespace()
					: "";
			return name + tail;
		}
		
		if (obj instanceof XSDParticle) {
			XSDParticle xsdParticle = (XSDParticle)obj;
			XSDParticleContent content = xsdParticle.getContent();
			XSDTerm xsdTerm = xsdParticle.getTerm();
			String name = "";
			if (content instanceof XSDElementDeclaration) {
				XSDElementDeclaration decl = (XSDElementDeclaration) content;
				name+= (decl.getName()==null ? "" : decl.getName());
				if (decl.getTypeDefinition() == null) {
					name+=" ["+((XSDElementDeclaration)xsdTerm).getName()+"]";
				}
			} else if (content instanceof XSDModelGroup) {
				//System.out.println("SHOULD NOT HAPPEN????");
				if (xsdParticle.getContainer() instanceof XSDComplexTypeDefinition) {
					String ctdName = ((XSDComplexTypeDefinition)xsdParticle.getContainer()).getName();
					name = (ctdName != null ? ctdName : "");
				}

			} else {
				name = "[Any]";
			}
			if (! ((xsdParticle.getMinOccurs() == 1) && (xsdParticle.getMaxOccurs() == 1))) {			
				name+="  [";
				name+=xsdParticle.getMinOccurs();
				name+="...";
				name+= (xsdParticle.getMaxOccurs() == -1) ? "many" : ""+xsdParticle.getMaxOccurs();
				name+="]";
			}
			return name;
		} 
		
		if (obj instanceof XSDSimpleTypeDefinition) {
			return getSimpleTypeDefinition((XSDSimpleTypeDefinition)obj);
		}
		
		
		if (obj instanceof XSDModelGroup) {
			//return the name of the complex type definition
			XSDParticle particle = (XSDParticle)(((XSDModelGroup)obj).getContainer());
			String name = ((XSDComplexTypeDefinition)particle.getContainer()).getName();
			if (name==null) name = "anonymous type ";
			//return the occurence
			if (! ((particle.getMinOccurs() == 1) && (particle.getMaxOccurs() == 1))) {			
				name+="  [";
				name+=particle.getMinOccurs();
				name+="...";
				name+= (particle.getMaxOccurs() == -1) ? "many" : ""+particle.getMaxOccurs();
				name+="]";
			}
			String tail = particle.getSchema().getTargetNamespace() == null ? ""  :  " : " + particle.getSchema().getTargetNamespace();
			return name + tail;

		}

		if (obj instanceof XSDFacet) {
			return 	((XSDFacet)obj).getFacetName()+
							": "+((XSDFacet)obj).getLexicalValue();
		}

		if (obj instanceof XSDIdentityConstraintDefinition) {
			return ((XSDIdentityConstraintDefinition)obj).getName();
		}

		if (obj instanceof XSDXPathDefinition) {
			XSDXPathDefinition xpath = (XSDXPathDefinition) obj;
			return xpath.getValue();
		}
		
		if (obj instanceof XSDAttributeGroupDefinition) {
			XSDAttributeGroupDefinition attributeGroupDefinition = (XSDAttributeGroupDefinition) obj;
			String name = (attributeGroupDefinition.getName() == null ? "" : attributeGroupDefinition.getName()); 
			if (attributeGroupDefinition.getContents().size()==0) //a ref
				name+= " ["+attributeGroupDefinition.getResolvedAttributeGroupDefinition().getName()+"]";
			return name;
		}
		
		if (obj instanceof XSDAttributeUse) {
			XSDAttributeUse attributeUse = (XSDAttributeUse) obj;
			String name = attributeUse.getAttributeDeclaration().getName();
			if (name == null) 
				name = " ["+attributeUse.getAttributeDeclaration().getResolvedAttributeDeclaration().getName()+"]";
			return name;
		}
		
		if (obj instanceof XSDAnnotation) {
			//XSDAnnotation annotation = (XSDAnnotation) obj;
			return "Annotations";
		}
		
		if (obj instanceof Element) {
			try {
				Element e = (Element) obj;
				if (e.getLocalName().equals("documentation")) {
					return "Documentation: "+e.getChildNodes().item(0).getNodeValue();
				} else if (e.getLocalName().equals("appinfo")) {
					String source = e.getAttribute("source");
					if (source!=null) {
						if (source.startsWith("X_Label_")) {
							return Util.iso2lang.get(source.substring(8).toLowerCase())+" Label: "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.equals("X_ForeignKey")) {
							return "Foreign Key:  "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.equals("X_Visible_Rule")) {
							return "Visible Rule:  "+e.getChildNodes().item(0).getNodeValue();
						}else if (source.equals("X_Default_Value_Rule")) {
							return "Default Value Rule:  "+e.getChildNodes().item(0).getNodeValue();
						}else if (source.equals("X_ForeignKeyInfo")) {
							return "Foreign Key Info:  "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.equals("X_PrimaryKeyInfo")) {
                            return "Primary Key Info:  "+e.getChildNodes().item(0).getNodeValue();
                        } else if (source.equals("X_SourceSystem")) {
							return "Source System:  "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.equals("X_TargetSystem")) {
							return "Target System(s):  "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.startsWith("X_Description_")) {
							return Util.iso2lang.get(source.substring(14).toLowerCase())+" Description: "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.equals("X_Write")) {
							return "Writable By : "+e.getChildNodes().item(0).getNodeValue();
						}else if (source.equals("X_Lookup_Field")) {
							return "Look Field : "+e.getChildNodes().item(0).getNodeValue();
						} else if (source.equals("X_Workflow")) {
							return "Workflow access : "+e.getChildNodes().item(0).getNodeValue();							
						} else if (source.equals("X_Hide")) {
							return "No Access to : "+e.getChildNodes().item(0).getNodeValue();
							//add by ymli; bugId 0009157
						} else if(source.startsWith("X_Facet")){
							return source.substring(2, 7)+"_Msg_"+source.substring(8)+": "+ e.getChildNodes().item(0).getNodeValue();
						   //made schematron show:Schematron: schematron
						} 
						else if(source.startsWith("X_Display_Format_")){
							return source+": "+e.getChildNodes().item(0).getNodeValue();
						}
						else if(source.equals("X_Schematron")){
							
							 String pattern=(String)e.getFirstChild().getUserData("pattern_name");
							 if(pattern==null){
								 Element el=Util.parse(e.getChildNodes().item(0).getNodeValue()).getDocumentElement();	
								 if(el.getAttributes().getNamedItem("name")!=null)pattern=el.getAttributes().getNamedItem("name").getTextContent();
							 }
							return "Validation Rule: " + (pattern==null?"":pattern);//e.getChildNodes().item(0).getNodeValue();	
							//end
						} else if(source.equals("X_Retrieve_FKinfos")) {
						   return "Foreign Key resolution:  "+e.getChildNodes().item(0).getNodeValue();
						}if(source.equals("X_ForeignKey_Filter")) {
						   return "Foreign Key Filter:  "+e.getChildNodes().item(0).getNodeValue();
						} else {
							return source+": "+Util.nodeToString((Element)obj);
						}
					} else {
						return Util.nodeToString((Element)obj);
					}
				} else {
					return Util.nodeToString((Element)obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if (obj==null) return "NULL";
		return "?? "+obj.getClass().getName()+" : "+obj.toString();

	}
	
	
	
	
	
	
	
	
	
	
	public Image getImage(Object obj) {

		if (obj instanceof XSDElementDeclaration) {
			//top declaration
			XSDElementDeclaration decl = (XSDElementDeclaration)obj;
			//check if concept or "just" element
			boolean isConcept=false;
			EList l = decl.getIdentityConstraintDefinitions();
			for (Iterator iter = l.iterator(); iter.hasNext(); ) {
				XSDIdentityConstraintDefinition icd = (XSDIdentityConstraintDefinition) iter.next();
				if (icd.getIdentityConstraintCategory().equals(XSDIdentityConstraintCategory.UNIQUE_LITERAL)) {
					isConcept=true;
					break;
				}
			}
			//display approprite image
			if (isConcept) {
				return ImageCache.getCreatedImage( EImage.CONCEPT.getPath());
			} else {
				return ImageCache.getCreatedImage( EImage.ELEMENT_ONLY.getPath());
				/*
				if (decl.getTypeDefinition() instanceof XSDComplexTypeDefinition)
					return PlatformUI.getWorkbench().getSharedImages().getCreatedImage(ISharedImages.IMG_OBJ_FOLDER);
				else
					return ImageCache.getCreatedImage( "icons/elements_obj_+.gif");
				*/
			}
		}
		
		if (obj instanceof XSDParticle) {
			XSDParticle xsdParticle = (XSDParticle)obj;

			XSDTerm xsdTerm = xsdParticle.getTerm();
			if (xsdTerm instanceof XSDElementDeclaration) {
				//get Type of Parent Group
				if (Util.getKeyInfo(xsdTerm) != null&&Util.getKeyInfo(xsdTerm).size()>0)
					return ImageCache.getCreatedImage( EImage.PRIMARYKEY.getPath());
				XSDConcreteComponent xsdConcreteComponent =  xsdParticle.getContainer();
				if (xsdConcreteComponent instanceof XSDModelGroup) {
//					if (((XSDModelGroup)xsdConcreteComponent).getCompositor() == XSDCompositor.CHOICE_LITERAL)
//						return ImageCache.getCreatedImage( EImage.ELEMENTS_OBJ_CHOICE.getPath());
//					else if (((XSDModelGroup)xsdConcreteComponent).getCompositor() == XSDCompositor.SEQUENCE_LITERAL)
//						return ImageCache.getCreatedImage( EImage.ELEMENTS_OBJ_SEQUENCE.getPath());
					return ImageCache.getCreatedImage( EImage.SCHEMAELEMENT.getPath());
				}
/*				if(((XSDElementDeclaration) xsdTerm).getAnonymousTypeDefinition() instanceof XSDComplexTypeDefinition)
					return ImageCache.getCreatedImage( EImage.COMPLEXTYPE.getPath());
				else 
					return ImageCache.getCreatedImage( EImage.SIMPLETYPE.getPath());*/
				
			} else if (xsdTerm instanceof XSDModelGroup) {
				int type = ((XSDModelGroup)xsdTerm).getCompositor().getValue();
				switch (type) {
					case XSDCompositor.ALL:
						return ImageCache.getCreatedImage( EImage.COMPLEX_ALL.getPath());
					case XSDCompositor.CHOICE:
						return ImageCache.getCreatedImage( EImage.COMPLEX_CHOICE.getPath());
					case XSDCompositor.SEQUENCE:
						return ImageCache.getCreatedImage( EImage.COMPLEX_SEQUENCE.getPath());				
				}
			} else if (xsdTerm instanceof XSDWildcard) {
				return ImageCache.getCreatedImage( "icons/wildcard.gif");
			} else {
				System.out.println("ERROR XSD Term "+xsdTerm.getClass().getName());
				return ImageCache.getCreatedImage( "icons/error.gif");
			}	
		}
		
		if (obj instanceof XSDSimpleTypeDefinition) {
			return ImageCache.getCreatedImage( EImage.SIMPLETYPE.getPath());
		}

		if (obj instanceof XSDComplexTypeDefinition) {
			XSDComplexTypeDefinition ctd = (XSDComplexTypeDefinition) obj;
			XSDComplexTypeContent ctc = ctd.getContent();
			if (ctc instanceof XSDParticle) {
				if (((XSDParticle)ctc).getTerm() instanceof XSDModelGroup) {
					int type = ((XSDModelGroup)((XSDParticle)ctc).getTerm()).getCompositor().getValue();
					switch (type) {
						case XSDCompositor.ALL:
							return ImageCache.getCreatedImage( EImage.COMPLEX_ALL.getPath());
						case XSDCompositor.CHOICE:
							return ImageCache.getCreatedImage( EImage.COMPLEX_CHOICE.getPath());
						case XSDCompositor.SEQUENCE:
							return ImageCache.getCreatedImage( EImage.COMPLEX_SEQUENCE.getPath());				
					}
				}
			} else {
				//simple Type!!!
				System.out.println("ERROR XSD Type Content: "+ctc.getClass().getName());
				return ImageCache.getCreatedImage( "icons/error.gif");
			}
		}

		if (obj instanceof XSDModelGroup) {
			int type = ((XSDModelGroup)obj).getCompositor().getValue();
			switch (type) {
				case XSDCompositor.ALL:
					return ImageCache.getCreatedImage( EImage.COMPLEX_ALL.getPath());
				case XSDCompositor.CHOICE:
					return ImageCache.getCreatedImage( EImage.COMPLEX_CHOICE.getPath());
				case XSDCompositor.SEQUENCE:
					return ImageCache.getCreatedImage( EImage.COMPLEX_SEQUENCE.getPath());
			}
		}

		
		
		if (obj instanceof XSDFacet) {
			return ImageCache.getCreatedImage( EImage.FACET.getPath());
		}
		
		if (obj instanceof XSDIdentityConstraintDefinition) {
			XSDIdentityConstraintDefinition identity = (XSDIdentityConstraintDefinition) obj;
			if (identity.getIdentityConstraintCategory().equals(XSDIdentityConstraintCategory.UNIQUE_LITERAL))
				return ImageCache.getCreatedImage( EImage.KEYS.getPath());
			return ImageCache.getCreatedImage( EImage.PRIMARYKEY.getPath());
		}

		if (obj instanceof XSDXPathDefinition) {
			XSDXPathDefinition xpath = (XSDXPathDefinition) obj;
			if (xpath.getVariety().equals(XSDXPathVariety.FIELD_LITERAL))
				return ImageCache.getCreatedImage( "icons/field.gif");
			return ImageCache.getCreatedImage( "icons/selector.gif");
		}
		
		if (obj instanceof XSDAttributeGroupDefinition) {
			return ImageCache.getCreatedImage( "icons/attribute_group.gif");
		}

		if (obj instanceof XSDAttributeUse) {
			XSDAttributeUse att = (XSDAttributeUse) obj;
			if ("xmlns".equals(att.getAttributeDeclaration().getTargetNamespace())) {
				return ImageCache.getCreatedImage( EImage.ANNOTATION.getPath());
			}
			if (att.getUse().equals(XSDAttributeUseCategory.REQUIRED_LITERAL))
				return ImageCache.getCreatedImage( "icons/attribute_mandatory.gif");
			else
				return ImageCache.getCreatedImage( "icons/attribute.gif");
		}

		if (obj instanceof XSDAnnotation) {
			return ImageCache.getCreatedImage( EImage.ANNOTATION.getPath());
		}

		if (obj instanceof Element) {
			try {
				Element e = (Element) obj;
				if (e.getLocalName().equals("documentation")) { 
					return ImageCache.getCreatedImage( EImage.DOCUMENTATION.getPath());
				} else  if (e.getLocalName().equals("appinfo")) {
					String source = e.getAttribute("source");
					if (source!=null) {
						if (source.startsWith("X_Label_")) {
							return ImageCache.getCreatedImage( EImage.LABEL.getPath());
						} else if (source.equals("X_ForeignKey")) {
							return ImageCache.getCreatedImage( EImage.PRIMARYKEY.getPath());
						}else if (source.equals("X_Visible_Rule")) {
							return ImageCache.getCreatedImage( EImage.ROUTINE.getPath());
						}else if (source.equals("X_Default_Value_Rule")) {
							return ImageCache.getCreatedImage( EImage.ROUTINE.getPath());
						} else if (source.equals("X_ForeignKeyInfo")) {
							return ImageCache.getCreatedImage( EImage.KEYINFO.getPath());
							//fix bug 0013194 by rhou.
						} else if (source.equals("X_Retrieve_FKinfos")) {
							return ImageCache.getCreatedImage( EImage.KEYINFO.getPath());
						} else if (source.equals("X_SourceSystem")) {
							return ImageCache.getCreatedImage( EImage.SOURCESYSTEM.getPath());
						} else if (source.equals("X_TargetSystem")) {
							return ImageCache.getCreatedImage( EImage.TARGETSYSTEM.getPath());
						} else if (source.startsWith("X_Description_")) {
							return ImageCache.getCreatedImage( EImage.DOCUMENTATION.getPath());
						} else if (source.equals("X_Write")) {
							return ImageCache.getCreatedImage( EImage.SECURITYANNOTATION.getPath());
						}else if (source.equals("X_Lookup_Field")) {
							return ImageCache.getCreatedImage( EImage.BROWSE.getPath());
						} else if (source.equals("X_Hide")) {
							return ImageCache.getCreatedImage( EImage.SECURITYANNOTATION.getPath());
						} else if (source.equals("X_Schematron")) {
							return ImageCache.getCreatedImage( EImage.ROUTINE.getPath());
						}else if (source.equals("X_Workflow")) {
							return ImageCache.getCreatedImage( EImage.WORKFLOW_PROCESS.getPath());
						}if(source.equals("X_ForeignKey_Filter")) {
						   return ImageCache.getCreatedImage( EImage.FILTER_PS.getPath());
						}
						else if(source.startsWith("X_Display_Format_")){
							return ImageCache.getCreatedImage( EImage.THIN_MIN_VIEW.getPath());
						}
						else {
							return ImageCache.getCreatedImage( EImage.DOCUMENTATION.getPath());
						}
					} else {
						return ImageCache.getCreatedImage( EImage.DOCUMENTATION.getPath());
					}
				} else {
					return ImageCache.getCreatedImage( EImage.DOCUMENTATION.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		return ImageCache.getCreatedImage( "icons/small_warn.gif");
		//return PlatformUI.getWorkbench().getSharedImages().getCreatedImage(ISharedImages.IMG_OBJ_ELEMENT);		
	}
	
	
	
	/**
	 * Print a simple type definition for the document.
	 * 
	 * @param xsdSimpleTypeDefinition
	 *            a simple type definition in the schema for schema.
	 */
	public String getSimpleTypeDefinition(XSDSimpleTypeDefinition xsdSimpleTypeDefinition) {
		String s = "";
		if (xsdSimpleTypeDefinition == null) {
		} else if (xsdSimpleTypeDefinition.getEffectiveEnumerationFacet() != null) {
			/*	List value = xsdSimpleTypeDefinition.getEffectiveEnumerationFacet().getValue();
			if (value.size() > 1) {
				s+= "(";
			}
			for (Iterator enumerators = value.iterator(); enumerators.hasNext();) {
				String enumerator = enumerators.next().toString();
				s+= enumerator;
				if (enumerators.hasNext()) {
					s+= " | ;";
				}
			}
			if (value.size() > 1) {
				s+= ")";
			
			}*/
			s+=xsdSimpleTypeDefinition.getName() != null ? xsdSimpleTypeDefinition.getName() : xsdSimpleTypeDefinition.getBaseTypeDefinition().getName();
		} else if (xsdSimpleTypeDefinition.getElement() != null && xsdSimpleTypeDefinition.getElement().hasAttribute(XSDConstants.ID_ATTRIBUTE)) {
			s+= xsdSimpleTypeDefinition.getName();
		} else if ((XSDVariety.UNION_LITERAL == xsdSimpleTypeDefinition.getVariety()) || (XSDVariety.LIST_LITERAL == xsdSimpleTypeDefinition.getVariety())) {
			s+= "(";
			for (Iterator members = xsdSimpleTypeDefinition.getMemberTypeDefinitions().iterator(); members.hasNext();) {
				XSDSimpleTypeDefinition memberTypeDefinition = (XSDSimpleTypeDefinition) members.next();
				s+=getSimpleTypeDefinition(memberTypeDefinition);
				if (members.hasNext()) {
					s+= " | ";
				}
			}
			s+= ")";
			if(xsdSimpleTypeDefinition.getMemberTypeDefinitions().isEmpty())
			{
				s = xsdSimpleTypeDefinition.getVariety() + "";
			}
		} else if ((XSDVariety.UNION_LITERAL == xsdSimpleTypeDefinition.getVariety()) | (XSDVariety.LIST_LITERAL == xsdSimpleTypeDefinition.getVariety())) {
			s+= "List of ";
			s+= getSimpleTypeDefinition(xsdSimpleTypeDefinition.getItemTypeDefinition());
		} else if (xsdSimpleTypeDefinition.getName() != null) {
				s+= xsdSimpleTypeDefinition.getName();
		} else if (xsdSimpleTypeDefinition.getEffectivePatternFacet() != null) {
			// s+= xsdSimpleTypeDefinition.getEffectivePatternFacet().getLexicalValue());
			s+= "a restricted xpath expression";
		} else {
			s+= "";
		}
		String tail = xsdSimpleTypeDefinition.getTargetNamespace() == null ? ""  :  xsdSimpleTypeDefinition.getTargetNamespace();
		if(tail.equals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001) || tail.equals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_1999))
			tail = "";
		else if(!tail.equals(""))
			tail = " : " + tail;
		return s + tail;
	}
	
}
