/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class SelectByExampleWithoutBLOBsMethodGenerator extends
        AbstractJavaMapperMethodGenerator {

    public SelectByExampleWithoutBLOBsMethodGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getExampleType());
        importedTypes.add(type);
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType;
        if (introspectedTable.getRules().generateBaseRecordClass()) {
            listType = new FullyQualifiedJavaType(introspectedTable
                    .getBaseRecordType());
        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            listType = new FullyQualifiedJavaType(introspectedTable
                    .getPrimaryKeyType());
        } else {
            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
        }

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);

        method.setName(introspectedTable.getSelectByExampleStatementId());
        method.addParameter(new Parameter(type, "example")); //$NON-NLS-1$

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        addMapperAnnotations(interfaze, method);
        
        if (context.getPlugins()
                .clientSelectByExampleWithoutBLOBsMethodGenerated(method,
                        interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
            
            //test
            try
            {
                //构造导入语句
                FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("com.txframework.core.jdbc.PageRequest");
                importedTypes.add(fqjt);
                
                //构造分页方法
                Method extMethod = deepCopyMethod(method);
                FullyQualifiedJavaType pageRequestType = new FullyQualifiedJavaType("com.txframework.core.jdbc.PageRequest");
                extMethod.addParameter(new Parameter(pageRequestType, "pageRequest")); //增加分页参数
                
                //增加到interfaze(就是即将要解析对象)
                interfaze.addImportedTypes(importedTypes);
                interfaze.addMethod(extMethod);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private Method deepCopyMethod(Method method) throws IOException, ClassNotFoundException
    {
        Method out = new Method();
        out.setVisibility(JavaVisibility.PUBLIC);
        out.setReturnType(method.getReturnType());
        out.setName(method.getName());
        for(Parameter parameter : method.getParameters())
        {
            out.addParameter(parameter); //$NON-NLS-1$    
        }
        
        return out;  
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
        return;
    }
}
