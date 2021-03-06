/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2006-2008 Sun Microsystems, Inc.
 * Portions Copyright 2014-2016 ForgeRock AS.
 */
package org.opends.server.extensions;

import static org.forgerock.opendj.ldap.ModificationType.*;
import static org.opends.server.protocols.internal.InternalClientConnection.*;
import static org.opends.server.util.CollectionUtils.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.forgerock.i18n.LocalizableMessageBuilder;
import org.forgerock.opendj.config.server.ConfigException;
import org.forgerock.opendj.ldap.ByteString;
import org.opends.server.TestCaseUtils;
import org.forgerock.opendj.server.config.meta.LengthBasedPasswordValidatorCfgDefn;
import org.opends.server.core.ModifyOperationBasis;
import org.opends.server.types.Attributes;
import org.opends.server.types.Control;
import org.forgerock.opendj.ldap.DN;
import org.opends.server.types.DirectoryException;
import org.opends.server.types.Entry;
import org.opends.server.types.InitializationException;
import org.opends.server.types.Modification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * A set of test cases for the length-based password validator.
 */
public class LengthBasedPasswordValidatorTestCase
       extends ExtensionsTestCase
{
  /**
   * Ensures that the Directory Server is running.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @BeforeClass
  public void startServer()
         throws Exception
  {
    TestCaseUtils.startServer();
  }



  /**
   * Retrieves a set of valid configuration entries that may be used to
   * initialize the validator.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @DataProvider(name = "validConfigs")
  public Object[][] getValidConfigs()
         throws Exception
  {
    List<Entry> entries = TestCaseUtils.makeEntries(
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 6",
         "ds-cfg-max-password-length: 0",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 6",
         "ds-cfg-max-password-length: 10",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 0",
         "ds-cfg-max-password-length: 0",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 6",
         "ds-cfg-max-password-length: 6",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 6",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 0",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-max-password-length: 10",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true");

    Object[][] array = new Object[entries.size()][1];
    for (int i=0; i < array.length; i++)
    {
      array[i] = new Object[] { entries.get(i) };
    }

    return array;
  }



  /**
   * Tests the process of initializing the server with valid configurations.
   *
   * @param  entry  The configuration entry to use for the initialization.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(dataProvider = "validConfigs")
  public void testInitializeWithValidConfigs(Entry e)
         throws Exception
  {
    initializePasswordValidator(e);
  }



  /**
   * Retrieves a set of invalid configuration entries.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @DataProvider(name = "invalidConfigs")
  public Object[][] getInvalidConfigs()
         throws Exception
  {
    List<Entry> entries = TestCaseUtils.makeEntries(
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: -1",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: notNumeric",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-max-password-length: -1",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-max-password-length: notNumeric",
         "",
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 6",
         "ds-cfg-max-password-length: 5");

    Object[][] array = new Object[entries.size()][1];
    for (int i=0; i < array.length; i++)
    {
      array[i] = new Object[] { entries.get(i) };
    }

    return array;
  }



  /**
   * Tests the process of initializing the server with invalid configurations.
   *
   * @param  entry  The configuration entry to use for the initialization.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(dataProvider = "invalidConfigs",
        expectedExceptions = { ConfigException.class,
                               InitializationException.class })
  public void testInitializeWithInvalidConfigs(Entry e)
         throws Exception
  {
    initializePasswordValidator(e);
  }



  /**
   * Tests the <CODE>passwordIsAcceptable</CODE> method with no constraints on
   * password length.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test
  public void testPasswordIsAcceptableNoConstraints()
         throws Exception
  {
    TestCaseUtils.initializeTestBackend(true);
    Entry userEntry = TestCaseUtils.makeEntry(
         "dn: uid=test.user,o=test",
         "objectClass: top",
         "objectClass: person",
         "objectClass: organizationalPerson",
         "objectClass: inetOrgPerson",
         "uid: test.user",
         "givenName: Test",
         "sn: User",
         "cn: Test User",
         "userPassword: password");

    Entry validatorEntry = TestCaseUtils.makeEntry(
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 0",
         "ds-cfg-max-password-length: 0");

    LengthBasedPasswordValidator validator = initializePasswordValidator(validatorEntry);

    StringBuilder buffer = new StringBuilder();
    for (int i=0; i < 20; i++)
    {
      buffer.append('x');
      ByteString password = ByteString.valueOfUtf8(buffer.toString());

      ModifyOperationBasis op = newModifyOperation(buffer);

      LocalizableMessageBuilder invalidReason = new LocalizableMessageBuilder();
      assertTrue(validator.passwordIsAcceptable(password,
                                                new HashSet<ByteString>(0),
                                                op, userEntry, invalidReason));
    }

    validator.finalizePasswordValidator();
  }

  private LengthBasedPasswordValidator initializePasswordValidator(Entry validatorEntry) throws Exception {
    return InitializationUtils.initializePasswordValidator(
        new LengthBasedPasswordValidator(), validatorEntry, LengthBasedPasswordValidatorCfgDefn.getInstance());
  }

  private ModifyOperationBasis newModifyOperation(StringBuilder buffer) throws DirectoryException
  {
    ArrayList<Modification> mods = newArrayList(
        new Modification(REPLACE, Attributes.create("userpassword", buffer.toString())));

    ModifyOperationBasis op = new ModifyOperationBasis(
        getRootConnection(), nextOperationID(), nextMessageID(),
        new ArrayList<Control>(), DN.valueOf("cn=uid=test.user,o=test"), mods);
    return op;
  }

  /**
   * Tests the <CODE>passwordIsAcceptable</CODE> method with a constraint on the
   * minimum password length.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test
  public void testPasswordIsAcceptableMinLengthConstraint()
         throws Exception
  {
    TestCaseUtils.initializeTestBackend(true);
    Entry userEntry = TestCaseUtils.makeEntry(
         "dn: uid=test.user,o=test",
         "objectClass: top",
         "objectClass: person",
         "objectClass: organizationalPerson",
         "objectClass: inetOrgPerson",
         "uid: test.user",
         "givenName: Test",
         "sn: User",
         "cn: Test User",
         "userPassword: password");

    Entry validatorEntry = TestCaseUtils.makeEntry(
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 10",
         "ds-cfg-max-password-length: 0");

    LengthBasedPasswordValidator validator = initializePasswordValidator(validatorEntry);

    StringBuilder buffer = new StringBuilder();
    for (int i=0; i < 20; i++)
    {
      buffer.append('x');
      ByteString password = ByteString.valueOfUtf8(buffer.toString());

      ModifyOperationBasis op = newModifyOperation(buffer);

      LocalizableMessageBuilder invalidReason = new LocalizableMessageBuilder();
      boolean actual = validator.passwordIsAcceptable(
          password, new HashSet<ByteString>(0), op, userEntry, invalidReason);
      assertEquals(buffer.length() >= 10, actual);
    }

    validator.finalizePasswordValidator();
  }



  /**
   * Tests the <CODE>passwordIsAcceptable</CODE> method with a constraint on the
   * maximum password length.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test
  public void testPasswordIsAcceptableMaxLengthConstraint()
         throws Exception
  {
    TestCaseUtils.initializeTestBackend(true);
    Entry userEntry = TestCaseUtils.makeEntry(
         "dn: uid=test.user,o=test",
         "objectClass: top",
         "objectClass: person",
         "objectClass: organizationalPerson",
         "objectClass: inetOrgPerson",
         "uid: test.user",
         "givenName: Test",
         "sn: User",
         "cn: Test User",
         "userPassword: password");

    Entry validatorEntry = TestCaseUtils.makeEntry(
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 0",
         "ds-cfg-max-password-length: 10");

    LengthBasedPasswordValidator validator = initializePasswordValidator(validatorEntry);

    StringBuilder buffer = new StringBuilder();
    for (int i=0; i < 20; i++)
    {
      buffer.append('x');
      ByteString password = ByteString.valueOfUtf8(buffer.toString());

      ModifyOperationBasis op = newModifyOperation(buffer);

      LocalizableMessageBuilder invalidReason = new LocalizableMessageBuilder();
      boolean actual = validator.passwordIsAcceptable(
          password, new HashSet<ByteString>(0), op, userEntry, invalidReason);
      assertEquals(buffer.length() <= 10, actual);
    }

    validator.finalizePasswordValidator();
  }



  /**
   * Tests the <CODE>passwordIsAcceptable</CODE> method with constraints on both
   * the minimum and maximum password length.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test
  public void testPasswordIsAcceptableMinAndMaxLengthConstraints()
         throws Exception
  {
    TestCaseUtils.initializeTestBackend(true);
    Entry userEntry = TestCaseUtils.makeEntry(
         "dn: uid=test.user,o=test",
         "objectClass: top",
         "objectClass: person",
         "objectClass: organizationalPerson",
         "objectClass: inetOrgPerson",
         "uid: test.user",
         "givenName: Test",
         "sn: User",
         "cn: Test User",
         "userPassword: password");

    Entry validatorEntry = TestCaseUtils.makeEntry(
         "dn: cn=Length-Based Password Validator,cn=Password Validators," +
              "cn=config",
         "objectClass: top",
         "objectClass: ds-cfg-password-validator",
         "objectClass: ds-cfg-length-based-password-validator",
         "cn: Length-Based Password Validator",
         "ds-cfg-java-class: org.opends.server.extensions." +
              "LengthBasedPasswordValidator",
         "ds-cfg-enabled: true",
         "ds-cfg-min-password-length: 6",
         "ds-cfg-max-password-length: 10");

    LengthBasedPasswordValidator validator = initializePasswordValidator(validatorEntry);

    StringBuilder buffer = new StringBuilder();
    for (int i=0; i < 20; i++)
    {
      buffer.append('x');
      ByteString password = ByteString.valueOfUtf8(buffer.toString());

      ModifyOperationBasis op = newModifyOperation(buffer);

      LocalizableMessageBuilder invalidReason = new LocalizableMessageBuilder();
      boolean actual = validator.passwordIsAcceptable(
          password, new HashSet<ByteString>(0), op, userEntry, invalidReason);
      assertEquals(buffer.length() >= 6 && buffer.length() <= 10, actual);
    }

    validator.finalizePasswordValidator();
  }
}
