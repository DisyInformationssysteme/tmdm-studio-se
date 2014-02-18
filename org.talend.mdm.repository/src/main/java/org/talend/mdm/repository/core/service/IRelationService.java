// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.core.service;

import java.util.List;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.talend.core.IService;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;

/**
 * created by HHB on 2014-2-17 Detailled comment
 * 
 */
public interface IRelationService extends IService {

    void buildRelationMap();

    ISchedulingRule getScheduleRule();

    void updateRelationMap(Item item);

    List<IRepositoryViewObject> getPositiveRelations(IRepositoryViewObject viewObj);

    List<IRepositoryViewObject> getInverseRelations(IRepositoryViewObject viewObj);
}
