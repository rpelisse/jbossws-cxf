/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.ws.jaxws.cxf.descriptor;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import junit.framework.Test;

import org.jboss.wsf.test.JBossWSTest;
import org.jboss.wsf.test.JBossWSTestSetup;

/**
 * Test a CXF endpoint with provided jbossws-cxf.xml 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 12-Dec-2007
 */
public class DescriptorJSETestCase extends JBossWSTest
{
   private String endpointURL = "http://" + getServerHost() + ":8080/jaxws-cxf-descriptor/TestService";
   private String targetNS = "http://org.jboss.ws.jaxws.cxf/descriptor";

   public static Test suite()
   {
      return new JBossWSTestSetup(DescriptorJSETestCase.class, "jaxws-cxf-descriptor.war");
   }

   public void testLegalAccess() throws Exception
   {
      URL wsdlURL = new URL(endpointURL + "?wsdl");
      QName serviceName = new QName(targetNS, "DescriptorService");

      Service service = Service.create(wsdlURL, serviceName);
      DescriptorEndpoint port = (DescriptorEndpoint)service.getPort(DescriptorEndpoint.class);

      Object retObj = port.echo("Hello");
      assertEquals("Hello", retObj);
      
      //JBPAPP-5494/JBWS-3174: test the cxf.xml is loaded
      String serverInBoundLog = port.getInBoundLog();
      assertTrue(serverInBoundLog.length() > 0);
   }
}